package com.chinawiserv.wsmp.IDW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class IDWMain {
	 /*局部变量*/
	private double beta=1;   //beta值，一般设为1或2


	
	/**
	 * @param in 输入的坐标点 含有值
	 * @param out 输出的坐标点,必须提供坐标点
	 */
	public void getRes(List<IDWPoint> in , List<IDWPoint> out){
		if(in==null||in.size()<1||out==null||out.size()<1){
			return;
		}

		in.addAll(setRange(out));
		doIDW(in, out);
	     Collections.sort(out, new Comparator<IDWPoint>(){
					@Override
					public int compare(IDWPoint o1, IDWPoint o2) {
						return new Double(o1.getZ()).compareTo(new Double(o2.getZ()));
					}
		        });

	     out.addAll(in);

	}
	
	
	  public void doIDW(List<IDWPoint>in,List<IDWPoint> out){
		  if(in==null||in.size()<1||out==null||out.size()<1){
			  return;
		  }
		  for(IDWPoint outP:out ){
			  getDistance(outP,in);
			  getWeight(in);
			  getTargetZ(outP,in);
		  }
		 
	  }
	    //计算距离，每一个离散点至目标点的平面距离
     private  void getDistance(IDWPoint p,List<IDWPoint> ps)
      {
          for (IDWPoint temp:ps)
          {
        	 double d=Math.sqrt(Math.pow((p.getMercatorX() - temp.getMercatorX()), 2) + Math.pow((p.getMercatorY() - temp.getMercatorY()), 2));
        	 temp.setD(d);
          }
      }
     
     //获取分母，计算中会用到
     private double getFenmu(List<IDWPoint> ps)
     {
         double fenmu = 0;
         
         for (IDWPoint temp:ps)
         {
        	 fenmu += Math.pow((1.0/temp.getD()),this.beta);
         }
         return fenmu;
     }
     
     //计算权重
     private void getWeight(List<IDWPoint> ps)
     {
         //权重是距离的倒数的函数
         double fenmu = getFenmu(ps);
         for (IDWPoint temp:ps)
         {
        	 temp.setW(Math.pow((1 / temp.getD()),this.beta) / fenmu);
         }
     }
     //得到最终高程值
     private void getTargetZ(IDWPoint outP,List<IDWPoint> ps)
     {
    	 for (IDWPoint temp:ps)
         {
    		 outP.setZ(outP.getZ()+(temp.getZ()*temp.getW()));
    		 temp.setW(0);
    		 temp.setD(0);
         }
     }
     
//   //经纬度转墨卡托
//     private void lonLat2Mercator(IDWPoint p)
//     {
//         double x = p.getX() * 20037508.34 / 180;
//         double y = Math.log(Math.tan((90 + p.getY()) * Math.PI / 360)) / (Math.PI / 180);
//         y = y * 20037508.34 / 180;
//         p.setMercatorX(x);
//         p.setMercatorY(y);
//     }
//     
//     //墨卡托转经纬度
//     private void Mercator2lonLat(IDWPoint p)
//     {
//         double x = p.getMercatorX() / 20037508.34 * 180;
//         double y = p.getMercatorY() / 20037508.34 * 180;
//         y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
//         p.setX(x);
//         p.setY(y);
//     }
     
     private static double EARTH_RADIUS = 6371.0;//km 地球半径 平均值，千米
 	private  double distancePoint(IDWPoint A,IDWPoint B ){
	       //用haversine公式计算球面两点间的距离。
     //经纬度转换成弧度
		double lat1 = ConvertDegreesToRadians(A.getX());
		double lon1 = ConvertDegreesToRadians(A.getY());
		double lat2 = ConvertDegreesToRadians(B.getX());
		double lon2 = ConvertDegreesToRadians(B.getY());
     //差值
		double vLon = Math.abs(lon1 - lon2);
		double vLat = Math.abs(lat1 - lat2);

     //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
		double h = HaverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * HaverSin(vLon);

		double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
		
		return distance;
	}
	
	
	  /// <summary>
    /// 将角度换算为弧度。
    /// </summary>
    /// <param name="degrees">角度</param>
    /// <returns>弧度</returns>
	private  double ConvertDegreesToRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }
	
	private  double HaverSin(double theta)
  {
      double v = Math.sin(theta / 2);
      return v * v;
  }
	
	private List<IDWPoint>  setRange(List<IDWPoint> DataIn){
        //初始化经纬度范围计算量
        double m_LongMax = 0, m_LongMin = DataIn.get(0).getX();
        double m_LatMax = 0, m_LatMin = DataIn.get(0).getY();
        //获得经纬度的范围
        for (IDWPoint item : DataIn)
        {
            //返回经纬度的极值
            m_LongMax = Math.max(item.getX(), m_LongMax);
            m_LongMin = Math.min(item.getX(), m_LongMin);
            m_LatMax = Math.max(item.getY(), m_LatMax);
            m_LatMin = Math.min(item.getY(), m_LatMin);
        }
        List<IDWPoint> newSta=new ArrayList<IDWPoint>(4);
        newSta.add(new IDWPoint(m_LongMax,m_LatMax,0));
        newSta.add(new IDWPoint(m_LongMax,m_LatMin,0));
        newSta.add(new IDWPoint(m_LongMin,m_LatMax,0));
        newSta.add(new IDWPoint(m_LongMin,m_LatMin,0));
        return newSta;
	}
}
