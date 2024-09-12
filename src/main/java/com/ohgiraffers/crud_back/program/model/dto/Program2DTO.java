package com.ohgiraffers.crud_back.program.model.dto;
// 서울시 교육 공공서비스예약 정보 조회
public class Program2DTO {
    private String svcid;      // 서비스ID
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
    private String vmax;        // 서비스이용 종료시간
    private String vmin;        // 서비스이용 시작시간

    public Program2DTO() {
    }

    public Program2DTO(String svcid, String minclassnm, String svcstatnm, String svcnm, String payatnm,
                       String placenm, String usetgtinfo, String svcurl, String x, String y, String svcopnbgndt,
                       String svcopnenddt, String rcptbgndt, String rcptenddt, String areanm, String imgurl,
                       String dtlcont, String telno, String vmax, String vmin)
    {
        this.svcid = svcid;
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
        this.vmax = vmax;
        this.vmin = vmin;
    }

    // getter

    public String getSvcid() {
        return svcid;
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

    public String getVmax() {
        return vmax;
    }

    public String getVmin() {
        return vmin;
    }

    //setter


    public void setSvcid(String svcid) {
        this.svcid = svcid;
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

    public void setVmax(String vmax) {
        this.vmax = vmax;
    }

    public void setVmin(String vmin) {
        this.vmin = vmin;
    }
}
