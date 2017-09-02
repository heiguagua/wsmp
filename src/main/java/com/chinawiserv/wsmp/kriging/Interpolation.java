package com.chinawiserv.wsmp.kriging;


import com.chinawiserv.wsmp.kriging.model.DataInfo;
import com.chinawiserv.wsmp.kriging.model.ParamModel;
import com.chinawiserv.wsmp.kriging.model.PointsRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

;

public class Interpolation {
	List<Double> m_pDataDistance = null;//???DataOut??DataIn?????????????
    List<DataInfo> DataOut = new ArrayList<DataInfo>();
    int DataOutNum; //???????????
    List<DataInfo> DataIn = new ArrayList<DataInfo>();
    int DataInNum; //??????????????
    PointsRange M_Range = new PointsRange(); //?????????????
    ParamModel ModelPara = new ParamModel(); //???????
    List<Double> Outgammah = new ArrayList<Double>(); //???????
   // double[] para = new double[4] { 0, 0, 0, 0 }; //?????????????????????????????????????????
    double sDataOutMax = 0;
    double sDataOutMin = 9999;//???????????DataOut??????????
    int Kmin = 5, Kmax = 10;//????DataOut??????????????[Kmin, Kmax]???????DataIn??
    int NumH = 37;//???h????????????????????? 0.95/0.025 -1 = 37
    double MaxLevel = 120, MinLevel = 30; //?????????
    int Glo = 3;//???????????????? 1????????--10????????? ????????????Kmin, Kmax???????????????

    /// <summary>
    ///Step1: ???????????????
    ///????DataOut??DataIn
    ///????????????DataOut????????????
    ///DataIn???????????????DataIn??????????????????M_Range??
    ///DataIn??DataOut??????????????List
    /// </summary>
    public void InitCal(List<DataInfo> InPutData, List<DataInfo> OutPutData)
    {
    	if(InPutData==null||InPutData.isEmpty()){
    		throw new NullPointerException("InPutData must not be null or Empty!");
    	}
    	if(OutPutData==null){
    		OutPutData = new ArrayList<DataInfo>();
    	}
    	
        //#region ???????????
        //??????????DataOut??DataIn???????????????
        this.DataIn = InPutData;
        this.DataInNum = InPutData.size();
        this.DataOut = OutPutData;
        this.DataOutNum = OutPutData.size();
        //#endregion
       // #region ?????DataIn
        //???????????????????????,
        double InMax = 0;
        double InMin = 9999;
        //?????????????????????????????50x50????????
        if (OutPutData.size() == 0)
        {
            GenGrid(InPutData, OutPutData, 50);
            this.DataOut = OutPutData;
            this.DataOutNum = OutPutData.size();
        }
        for(DataInfo item : DataIn)
        {
            if (InMax < item.getValue()) InMax = item.getValue();
            if (InMin > item.getValue()) InMin = item.getValue();
        }
        //#endregion
        //#region ???DataIn???????????
        GetRange();
       // #endregion
      //  #region ??????????????
        //??????????????????????????????????
        int DstNum = this.DataInNum * (this.DataInNum + this.DataOutNum);
        this.m_pDataDistance = new ArrayList<Double>(DstNum);
        this.m_pDataDistance.clear();
        for(int i=0;i<DstNum;i++){
        	 this.m_pDataDistance.add((double)0.0);
        }
    }
    
