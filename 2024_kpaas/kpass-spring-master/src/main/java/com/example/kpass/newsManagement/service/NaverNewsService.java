package com.example.kpass.newsManagement.service;

import com.example.kpass.newsManagement.entity.NaverNews;
import com.example.kpass.newsManagement.repository.NaverNewsRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NaverNewsService {
    private final NaverNewsRepository naverNewsRepository;

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    public NaverNewsService(NaverNewsRepository naverNewsRepository) {
        this.naverNewsRepository = naverNewsRepository;
    }

    // 최신 뉴스 10개 반환
    public List<NaverNews> getLatestNews() {
        return naverNewsRepository.findTop10ByOrderByPubDateDesc();
    }

    // 5분마다 뉴스 가져오기
    @Scheduled(fixedRate = 300000)  // 5분 = 300,000ms
    public void fetchAndStoreNewsScheduled() {
        fetchAndStoreNews();
    }

    public void fetchAndStoreNews() {
        List<NaverNews> latestNews = fetchNewsFromNaverAPI();
        for (NaverNews news : latestNews) {
            // 중복 여부 확인
            if (!naverNewsRepository.existsByLink(news.getLink())) { // 중복이 아니면 저장
                naverNewsRepository.save(news);
            }
        }
    }

    private List<NaverNews> fetchNewsFromNaverAPI() {
        String query = "재난";
        int display = 20; // 20개의 뉴스 가져오기
        String sort = "date"; // 최신순 정렬

        String apiURL = "https://openapi.naver.com/v1/search/news.xml?query=" + query
                + "&display=" + display
                + "&sort=" + sort;

        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiURL, HttpMethod.GET, entity, String.class);

            return parseXML(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("API 호출 오류", e);
        }
    }

    private List<NaverNews> parseXML(String xmlResponse) {
        List<NaverNews> newsList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            NodeList itemList = document.getElementsByTagName("item");

            DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME; // pubDate 형식

            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);
                NaverNews naverNews = new NaverNews();

                // Jsoup을 사용하여 HTML 태그를 제거하고 텍스트만 추출
                naverNews.setTitle(Jsoup.parse(getTagValue("title", item)).text()); // HTML 태그 제거 후 텍스트만 저장
                naverNews.setDescription(Jsoup.parse(getTagValue("description", item)).text()); // HTML 태그 제거 후 텍스트만 저장
                naverNews.setLink(getTagValue("link", item));

                // pubDate는 RFC 1123 형식으로 제공되므로 파싱
                String pubDate = getTagValue("pubDate", item);
                naverNews.setPubDate(LocalDateTime.parse(pubDate, formatter));

                newsList.add(naverNews);
            }

        } catch (Exception e) {
            throw new RuntimeException("XML 파싱 오류", e);
        }
        return newsList;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node != null ? node.getNodeValue() : "";
    }
}
