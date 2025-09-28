package cohort_65.java.personhomework.accounting.controller;


import cohort_65.java.personhomework.accounting.dao.PersonRepository;
import cohort_65.java.personhomework.accounting.dto.PersonDto;
import cohort_65.java.personhomework.accounting.model.Person;
import cohort_65.java.personhomework.accounting.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
}