    /// <summary>
    /// Step2: ??????????????
    /// </summary>
    public void OkrigingCal()
    {
        InitOkriging();
        //????????
        if (DataIn == null || DataOut == null)
        {
            return;
        }
        //long sDataOutMax = -32767, sDataOutMin = 32767;
        int KCount = 0, TempI = 0, CurNum = 0;
        double Data = 0;
        int[] Array_DataInNum_int = new int[DataInNum + 1];
        double[] SelDst = new double[Array_DataInNum_int.length];
        int[] SelIndex = new int[Array_DataInNum_int.length];

        double[] Array_DataOutNum_do = new double[DataOutNum];
        double[]  KK = new  double[Array_DataOutNum_do.length] ;

        double[] Array_Kmax_do = new double[Kmax + 1];
        int[] Array_Kmax_int = new int[Kmax + 1];
        double[] lammda = new double[Array_Kmax_do.length] ;
        int[]  js =new int[Array_Kmax_int.length]; 
        int[]  iss = new int[Array_Kmax_int.length]  ; 

        DataInfo Temp = new DataInfo();
        //???????,???????DataOut???
        for (CurNum = 0; CurNum < DataOutNum; CurNum++)
        {
            //????????????????M2
            //1.1????????? 
            for (int i = 0; i < DataInNum; i++)
            {
                SelDst[i]= m_pDataDistance.get((CurNum + DataInNum) * DataInNum + i);
            }
            //1.2????????
            if (Kmin >= DataInNum)
            {
                KCount = DataInNum;
                for (int i = 0; i < KCount; i++)
                {
                    SelIndex[i]= i;
                }
            }
            else // Kmin < DataInNum
            {
                KCount = 0;
                //??????????0.3????????????
                for (int j = 0; j < DataInNum; j++)
                {
                    if (m_pDataDistance.get(CurNum * DataInNum + j)<= 0.2)
                    {
                        KCount++;
                    }
                    SelIndex[j]= j;
                }
                //???????????????????????????????
                //for (int k = 0; k < DataInNum - 2; k++)	//???????????
                for (int k = 0; k < DataInNum - 1; k++) //2016-4-12
                {
                    for (int l = k + 1; l < DataInNum - 1; l++)
                    {
                        if (SelDst[k] > SelDst[l])
                        {
                            double TempD = SelDst[k];
                            SelDst[k]= SelDst[l];
                            SelDst[l]= TempD;

                            TempI = SelIndex[k];
                            SelIndex[k]= SelIndex[l];
                            SelIndex[l]= TempI;
                        }
                    }
                }
                //?????????
                if (KCount > Kmax)
                {
                    KCount = Kmax;
                }
                else if (KCount < Kmin)
                {
                    KCount = Kmin;
                }
            }
            //1.3??????M2
            SemiVariogram(SelDst, 1, KCount);//?????????
            SelDst[KCount]=(double)1;// ?????????????????1
            //2??????????????KK
            //memset(KK, 0, (KCount + 1) * (KCount + 1));
            for (int i = 0; i < KCount; i++)
            {
                TempI = SelIndex[i];
                for (int j = 0; j < KCount; j++)
                {
                 //   KK[i * (KCount + 1) + j] = m_pDataDistance[TempI * DataInNum + SelIndex[j]];
                    KK[i * (KCount + 1) + j]= m_pDataDistance.get(TempI * DataInNum + SelIndex[j]);
                    
                    
                }
            }
            SemiVariogram(KK, KCount, KCount);
            for (int i = 0; i < KCount + 1; i++)
            {
              KK[KCount * (KCount + 1) + i] = (double)1;
             KK[i * (KCount + 1) + KCount] = (double)1;
            }
            KK[(KCount + 1) * (KCount + 1) - 1] = (double)0;
            //3?????????? 
            //3.1 KK ????SVD???
            Rinv(KK, KCount + 1, iss, js);
            //3.2????lammda(??????)
            for (int i = 0; i < KCount + 1; i++)
            {
                lammda[i] = (double)0;
                for (int j = 0; j < KCount + 1; j++)
                {
                   lammda[i] += KK[i * (KCount + 1) + j] * SelDst[j];
                }
            }
            //3.3?????????????
            Data = 0;
            //??????????
            for (int j = 0; j < KCount; j++)
            {
                Data += lammda[j]  * DataIn.get(SelIndex[j]).getValue();
            }
            //?????????????????
            if (Data > sDataOutMax)
            {
                sDataOutMax = (short)Data;
            }
            if (Data < sDataOutMin)
            {
                sDataOutMin = (short)Data;
            }
            Temp = new DataInfo(DataOut.get(CurNum).getLon(), DataOut.get(CurNum).getLat(), DataOut.get(CurNum).getHigh(), Data);
            DataOut.set(CurNum, Temp);
        }//???????
        sDataOutMax += 1;
        sDataOutMin -= 1;
        //return (DataOut);
    }
    
