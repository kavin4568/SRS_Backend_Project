package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.Project;
import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long>{
    List<Project> findByStatus(String status);
}
  