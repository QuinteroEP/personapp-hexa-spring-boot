package co.edu.javeriana.as.personapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.as.personapp.adapter.PersonaInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.PhoneRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/phones")
public class PhoneControllerV1 {

    @Autowired
    private PersonaInputAdapterRest personaInputAdapterRest;

    @ResponseBody
    @GetMapping(path = "/{database}/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhoneRequest> phonesByPerson(@PathVariable String database, @PathVariable Integer personId) {
        log.info("Into phones REST API (PhoneControllerV1)");
        return personaInputAdapterRest.getPhones(database.toUpperCase(), personId);
    }

}
