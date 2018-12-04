package com.zyh.wanandroid.ui;

/**
 * author : zyh
 * Date : 2018/12/4
 * Description :
 */
public class MsgEvent {
    Object o;
    private MsgEvent(Object o){
        this.o = o;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
