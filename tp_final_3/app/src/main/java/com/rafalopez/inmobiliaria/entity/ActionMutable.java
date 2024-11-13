package com.rafalopez.inmobiliaria.entity;

public class ActionMutable {
    private boolean visible;
    private String action;


    public ActionMutable() {

    }

    public ActionMutable(boolean visible, String action) {
        this.visible = visible;
        this.action = action;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
