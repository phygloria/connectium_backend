package com.ohgiraffers.crud_back.weather.service;

import com.ohgiraffers.crud_back.weather.exception.WeatherServiceException;
import com.ohgiraffers.crud_back.weather.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.data-type}")
    private String dataType;

    @Value("${weather.api.num-of-rows}")
    private int numOfRows;

    @Value("${weather.api.page-no}")
    private int pageNo;

    @Value("${weather.api.default.nx}")
    private int defaultNx;

    @Value("${weather.api.default.ny}")
    private int defaultNy;

    private final RestTemplate weatherRestTemplate;
    private final CoordinateConverterService converterService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    public WeatherService(@Qualifier("weatherRestTemplate") RestTemplate weatherRestTemplate,
                          CoordinateConverterService converterService) {
        this.weatherRestTemplate = weatherRestTemplate;
        this.converterService = converterService;
    }

    public ComprehensiveWeatherData getComprehensiveWeatherData(Double latitude, Double longitude) {
        try {
            int nx, ny;
            if (latitude != null && longitude != null) {
                Map<String, Double> gridCoord = converterService.convertCoordinate("toXY", latitude, longitude);
                nx = gridCoord.get("x").intValue();
                ny = gridCoord.get("y").intValue();
            } else {
                nx = defaultNx;
                ny = defaultNy;
            }

            WeatherData currentWeather = getCurrentWeather(nx, ny);
            List<ShortTermForecast> shortTermForecasts = getShortTermForecasts(nx, ny);
            List<MidTermForecast> midTermForecasts = getMidTermForecasts(nx, ny);

            logger.info("Current Weather: {}", currentWeather);
            logger.info("Short Term Forecasts: {}", shortTermForecasts);
            logger.info("Mid Term Forecasts: {}", midTermForecasts);

            return new ComprehensiveWeatherData(currentWeather, shortTermForecasts, midTermForecasts);
        } catch (Exception e) {
            logger.error("Error fetching comprehensive weather data: ", e);
            throw new WeatherServiceException("날씨 데이터를 가져오는 데 실패했습니다.", e);
        }
    }

    private WeatherData getCurrentWeather(int nx, int ny) {
        String url = constructApiUrl("/getUltraSrtNcst", nx, ny);
        ResponseEntity<WeatherApiResponse> response = callWeatherApi(url);
        logger.info("UltraSrtNcst Response: {}", response.getBody());
        return processCurrentWeatherResponse(response.getBody());
    }

    private List<ShortTermForecast> getShortTermForecasts(int nx, int ny) {
        String url = constructApiUrl("/getUltraSrtFcst", nx, ny);
        ResponseEntity<WeatherApiResponse> response = callWeatherApi(url);
        logger.info("UltraSrtFcst Response: {}", response.getBody());
        return processShortTermForecastResponse(response.getBody());
    }

    private List<MidTermForecast> getMidTermForecasts(int nx, int ny) {
        String url = constructApiUrl("/getVilageFcst", nx, ny);
        ResponseEntity<WeatherApiResponse> response = callWeatherApi(url);
        logger.info("VilageFcst Response: {}", response.getBody());
        return processMidTermForecastResponse(response.getBody());
    }

    private String constructApiUrl(String endpoint, int nx, int ny) {
        return apiUrl + endpoint +
                "?ServiceKey=" + apiKey +
                "&dataType=" + dataType +
                "&numOfRows=" + numOfRows +
                "&pageNo=" + pageNo +
                "&base_date=" + getCurrentDate() +
                "&base_time=" + getCurrentTime() +
                "&nx=" + nx + "&ny=" + ny;
    }

    private ResponseEntity<WeatherApiResponse> callWeatherApi(String url) {
        logger.info("Calling weather API with URL: {}", url);
        try {
            ResponseEntity<WeatherApiResponse> response = weatherRestTemplate.getForEntity(url, WeatherApiResponse.class);
            logger.info("API Response: {}", response.getBody());
            return response;
        } catch (Exception e) {
            logger.error("Error calling weather API: ", e);
            throw new WeatherServiceException("날씨 API 호출 중 오류 발생", e);
        }
    }

    private WeatherData processCurrentWeatherResponse(WeatherApiResponse response) {
        logger.info("Start processing current weather response");
        WeatherData weatherData = new WeatherData();
        if (response != null && response.getResponse() != null && response.getResponse().getBody() != null
                && response.getResponse().getBody().getItems() != null) {

            List<WeatherApiResponse.Item> items = response.getResponse().getBody().getItems().getItem();
            for (WeatherApiResponse.Item item : items) {
                if (item != null && item.getCategory() != null && item.getObsrValue() != null) {
                    try {
                        switch (item.getCategory()) {
                            case "T1H": // 기온
                                weatherData.setTemperature(Double.parseDouble(item.getObsrValue()));
                                break;
                            case "RN1": // 1시간 강수량
                                weatherData.setHourlyRainfall(Double.parseDouble(item.getObsrValue()));
                                break;
                            case "REH": // 습도
                                weatherData.setHumidity(Integer.parseInt(item.getObsrValue()));
                                break;
                            case "VEC": // 풍향
                                weatherData.setWindDirection(convertWindDirection(Double.parseDouble(item.getObsrValue())));
                                break;
                            case "WSD": // 풍속
                                weatherData.setWindSpeed(Double.parseDouble(item.getObsrValue()));
                                break;
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid numeric value for category {}: {}", item.getCategory(), item.getObsrValue());
                    }
                }
            }
            if (!items.isEmpty()) {
                WeatherApiResponse.Item firstItem = items.get(0);
                LocalDateTime dateTime = LocalDateTime.parse(
                        firstItem.getBaseDate() + firstItem.getBaseTime(),
                        DateTimeFormatter.ofPattern("yyyyMMddHHmm")
                );
                weatherData.setBaseDate(dateTime);
            }
        }
        logger.info("Finished processing current weather response: {}", weatherData);
        return weatherData;
    }

    private List<ShortTermForecast> processShortTermForecastResponse(WeatherApiResponse response) {
        logger.info("Start processing short term forecast response");
        List<ShortTermForecast> forecasts = new ArrayList<>();
        if (response != null && response.getResponse() != null && response.getResponse().getBody() != null
                && response.getResponse().getBody().getItems() != null) {

            List<WeatherApiResponse.Item> items = response.getResponse().getBody().getItems().getItem();
            for (WeatherApiResponse.Item item : items) {
                ShortTermForecast forecast = new ShortTermForecast(
                        item.getFcstDate(),
                        item.getFcstTime(),
                        item.getCategory(),
                        item.getFcstValue()
                );
                forecasts.add(forecast);
            }
        }
        logger.info("Finished processing short term forecast response: {}", forecasts);
        return forecasts;
    }

    private List<MidTermForecast> processMidTermForecastResponse(WeatherApiResponse response) {
        logger.info("Start processing mid term forecast response");
        List<MidTermForecast> forecasts = new ArrayList<>();
        if (response != null && response.getResponse() != null && response.getResponse().getBody() != null
                && response.getResponse().getBody().getItems() != null) {

            List<WeatherApiResponse.Item> items = response.getResponse().getBody().getItems().getItem();
            for (WeatherApiResponse.Item item : items) {
                MidTermForecast forecast = new MidTermForecast(
                        item.getFcstDate(),
                        item.getFcstTime(),
                        item.getCategory(),
                        item.getFcstValue()
                );
                forecasts.add(forecast);
            }
        }
        logger.info("Finished processing mid term forecast response: {}", forecasts);
        return forecasts;
    }

    // 날짜 형식 변환 메서드 (yyyyMMdd)
    private String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    // 시간 형식 변환 메서드 (HHmm)
    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime roundedTime = now.withMinute(now.getMinute() / 30 * 30).withSecond(0).withNano(0);
        return roundedTime.format(DateTimeFormatter.ofPattern("HHmm"));
    }

    // 풍향 각도를 방향으로 변환하는 메서드
    private String convertWindDirection(double degree) {
        if ((degree >= 337.5 && degree <= 360) || (degree >= 0 && degree < 22.5)) return "북";
        if (degree >= 22.5 && degree < 67.5) return "북동";
        if (degree >= 67.5 && degree < 112.5) return "동";
        if (degree >= 112.5 && degree < 157.5) return "남동";
        if (degree >= 157.5 && degree < 202.5) return "남";
        if (degree >= 202.5 && degree < 247.5) return "남서";
        if (degree >= 247.5 && degree < 292.5) return "서";
        if (degree >= 292.5 && degree < 337.5) return "북서";
        return "알 수 없음";
    }
}