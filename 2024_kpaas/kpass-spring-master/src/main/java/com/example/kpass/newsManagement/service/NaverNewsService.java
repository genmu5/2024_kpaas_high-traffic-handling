package com.example.kpass.newsManagement.service;

import com.example.kpass.newsManagement.entity.NaverNews;
import com.example.kpass.newsManagement.repository.NaverNewsRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class NaverNewsService {
    private final NaverNewsRepository naverNewsRepository;

    public NaverNewsService(NaverNewsRepository naverNewsRepository) {
        this.naverNewsRepository = naverNewsRepository;
    }

    public void fetchAndStoreNews() {
        List<NaverNews> latestNews = fetchNewsFromNaverAPI();
        for (NaverNews news : latestNews) {
            news.setCreatedAt(LocalDateTime.now());
            naverNewsRepository.save(news);
        }
    }

    private List<NaverNews> fetchNewsFromNaverAPI() {
        String clientId = "OLYEwByAn_YBAWf8Bfh8"; // 네이버 API 클라이언트 아이디
        String clientSecret = "s8moO2wYH7"; // 네이버 API 클라이언트 시크릿
        String query = "재난";

        String apiURL = "https://openapi.naver.com/v1/search/news.xml?query=" + query;

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

            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);
                NaverNews naverNews = new NaverNews();

                naverNews.setTitle(getTagValue("title", item));
                naverNews.setSource(getTagValue("originallink", item));
                naverNews.setDescription(getTagValue("description", item));
                naverNews.setLink(getTagValue("link", item));
                naverNews.setTag("#재난");

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
