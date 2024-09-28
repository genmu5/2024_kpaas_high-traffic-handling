//package com.example.kpass.disastermanagement;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLEncoder;
//
//@RestController
//@RequestMapping("/api")
//public class DisasterController {
//
//    @GetMapping("/disasters")
//    public ResponseEntity<String> getDisasters() {
//        String serviceKey = "YOUR_SERVICE_KEY";
//        String pageNo = "1";
//        String numOfRows = "10";
//        StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
//        urlBuilder.append("?serviceKey=").append(serviceKey);
//        urlBuilder.append("&pageNo=").append(pageNo);
//        urlBuilder.append("&numOfRows=").append(numOfRows);
//
//        try {
//            URL url = new URL(urlBuilder.toString());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            rd.close();
//            conn.disconnect();
//            return ResponseEntity.ok(sb.toString());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//}
