/*
package com.example.kpass.disastermanagement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory; ////
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class DisasterController {
    private static final Logger logger = LoggerFactory.getLogger(DisasterController.class); ////

    @GetMapping("/disasters")
    public String getDisasters() {
        String serviceKey = "E1SG516JK20EZN97"; // 실제 서비스 키 입력
        String pageNo = "1";
        String numOfRows = "10";
        String crtDt = "20241001";
        StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
        urlBuilder.append("?serviceKey=").append(serviceKey);
        urlBuilder.append("&pageNo=").append(pageNo);
        urlBuilder.append("&numOfRows=").append(numOfRows);
        urlBuilder.append("&crtDt=").append(crtDt);

        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            logger.info("API 호출 성공: {}", sb.toString()); // API 응답 로그로 출력
            return sb.toString(); // API 데이터 반환

        } catch (Exception e) {
            logger.error("API 호출 중 오류 발생", e); // 오류 로그 출력
            return "Error: " + e.getMessage(); // 에러 처리
        }
    }
}
*/


/*package com.example.kpass.disastermanagement;

import com.example.kpass.disastermanagement.service.DisasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class DisasterController {
    private static final Logger logger = LoggerFactory.getLogger(DisasterController.class);

    public DisasterController(DisasterService disasterService) {
    }

    @GetMapping("/disasters")
    public String getDisasters() {
        String serviceKey = "E1SG516JK20EZN97"; // 실제 서비스 키 입력
        String pageNo = "1";
        String numOfRows = "10";
        String crtDt = "20241001"; // 조회 시작일자를 2024년 10월 1일로 설정 (YYYYMMDD 형식)

        try {
            // URL 빌더 설정 (파라미터 포함)
            StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
            urlBuilder.append("?" + "serviceKey=" + URLEncoder.encode(serviceKey, "UTF-8"));
            urlBuilder.append("&" + "pageNo=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + "numOfRows=" + URLEncoder.encode(numOfRows, "UTF-8"));
            urlBuilder.append("&" + "crtDt=" + URLEncoder.encode(crtDt, "UTF-8")); // 시작일자 추가

            // 로그로 최종 URL 출력
            logger.info("API 호출 URL: {}", urlBuilder.toString());

            // API 호출
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            // 로그로 API 응답 출력
            logger.info("API 호출 성공: {}", sb.toString());

            return sb.toString(); // API 데이터 반환

        } catch (Exception e) {
            logger.error("API 호출 중 오류 발생", e);
            return "Error: " + e.getMessage();
        }
    }
}*/

//package com.example.kpass.disastermanagement.controller;
//
//import com.example.kpass.disastermanagement.dto.DisasterResponseDto;
//import com.example.kpass.disastermanagement.entity.DisasterEntity;
//import com.example.kpass.disastermanagement.repository.DisasterRepository; // 이 부분이 사용됨
//import com.example.kpass.disastermanagement.service.DisasterService;
//import com.example.kpass.disastermanagement.util.LocationMapper;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class DisasterController {
//    private static final Logger logger = LoggerFactory.getLogger(DisasterController.class);
//
//    private final DisasterService disasterService;
//    private final ObjectMapper objectMapper; // Jackson의 ObjectMapper를 사용하여 JSON 파싱
//
//    public DisasterController(DisasterService disasterService) {
//        this.disasterService = disasterService;
//        this.objectMapper = new ObjectMapper(); // ObjectMapper 초기화
//    }
//
//    @GetMapping("/disasters")
//    public String getDisasters() {
//        String serviceKey = "E1SG516JK20EZN97"; // 실제 서비스 키 입력
//        String pageNo = "1";
//        String numOfRows = "10";
//        // 오늘 날짜를 "YYYYMMDD" 형식으로 가져오기
//        LocalDate currentDate = LocalDate.now();  // 오늘 날짜
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");  // "YYYYMMDD" 형식
//        String crtDt = currentDate.format(formatter);  // 오늘 날짜를 "YYYYMMDD" 형식의 문자열로 변환
//        //String crtDt = "20241001"; // 조회 시작일자를 2024년 10월 1일로 설정 (YYYYMMDD 형식)
//
//        try {
//            StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
//            urlBuilder.append("?" + "serviceKey=" + serviceKey);
//            urlBuilder.append("&" + "pageNo=" + pageNo);
//            urlBuilder.append("&" + "numOfRows=" + numOfRows);
//            urlBuilder.append("&" + "crtDt=" + crtDt);  // 시작일자 추가
//
//            logger.info("API 호출 URL: {}", urlBuilder.toString());
//
//            URL url = new URL(urlBuilder.toString());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            rd.close();
//            conn.disconnect();
//
//            // JSON 응답 파싱
//            String jsonResponse = sb.toString();
//            logger.info("API 호출 성공: {}", jsonResponse);
//
//            // JSON 파싱 시작 (ObjectMapper 사용)
//            JsonNode rootNode = objectMapper.readTree(jsonResponse);
//            JsonNode bodyNode = rootNode.path("body");
//
//            if (bodyNode.isArray()) {
//                for (JsonNode disasterNode : bodyNode) {
//                    String createdAt = disasterNode.path("CRT_DT").asText();
//                    String regionName = disasterNode.path("RCPTN_RGN_NM").asText();
//                    String disasterType = disasterNode.path("DST_SE_NM").asText();
//
//                    // 필터링 및 DB 저장
//                    disasterService.filterAndSaveDisaster(createdAt, regionName, disasterType);
//                }
//            }
//
//            return jsonResponse; // 전체 응답을 반환
//
//        } catch (Exception e) {
//            logger.error("API 호출 중 오류 발생", e);
//            return "Error: " + e.getMessage();
//        }
//    }
//}