    /// <summary>
    /// Step3: ?????????????????????????????????????????????????????????????????????????[30,120]???
    /// </summary>
    public List<DataInfo> CopyResults()
    {
        //DataOut.AddRange(DataIn);//??????????
        //????????????
        List<DataInfo> tempDataOut = new ArrayList<DataInfo>();
        Collections.sort(DataIn, new Comparator<DataInfo>(){
			@Override
			public int compare(DataInfo o1, DataInfo o2) {
				return new Double(o1.getValue()).compareTo(new Double(o2.getValue()));
			}
        });
        double max=DataIn.get(DataIn.size()-1).getValue();
        double min=DataIn.get(0).getValue();
        MinLevel = min - (max - min) * 0.1;
        MaxLevel = max;
        tempDataOut = Normaliz(DataOut, MinLevel, MaxLevel);
        //tempDataOut.AddRange(DataIn);
        tempDataOut.addAll(DataIn);
        return (tempDataOut);
    }
    
    
    /// <summary>
    /// ?????????????????[Low,High]???
    /// </summary>
    public List<DataInfo> Normaliz(List<DataInfo>InPutData, double Low, double High)
    {
        //???????????
        if (InPutData.size() == 0) { return InPutData; }
        double maxvalue = InPutData.get(0).getValue();
        double minvalue = InPutData.get(0).getValue();
        //????????????????????
        for(DataInfo item: InPutData)
        {
            if (item.getValue() > maxvalue)
            {
                maxvalue = item.getValue();
            }
            else if (item.getValue() < minvalue)
            {
                minvalue = item.getValue();
            }
       }
        //???????????????????????????????????????????????
        if ((maxvalue == minvalue) || (Low >= High))
        {
            return InPutData;
        }
        double set_range = High - Low; //????????????
        double data_range = maxvalue - minvalue; //??????????????
        List<DataInfo> NormalizedData = new ArrayList<DataInfo>();
       //??????????????????????[Low,High]
        for(DataInfo item : InPutData)
        {
            NormalizedData.add(new DataInfo(item.getLon(), item.getLat(), item.getHigh(), set_range*((item.getValue()-minvalue)/data_range)+Low));
        }

       // #region xiechao
        List<DataInfo> cloneNormalizeData = new ArrayList<DataInfo>();
        for (DataInfo tmp : NormalizedData)
            cloneNormalizeData.add(new DataInfo(tmp.getLon(), tmp.getLat(), tmp.getHigh(), tmp.getValue()));
        NormalizedData.clear();
        for (int numb = 0; numb < cloneNormalizeData.size(); numb++)
        {
            boolean needAdd = false;
            DataInfo tmp = cloneNormalizeData.get(numb);
            for(DataInfo station: DataIn)
            {
                if (Math.pow((tmp.getLon() - station.getLon()), 2) + Math.pow((tmp.getLat() - station.getLat()), 2) <= Math.pow(0.000696 / 2, 2) + Math.pow(0.042038 / 2, 2))
                    needAdd = true;
            }
            if (needAdd)
                NormalizedData.add(new DataInfo(tmp.getLon(), tmp.getLat(), tmp.getHigh(), tmp.getValue()));
        }
        //#endregion

        return NormalizedData;
    }
    
