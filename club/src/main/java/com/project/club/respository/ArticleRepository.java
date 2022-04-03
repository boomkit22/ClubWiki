package com.project.club.respository;

import com.project.club.domain.Article;
import com.project.club.domain.ClubBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    public List<Article> findByClubBoard(ClubBoard clubBoard);
}
