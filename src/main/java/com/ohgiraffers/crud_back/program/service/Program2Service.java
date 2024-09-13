package com.ohgiraffers.crud_back.program.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;



// 서울시 교육 공공서비스예약 정보
@Service
public class Program2Service {

    private static final Logger logger = LoggerFactory.getLogger(Program2Service.class);

    @Value("${openapi.key2}")
    private String apiKey;

    private final String BASE_URL = "http://openAPI.seoul.go.kr:8088/";

    public List<Program2DTO> getEduProgram() {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append(apiKey)
                .append("/xml/")
                .append("ListPublicReservationEducation/")
                .append("1/")  // START_INDEX
                .append("1000/"); // END_INDEX

        String url = urlBuilder.toString();
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);

        return parseXmlResponse(xmlResponse);
    }


    private List<Program2DTO> parseXmlResponse(String xmlResponse) {
        List<Program2DTO> events = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new org.xml.sax.InputSource(new StringReader(xmlResponse)));

            NodeList rowList = document.getElementsByTagName("row");


            for (int i = 0; i < rowList.getLength(); i++) {
                Element row = (Element) rowList.item(i);
                String svcstatnm = getElementValue(row, "SVCSTATNM"); // 서비스상태
                String areanm = getElementValue(row, "AREANM"); // 지역명
                String minclassnm = getElementValue(row, "MINCLASSNM"); // 소분류명
                String usetgtinfo = getElementValue(row, "USETGTINFO"); // 서비스대상

                // 필터링 조건 적용
                if ("접수중".equals(svcstatnm) && "중랑구".equals(areanm) &&
                        (usetgtinfo.contains("어린이") || usetgtinfo.contains("가족") ||
                                usetgtinfo.contains("제한없음") || usetgtinfo.contains("초등학생") || usetgtinfo.contains("유아"))) {

                    Program2DTO event = new Program2DTO();
                    event.setMinclassnm(minclassnm);
                    event.setSvcnm(getElementValue(row, "SVCNM")); // 서비스명

                    // 이미지 URL 처리 로직 추가
                    String imgurl = getElementValue(row, "IMGURL");
                    if (imgurl != null && !imgurl.isEmpty()) {
                        event.setImgurl("/api/programs/proxy-image?filename=" + imgurl + "&type=program2");
                    } else {
                        logger.warn("IMGURL is missing or empty for item: {}", event.getSvcnm());
                    }

                    event.setDtlcont(getElementValue(row, "DTLCONT")); // 상세내용
                    events.add(event);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }


    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }


    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void updateEduProgramData() {
        try {
            logger.info("교육 프로그램 데이터 업데이트 시작");
            List<Program2DTO> updatedData = getEduProgram();
            // 여기서 updatedData를 저장하거나 처리합니다.
            // 예: saveOrUpdateData(updatedData);
            logger.info("교육 프로그램 데이터가 성공적으로 업데이트되었습니다. 업데이트된 항목 수: {}", updatedData.size());
        } catch (Exception e) {
            logger.error("교육 프로그램 데이터 업데이트 중 오류 발생", e);
        }
    }

    // 필요한 경우, 데이터를 저장하는 메소드를 추가할 수 있습니다.
    // private void saveOrUpdateData(List<Program2DTO> data) {
    //     // 데이터를 데이터베이스에 저장하거나 캐시를 업데이트하는 로직
    // }

    public Program2DTO getProgram2Detail(String svcid) {
        List<Program2DTO> allPrograms = getEduProgram();
        return allPrograms.stream()
                .filter(program -> program.getSvcid().equals(svcid))
                .findFirst()
                .orElse(null);
    }
}