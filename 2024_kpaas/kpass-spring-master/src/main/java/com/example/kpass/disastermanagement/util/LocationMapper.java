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
//             Workbook workbook = WorkbookFactory.create(is)) { // WorkbookFactory.create(is) 수정
//
//            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 사용
//            for (Row row : sheet) {
//                String regionName = row.getCell(0).getStringCellValue(); // 'docity' 사용
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
//        return locationMap.get(regionName);
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