// 수정 전
//package com.example.kpass.disastermanagement.controller;
//
//import com.example.kpass.disastermanagement.dto.DisasterResponseDto;
//import com.example.kpass.disastermanagement.entity.DisasterEntity;
//import com.example.kpass.disastermanagement.repository.DisasterRepository; // 이 부분이 사용됨
//import com.example.kpass.disastermanagement.service.DisasterService;
//import com.example.kpass.disastermanagement.util.LocationMapper;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class DisasterController {
//    private static final Logger logger = LoggerFactory.getLogger(DisasterController.class);
//
//    private final DisasterService disasterService;
//    private final DisasterRepository disasterRepository; // DB 접근용
//    private final LocationMapper locationMapper;
//    private final ObjectMapper objectMapper;
//
//    public DisasterController(DisasterService disasterService, DisasterRepository disasterRepository) {
//        this.disasterService = disasterService;
//        this.disasterRepository = disasterRepository;  // Repository 주입
//        this.locationMapper = new LocationMapper();  // LocationMapper 초기화
//        this.objectMapper = new ObjectMapper();  // ObjectMapper 초기화
//    }
//
//    // 외부 API에서 재난 데이터를 가져와 DB에 저장하는 API
//    @GetMapping("/disasters")
//    public String getDisasters() {
//        String serviceKey = "E1SG516JK20EZN97"; // 실제 서비스 키 입력
//        String pageNo = "1";
//        String numOfRows = "10";
//        // 오늘 날짜를 "YYYYMMDD" 형식으로 가져오기
//        LocalDate currentDate = LocalDate.now();  // 오늘 날짜
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");  // "YYYYMMDD" 형식
//        String crtDt = currentDate.format(formatter);  // 오늘 날짜를 "YYYYMMDD" 형식의 문자열로 변환
//
//        try {
//            StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
//            urlBuilder.append("?" + "serviceKey=" + serviceKey);
//            urlBuilder.append("&" + "pageNo=" + pageNo);
//            urlBuilder.append("&" + "numOfRows=" + numOfRows);
//            urlBuilder.append("&" + "crtDt=" + crtDt);  // 시작일자 추가
//
//            logger.info("API 호출 URL: {}", urlBuilder.toString());
//
//            URL url = new URL(urlBuilder.toString());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            rd.close();
//            conn.disconnect();
//
//            // JSON 응답 파싱
//            String jsonResponse = sb.toString();
//            logger.info("API 호출 성공: {}", jsonResponse);
//
//            // JSON 파싱 시작 (ObjectMapper 사용)
//            JsonNode rootNode = objectMapper.readTree(jsonResponse);
//            JsonNode bodyNode = rootNode.path("body");
//
//            if (bodyNode.isArray()) {
//                for (JsonNode disasterNode : bodyNode) {
//                    String createdAt = disasterNode.path("CRT_DT").asText();
//                    String regionName = disasterNode.path("RCPTN_RGN_NM").asText();
//                    String disasterType = disasterNode.path("DST_SE_NM").asText();
//
//                    // 필터링 및 DB 저장
//                    disasterService.filterAndSaveDisaster(createdAt, regionName, disasterType);
//                }
//            }
//
//            return jsonResponse; // 전체 응답을 반환
//
//        } catch (Exception e) {
//            logger.error("API 호출 중 오류 발생", e);
//            return "Error: " + e.getMessage();
//        }
//    }
//
//    // DB에 저장된 재난 데이터를 프론트엔드로 제공하는 API
//    @GetMapping("/disasters/location")
//    public List<DisasterResponseDto> getDisasterLocations() {
//        // DB에서 재난 데이터 조회
//        List<DisasterEntity> disasters = disasterRepository.findAll();  // DB 접근
//
//        // 각 재난 데이터에 대해 지역 정보를 가져오고 프론트엔드에 전달할 형식으로 변환
//        return disasters.stream().map(disaster -> {
//            LocationMapper.Location location = locationMapper.getLocation(disaster.getRegionName());
//            return new DisasterResponseDto(
//                    disaster.getCreatedAt(),
//                    disaster.getDisasterType(),
//                    disaster.getRegionName(),
//                    location != null ? location.getLatitude() : null,
//                    location != null ? location.getLongitude() : null
//            );
//        }).collect(Collectors.toList());
//    }
//}

