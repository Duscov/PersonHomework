package cohort_65.java.personhomework.accounting.controller;

import cohort_65.java.personhomework.accounting.dao.PersonRepository;
import cohort_65.java.personhomework.accounting.model.Person;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Person> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return repository.save(person);
    }
}
