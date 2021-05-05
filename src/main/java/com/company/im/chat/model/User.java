package com.company.im.chat.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * User Info
 */
public class User {

    //必须赋初始值
    private StringProperty userNameProperty=new SimpleStringProperty("");

    private StringProperty passwordProperty=new SimpleStringProperty("");

    private String sex;

    private int age;

    private StringProperty signatureProperty=new SimpleStringProperty("");

    public User(String userName, String password, String sex, int age, String signature) {
        this.userNameProperty = new SimpleStringProperty(userName);
        this.passwordProperty = new SimpleStringProperty(password);
        this.sex = sex;
        this.age = age;
        this.signatureProperty=new SimpleStringProperty(signature);
    }

    public User(){

    }

    public static User createUser(String userName, String password, String sex, int age, String signature){
        return new User(userName,password,sex,age,signature);
    }

    public final StringProperty getUserNameProperty(){
        return userNameProperty;
    }

    public final StringProperty getPasswordProperty(){
        return passwordProperty;
    }

    public String getUserName(){
        return userNameProperty.get();
    }

    public String getPassword(){
        return  passwordProperty.get();
    }

    public void setUserNameProperty(String userName){
        this.userNameProperty=new SimpleStringProperty(userName);
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

    public String getSignature(){
        return this.signatureProperty.get();
    }

    @Override
    public String toString() {
        return "userName:"+userNameProperty.get()+
                ",password:"+passwordProperty.get()+
                ",sex:"+sex+
                ",age:"+age+
                ",signature:"+signatureProperty.get();
    }
}
