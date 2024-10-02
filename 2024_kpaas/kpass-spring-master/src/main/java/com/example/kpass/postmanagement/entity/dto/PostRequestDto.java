package com.example.kpass.postmanagement.entity.dto;

import com.example.kpass.postmanagement.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private int likeCount;
    private Region region;
}
