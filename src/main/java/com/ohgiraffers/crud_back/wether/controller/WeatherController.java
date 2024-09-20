package com.ohgiraffers.crud_back.wether.controller;

import com.ohgiraffers.crud_back.wether.model.dto.WeatherData;
import com.ohgiraffers.crud_back.wether.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherData getWeather(@RequestParam(value = "lat", required = false) Double latitude,
                                  @RequestParam(value = "lon", required = false) Double longitude) {
        return weatherService.getWeatherDataByLocation(latitude, longitude);
    }
}
