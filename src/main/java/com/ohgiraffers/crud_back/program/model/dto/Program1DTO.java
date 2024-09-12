package com.ohgiraffers.crud_back.program.model.dto;

import java.util.List;

// 중랑구 문화행사 공공서비스예약 정보 조회
public class Program1DTO {
    private String svcid;      // 서비스ID
    private String maxclassnm; // 대분류명
    private String minclassnm; // 소분류명
    private String svcstatnm;  // 서비스상태
    private String svcnm;      // 서비스명
    private String payatnm;    // 결제방법
    private String placenm;    // 장소명
    private String usetgtinfo; // 서비스대상
    private String svcurl;     // 바로가기URL
    private String x;          // 장소X좌표
    private String y;          // 장소Y좌표
    private String svcopnbgndt; // 서비스개시시작일시
    private String svcopnenddt; // 서비스개시종료일시
    private String rcptbgndt;   // 접수시작일시
    private String rcptenddt;   // 접수종료일시
    private String areanm;      // 지역명
    private String imgurl;      // 이미지경로
    private String dtlcont;     // 상세내용
    private String telno;       // 전화번호

    private String operationTime; // 운영시간
    private String facilities; // 편의시설
    private String price; // 가격
    private String reservationMethod; // 예약방법
    private String address; //문의처
    private boolean liked; // 좋아요
    private boolean bookmarked; // 북마크
    private List<String> imageUrls;  // 여러 이미지 URL을 저장하기 위한 리스트

    public Program1DTO() {
    }

    public Program1DTO(String svcid, String maxclassnm, String minclassnm, String svcstatnm, String svcnm, String payatnm, String placenm, String usetgtinfo, String svcurl, String x, String y, String svcopnbgndt, String svcopnenddt, String rcptbgndt, String rcptenddt, String areanm, String imgurl, String dtlcont, String telno, String operationTime, String facilities, String price, String reservationMethod, String address, boolean liked, boolean bookmarked, List<String> imageUrls) {
        this.svcid = svcid;
        this.maxclassnm = maxclassnm;
        this.minclassnm = minclassnm;
        this.svcstatnm = svcstatnm;
        this.svcnm = svcnm;
        this.payatnm = payatnm;
        this.placenm = placenm;
        this.usetgtinfo = usetgtinfo;
        this.svcurl = svcurl;
        this.x = x;
        this.y = y;
        this.svcopnbgndt = svcopnbgndt;
        this.svcopnenddt = svcopnenddt;
        this.rcptbgndt = rcptbgndt;
        this.rcptenddt = rcptenddt;
        this.areanm = areanm;
        this.imgurl = imgurl;
        this.dtlcont = dtlcont;
        this.telno = telno;
        this.operationTime = operationTime;
        this.facilities = facilities;
        this.price = price;
        this.reservationMethod = reservationMethod;
        this.address = address;
        this.liked = liked;
        this.bookmarked = bookmarked;
        this.imageUrls = imageUrls;
    }

    //getter


    public String getSvcid() {
        return svcid;
    }

    public String getMaxclassnm() {
        return maxclassnm;
    }

    public String getMinclassnm() {
        return minclassnm;
    }

    public String getSvcstatnm() {
        return svcstatnm;
    }

    public String getSvcnm() {
        return svcnm;
    }

    public String getPayatnm() {
        return payatnm;
    }

    public String getPlacenm() {
        return placenm;
    }

    public String getUsetgtinfo() {
        return usetgtinfo;
    }

    public String getSvcurl() {
        return svcurl;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getSvcopnbgndt() {
        return svcopnbgndt;
    }

    public String getSvcopnenddt() {
        return svcopnenddt;
    }

    public String getRcptbgndt() {
        return rcptbgndt;
    }

    public String getRcptenddt() {
        return rcptenddt;
    }

    public String getAreanm() {
        return areanm;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getDtlcont() {
        return dtlcont;
    }

    public String getTelno() {
        return telno;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getPrice() {
        return price;
    }

    public String getReservationMethod() {
        return reservationMethod;
    }

    public String getAddress() {
        return address;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setSvcid(String svcid) {
        this.svcid = svcid;
    }

    public void setMaxclassnm(String maxclassnm) {
        this.maxclassnm = maxclassnm;
    }

    public void setMinclassnm(String minclassnm) {
        this.minclassnm = minclassnm;
    }

    public void setSvcstatnm(String svcstatnm) {
        this.svcstatnm = svcstatnm;
    }

    public void setSvcnm(String svcnm) {
        this.svcnm = svcnm;
    }

    public void setPayatnm(String payatnm) {
        this.payatnm = payatnm;
    }

    public void setPlacenm(String placenm) {
        this.placenm = placenm;
    }

    public void setUsetgtinfo(String usetgtinfo) {
        this.usetgtinfo = usetgtinfo;
    }

    public void setSvcurl(String svcurl) {
        this.svcurl = svcurl;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setSvcopnbgndt(String svcopnbgndt) {
        this.svcopnbgndt = svcopnbgndt;
    }

    public void setSvcopnenddt(String svcopnenddt) {
        this.svcopnenddt = svcopnenddt;
    }

    public void setRcptbgndt(String rcptbgndt) {
        this.rcptbgndt = rcptbgndt;
    }

    public void setRcptenddt(String rcptenddt) {
        this.rcptenddt = rcptenddt;
    }

    public void setAreanm(String areanm) {
        this.areanm = areanm;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setDtlcont(String dtlcont) {
        this.dtlcont = dtlcont;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setReservationMethod(String reservationMethod) {
        this.reservationMethod = reservationMethod;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}