package com.example.kpass.disastermanagement.controller;

import com.example.kpass.disastermanagement.dto.DisasterResponseDto;
import com.example.kpass.disastermanagement.entity.DisasterEntity;
import com.example.kpass.disastermanagement.repository.DisasterRepository;
import com.example.kpass.disastermanagement.service.DisasterService;
import com.example.kpass.disastermanagement.util.LocationMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DisasterController {
    private static final Logger logger = LoggerFactory.getLogger(DisasterController.class);

    private final DisasterService disasterService;
    private final DisasterRepository disasterRepository;
    private final LocationMapper locationMapper;
    private final ObjectMapper objectMapper;

    public DisasterController(DisasterService disasterService, DisasterRepository disasterRepository) {
        this.disasterService = disasterService;
        this.disasterRepository = disasterRepository;
        this.locationMapper = new LocationMapper();
        this.objectMapper = new ObjectMapper();
    }

    // 외부 API에서 재난 데이터를 가져와 DB에 저장하는 API
    @GetMapping("/disasters")
    public String getDisasters() {
        String serviceKey = "E1SG516JK20EZN97";
        String pageNo = "1";
        String numOfRows = "10";
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String crtDt = currentDate.format(formatter);

        try {
            StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
            urlBuilder.append("?serviceKey=").append(serviceKey);
            urlBuilder.append("&pageNo=").append(pageNo);
            urlBuilder.append("&numOfRows=").append(numOfRows);
            urlBuilder.append("&crtDt=").append(crtDt);

            logger.info("API 호출 URL: {}", urlBuilder.toString());

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            // JSON 응답 파싱
            String jsonResponse = sb.toString();
            logger.info("API 호출 성공: {}", jsonResponse);

            // JSON 파싱 시작 (ObjectMapper 사용)
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode bodyNode = rootNode.path("body");

            if (bodyNode.isArray()) {
                for (JsonNode disasterNode : bodyNode) {
                    String createdAt = disasterNode.path("CRT_DT").asText();
                    String regionName = disasterNode.path("RCPTN_RGN_NM").asText();
                    String disasterType = disasterNode.path("DST_SE_NM").asText();

                    // 필터링 및 DB 저장
                    disasterService.filterAndSaveDisaster(createdAt, regionName, disasterType);
                }
            }

            return jsonResponse;

        } catch (Exception e) {
            logger.error("API 호출 중 오류 발생", e);
            return "Error: " + e.getMessage();
        }
    }

    // DB에 저장된 재난 데이터를 프론트엔드로 제공하는 API
    @GetMapping("/disasters/location")
    public List<DisasterResponseDto> getDisasterLocations() {
        // DB에서 재난 데이터 조회
        List<DisasterEntity> disasters = disasterRepository.findAll();

        // 각 재난 데이터에 대해 지역 정보를 가져오고 프론트엔드에 전달할 형식으로 변환
        return disasters.stream().map(disaster -> {
            LocationMapper.Location location = locationMapper.getLocation(disaster.getRegionName());
            return new DisasterResponseDto(
                    disaster.getCreatedAt(),
                    disaster.getDisasterType(),
                    disaster.getRegionName(),
                    location != null ? location.getLatitude() : null,
                    location != null ? location.getLongitude() : null
            );
        }).collect(Collectors.toList());
    }
}

