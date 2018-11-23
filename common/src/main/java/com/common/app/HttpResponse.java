package com.common.app;

public class HttpResponse<T> {
    public T data;//数据
    public T result;//数据
    public String message;//信息
    public int code;//服务器状态

    //只有全区排行 json 有这个 字段
    public T rank;//数据

}