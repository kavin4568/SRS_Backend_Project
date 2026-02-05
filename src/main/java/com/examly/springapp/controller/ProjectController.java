package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.examly.springapp.model.Project;
import com.examly.springapp.service.ProjectService;
import com.examly.springapp.dto.MessageResponse;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        Project saved = projectService.addProject(project);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,@RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @GetMapping("/status/{status}")
    public List<Project> getProjectsByStatus(@PathVariable String status) {
        return projectService.getProjectsByStatus(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long id) {
        String message = projectService.deleteProject(id);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }
}

