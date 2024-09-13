package com.ohgiraffers.crud_back.program.service;

import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class Program1Service {

    private static final Logger logger = LoggerFactory.getLogger(Program1Service.class);

    @Value("${openapi.key}")
    private String apiKey;

    private final String BASE_URL = "http://openAPI.seoul.go.kr:8088/";

    public List<Program1DTO> getCulProgram() {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append(apiKey)
                .append("/xml/")
                .append("JRListPublicReservationCulture/")
                .append("1/")  // START_INDEX
                .append("1000/"); // END_INDEX

        String url = urlBuilder.toString();
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);

        return parseXmlResponse(xmlResponse);
    }

    private List<Program1DTO> parseXmlResponse(String xmlResponse) {
        List<Program1DTO> events = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new org.xml.sax.InputSource(new StringReader(xmlResponse)));

            NodeList rowList = document.getElementsByTagName("row");

            for (int i = 0; i < rowList.getLength(); i++) {
                Element row = (Element) rowList.item(i);
                String svcstatnm = getElementValue(row, "SVCSTATNM");
                String areanm = getElementValue(row, "AREANM");
                String usetgtinfo = getElementValue(row, "USETGTINFO");
                String svcid = getElementValue(row, "SVCID");

                // 필터링 조건 적용
                if ("중랑구".equals(areanm) &&
                        !"예약마감".equals(svcstatnm) &&
                        !"접수종료".equals(svcstatnm) &&
                        !usetgtinfo.contains("성인")) {

                    Program1DTO event = new Program1DTO();
                    event.setSvcid(svcid);
                    event.setSvcnm(getElementValue(row, "SVCNM"));
                    event.setPlacenm(getElementValue(row, "PLACENM"));
                    event.setSvcopnbgndt(getElementValue(row, "SVCOPNBGNDT"));
                    event.setPayatnm(getElementValue(row, "PAYATNM"));
                    event.setSvcstatnm(svcstatnm);
                    event.setUsetgtinfo(usetgtinfo);

                    // 이미지 URL 설정 수정
                    event.setImgurl("event_" + svcid + ".jpg");

                    events.add(event);
                }
            }
        } catch (Exception e) {
            logger.error("XML 파싱 중 오류 발생", e);
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
    public void updateCulProgramData() {
        try {
            logger.info("문화 프로그램 데이터 업데이트 시작");
            List<Program1DTO> updatedData = getCulProgram();
            logger.info("문화 프로그램 데이터가 성공적으로 업데이트되었습니다. 업데이트된 항목 수: {}", updatedData.size());
        } catch (Exception e) {
            logger.error("문화 프로그램 데이터 업데이트 중 오류 발생", e);
        }
    }

    public Program1DTO getProgram1Detail(String svcid) {
        List<Program1DTO> allPrograms = getCulProgram();
        return allPrograms.stream()
                .filter(program -> program.getSvcid().equals(svcid))
                .findFirst()
                .orElse(null);
    }
}