    //????????DataIn???????
    private void GetRange()
    {
        //??????????????????
        double m_LongMax = 0, m_LongMin = this.DataIn.get(0).getLon();
        double m_LatMax = 0, m_LatMin = this.DataIn.get(0).getLat();
        double m_LongSum = 0, m_LatSum = 0;
        //??????????
        for (DataInfo item : this.DataIn)
        {
            //???????????
            m_LongMax = Math.max(item.getLon(), m_LongMax);
            m_LongMin = Math.min(item.getLon(), m_LongMin);
            m_LatMax = Math.max(item.getLat(), m_LatMax);
            m_LatMin = Math.min(item.getLat(), m_LatMin);
            //?????????? 2016-4-7
            m_LongSum += item.getLon();
            m_LatSum += item.getLat();
        }
        //????????????????????
        this.M_Range.m_LongAvg = m_LongSum / this.DataInNum;
        this.M_Range.m_LatAvg = m_LatSum / this.DataInNum;
        //???????????????????
        this.M_Range.m_LongRange = m_LongMax - m_LongMin;
        this.M_Range.m_LatRange = m_LatMax - m_LatMin;
    }
    
    /// <summary>
    /// ??????????????
    /// </summary>
    private void InitOkriging()
    {
        //?????????????????tmpDataIn=DataIn
        List<DataInfo> tmpDataIn = new ArrayList<DataInfo>();
        for(DataInfo item: this.DataIn)
        {
            tmpDataIn.add(item);
        }
        //????????????????
        List<DataInfo> newDataIn = new ArrayList<DataInfo>(DataInNum);
        int newDataInNum = 0;
        newDataInNum = DataInPrepare(tmpDataIn, newDataIn, newDataInNum);
        int newDataOutNum = 0;//????????????
        for (int i = 0; i < DataOutNum; i++)
        {
            if (DataOut.get(i).getValue() == 0)//??????????????????????????
            {
                newDataOutNum++;//???????????????????
            }
        }
        List<DataInfo> newDataOut = new ArrayList<DataInfo>(newDataOutNum);
        int Index = 0;
        for (int i = 0; i < DataOutNum; i++)
        {
            if (DataOut.get(i).getValue() == 0)
            {
                newDataOut.add(new DataInfo(DataOut.get(i).getLon(), DataOut.get(i).getLat(), DataOut.get(i).getHigh(), DataOut.get(i).getValue()));
                //????????????????????newDataOut List??
                Index++;//????????
            }
        }
        //???????????????????List
        DataOutPrepare(newDataOut, newDataOutNum);
        //?????????????
        InitDataDistance(newDataIn, newDataInNum, newDataOut, newDataOutNum);
        Outgammah = new ArrayList<Double>(NumH);
        Variogramproc(newDataIn, Outgammah, NumH);
        //?????????????
        SetDefaultVario();
        //?????????????????????????
        //ManunallySetVario();
        //SetVario();
    }
    
    //??????????
    private ParamModel SetDefaultVario()
    {
        //??????ModelPara?????
        double max = 0;
        for (int i = 0; i < NumH; i++)
        {
            if (Outgammah.get(i) > max)
            {
                max = Outgammah.get(i);
            }
        }
        ModelPara.setSill( 0.7 * max); //2016-3-28
        //ModelPara.Sill = max;
        int h = (int)((NumH) / 2);
        int indx = 0;
        max = 0;
        for (int i = 0; i < h; i++)
        {
            if (max < Outgammah.get(i))
            {
                max = Outgammah.get(i);
                indx = i;
            }
        }
        double min = 0.3;
        int indx1 = 1;
        double tmp;
        for (int i = 0; i <= indx; i++)
        {
            tmp = Math.abs((Outgammah.get(i) - 0.3));
            if (min > tmp)
            {
                min = tmp;
                indx1 = i;
            }
        }
        //#region xiechao
        ModelPara.setLscl(0.025 * (indx1 + 1));; //???
        //ModelPara.Lscl = 0.95; //0.025 * (indx1 + 1); //???
       // #endregion
        ModelPara.setHole(0);
        ModelPara.setNugt(0.0357); //???
        ModelPara.setPower( 0.63); //?????????????????????
        //ModelPara.Power = 1.5;
        //ModelPara.Power = 0.63;
        return (ModelPara);
    }
    
