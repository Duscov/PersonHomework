package cohort_65.java.personhomework.person.service;

import cohort_65.java.personhomework.person.dao.PersonRepository;
import cohort_65.java.personhomework.person.dto.*;
import cohort_65.java.personhomework.person.dto.exception.PersonNotFoundException;
import cohort_65.java.personhomework.person.model.Address;
import cohort_65.java.personhomework.person.model.Child;
import cohort_65.java.personhomework.person.model.Employee;
import cohort_65.java.personhomework.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

    final PersonRepository personRepository;
    final ModelMapper modelMapper;
    final PersonModelDtoMapper mapper;

    @Override
    public boolean addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            return false;
        }

        personRepository.save(mapper.mapToModel(personDto));
        return true;
    }

    @Override
    public PersonDto findPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return mapper.mapToDto(person);
    }

    @Override
    public PersonDto removePersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto updatePersonName(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setName(name);
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if (addressDto == null) {
            return null;
        }
        person.setAddress(modelMapper.map(addressDto, Address.class));
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto[] findPersonsByCity(String city) {
        return personRepository.findByAddressCityIgnoreCase(city)
                .stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsByName(String name) {
        return personRepository.findByNameIgnoreCase(name)
                .stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge) {
        LocalDate fromDate = LocalDate.now().minusYears(maxAge);
        LocalDate toDate = LocalDate.now().minusYears(minAge);

        return personRepository.findByBirthDateBetween(fromDate, toDate)
                .stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personRepository.getCityPopulation();
    }

    @Override
    public Iterable<EmployeeDto> findEmployeeBySalary(Integer min, Integer max) {
        return personRepository.findBySalaryBetween(min, max)
                .stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .toList();
    }

    @Override
    public Iterable<ChildDto> findAllChildren() {
        return personRepository.findChildBy()
                .stream().map(c -> modelMapper.map(c, ChildDto.class))
                .toList();
    }

    @Override
    public void run(String... args) throws Exception {
        if (personRepository.count() == 0) {
            Person person = new Person(1000,
                    "John",
                    LocalDate.now().minusYears(20),
                    new Address("Berlin", "KantStr", 33));
            Child child = new Child(2000,
                    "Peter",
                    LocalDate.now().minusYears(5),
                    new Address("Berlin", "KantStr", 33),
                    "Kindergarten");
            Employee employee = new Employee(
                    3000,
                    "Karl",
                    LocalDate.now().minusYears(30),
                    new Address("Berlin", "KantStr", 63),
                    "Apple", 8000);
            personRepository.save(person);
            personRepository.save(child);
            personRepository.save(employee);
        }
    }
}