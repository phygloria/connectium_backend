package com.ohgiraffers.crud_back.wether.service;

import com.ohgiraffers.crud_back.wether.model.dto.WeatherApiResponse;
import com.ohgiraffers.crud_back.wether.model.dto.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 날씨 정보를 가져오고 처리하는 서비스 클래스
 */
@Service
public class WeatherService {

    // API 관련 설정값들을 application.properties 파일에서 주입받음
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

    // RestTemplate과 좌표 변환 서비스 주입
    private final RestTemplate weatherRestTemplate;
    private final CoordinateConverterService converterService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    /**
     * WeatherService 생성자
     * @param weatherRestTemplate API 호출을 위한 RestTemplate
     * @param converterService 좌표 변환을 위한 서비스
     */
    @Autowired
    public WeatherService(@Qualifier("weatherRestTemplate") RestTemplate weatherRestTemplate,
                          CoordinateConverterService converterService) {
        this.weatherRestTemplate = weatherRestTemplate;
        this.converterService = converterService;
    }

    /**
     * 주어진 위도와 경도에 대한 날씨 정보를 가져옴
     * @param latitude 위도
     * @param longitude 경도
     * @return 처리된 날씨 데이터
     */
    public WeatherData getWeatherDataByLocation(Double latitude, Double longitude) {
        int nx, ny;
        // 위도와 경도가 제공되면 좌표 변환, 아니면 기본값 사용
        if (latitude != null && longitude != null) {
            Map<String, Double> gridCoord = converterService.convertCoordinate("toXY", latitude, longitude);
            nx = gridCoord.get("x").intValue();
            ny = gridCoord.get("y").intValue();
        } else {
            nx = defaultNx;
            ny = defaultNy;
        }

        // API 요청 URL 구성
        String url = apiUrl + "/getUltraSrtNcst" +
                "?ServiceKey=" + apiKey +
                "&dataType=" + dataType +
                "&numOfRows=" + numOfRows +
                "&pageNo=" + pageNo +
                "&base_date={baseDate}&base_time={baseTime}" +
                "&nx=" + nx + "&ny=" + ny;

        // 현재 날짜와 시간을 기준으로 base_date와 base_time을 설정
        LocalDateTime now = LocalDateTime.now();
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = now.format(DateTimeFormatter.ofPattern("HHmm"));

        // API 호출
        ResponseEntity<WeatherApiResponse> response = weatherRestTemplate.getForEntity(
                url,
                WeatherApiResponse.class,
                baseDate,
                baseTime
        );

        logger.info("API Response: {}", response.getBody());

        // 응답 처리
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            WeatherApiResponse apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.getResponse() != null &&
                    apiResponse.getResponse().getBody() != null &&
                    apiResponse.getResponse().getBody().getItems() != null &&
                    apiResponse.getResponse().getBody().getItems().getItem() != null) {
                return processApiResponse(apiResponse);
            } else {
                throw new RuntimeException("Invalid API response structure");
            }
        } else {
            throw new RuntimeException("Failed to fetch weather data: " + response.getStatusCode());
        }
    }

    /**
     * API 응답을 처리하여 WeatherData 객체로 변환
     * @param apiResponse API로부터 받은 응답
     * @return 처리된 날씨 데이터
     */
    private WeatherData processApiResponse(WeatherApiResponse apiResponse) {
        WeatherData weatherData = new WeatherData();
        List<WeatherApiResponse.Item> items = apiResponse.getResponse().getBody().getItems().getItem();

        for (WeatherApiResponse.Item item : items) {
            if (item != null && item.getCategory() != null && item.getObsrValue() != null) {
                switch (item.getCategory()) {
                    case "T1H":
                        // 기온 (℃)
                        weatherData.setTemperature(Double.parseDouble(item.getObsrValue()));
                        break;
                    case "RN1":
                        // 1시간 강수량 (mm)
                        weatherData.setHourlyRainfall(Double.parseDouble(item.getObsrValue()));
                        break;
                    case "REH":
                        // 습도 (%)
                        weatherData.setHumidity(Integer.parseInt(item.getObsrValue()));
                        break;
                    case "VEC":
                        // 풍향 (deg)
                        weatherData.setWindDirection(convertWindDirection(Double.parseDouble(item.getObsrValue())));
                        break;
                    case "WSD":
                        // 풍속 (m/s)
                        weatherData.setWindSpeed(Double.parseDouble(item.getObsrValue()));
                        break;
                    // 기타 필요한 데이터 처리
                }
            }
        }

        // baseDate와 baseTime을 조합하여 LocalDateTime 설정
        String baseDate = items.get(0).getBaseDate();
        String baseTime = items.get(0).getBaseTime();
        LocalDateTime dateTime = LocalDateTime.parse(baseDate + baseTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        weatherData.setBaseDate(dateTime);

        return weatherData;
    }

    /**
     * 풍향 각도를 문자열로 변환
     * @param degree 풍향 각도
     * @return 풍향을 나타내는 문자열
     */
    private String convertWindDirection(double degree) {
        if (degree >= 337.5 || degree < 22.5) return "북";
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