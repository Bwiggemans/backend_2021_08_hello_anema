package nl.novi.hello.controller;

import nl.novi.hello.model.Person;
import nl.novi.hello.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/persons")
    public ResponseEntity<Object> getPersons(@RequestParam(name="title", defaultValue="") String title) {
        return ResponseEntity.ok(personService.getPersons(title));   // Jackson  object => json
    }

    @GetMapping(value = "/persons/{id}")
    public ResponseEntity<Object> getPerson(@PathVariable int id) {
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @DeleteMapping(value = "/persons/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/persons")
    public ResponseEntity<Object> addPerson(@RequestBody Person person) {
        int newId = personService.addPerson(person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).build();
    }
}
