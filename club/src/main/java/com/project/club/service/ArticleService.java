package com.project.club.service;

import com.project.club.domain.Article;
import com.project.club.domain.ClubBoard;
import com.project.club.respository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository)
    {
        this.articleRepository = articleRepository;
    }

    public List<Article> findArticles(){
        return articleRepository.findAll();
    }

    @Transactional
    public Long join(Article article){
        articleRepository.save(article);
        return article.getId();
    }

    @Transactional
    public Long deleteById(Long id)
    {
        articleRepository.deleteById(id);
        return id;


    }

    @Transactional
    public Long save(Article article)
    {
        articleRepository.save(article);

        return article.getId();
    }

    public List<Article> findByClubBoard(ClubBoard clubBoard)
    {
        return articleRepository.findByClubBoard(clubBoard);
    }

    public Article findById(Long articleId)
    {
        Optional<Article> byId = articleRepository.findById(articleId);
        if(byId.isPresent()){
            return byId.get();
        }
        else{
            return null;
        }
    }

}
