package com.ohgiraffers.crud_back.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonRootName("response")
public class WeatherApiResponse {
    @JsonProperty("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @JsonProperty("header")
        private Header header;
        @JsonProperty("body")
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMsg")
        private String resultMsg;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }

    public static class Body {
        @JsonProperty("dataType")
        private String dataType;
        @JsonProperty("items")
        private Items items;
        @JsonProperty("pageNo")
        private int pageNo;
        @JsonProperty("numOfRows")
        private int numOfRows;
        @JsonProperty("totalCount")
        private int totalCount;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public Items getItems() {
            return items;
        }

        public void setItems(Items items) {
            this.items = items;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getNumOfRows() {
            return numOfRows;
        }

        public void setNumOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    public static class Items {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<Item> item;

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }
    }

    public static class Item {
        @JsonProperty("baseDate")
        private String baseDate;
        @JsonProperty("baseTime")
        private String baseTime;
        @JsonProperty("category")
        private String category;
        @JsonProperty("fcstDate")
        private String fcstDate;
        @JsonProperty("fcstTime")
        private String fcstTime;
        @JsonProperty("fcstValue")
        private String fcstValue;
        @JsonProperty("nx")
        private String nx;
        @JsonProperty("ny")
        private String ny;
        @JsonProperty("obsrValue")
        private String obsrValue;

        // Getter와 Setter 메소드
        public String getBaseDate() { return baseDate; }
        public void setBaseDate(String baseDate) { this.baseDate = baseDate; }

        public String getBaseTime() { return baseTime; }
        public void setBaseTime(String baseTime) { this.baseTime = baseTime; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getFcstDate() { return fcstDate; }
        public void setFcstDate(String fcstDate) { this.fcstDate = fcstDate; }

        public String getFcstTime() { return fcstTime; }
        public void setFcstTime(String fcstTime) { this.fcstTime = fcstTime; }

        public String getFcstValue() { return fcstValue; }
        public void setFcstValue(String fcstValue) { this.fcstValue = fcstValue; }

        public String getNx() { return nx; }
        public void setNx(String nx) { this.nx = nx; }

        public String getNy() { return ny; }
        public void setNy(String ny) { this.ny = ny; }

        public String getObsrValue() { return obsrValue; }
        public void setObsrValue(String obsrValue) { this.obsrValue = obsrValue; }
    }
}