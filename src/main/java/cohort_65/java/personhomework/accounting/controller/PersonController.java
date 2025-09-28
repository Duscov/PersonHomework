package cohort_65.java.personhomework.accounting.controller;


import cohort_65.java.personhomework.accounting.dao.PersonRepository;
import cohort_65.java.personhomework.accounting.dto.PersonDto;
import cohort_65.java.personhomework.accounting.model.Person;
import cohort_65.java.personhomework.accounting.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/*REST-контроллер для управления сущностями Person. Внедряет PersonRepository через конструктор.*/
@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    /*Добавляет нового человека в базу данных. @param person объект Person из JSON тела запроса
     * @return сохранённый объект Person
     */
    private final PersonService personService;

    @PostMapping("/add")
    public ResponseEntity<Person> addPerson(@RequestBody PersonDto personDto) {
        Person saved = personService.addPerson(personDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findPerson(@PathVariable Long id) {
        PersonDto personDto = personService.findPerson(id);
        return ResponseEntity.ok(personDto);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<PersonDto>> findByCity(@PathVariable String city) {
        List<PersonDto> persons = personService.findByCity(city);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/ages")
    public ResponseEntity<List<PersonDto>> findByAges(
            @RequestParam int from,
            @RequestParam int to) {
        List<PersonDto> persons = personService.findByAges(from, to);
        return ResponseEntity.ok(persons);
    }

    @PutMapping("/{id}/{name}")
    public ResponseEntity<PersonDto> updateName(
            @PathVariable Long id,
            @PathVariable String name) {
        PersonDto updated = personService.updateName(id, name);
        return ResponseEntity.ok(updated);
    }
}