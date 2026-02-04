package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.NoContentException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Project;
import com.examly.springapp.repository.ProjectRepo;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    public Project addProject(Project project) {
        return projectRepo.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    public Project updateProject(Long id, Project project) {
        Project existing = projectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        existing.setProjectName(project.getProjectName());
        return projectRepo.save(existing);
    }

    public List<Project> getProjectsByStatus(String status) {
        List<Project> projects = projectRepo.findByStatus(status);
        if (projects.isEmpty()) {
            throw new NoContentException("No projects found with status: " + status);
        }
        return projects;
    }
}
