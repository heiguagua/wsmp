package com.chinawiserv.wsmp;

public class UnusualLevel {

    private char[] temp;
    public char[] Res;
    public int index = 0;

    public UnusualLevel(int count) {
        this.temp = new char[count];
        this.Res = new char[count];
    }

    public int calcUnusualLevel(short[] Levels, short[] Variance, int Count) {
        ++this.index;
        try {
            if(this.index == 1) {
                for(int e = 0; e < Count; ++e) {
                    this.temp[e] = 48;
                    if(Levels[e] > 120 || Levels[e] < 0) {
                        return -2;
                    }
                    if(e > 2678 && e < 3520 && Levels[e] > 100) {
                        this.temp[e] = 49;
                    }
                }
            }

            if(this.index == 11) {
                this.Res = this.temp;
            }
            return 1;
        } catch (Exception var5) {
            return 0;
        }
    }

    public int free() {
        try {
            this.temp = null;
            this.Res = null;
            this.index = 0;
            return 1;
        } catch (Exception var2) {
            return 0;
        }
    }
}
