package com.project.club.api.dto;

import com.project.club.api.ClubArticleApiController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class WikiPageDto{
    private String wikiName;
    private String wikiIntro;
    private String cpAnnouncement;
    private boolean isLock;
    private Long articleCount;
    private List<ArticleListDto> articleList;
}
