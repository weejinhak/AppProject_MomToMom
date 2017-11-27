package com.mom.momtomom.DTO;

import java.io.Serializable;

/**
 * Created by wee on 2017. 11. 27..
 */

public class DonorInfoDto implements Serializable {

    private String donorName;
    private String donorAdress;
    private String donorDeliveryDate;
    private String donorEmail;
    private int donorPhoneNumber;
    private CheckListDto checkListDto;
    private String donorUid;

    public DonorInfoDto() {
    }

    public DonorInfoDto(String donorName, String donorAdress, String donorDeliveryDate,
                        String donorEmail, int donorPhoneNumber, CheckListDto checkListDto) {
        this.donorName = donorName;
        this.donorAdress = donorAdress;
        this.donorDeliveryDate = donorDeliveryDate;
        this.donorEmail = donorEmail;
        this.donorPhoneNumber = donorPhoneNumber;
        this.checkListDto = checkListDto;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorAdress() {
        return donorAdress;
    }

    public void setDonorAdress(String donorAdress) {
        this.donorAdress = donorAdress;
    }

    public String getDonorDeliveryDate() {
        return donorDeliveryDate;
    }

    public void setDonorDeliveryDate(String donorDeliveryDate) {
        this.donorDeliveryDate = donorDeliveryDate;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public int getDonorPhoneNumber() {
        return donorPhoneNumber;
    }

    public void setDonorPhoneNumber(int donorPhoneNumber) {
        this.donorPhoneNumber = donorPhoneNumber;
    }

    public String getDonorUid() {
        return donorUid;
    }

    public void setDonorUid(String donorUid) {
        this.donorUid = donorUid;
    }

    public CheckListDto getCheckListDto() {
        return checkListDto;
    }

    public void setCheckListDto(CheckListDto checkListDto) {
        this.checkListDto = checkListDto;
    }

    @Override
    public String toString() {
        return "DonorInfoDto{" +
                "donorName='" + donorName + '\'' +
                ", donorAdress='" + donorAdress + '\'' +
                ", donorDeliveryDate='" + donorDeliveryDate + '\'' +
                ", donorEmail='" + donorEmail + '\'' +
                ", donorPhoneNumber=" + donorPhoneNumber +
                ", checkListDto=" + checkListDto +
                ", donorUid='" + donorUid + '\'' +
                '}';
    }
}
