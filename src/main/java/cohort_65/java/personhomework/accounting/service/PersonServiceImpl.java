package cohort_65.java.personhomework.accounting.service;

import cohort_65.java.personhomework.accounting.dto.PersonDto;
import cohort_65.java.personhomework.accounting.dto.exceptions.PersonExistsException;
import cohort_65.java.personhomework.accounting.dto.exceptions.PersonNotFoundException;
import cohort_65.java.personhomework.accounting.model.Person;
import cohort_65.java.personhomework.accounting.dao.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервисного слоя для работы с Person.
 * Использует ModelMapper для преобразования DTO в Entity.
 * id задаётся пользователем, auto-increment не используется.
 */
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;
    final ModelMapper modelMapper;

    @Override
    public Person addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            throw new PersonExistsException();
        }       // Маппинг PersonDto → Person через ModelMapper
        Person person = modelMapper.map(personDto, Person.class);
        person =  personRepository.save(person);
        return modelMapper.map(person, Person.class);
    }

    @Override
    public PersonDto findPerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public List<PersonDto> findByCity(String city) {
        List<Person> people = personRepository.findByAdress_City(city);
        if (people.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return people.stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());
    }
}