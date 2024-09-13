package com.ohgiraffers.crud_back.outdoor.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "park_information")
public class OutdoorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String feature;

    @Column(nullable = false)
    private String opr_info;

    @Column(nullable = false)
    private String opr_hours;

    @Column(nullable = true)
    private String ent_fee;

    @Column(nullable = true)
    private String web_url;

    @Column(nullable = true)
    private String imagePath;

    public OutdoorEntity() {
    }

    public OutdoorEntity(Long id, String name, String address, String phone, String feature, String opr_info, String opr_hours, String ent_fee, String web_url, String imagePath) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.feature = feature;
        this.opr_info = opr_info;
        this.opr_hours = opr_hours;
        this.ent_fee = ent_fee;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getOpr_info() {
        return opr_info;
    }

    public void setOpr_info(String opr_info) {
        this.opr_info = opr_info;
    }

    public String getOpr_hours() {
        return opr_hours;
    }

    public void setOpr_hours(String opr_hours) {
        this.opr_hours = opr_hours;
    }

    public String getEnt_fee() {
        return ent_fee;
    }

    public void setEnt_fee(String ent_fee) {
        this.ent_fee = ent_fee;
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
        return "OutdoorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", feature='" + feature + '\'' +
                ", opr_info='" + opr_info + '\'' +
                ", opr_hours='" + opr_hours + '\'' +
                ", ent_fee='" + ent_fee + '\'' +
                ", web_url='" + web_url + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

}

