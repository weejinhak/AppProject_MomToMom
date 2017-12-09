package com.mom.momtomom.DTO;

import java.io.Serializable;

/**
 * Created by wee on 2017. 11. 21..
 */

public class CheckListDto implements Serializable {

    private boolean question1;
    private boolean question2;
    private boolean question3;
    private boolean question4;
    private boolean question5;
    private boolean question6;
    private boolean question7;
    private boolean question8;
    private boolean question9;

    public CheckListDto() {
    }

    public CheckListDto(boolean question1, boolean question2, boolean question3,
                        boolean question4, boolean question5, boolean question6,
                        boolean question7, boolean question8, boolean question9) {
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.question7 = question7;
        this.question8 = question8;
        this.question9 = question9;
    }

    public boolean isQuestion1() {
        return question1;
    }

    public void setQuestion1(boolean question1) {
        this.question1 = question1;
    }

    public boolean isQuestion2() {
        return question2;
    }

    public void setQuestion2(boolean question2) {
        this.question2 = question2;
    }

    public boolean isQuestion3() {
        return question3;
    }

    public void setQuestion3(boolean question3) {
        this.question3 = question3;
    }

    public boolean isQuestion4() {
        return question4;
    }

    public void setQuestion4(boolean question4) {
        this.question4 = question4;
    }

    public boolean isQuestion5() {
        return question5;
    }

    public void setQuestion5(boolean question5) {
        this.question5 = question5;
    }

    public boolean isQuestion6() {
        return question6;
    }

    public void setQuestion6(boolean question6) {
        this.question6 = question6;
    }

    public boolean isQuestion7() {
        return question7;
    }

    public void setQuestion7(boolean question7) {
        this.question7 = question7;
    }

    public boolean isQuestion8() {
        return question8;
    }

    public void setQuestion8(boolean question8) {
        this.question8 = question8;
    }

    public boolean isQuestion9() {
        return question9;
    }

    public void setQuestion9(boolean question9) {
        this.question9 = question9;
    }

    @Override
    public String toString() {
        return "CheckListDto{" +
                "question1=" + question1 +
                ", question2=" + question2 +
                ", question3=" + question3 +
                ", question4=" + question4 +
                ", question5=" + question5 +
                ", question6=" + question6 +
                ", question7=" + question7 +
                ", question8=" + question8 +
                ", question9=" + question9 +
                '}';
    }
}
