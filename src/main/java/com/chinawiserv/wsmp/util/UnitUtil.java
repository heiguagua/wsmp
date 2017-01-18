package com.chinawiserv.wsmp.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UnitUtil {

    public static final BigDecimal BLOCK_SIZE = new BigDecimal(1024);
    public static final String BYTE = "Byte";
    public static final String KB = "KB";
    public static final String MB = "MB";
    public static final String GB = "GB";
    public static final String TB = "TB";
    public static final String PB = "PB";
    public static final String EB = "EB";
    public static final String ZB = "ZB";
    public static final String YB = "YB";
    public static final String DB = "DB";
    public static final String NB = "NB";
    /**
     * 计量单位列表
     */
    private static List<String> unitList;

    static {
        if (unitList == null) {
            unitList = new ArrayList<>();
            unitList.add(BYTE);
            unitList.add(KB);
            unitList.add(MB);
            unitList.add(GB);
            unitList.add(TB);
            unitList.add(PB);
            unitList.add(EB);
            unitList.add(ZB);
            unitList.add(YB);
            unitList.add(DB);
            unitList.add(NB);
        }
    }

    /**
     * 将容量大小 改为字符串形式
     *
     * @param sizeLong
     * @param unit
     * @return
     */
    public static String convertSize(long sizeLong, String unit) {
        BigDecimal size = new BigDecimal(sizeLong);
        String result;
        String negativeStr = "";
        int index = 0;
        if(size.compareTo(new BigDecimal(0)) == -1){
            size = size.abs();
            negativeStr = "-";
        }
        while (size.compareTo(BLOCK_SIZE) == 1) {
            size = size.divide(BLOCK_SIZE);
            index++;
        }
        int unitStart = 0;
        for (int i = 0; i < unitList.size(); i++) {
            if (unitList.get(i).equals(unit)) {
                unitStart = i;
                break;
            }
        }
        if ((unitStart + index) <= unitList.size()) {
            String newUnit = unitList.get(unitStart + index);
            result = negativeStr + size.setScale(2, BigDecimal.ROUND_HALF_UP) + newUnit;
        } else {
            result = negativeStr + size.setScale(2, BigDecimal.ROUND_HALF_UP) + unit;
        }
        return result;
    }

    public static String convertSize(long sizeLong) {
        return convertSize(sizeLong, BYTE);
    }

    public static String convertSize(long sizeLong, String unit, String newUnit){
        BigDecimal size = new BigDecimal(sizeLong);
        if(unitList.contains(unit) && unitList.contains(newUnit)) {
            String negativeStr = "";
            if (size.compareTo(new BigDecimal(0)) == -1) {
                size = size.abs();
                negativeStr = "-";
            }
            int unitStart = 0;
            int unitEnd = 0;
            boolean isSetUnitStart = false;
            boolean isSetUnitEnd = false;
            for (int i = 0; i < unitList.size(); i++) {
                if (!isSetUnitStart && unitList.get(i).equals(unit)) {
                    unitStart = i;
                    isSetUnitStart = true;
                } else if (!isSetUnitEnd && unitList.get(i).equals(newUnit)) {
                    unitEnd = i;
                    isSetUnitEnd = true;
                }
                if (isSetUnitStart && isSetUnitEnd) {
                    break;
                }
            }
            if (isSetUnitStart && isSetUnitEnd) {
                int unitStep = unitEnd - unitStart;
                boolean negative = unitStep < 0;
                if (negative) {
                    unitStep = Math.abs(unitStep);
                }
                while (unitStep > 0) {
                    size = negative ? size.multiply(BLOCK_SIZE) : size.divide(BLOCK_SIZE);
                    unitStep--;
                }
                return negativeStr + size.setScale(2, BigDecimal.ROUND_HALF_UP) + newUnit;
            }
        }
        return "";
    }

    /**
     * 将字符串描述的大小改为真实的byte大小
     *
     * @param sizeDesc
     * @return
     */
    public static BigDecimal convertStrToSize(String sizeDesc) {
        BigDecimal result = new BigDecimal(0);
        if (sizeDesc != null && sizeDesc.endsWith("B") && sizeDesc.length() > 2) {
            String unit = sizeDesc.substring(sizeDesc.length() - 2).toUpperCase();
            if (!unitList.contains(unit)) {
                //如果unitList不包含sizeDesc的最后两位，则认为此数据单位为最小的Byte
                result = new BigDecimal(sizeDesc.substring(0, sizeDesc.length() - 1));
            } else {
                int index = unitList.indexOf(unit);
                BigDecimal number = new BigDecimal(sizeDesc.substring(0, sizeDesc.length() - 2));
                for (int i = 0; i < index; i++) {
                    number = number.multiply(BLOCK_SIZE);
                }
                result = number;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(convertSize(12132464, BYTE, GB));
        System.out.println(convertStrToSize("2.71KB"));
    }
}
