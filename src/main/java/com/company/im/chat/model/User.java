package com.company.im.chat.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * User Info
 */
public class User {

    //必须赋初始值
    private StringProperty userNameProperty=new SimpleStringProperty("");

    private String password;

    private String sex;

    private int age;

    private StringProperty signatureProperty=new SimpleStringProperty("");


    public final StringProperty userNameProperty() {
        return userNameProperty;
    }

    public void setUserName(String userName) {
        this.userNameProperty.set(userName);
    }

    public String getUserName(){
        return userNameProperty.get();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSignature() {
        return signatureProperty.get();
    }

    public final StringProperty signatureProperty() {
        return signatureProperty;
    }

    public void setSignature(String signature) {
        this.signatureProperty.set(signature);
    }

    @Override
    public String toString() {
        return "userName:"+userNameProperty.get()+
                ",password:"+password+
                ",sex:"+sex+
                ",age:"+age+
                ",signature:"+signatureProperty.get();
    }
}
