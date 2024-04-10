package com.rede_social.memora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.rede_social.memora.model.subject.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
    public List<Subject> findAllByDescriptionContainingIgnoreCase(@Param("description") String description);

}
