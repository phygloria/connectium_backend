package com.ohgiraffers.crud_back.weather.model.dto;

import java.time.LocalDateTime;

/**
 * 날씨 데이터를 저장하는 DTO (Data Transfer Object) 클래스
 */
public class WeatherData {
    private LocalDateTime baseDate;  // 기준 날짜 및 시간
    private double temperature;      // 기온 (℃)
    private Integer humidity;        // 습도 (%)
    private double hourlyRainfall;   // 1시간 강수량 (mm)
    private double windSpeed;        // 풍속 (m/s)
    private String windDirection;    // 풍향
    private double precipitation;    // 강수량 (mm)
    private String skyCondition;     // 하늘 상태

    /**
     * 기본 생성자
     */
    public WeatherData() {}

    // Getter 및 Setter 메서드

    /**
     * 기준 날짜 및 시간을 반환
     * @return 기준 날짜 및 시간
     */
    public LocalDateTime getBaseDate() {
        return baseDate;
    }

    /**
     * 기준 날짜 및 시간을 설정
     * @param baseDate 설정할 기준 날짜 및 시간
     */
    public void setBaseDate(LocalDateTime baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * 기온을 반환
     * @return 기온 (℃)
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * 기온을 설정
     * @param temperature 설정할 기온 (℃)
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * 습도를 반환
     * @return 습도 (%)
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     * 습도를 설정
     * @param humidity 설정할 습도 (%)
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     * 1시간 강수량을 반환
     * @return 1시간 강수량 (mm)
     */
    public double getHourlyRainfall() {
        return hourlyRainfall;
    }

    /**
     * 1시간 강수량을 설정
     * @param hourlyRainfall 설정할 1시간 강수량 (mm)
     */
    public void setHourlyRainfall(double hourlyRainfall) {
        this.hourlyRainfall = hourlyRainfall;
    }

    /**
     * 풍속을 반환
     * @return 풍속 (m/s)
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * 풍속을 설정
     * @param windSpeed 설정할 풍속 (m/s)
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * 풍향을 반환
     * @return 풍향
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     * 풍향을 설정
     * @param windDirection 설정할 풍향
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * 강수량을 반환
     * @return 강수량 (mm)
     */
    public double getPrecipitation() {
        return precipitation;
    }

    /**
     * 강수량을 설정
     * @param precipitation 설정할 강수량 (mm)
     */
    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    /**
     * 하늘 상태를 반환
     * @return 하늘 상태
     */
    public String getSkyCondition() {
        return skyCondition;
    }

    /**
     * 하늘 상태를 설정
     * @param skyCondition 설정할 하늘 상태
     */
    public void setSkyCondition(String skyCondition) {
        this.skyCondition = skyCondition;
    }
}