package com.project.club.service;

import com.project.club.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public interface ImageFileService extends JpaRepository<ImageFile, Long> {

}
