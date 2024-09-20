package com.example.kpass.sociallogin.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oauth2User;

    @Getter
    private Map<String, String> stringAttributes;

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User != null ? oauth2User.getAttributes() : Collections.emptyMap();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User != null ? oauth2User.getAuthorities() : Collections.emptyList();
    }

    @Override
    public String getName(){
        return stringAttributes != null && stringAttributes.containsKey("name")
                ? stringAttributes.get("name")
                : oauth2User != null ? oauth2User.getName() : null;
    }
}
