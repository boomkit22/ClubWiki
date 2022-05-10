package com.project.club.api;

import com.project.club.api.dto.CreateClubBoardRequestDto;
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
    public CreateClubBoardResponse saveClubBoard(@PathVariable("clubId") Long clubId
    , @RequestBody CreateClubBoardRequestDto request)
    {
        Club club = clubService.findOne(clubId);

        if(club==null){
            log.warn("saveClubBoard club was Null clubId = ",clubId);
        }

        ClubBoard clubBoard = ClubBoard.builder()
                .club(club)
                .name(request.getName())
                .intro(request.getIntro())
                .oneLineReview(request.getOneLineReview())
                .boardCategory(request.getBoardCategory())
                .bLock(request.isBLock())
                .build();

        Long id = clubBoardService.join(clubBoard);

        return new CreateClubBoardResponse(id);

    }

    @PutMapping("/api/clubs/clubBoard/{clubBoardId}")
    public boolean Lock(@PathVariable("clubBoardId") Long clubBoardId)
    {
        return clubBoardService.updateLock(clubBoardId);
    }

    @GetMapping("/api/clubs/clubBoard/{clubId}")
    public Result clubBoardList(@PathVariable("clubId") Long clubId){

        Club club = clubService.findOne(clubId);
        List<ClubBoard> clubBoardList = clubBoardService.findByClub(club);
        List<ClubBoardListDto> collect = clubBoardList.stream().map(m -> new ClubBoardListDto(m.getName(), m.getIntro(), m.getOneLineReview(), m.getBoardCategory(), m.isBLock()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);

    }




    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class ClubBoardListDto{
        private String name;
        private String intro;
        private String oneLineReview;
        private String boardCategory;
        private boolean bLock;
    }

    @Data
    static class CreateClubBoardResponse{
        private Long id;

        public CreateClubBoardResponse(Long id) {
            this.id = id;
        }
    }


}
