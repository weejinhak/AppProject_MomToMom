package com.mom.momtomom;

import java.io.Serializable;

/**
 * Created by wee on 2017. 11. 5..
 */

public class UserInfoDto implements Serializable {

    public String email;
    public String name;
    public String nickName;
    public String age;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
