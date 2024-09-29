package com.ohgiraffers.crud_back.weather.controller;

import com.ohgiraffers.crud_back.weather.exception.WeatherServiceException;
import com.ohgiraffers.crud_back.weather.model.dto.ComprehensiveWeatherData;
import com.ohgiraffers.crud_back.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<ComprehensiveWeatherData> getWeather(
            @RequestParam(value = "lat", required = false) Double latitude,
            @RequestParam(value = "lon", required = false) Double longitude) {
        logger.info("Received weather request for lat: {}, lon: {}", latitude, longitude);
        try {
            ComprehensiveWeatherData weatherData = weatherService.getComprehensiveWeatherData(latitude, longitude);
            logger.info("Weather data: {}", weatherData);
            return ResponseEntity.ok(weatherData);
        } catch (WeatherServiceException e) {
            logger.error("Error fetching weather data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}