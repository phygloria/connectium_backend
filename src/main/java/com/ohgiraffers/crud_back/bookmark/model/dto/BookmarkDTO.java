package com.ohgiraffers.crud_back.bookmark.model.dto;

import com.ohgiraffers.crud_back.education.model.entity.EducationEntity;
import com.ohgiraffers.crud_back.outdoor.model.entity.OutdoorEntity;
import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;

public class BookmarkDTO {
    private Long id;
    private String itemId;
    private String itemType;
    private String name;
    private String description;
    private String location;
    private String period;
    private String cost;
    private String imagePath;

    // 추가 필드
    private String address;
    private String feature;
    private String registrationPeriod;
    private String targetAudience;
    private String placenm;
    private String svcopnbgndt;
    private String svcopnenddt;
    private String payatnm;
    private String ent_fee;
    private String opr_hours;

    public BookmarkDTO() {
    }

    public BookmarkDTO(EducationEntity education) {
        this.itemId = education.getId().toString();
        this.itemType = "EDUCATION";
        this.name = education.getName();
        this.description = education.getTargetAudience();
        this.location = education.getLocation();
        this.period = education.getEducationPeriod();
        this.cost = education.getCost();
        this.imagePath = education.getImagePath();
        this.registrationPeriod = education.getRegistrationPeriod();
        this.targetAudience = education.getTargetAudience();
    }

    public BookmarkDTO(OutdoorEntity outdoor) {
        this.itemId = outdoor.getId().toString();
        this.itemType = "OUTDOOR";
        this.name = outdoor.getName();
        this.description = outdoor.getFeature();
        this.location = outdoor.getAddress();
        this.period = outdoor.getOpr_hours();
        this.cost = outdoor.getEnt_fee();
        this.imagePath = outdoor.getImagePath();
        this.address = outdoor.getAddress();
        this.feature = outdoor.getFeature();
        this.ent_fee = outdoor.getEnt_fee();
        this.opr_hours = outdoor.getOpr_hours();
    }

    public BookmarkDTO(Program1DTO program) {
        this.itemId = program.getSvcid();
        this.itemType = "PROGRAM1";
        this.name = program.getSvcnm();
        this.description = program.getUsetgtinfo();
        this.location = program.getPlacenm();
        this.period = program.getSvcopnbgndt() + " ~ " + program.getSvcopnenddt();
        this.cost = program.getPayatnm();
        this.imagePath = program.getImgurl();
        this.placenm = program.getPlacenm();
        this.svcopnbgndt = program.getSvcopnbgndt();
        this.svcopnenddt = program.getSvcopnenddt();
        this.payatnm = program.getPayatnm();
    }

    public BookmarkDTO(Program2DTO program) {
        this.itemId = program.getSvcid();
        this.itemType = "PROGRAM2";
        this.name = program.getSvcnm();
        this.description = program.getUsetgtinfo();
        this.location = program.getPlacenm();
        this.period = program.getSvcopnbgndt() + " ~ " + program.getSvcopnenddt();
        this.cost = program.getPayatnm();
        this.imagePath = program.getImgurl();
        this.placenm = program.getPlacenm();
        this.svcopnbgndt = program.getSvcopnbgndt();
        this.svcopnenddt = program.getSvcopnenddt();
        this.payatnm = program.getPayatnm();
    }

    // Getter and Setter methods for all fields
    // ... (include all getters and setters for the new fields as well)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public String getPlacenm() {
        return placenm;
    }

    public void setPlacenm(String placenm) {
        this.placenm = placenm;
    }

    public String getSvcopnbgndt() {
        return svcopnbgndt;
    }

    public void setSvcopnbgndt(String svcopnbgndt) {
        this.svcopnbgndt = svcopnbgndt;
    }

    public String getSvcopnenddt() {
        return svcopnenddt;
    }

    public void setSvcopnenddt(String svcopnenddt) {
        this.svcopnenddt = svcopnenddt;
    }

    public String getPayatnm() {
        return payatnm;
    }

    public void setPayatnm(String payatnm) {
        this.payatnm = payatnm;
    }

    public String getEnt_fee() {
        return ent_fee;
    }

    public void setEnt_fee(String ent_fee) {
        this.ent_fee = ent_fee;
    }

    public String getOpr_hours() {
        return opr_hours;
    }

    public void setOpr_hours(String opr_hours) {
        this.opr_hours = opr_hours;
    }

    @Override
    public String toString() {
        return "BookmarkDTO{" +
                "id=" + id +
                ", itemId='" + itemId + '\'' +
                ", itemType='" + itemType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", period='" + period + '\'' +
                ", cost='" + cost + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", address='" + address + '\'' +
                ", feature='" + feature + '\'' +
                ", registrationPeriod='" + registrationPeriod + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", placenm='" + placenm + '\'' +
                ", svcopnbgndt='" + svcopnbgndt + '\'' +
                ", svcopnenddt='" + svcopnenddt + '\'' +
                ", payatnm='" + payatnm + '\'' +
                ", ent_fee='" + ent_fee + '\'' +
                ", opr_hours='" + opr_hours + '\'' +
                '}';
    }
}