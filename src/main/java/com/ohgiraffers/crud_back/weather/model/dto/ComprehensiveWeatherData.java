package com.ohgiraffers.crud_back.weather.model.dto;

import java.util.List;

public class ComprehensiveWeatherData {
    private WeatherData currentWeather;
    private List<ShortTermForecast> shortTermForecasts;
    private List<MidTermForecast> midTermForecasts;

    public ComprehensiveWeatherData(WeatherData currentWeather, List<ShortTermForecast> shortTermForecasts, List<MidTermForecast> midTermForecasts) {
        this.currentWeather = currentWeather;
        this.shortTermForecasts = shortTermForecasts;
        this.midTermForecasts = midTermForecasts;
    }

    public WeatherData getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(WeatherData currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<ShortTermForecast> getShortTermForecasts() {
        return shortTermForecasts;
    }

    public void setShortTermForecasts(List<ShortTermForecast> shortTermForecasts) {
        this.shortTermForecasts = shortTermForecasts;
    }

    public List<MidTermForecast> getMidTermForecasts() {
        return midTermForecasts;
    }

    public void setMidTermForecasts(List<MidTermForecast> midTermForecasts) {
        this.midTermForecasts = midTermForecasts;
    }
}