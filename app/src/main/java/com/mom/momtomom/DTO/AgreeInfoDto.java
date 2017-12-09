package com.mom.momtomom.DTO;

import java.io.Serializable;

/**
 * Created by wee on 2017. 12. 9..
 */

public class AgreeInfoDto implements Serializable {

    boolean agree1;
    boolean agree2;
    boolean agree3;

    public AgreeInfoDto() {
    }

    public AgreeInfoDto(boolean agree1, boolean agree2, boolean agree3) {
        this.agree1 = agree1;
        this.agree2 = agree2;
        this.agree3 = agree3;
    }

    public boolean isAgree1() {
        return agree1;
    }

    public void setAgree1(boolean agree1) {
        this.agree1 = agree1;
    }

    public boolean isAgree2() {
        return agree2;
    }

    public void setAgree2(boolean agree2) {
        this.agree2 = agree2;
    }

    public boolean isAgree3() {
        return agree3;
    }

    public void setAgree3(boolean agree3) {
        this.agree3 = agree3;
    }

    @Override
    public String toString() {
        return "AgreeInfoDto{" +
                "agree1=" + agree1 +
                ", agree2=" + agree2 +
                ", agree3=" + agree3 +
                '}';
    }
}
