package com.ohgiraffers.crud_back.care.model.dto;

import jakarta.persistence.Column;

public class CareDTO {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String introduction;
    private String operatingHours;
    private String usageFee;
    private String imagePath;

    public CareDTO() {
    }

    public CareDTO(Long id, String name, String address, String phoneNumber, String introduction, String operatingHours, String usageFee, String imagePath) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.operatingHours = operatingHours;
        this.usageFee = usageFee;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getUsageFee() {
        return usageFee;
    }

    public void setUsageFee(String usageFee) {
        this.usageFee = usageFee;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "CareDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", introduction='" + introduction + '\'' +
                ", operatingHours='" + operatingHours + '\'' +
                ", usageFee='" + usageFee + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
