package com.tajo.memfoyy;

public class Ani {

    private String bas, hikaye, key;

    public Ani() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Ani(String bas, String hikaye, String key) {
        this.bas = bas;
        this.hikaye = hikaye;
        this.key = key;
    }

    public String getBas() {
        return bas;
    }

    public void setBas(String bas) {
        this.bas = bas;
    }

    public String getHikaye(){
        return hikaye;
    }

    public void  setHikaye(String hikaye){
        this.hikaye = hikaye;
    }
}
