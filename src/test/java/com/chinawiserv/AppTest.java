package com.chinawiserv;

import com.dgbc.caculate.CUnusualLevel;

public class AppTest {

    public static void main(String[] args) {
        System.out.println("test---------");
        int count = 143201;
        short[] Levels = new short[count];
        CUnusualLevel level = new CUnusualLevel(count);

        int i;
        for(i = 0; i < count; ++i) {
            Levels[i] = (short)((int)(Math.random() * 100.0D));
            if(i == 2690) {
                Levels[i] = 120;
            }
        }

        for(i = 0; i < 11; ++i) {
            level.CalcUnusualLevel(Levels, (short[])null, count);
        }

        System.out.println(level.Res[2690]);
    }

    public static void print(char[] chas) {
        for(int i = 0; i < chas.length; ++i) {
            System.out.println(i + "--->" + chas[i]);
        }

    }
}
