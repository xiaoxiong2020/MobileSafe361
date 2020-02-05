package com.xiao.mobilesafe361.bean;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/5 21:38
 */

public class BlackNumberInfo {

    public String blacknumber;
    public int mode;

    public BlackNumberInfo(String blacknumber, int mode) {
        super();
        this.blacknumber = blacknumber;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BlackNumberInfo [blacknumber=" + blacknumber + ", mode=" + mode
                + "]";
    }

}