    //?????????
    private void VarioCompute(List<DataInfo> newDataIn, int OutNum, List<Double> num, List<Double> sum)
    {
        double m_step = 0.025;	//????
       // List<Integer> DataIndex = new ArrayList<Integer>(DataInNum);
        Integer[] DataIndex = new  Integer[DataInNum];
        //List<Integer> nDIs =  new ArrayList<Integer>(DataInNum);
        int[] nDIs = new  int[DataInNum];

        double[] numarray =new double[OutNum];
        double[] sumarray =new double[OutNum];
        
        double distance, dx, dy, Temp;
        int IndexNum;
        for (int i = 0; i < DataInNum - 1; i++)
        {
            //????????????? 
            IndexNum = 0;
            for (int j = i + 1; j < DataInNum; j++)
            {
                dx = newDataIn.get(j).getLon() - newDataIn.get(i).getLon();		//???????????????????
                dy = newDataIn.get(j).getLat() - newDataIn.get(i).getLat();
                if (dx <= 0 && dy >= 0)				//????????????????
                {
                    continue;
                }
                //nDIs[DataIndex] ????????????????????
                DataIndex[j]= IndexNum ; //??i ?? ??j ?????
                //???i?????????????????
                distance = Math.sqrt(dx * dx + dy * dy);
                //distance = m_pDataDistance[i * DataInNum + j];//2016-4-12
                nDIs[IndexNum] = (int)(distance / m_step + 0.5);
                //nDIs[IndexNum] = (int)(distance / m_step); //2016-4-12
                IndexNum++;
            }
            //?????????????????????????
            for (int k = 0; k < IndexNum; k++)
            {
                if (nDIs[k] < 1 || nDIs[k] > 37)
                {
                    continue;
                }
                
                numarray[nDIs[k] - 1] += 1;
                double after=newDataIn.get(i)==null?(double)0.0:newDataIn.get(i).getValue();
                double befor=DataIndex[k]==null?(double)0.0:(newDataIn.get(DataIndex[k])==null?(double)0.0:newDataIn.get(DataIndex[k]).getValue());
                Temp = (after -befor );
                sumarray[nDIs[k] - 1] += Temp * Temp;
                
//                num.set(nDIs[k]-1, num.get(nDIs[k]-1)+1);
//                Temp = (newDataIn.get(i).getValue() - newDataIn.get(DataIndex[k]).getValue());
//                sum.set(nDIs[k]-1, (sum.get(nDIs[k]-1)+(Temp * Temp)));
            }
        }
        num.clear();
        for(double num_v:numarray){
        	num.add(num_v);
        }
        sum.clear();
        for(double num_v:sumarray){
        	sum.add(num_v);
        }
        numarray=null;
        sumarray=null;
    }
    
