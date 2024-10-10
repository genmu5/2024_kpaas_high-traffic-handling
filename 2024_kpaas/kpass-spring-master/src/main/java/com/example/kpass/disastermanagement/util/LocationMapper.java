// 수정 전 코드
//package com.example.kpass.disastermanagement.util;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class LocationMapper {
//
//    private Map<String, Location> locationMap = new HashMap<>();
//
//    public LocationMapper() {
//        loadLocationData();
//    }
//
//    private void loadLocationData() {
//        try (InputStream is = new ClassPathResource("latitude_longitude.xlsx").getInputStream();
//             Workbook workbook = WorkbookFactory.create(is)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    continue; // 첫 번째 행(헤더) 건너뛰기
//                }
//                String regionName = row.getCell(0).getStringCellValue(); // 'docity' 값
//                double latitude = row.getCell(4).getNumericCellValue(); // 위도
//                double longitude = row.getCell(3).getNumericCellValue(); // 경도
//                locationMap.put(regionName, new Location(latitude, longitude));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Location getLocation(String regionName) {
//        for (String key : locationMap.keySet()) {
//            if (regionName.contains(key)) { // 부분 문자열 일치
//                return locationMap.get(key);
//            }
//        }
//        return null; // 해당 지역이 없을 때 null 반환
//    }
//
//    // Location 클래스 정의
//    public static class Location {
//        private double latitude;
//        private double longitude;
//
//        public Location(double latitude, double longitude) {
//            this.latitude = latitude;
//            this.longitude = longitude;
//        }
//
//        public double getLatitude() {
//            return latitude;
//        }
//
//        public double getLongitude() {
//            return longitude;
//        }
//    }
//}


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
        for (String key : locationMap.keySet()) {
            // 부분 문자열로 일치 확인 (지역명이 포함되어 있으면 위치를 반환)
            if (regionName.contains(key)) {
                return locationMap.get(key);
            }
        }
        return null; // 해당 지역이 없을 때 null 반환
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
