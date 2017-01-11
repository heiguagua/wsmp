package com.chinawiserv.wsmp.unusual;

public class UnusualLevel {

    private byte[] temp;
    public byte[] Res;
    public int index = 0;

    public UnusualLevel(int count) {
        this.temp = new byte[count];
        this.Res = new byte[count];
    }

    public UnusualLevel CreateObj() {
        return this;
    }

    public int CalcUnusualLevel(short[] Levels, short[] Variance, int Count) {
        ++this.index;

        try {
            if(this.index == 1) {
                for(int e = 0; e < Count; ++e) {
                    this.temp[e] = 0;
                    if(Levels[e] > 120 || Levels[e] < 0) {
                        return -2;
                    }

                    if(e > 2678 && e < 3520 && Levels[e] > 90) {
                        this.temp[e] = 1;
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

    public int FreeOccObj() {
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