    //??????????????
    private void Variogramproc(List<DataInfo> newDataIn, List<Double> Outgammah, int OutNum)
    {
        double m_C0;	//???????????????
        double averange, sumz;
        double maxvalue = 3;

        List<Double> num = new ArrayList<Double>(OutNum);//????????????--???
        List<Double> sum = new ArrayList<Double>(OutNum);//???????????????---???

        //???????????????
        VarioCompute(newDataIn, OutNum, num, sum);
        //????m_C0?????
        averange = 0;
        for (int i = 0; i < DataInNum; i++)
        {
            averange += newDataIn.get(i).getValue();
        }
        averange = averange / DataInNum;		//????????

        sumz = 0;
        for (int i = 0; i < DataInNum; i++)
        {
            sumz += ( newDataIn.get(i).getValue() - averange) * ( newDataIn.get(i).getValue() - averange);
        }
        m_C0 = sumz / (DataInNum - 1);	//??????????????????

        //??C0??num??sum??????????????????????????Outgammah??
        //Outgammah = 0.5* E[(z(x)-z(x)')^2] = C0 - C(h) 
        for (int i = 0; i < OutNum; i++)
        {
            if (num.get(i) != 0)
            {
                Outgammah.add(i, (0.5 * sum.get(i) / (m_C0 * num.get(i))));
            }
            else
            {
                Outgammah.add(i, (double)0);
            }
            if (Outgammah.get(i) > maxvalue)
            {
                Outgammah.add(i, (double)0);
            }
        }
        num.clear();
        sum.clear();
    }
    
    
    //????????????????
    private boolean InitDataDistance(List<DataInfo> newDataIn, int newDataInNum, List<DataInfo> newDataOut, int newDataOutNum)
    {
        if (newDataOut == null || newDataIn == null)
        {
            return false;
        }
        //???newDataIn??newDataOut?????
        //???newDataIn??newDataIn?????
        //???newDataOut??newDataIn?????
        //List<double> m_pDataDistance = new List<double>(newDataInNum*(newDataInNum + newDataOutNum));
        //List<double> pDstData = m_pDataDistance;
        int index = 0;
        for (int i = 0; i < newDataInNum; i++)
        {
            for (int j = 0; j < newDataInNum; j++)
            {
                double tmpV = newDataIn.get(i).getLon() - newDataIn.get(j).getLon();
                double tmpV2 = newDataIn.get(i).getLat() - newDataIn.get(j).getLat();
                double temp = Math.sqrt(tmpV * tmpV + tmpV2 * tmpV2);
                //pDstData[(i + 1) * j] = temp; 
                m_pDataDistance.add(index, temp);//????????????????????newDataInNum*newDataInNum????????
                //m_pDataDistance[index] = temp;
                index++;//??????List????????????
            }
        }
        for (int i = 0; i < newDataOutNum; i++)
        {
            for (int j = 0; j < newDataInNum; j++)
            {
                double tmpV = newDataOut.get(i).getLon() - newDataIn.get(j).getLon();
                double tmpV2 = newDataOut.get(i).getLat() - newDataIn.get(j).getLat();
                double temp = Math.sqrt(tmpV * tmpV + tmpV2 * tmpV2);
                //pDstData[(i + 1) * j+index] = temp; //??????????????
                m_pDataDistance.add(index, temp);
                index++;
                //?????newDataIn????????newDataOut?????????
            }

        }
        return true;

    }
    
    //??????????
    private boolean DataOutPrepare(List<DataInfo> newDataOut, int newDataOutNum)
    {
        //?????????
        double tmpPI180 = Math.PI / 180;	//????????????????
        for (int i = 0; i < newDataOutNum; i++)
        {
            double TempLon = (newDataOut.get(i).getLon() - M_Range.m_LongAvg) * Math.cos(tmpPI180 * newDataOut.get(i).getLat()) / M_Range.m_LongRange;
            double TempLat = (newDataOut.get(i).getLat() - M_Range.m_LatAvg) / M_Range.m_LatRange;
            DataInfo Temp = new DataInfo(TempLon, TempLat, 0, 0);
            newDataOut.set(i, Temp) ;
            //newDataOut.Add(new DataInfo(TempLon, TempLat, 0, 0));
        }
        return true;
    }
    
