package cohort_65.java.personhomework.accounting.service;


import cohort_65.java.personhomework.accounting.dto.PersonDto;
import cohort_65.java.personhomework.accounting.model.Person;

public interface PersonService {
    Person addPerson(PersonDto dto);

    PersonDto findPerson(Long id);
}