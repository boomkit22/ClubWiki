package com.project.club.service;

import com.project.club.respository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ImageFileService{

    private final ImageFileRepository imageFileRepository;




}