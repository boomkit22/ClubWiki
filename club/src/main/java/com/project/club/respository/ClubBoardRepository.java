package com.project.club.respository;

import com.project.club.domain.Club;
import com.project.club.domain.ClubBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubBoardRepository extends JpaRepository<ClubBoard ,Long>
{

    public List<ClubBoard> findByClub(Club club);

}
