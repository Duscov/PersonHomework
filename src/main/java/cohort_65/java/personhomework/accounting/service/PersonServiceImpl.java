package cohort_65.java.personhomework.accounting.service;

import cohort_65.java.personhomework.accounting.dto.AdressDto;
import cohort_65.java.personhomework.accounting.dto.PersonDto;
import cohort_65.java.personhomework.accounting.dto.exceptions.InvalidAdressException;
import cohort_65.java.personhomework.accounting.dto.exceptions.InvalidPersonNameException;
import cohort_65.java.personhomework.accounting.dto.exceptions.PersonExistsException;
import cohort_65.java.personhomework.accounting.dto.exceptions.PersonNotFoundException;
import cohort_65.java.personhomework.accounting.model.Adress;
import cohort_65.java.personhomework.accounting.model.Person;
import cohort_65.java.personhomework.accounting.dao.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public List<PersonDto> findByAges(int fromAge, int toAge) {
        LocalDate now = LocalDate.now();
        LocalDate maxBirthDate = now.minusYears(fromAge);
        LocalDate minBirthDate = now.minusYears(toAge);

        List<Person> people = personRepository.findByBirthDateBetween(minBirthDate, maxBirthDate);
        if (people.isEmpty()) {
            throw new PersonNotFoundException();}
        return people.stream()
                    .map(person -> modelMapper.map(person, PersonDto.class))
                    .collect(Collectors.toList());

    }

    @Override
    public PersonDto updateName(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidPersonNameException("Имя не может быть пустым!");
        }
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
        person.setName(newName);
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public List<PersonDto> findByName (String name) {
        List<Person> people = personRepository.findByName(name);
        if (people.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return people.stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto updateAdress(Long id, AdressDto adressDto) {
        if (adressDto == null ||
                adressDto.getCity() == null || adressDto.getCity().trim().isEmpty() ||
                adressDto.getStreet() == null || adressDto.getStreet().trim().isEmpty() ||
                adressDto.getBuilding() == null || adressDto.getBuilding().trim().isEmpty()) {
            throw new InvalidAdressException("Поля city, street, building не должны быть пустыми!");
        }
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        person.setAdress(modelMapper.map(adressDto, Adress.class));
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

}