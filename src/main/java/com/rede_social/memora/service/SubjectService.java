package com.rede_social.memora.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rede_social.memora.dto.SubjectDto;
import com.rede_social.memora.model.Subject;
import com.rede_social.memora.repository.SubjectRepository;

@Service
public class SubjectService {
    
    @Autowired
    private SubjectRepository subjectRepository;

    public Optional<SubjectDto> findById(Long id) {
        return subjectRepository.findById(id)
                                .map(SubjectDto::new);
    }
    

    public SubjectDto convertToDto(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setDescription(subject.getDescription());
     
        return subjectDto;
    }

    public List<SubjectDto> findAll(){
        List<Subject> subject = subjectRepository.findAll();
        return subject.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
    }
}