    //????????????
    private int DataInPrepare(List<DataInfo> tmpDataIn, List<DataInfo> newDataIn, int newDataInNum)
    {
        //1?????????
        newDataInNum = 0;
        double High = 0;//?????????0
        int DistanceNum = DataInNum * (DataInNum - 1) / 2;		//????????????????
        List<Double> DistanceArray = new ArrayList<Double>(DistanceNum); //?????????
        List<Integer> DistanceIndex = new ArrayList<Integer>(DistanceNum);	//????????
        double avgDistance = 0;//????????
        int Num = 0;
        for (int i = 0; i < DataInNum; i++)
        {
            for (int j = i + 1; j < DataInNum; j++)
            {
                double tmpV = tmpDataIn.get(i).getLon() - tmpDataIn.get(j).getLon();
                double tmpV2 = tmpDataIn.get(i).getLat() - tmpDataIn.get(j).getLat();
                double tmp = Math.sqrt(tmpV * tmpV + tmpV2 * tmpV2);
                DistanceArray.add(tmp);
                avgDistance += DistanceArray.get(Num);
                DistanceIndex.add(i);
                Num++;
            }

        }
        avgDistance /= DistanceNum;							//?????????????????
        double rThreshold = 0.01 * avgDistance;				//?????????????
        List<Integer> IndexDelete = new ArrayList<Integer>(DistanceNum);  //????????????
        int DeletNum = 0;
        for (int i = 0; i < DataInNum; i++)
        {
            boolean flag = false;								//????????
            for (int j = 0; j < DeletNum; j++)
            {
                if (i == IndexDelete.get(j))
                {
                    flag = true;
                    break;
                }
            }
            if (!flag)										//??????????
            {
                newDataIn.add(new DataInfo(tmpDataIn.get(i).getLon(), tmpDataIn.get(i).getLat(), High, tmpDataIn.get(i).getValue()));
                newDataInNum++;
            }
        }
        //???????????
        if (newDataInNum == 0)
        {
            DistanceArray.clear();
            DistanceIndex.clear();
            IndexDelete.clear();
        }
        //2???????????
        double tmpPI180 = Math.PI / 180;						//????????????????
        for (int i = 0; i < newDataInNum; i++)
        {
            double TempLon = (newDataIn.get(i).getLon() - M_Range.m_LongAvg) * Math.cos(tmpPI180 * newDataIn.get(i).getLat()) / M_Range.m_LongRange;
            double TempLat = (newDataIn.get(i).getLat() - M_Range.m_LatAvg) / M_Range.m_LatRange;
            newDataIn.set(i, new DataInfo(TempLon, TempLat, High, tmpDataIn.get(i).getValue()));
        }
        //3???????????
        DistanceArray.clear();
        DistanceIndex.clear();
        IndexDelete.clear();
        int output = newDataInNum;
        return output;
    }
    
    
    /// <summary>
    /// ???????????????????DataOut??????????????????????????????????????
    /// </summary>
    public List<DataInfo> GenGrid(List<DataInfo> InPutData, List<DataInfo> OutPutData, int GridNum)
    {
        //?????????????????????????????????
        int GridMergin = 4;
        //??????????????????
        double m_LongMax = 0, m_LongMin = InPutData.get(0).getLon();
        double m_LatMax = 0, m_LatMin = InPutData.get(0).getLat();
        double m_LongSum = 0, m_LatSum = 0;
        double m_LongAvg = 0, m_LatAvg = 0;
        double m_LongRange = 0, m_LatRange = 0;
        int DataInNum = InPutData.size();
        //??????????
        for (DataInfo item : InPutData)
        {
            //???????????
            m_LongMax = Math.max(item.getLon(), m_LongMax);
            m_LongMin = Math.min(item.getLon(), m_LongMin);
            m_LatMax = Math.max(item.getLat(), m_LatMax);
            m_LatMin = Math.min(item.getLat(), m_LatMin);
            //?????????? 2016-4-7
            m_LongSum += item.getLon();
            m_LatSum += item.getLat();
        }    

        //#region xiechao
        double xMax, xMin, yMax, yMin;
        xMax = xMin = yMax = yMin = 0.0;
        xMax = (m_LongMax - m_LongMin) * 0.2 < 0.03 ? m_LongMax + 0.03 : m_LongMax + (m_LongMax - m_LongMin) * 0.2;
        xMin = (m_LongMax - m_LongMin) * 0.2 < 0.03 ? m_LongMin - 0.03 : m_LongMin - (m_LongMax - m_LongMin) * 0.2;
        yMax = (m_LatMax - m_LatMin) * 0.2 < 0.03 ? m_LatMax + 0.03 : m_LatMax + (m_LatMax - m_LatMin) * 0.2;
        yMin = (m_LatMax - m_LatMin) * 0.2 < 0.03 ? m_LatMin - 0.03 : m_LatMin - (m_LatMax - m_LatMin) * 0.2;
        m_LongMax = xMax;
        m_LongMin = xMin;
        m_LatMax = yMax;
        m_LatMin = yMin;
        GridNum = 100;
        //#endregion

        //????????????????????
        m_LongAvg = m_LongSum / DataInNum;
        m_LatAvg = m_LatSum / DataInNum;
        //???????????????????
        m_LongRange = m_LongMax - m_LongMin;
        m_LatRange = m_LatMax - m_LatMin;
        //???????????
        //????????????????????????????
        //?DataOut???????
        double LonStep = m_LongRange / GridNum;
        double LatStep = m_LatRange / GridNum;
        double m_Step;
        //?????????????????
        if (LonStep < LatStep)
        {
            m_Step = LatStep;
        }
        else
        {
            m_Step = LonStep;
        }
        double Lon = 0; double Lat = 0; double High = 0; double Value = 0;
        int DataOutNum = 0;
        //?DataOut???????
        for (int i = 0; (i - GridMergin) * m_Step < (m_LongRange); i++)
        {
            for (int j = 0; (j - GridMergin) * m_Step < (m_LatRange); j++)
            {
                Lon = m_LongMin + m_Step * (i - GridMergin);
                Lat = m_LatMin + m_Step * (j - GridMergin);
                OutPutData.add(new DataInfo(Lon, Lat, High, Value));
                DataOutNum++;
            }
        }
        return OutPutData;
    }
    
