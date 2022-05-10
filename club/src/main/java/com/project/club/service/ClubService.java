package com.project.club.service;

import com.project.club.domain.Club;
import com.project.club.respository.ClubRepository;
import com.project.club.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository)
    {
        this.clubRepository = clubRepository;
    }

    public List<Club> findClubs() { return clubRepository.findAll();}

    @Transactional
    public Long join(Club club){
        clubRepository.save(club);
        return club.getId();
    }

    public Club findOne(Long id){
        Optional<Club> byId = clubRepository.findById(id);


        if(byId.isPresent()){
            return byId.get();
        }

        else{
            return null;
        }
    }

    @Transactional
    public Long deleteById(Long id){
        clubRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Long delete(Club club){
        clubRepository.delete(club);
        return club.getId();
    }

    @Transactional
    public void deleteAll(){

        clubRepository.deleteAll();
    }

}
