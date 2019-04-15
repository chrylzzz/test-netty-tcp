package com.sdsoon.test.netty.bean;

import java.io.Serializable;

/**
 * 发送和接口的数据包装
 * <p>
 * Created By Chr on 2019/4/12/0012.
 */
public class RpcRequest implements Serializable {


    private static final long serialVersionUID = -3789942167907477118L;
    private String data;

    public RpcRequest() {
    }

    public RpcRequest(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