    //????????????????????????????
    private void SemiVariogram(double[] MatrixIn, int rowCount, int colCount)
    {
        //double C1 = 0;
        double C1 = ModelPara.getSill() - ModelPara.getNugt();
        double t = 0;
        if (ModelPara.getLscl() == 0)
        {
            return;
        }
        if (rowCount != 1)
        {
            colCount += 1;
        }
        // y = Sill*(1-exp^(-h/Lscl))
        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < colCount; j++)
            {
                t = -1*MatrixIn[i * colCount + j]/ ModelPara.getLscl();
                //t = -1 * Math.Pow(t, ModelPara.Power);
                t = Math.pow(Math.E, t);
                MatrixIn[i * colCount + j]=C1 * (1 - t) + ModelPara.getNugt() ;//????????????
            }
        }
    }
    
    //????????????
    private void Rinv(double[] a, int n, int[] iss,  int[] js)
    {
        int i, j, k, l, u, v;
        double d, p;
        for (k = 0; k <= (n - 1); k++)
        {
            d = 0.0;
            for (i = k; i <= n - 1; i++)
                for (j = k; j <= n - 1; j++)
                {
                    l = i * n + j; p = Math.abs(a[l]);
                    if (p > d) { d = p; iss[k]= i; js[k]= j; }
                }
            if (d + 1.0 == 1.0)
            {

                return;
            }
            if (iss[k] != k)
                for (j = 0; j <= n - 1; j++)
                {
                    u = k * n + j; v = iss[k] * n + j;
                    p = a[u];
                    a[u]=a[v];
                    a[v]=p;
                }
            if (js[k] != k)
                for (i = 0; i <= n - 1; i++)
                {
                    u = i * n + k; v = i * n + js[k];
                    p = a[u]; a[u] = a[v]; a[v] = p;
                }
            
            l = k * n + k;
            a[l] = 1.0 / a[l];
            for (j = 0; j <= n - 1; j++)
                if (j != k)
                { u = k * n + j; a[u] = a[u] * a[l]; }
            for (i = 0; i <= n - 1; i++)
                if (i != k)
                    for (j = 0; j <= n - 1; j++)
                        if (j != k)
                        {
                            u = i * n + j;
                            a[u] = a[u] - a[i * n + k] * a[k * n + j];
                           // a[u] = a[u] - a[i * n + k] * a[k * n + j];
                        }
            for (i = 0; i <= n - 1; i++)
                if (i != k)
                { u = i * n + k; a[u] = -a[u] * a[l]; }
        }
        for (k = n - 1; k >= 0; k--)
        {
            if (js[k] != k)
                for (j = 0; j <= n - 1; j++)
                {
                    u = k * n + j; v = js[k] * n + j;
                    p = a[u]; a[u] = a[v]; a[v] = p;
                }
            if (iss[k] != k)
                for (i = 0; i <= n - 1; i++)
                {
                    u = i * n + k; v = i * n + iss[k];
                    p = a[u]; a[u] = a[v]; a[v] = p;
                }
        }
    }
    

}
