package com.mom.momtomom.DTO;

/**
 * Created by wee on 2017. 11. 28..
 */

public class BeneficiaryInfoDto {
    private String beneficiaryName;
    private String beneficiaryPhoneNumber;

    public BeneficiaryInfoDto() {
    }

    public BeneficiaryInfoDto(String beneficiaryName, String beneficiaryPhoneNumber) {
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryPhoneNumber = beneficiaryPhoneNumber;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryPhoneNumber() {
        return beneficiaryPhoneNumber;
    }

    public void setBeneficiaryPhoneNumber(String beneficiaryPhoneNumber) {
        this.beneficiaryPhoneNumber = beneficiaryPhoneNumber;
    }

    @Override
    public String toString() {
        return "BeneficiaryInfoDto{" +
                "beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryPhoneNumber='" + beneficiaryPhoneNumber + '\'' +
                '}';
    }
}

