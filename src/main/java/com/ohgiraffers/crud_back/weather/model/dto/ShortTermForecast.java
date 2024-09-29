package com.ohgiraffers.crud_back.weather.model.dto;

public class ShortTermForecast {
    private String forecastDate;
    private String forecastTime;
    private String category;
    private String value;

    public ShortTermForecast() {
    }

    public ShortTermForecast(String forecastDate, String forecastTime, String category, String value) {
        this.forecastDate = forecastDate;
        this.forecastTime = forecastTime;
        this.category = category;
        this.value = value;

    }


    // 생성자, getter, setter 메소드 추가

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}