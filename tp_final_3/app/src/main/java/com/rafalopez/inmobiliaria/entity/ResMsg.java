package com.rafalopez.inmobiliaria.entity;
public class ResMsg {
    private String msg;
    private String body;

    // Getters y Setters
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ResMsg{" +
                "msg='" + msg + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
