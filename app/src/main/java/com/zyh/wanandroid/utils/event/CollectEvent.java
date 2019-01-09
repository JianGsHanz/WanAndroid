package com.zyh.wanandroid.utils.event;

/**
 * Time:2018/12/26
 * Author:ZYH
 * Description:
 */
public class CollectEvent {
    private int id;
    private int orginId;

    public CollectEvent(int id, int orginId) {
        this.id = id;
        this.orginId = orginId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrginId() {
        return orginId;
    }

    public void setOrginId(int orginId) {
        this.orginId = orginId;
    }
}
