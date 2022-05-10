package com.project.club.service;

import com.project.club.domain.Club;
import com.project.club.domain.ClubBoard;
import com.project.club.respository.ClubBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClubBoardService {

    private final ClubBoardRepository clubBoardRepository;

    @Autowired
    public ClubBoardService(ClubBoardRepository clubBoardRepository){
        this.clubBoardRepository = clubBoardRepository;
    }

    public List<ClubBoard> findClubBoards(){
        return clubBoardRepository.findAll();
    }

    @Transactional
    public Long join(ClubBoard clubBoard){
        clubBoardRepository.save(clubBoard);
        return clubBoard.getId();
    }

    public List<ClubBoard> findByClub(Club club){

        return clubBoardRepository.findByClub(club);
    }

    @Transactional
    public void deleteAll(){

        clubBoardRepository.deleteAll();
    }

    public ClubBoard findById(Long clubBoardId){

        Optional<ClubBoard> byId = clubBoardRepository.findById(clubBoardId);

        if(byId.isPresent()){
            return byId.get();
        }
        else{
            return null;
        }

    }

    public List<ClubBoard> findAll(){
        return clubBoardRepository.findAll();
    }

    @Transactional
    public boolean updateLock(Long clubBoardId){

        ClubBoard clubBoard = this.findById(clubBoardId);
        return clubBoard.updateLock();
    }


}
