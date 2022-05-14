package com.project.club.api;

import com.project.club.api.dto.ClubBoardListDto;
import com.project.club.api.dto.CreateClubBoardRequestDto;
import com.project.club.api.dto.CreateClubBoardResponseDto;
import com.project.club.api.dto.LockResponseDto;
import com.project.club.domain.Article;
import com.project.club.domain.Club;
import com.project.club.domain.ClubBoard;
import com.project.club.service.ArticleService;
import com.project.club.service.ClubBoardService;
import com.project.club.service.ClubService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClubBoardApiController {

    private final ClubService clubService;
    private final ClubBoardService clubBoardService;
    private final ArticleService articleService;

    @PostMapping("/api/clubs/clubBoard/{clubId}")
    public CreateClubBoardResponseDto saveClubBoard(HttpServletRequest httpServletRequest, @PathVariable("clubId") Long clubId
    , @RequestBody CreateClubBoardRequestDto request)
    {
        Club club = clubService.findOne(clubId);
        if(club==null)
        {
            log.warn("saveClubBoard club was Null clubId = ",clubId);
        }
        ClubBoard clubBoard = ClubBoard.builder()
                .club(club)
                .name(request.getWikiName())
                .intro(request.getWikiIntro())
                .oneLineReview(request.getCpAnnouncement())
                .boardCategory(request.getBoardCategory())
                .bLock(request.isLock())
                .build();

        Long id = clubBoardService.join(clubBoard);
        return new CreateClubBoardResponseDto("위키 게시판 생성 완료");
    }

    @PutMapping("/api/clubs/clubBoard/{clubBoardId}")
    public LockResponseDto Lock(@PathVariable("clubBoardId") Long clubBoardId)
    {
        return new LockResponseDto(clubBoardService.updateLock(clubBoardId));
    }

    @GetMapping("/api/clubs/clubBoard/{clubId}")
    public ClubBoardListResponseWithCount clubBoardList(@PathVariable("clubId") Long clubId){

        Club club = clubService.findOne(clubId);
        List<ClubBoard> clubBoardList = clubBoardService.findByClub(club);
        List<ClubBoardListDto> collect = clubBoardList.stream().map(m -> new ClubBoardListDto(m.getId(), m.getName()))
                .collect(Collectors.toList());

        return new ClubBoardListResponseWithCount(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class ClubBoardListResponseWithCount<T>{
        private int count;
        private T data;
    }

}
