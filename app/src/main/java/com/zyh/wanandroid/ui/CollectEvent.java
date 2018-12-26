package com.zyh.wanandroid.ui;

/**
 * Time:2018/12/26
 * Author:ZYH
 * Description:
 */
public class CollectEvent {
    private int id;
    private boolean collect;

    public CollectEvent(int id, boolean collect) {
        this.id = id;
        this.collect = collect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
