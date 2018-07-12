package com.example.polestaruser.apiconnect;

import java.io.Serializable;

public class user implements Serializable {

    String name;
    String number;
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
