package co.edu.javeriana.as.personapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.domain.Profession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/professions")
@RequiredArgsConstructor
public class ProfessionControllerV1 {

    private final ProfessionInputPort service;

    @PostMapping
    public Profession create(@RequestBody Profession profession) {
        return service.create(profession);
    }

    @GetMapping
    public List<Profession> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Profession find(@PathVariable Integer id) {
        try {
            return service.findOne(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Profession edit(@PathVariable Integer id, @RequestBody Profession profession) {
        try {
            return service.edit(id, profession);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        try {
            return service.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
