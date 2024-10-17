package com.example.kpass.disastermanagement.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class LocationMapper {

    private Map<String, Location> locationMap = new HashMap<>();

    public LocationMapper() {
        loadLocationData();
    }

    private void loadLocationData() {
        try (InputStream is = new ClassPathResource("latitude_longitude.xlsx").getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // 첫 번째 행(헤더) 건너뛰기
                }
                String regionName = row.getCell(0).getStringCellValue(); // 'docity' 값
                double latitude = row.getCell(4).getNumericCellValue(); // 위도
                double longitude = row.getCell(3).getNumericCellValue(); // 경도

                // 'docity' 값을 맵에 추가
                locationMap.put(regionName, new Location(latitude, longitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(String regionName) {
        // 공백이나 쉼표로 구분된 주요 지역명 추출
        String[] parts = regionName.split("[ ,]");
        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            // 지역명을 기준으로 부분 문자열 또는 정확한 매칭 시도
            for (String key : locationMap.keySet()) {
                if (key.contains(part) || part.contains(key)) {
                    System.out.println("Matched key: " + key + " for region: " + part);
                    return locationMap.get(key);
                }
            }
        }

        System.out.println("No match found for region: " + regionName);
        return null;
    }






    @Getter // Lombok을 사용하여 getter 메서드 자동 생성
    public static class Location {
        private double latitude;
        private double longitude;

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
