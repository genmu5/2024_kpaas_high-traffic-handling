package com.example.kpass.sociallogin.service;

import com.example.kpass.sociallogin.entity.UserEntity;
import com.example.kpass.sociallogin.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        String oauthClientName = oAuth2UserRequest.getClientRegistration().getClientName();

        UserEntity userEntity = null;
        String userId = "";
        String userName = "";
        String userEmail = "";

        //콘솔에 찍어볼 디버깅 용 문장 나중에 지워도됨
        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        }catch (Exception e) {
            log.info("유저 정보 로딩중 오류 발생");
        }

        if(oauthClientName.equals("naver")) {
            Object response = oAuth2User.getAttributes().get("response");
            if(response instanceof Map<?,?> responseMap) {
                userId = ((String) responseMap.get("id")).substring(0, ((String)responseMap.get("id")).length()-1);
                userEmail = (String) responseMap.get("email");
                userName = (String) responseMap.get("name");
                userEntity = new UserEntity(userId, userName, userEmail, "naver", "ROLE_USER");
            }
        }
        else if(oauthClientName.equals("google")) {
            userId = oAuth2User.getAttribute("sub");
            userEmail = oAuth2User.getAttribute("email");
            userName = oAuth2User.getAttribute("name");
            userEntity = new UserEntity(userId, userName, userEmail, "google", "ROLE_USER");
        }

        userRepository.save(userEntity);

        Map<String, String> stringAttributes = new HashMap<>();
        stringAttributes.put("id", userId);
        stringAttributes.put("name", userName);
        stringAttributes.put("email", userEmail);

        return oAuth2User;
    }
}