package com.rafalopez.inmobiliaria.entity;

public class ResMsg {
    public static String msg;
    public static String body;

    public ResMsg() {
    }

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        ResMsg.msg = msg;
    }

    public static String getBody() {
        return body;
    }

    public static void setBody(String body) {
        ResMsg.body = body;
    }
}
