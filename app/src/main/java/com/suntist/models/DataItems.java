package com.suntist.models;

/**
 * Created by Dell on 3/17/2018.
 */

public class DataItems {


    private String name;
    private String tel;
    private String sex;


    public DataItems(){

    }

    public DataItems(String name, String tel, String sex){

        this.name = name;
        this.tel = tel;
        this.sex = sex;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
