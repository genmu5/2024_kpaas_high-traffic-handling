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
import java.time.LocalDateTime;
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
        LocalDateTime currentDate = LocalDateTime.now();
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
        LocalDateTime startDate = LocalDateTime.now().minusDays(1); // 어제 날짜
        LocalDateTime endDate = LocalDateTime.now().plusDays(1); // 오늘 날짜

        // DB에서 해당 날짜 범위의 재난 데이터 조회
        List<DisasterEntity> disasters = disasterRepository.findByCreatedAtBetween(startDate, endDate);

        return disasters.stream().map(disaster -> {
            LocationMapper.Location location = locationMapper.getLocation(disaster.getRegionName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return new DisasterResponseDto(
                    disaster.getCreatedAt().format(formatter), // LocalDateTime을 포맷팅
                    disaster.getDisasterType(),
                    disaster.getRegionName(),
                    location != null ? location.getLatitude() : null,
                    location != null ? location.getLongitude() : null
            );
        }).collect(Collectors.toList());
    }
}
