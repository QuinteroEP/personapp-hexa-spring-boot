package co.edu.javeriana.as.personapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.domain.Study;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/studies")
@RequiredArgsConstructor
public class StudyControllerV1 {

    private final StudyInputPort service;

    @PostMapping
    public Study create(@RequestBody Study study) {
        log.info("POST /api/v1/studies body={}", study);
        return service.create(study);
    }

    @GetMapping
    public List<Study> findAll() {
        return service.findAll();
    }

    @GetMapping("/{personId}/{profId}")
    public Study find(@PathVariable Integer personId, @PathVariable Integer profId) {
        try {
            return service.findOne(personId, profId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/{personId}/{profId}")
    public Study edit(@PathVariable Integer personId, @PathVariable Integer profId, @RequestBody Study study) {
        try {
            log.info("PUT /api/v1/studies/{}/{} body={}", personId, profId, study);
            return service.edit(personId, profId, study);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{personId}/{profId}")
    public boolean delete(@PathVariable Integer personId, @PathVariable Integer profId) {
        try {
            return service.delete(personId, profId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
