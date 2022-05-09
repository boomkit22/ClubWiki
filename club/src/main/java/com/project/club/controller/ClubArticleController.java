package com.project.club.controller;

import com.project.club.domain.Article;
import com.project.club.domain.ClubBoard;
import com.project.club.domain.Member;
import com.project.club.service.ArticleService;
import com.project.club.service.ClubBoardService;
import com.project.club.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClubArticleController {


    private final MemberService memberService;
    private final ArticleService articleService;
    private final ClubBoardService clubBoardService;

    @GetMapping("clubBoards/{clubBoardId}/newArticle")
    public String createForm(@AuthenticationPrincipal Member member, Model model, @PathVariable("clubBoardId") Long clubBoardId)
    {
        ArticleForm articleForm = new ArticleForm();

        model.addAttribute("articleForm",articleForm);
        model.addAttribute("clubBoardId",clubBoardId);

        return "clubs/boards/createArticleForm";

    }

    @GetMapping("clubBoards/{clubBoardId}/article")
    public String showArticle(Model model, @RequestParam(name="articleId") Long articleId)
    {
        Article article = articleService.findById(articleId);

        model.addAttribute("article",article);

        return "clubs/boards/article";
    }

    @PostMapping("clubBoards/{clubBoardId}/newArticle")
    public String create(@AuthenticationPrincipal Member member, @Valid ArticleForm form, BindingResult result,  @PathVariable("clubBoardId") Long clubBoardId)
    {
        Article article = new Article();
        article.setMember(member);
        article.setData(form.getData());


        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        article.setClubBoard(clubBoard);

        articleService.join(article);
        String url = "redirect:/clubBoards/"+ clubBoardId.toString();

        return url;

    }

    @PostMapping("clubBoards/{clubBoardId}/deleteArticle")
    public String delete(@RequestParam(name="articleId") Long articleId, @PathVariable("clubBoardId") Long clubBoardId)
    {
        articleService.deleteById(articleId);

        String url = "redirect:/clubBoards/"+ clubBoardId.toString();
        return url;
    }


    @GetMapping("clubBoards/updateArticle")
    public String updateForm(@AuthenticationPrincipal Member member, Model model, @RequestParam(name="articleId") Long articleId)
    {
        ArticleForm articleForm = new ArticleForm();
        Article article = articleService.findById(articleId);

        articleForm.setData(article.getData());
        model.addAttribute("articleId",article.getId());
        Long id = article.getClubBoard().getId();
        model.addAttribute("articleForm",articleForm);
        model.addAttribute("clubBoardId", id);
        return "clubs/boards/updateArticleForm";

    }

    @PostMapping("clubBoards/updateArticle")
    public String update(@AuthenticationPrincipal Member member, @Valid ArticleForm form, BindingResult result,@RequestParam(name="clubBoardId") Long clubBoardId, @RequestParam(name="articleId") Long articleId)
    {
        Article article = articleService.findById(articleId);
        article.setData(form.getData());
        articleService.save(article);
        String url = "redirect:/clubBoards/"+ clubBoardId.toString();
        return url;
    }

}
