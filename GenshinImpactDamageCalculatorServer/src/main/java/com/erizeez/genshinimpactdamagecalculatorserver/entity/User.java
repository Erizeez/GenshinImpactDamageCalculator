package com.erizeez.genshinimpactdamagecalculatorserver.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

public class User {
    private Integer uID;
    private String userName;
    private String passWord;
    private String nickName;
    private Timestamp loginTime;

    public Integer getuID() {
        return uID;
    }

    public void setuID(Integer uID) {
        this.uID = uID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "UID: " + this.uID +
                ", UserName: " + this.userName +
                ", PassWord: " + this.passWord +
                ", NickName: " + this.nickName +
                ", LoginTime: " + this.loginTime.toString() +
                ";";
    }
}
