package com.example.kpass.entity.dto;

import com.example.kpass.entity.Region;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public class PostRequestDto {
    private String title;
    private String content;
    private int likeCount;
    private Region region;

    public PostRequestDto(String title, String content, int likeCount, Region region) {
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.region = region;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public Region getRegion() {
        return region;
    }
}
