package com.ohgiraffers.crud_back.education.model.dto;

public class EducationDTO {

    private Long id;
    private String name;                // 이름
    private String registrationPeriod;  // 접수기간
    private String targetAudience;      // 대상
    private String location;            // 장소
    private String cost;                // 비용
    private String educationPeriod;     // 교육기간
    private String phoneNumber;         // 전화번호
    private String web_url;
    private String imagePath;

    public EducationDTO() {
    }

    public EducationDTO(Long id, String name, String registrationPeriod, String targetAudience, String location, String cost, String educationPeriod, String phoneNumber, String web_url, String imagePath) {
        this.id = id;
        this.name = name;
        this.registrationPeriod = registrationPeriod;
        this.targetAudience = targetAudience;
        this.location = location;
        this.cost = cost;
        this.educationPeriod = educationPeriod;
        this.phoneNumber = phoneNumber;
        this.web_url = web_url;
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

    public String getRegistrationPeriod() {
        return registrationPeriod;
    }

    public void setRegistrationPeriod(String registrationPeriod) {
        this.registrationPeriod = registrationPeriod;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getEducationPeriod() {
        return educationPeriod;
    }

    public void setEducationPeriod(String educationPeriod) {
        this.educationPeriod = educationPeriod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "EducationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationPeriod='" + registrationPeriod + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", location='" + location + '\'' +
                ", cost=" + cost +
                ", educationPeriod='" + educationPeriod + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", web_url='" + web_url + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
