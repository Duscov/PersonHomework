package cohort_65.java.personhomework.person.service;

import cohort_65.java.personhomework.person.dao.PersonRepository;
import cohort_65.java.personhomework.person.dto.*;
import cohort_65.java.personhomework.person.model.Address;
import cohort_65.java.personhomework.person.model.Child;
import cohort_65.java.personhomework.person.model.Employee;
import cohort_65.java.personhomework.person.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PersonModelDtoMapper mapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void addPerson_test() {
        // DTO и соответствующие Entity с одинаковыми ID
        PersonDto personDto = new PersonDto(100, "John", LocalDate.of(2000, 1, 1), new AddressDto("Berlin", "MainStr", 10));
        Person person = new Person(100, "John", LocalDate.of(2000, 1, 1), new Address("Berlin", "MainStr", 10));

        ChildDto childDto = new ChildDto(101, "Peter", LocalDate.of(2020, 5, 15), new AddressDto("Berlin", "KantStr", 33), "Kindergarten Blumenthal");
        Child child = new Child(101, "Peter", LocalDate.of(2020, 5, 15), new Address("Berlin", "KantStr", 33), "Kindergarten Blumenthal");

        EmployeeDto employeeDto = new EmployeeDto(102, "Karl", LocalDate.of(1990, 3, 10), new AddressDto("Berlin", "KantStr", 63), "Apple GmbH", 8000);
        Employee employee = new Employee(102, "Karl", LocalDate.of(1990, 3, 10), new Address("Berlin", "KantStr", 63), "Apple GmbH", 8000);

        // Мокаем mapper.mapToModel(...) — возвращаем корректные объекты с ID
        when(mapper.mapToModel(personDto)).thenReturn(person);
        when(mapper.mapToModel(childDto)).thenReturn(child);
        when(mapper.mapToModel(employeeDto)).thenReturn(employee);

        // Мокаем existsById(...) универсально — даже если ID окажется null
        when(personRepository.existsById(any())).thenReturn(false);

        // Мокаем сохранение — возвращаем переданный объект
        when(personRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Проверяем, что метод возвращает true
        assertTrue(personService.addPerson(personDto));
        assertTrue(personService.addPerson(childDto));
        assertTrue(personService.addPerson(employeeDto));

        // Проверяем, что объекты были сохранены
        verify(personRepository).save(person);
        verify(personRepository).save(child);
        verify(personRepository).save(employee);
    }

    @Test
    void findPersonById_test() {
        // Пример 1: обычный Person
        Person person = new Person(1000, "John", LocalDate.of(2005, 1, 1), new Address("Berlin", "KantStr", 33));
        PersonDto personDto = new PersonDto(1000, "John", LocalDate.of(2005, 1, 1), new AddressDto("Berlin", "KantStr", 33));

        // Пример 2: Child
        Child child = new Child(2000, "Peter", LocalDate.of(2020, 5, 15), new Address("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");
        ChildDto childDto = new ChildDto(2000, "Peter", LocalDate.of(2020, 5, 15), new AddressDto("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");

        // Пример 3: Employee
        Employee employee = new Employee(3000, "Karl", LocalDate.of(1990, 3, 10), new Address("Berlin", "KantStr", 63), "Apple GmbH", 8000);
        EmployeeDto employeeDto = new EmployeeDto(3000, "Karl", LocalDate.of(1990, 3, 10), new AddressDto("Berlin", "KantStr", 63), "Apple GmbH", 8000);

        // Мокаем поведение репозитория и маппера
        when(personRepository.findById(1000)).thenReturn(Optional.of(person));
        when(personRepository.findById(2000)).thenReturn(Optional.of(child));
        when(personRepository.findById(3000)).thenReturn(Optional.of(employee));

        when(mapper.mapToDto(person)).thenReturn(personDto);
        when(mapper.mapToDto(child)).thenReturn(childDto);
        when(mapper.mapToDto(employee)).thenReturn(employeeDto);

        // Проверяем, что метод возвращает корректные DTO
        assertEquals(personDto, personService.findPersonById(1000));
        assertEquals(childDto, personService.findPersonById(2000));
        assertEquals(employeeDto, personService.findPersonById(3000));
    }

    @Test
    void removePersonById_test() {
        // Пример 1: обычный Person
        Person person = new Person(1000, "John", LocalDate.of(2005, 1, 1), new Address("Berlin", "KantStr", 33));
        PersonDto personDto = new PersonDto(1000, "John", LocalDate.of(2005, 1, 1), new AddressDto("Berlin", "KantStr", 33));

        // Пример 2: Child
        Child child = new Child(2000, "Peter", LocalDate.of(2020, 5, 15), new Address("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");
        ChildDto childDto = new ChildDto(2000, "Peter", LocalDate.of(2020, 5, 15), new AddressDto("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");

        // Пример 3: Employee
        Employee employee = new Employee(3000, "Karl", LocalDate.of(1990, 3, 10), new Address("Berlin", "KantStr", 63), "Apple GmbH", 8000);
        EmployeeDto employeeDto = new EmployeeDto(3000, "Karl", LocalDate.of(1990, 3, 10), new AddressDto("Berlin", "KantStr", 63), "Apple GmbH", 8000);

        // Мокаем поведение репозитория
        when(personRepository.findById(1000)).thenReturn(Optional.of(person));
        when(personRepository.findById(2000)).thenReturn(Optional.of(child));
        when(personRepository.findById(3000)).thenReturn(Optional.of(employee));

        // Мокаем маппинг
        when(modelMapper.map(person, PersonDto.class)).thenReturn(personDto);
        when(modelMapper.map(child, PersonDto.class)).thenReturn(childDto);
        when(modelMapper.map(employee, PersonDto.class)).thenReturn(employeeDto);

        // Проверяем, что метод возвращает корректные DTO
        assertEquals(personDto, personService.removePersonById(1000));
        assertEquals(childDto, personService.removePersonById(2000));
        assertEquals(employeeDto, personService.removePersonById(3000));

        // Проверяем, что объекты были удалены
        verify(personRepository).delete(person);
        verify(personRepository).delete(child);
        verify(personRepository).delete(employee);
    }

    @Test
    void updatePersonName_test() {
        // Пример 1: обычный Person
        Person person = new Person(1000, "John", LocalDate.of(2005, 1, 1), new Address("Berlin", "KantStr", 33));
        Person updatedPerson = new Person(1000, "Jonathan", LocalDate.of(2005, 1, 1), new Address("Berlin", "KantStr", 33));
        PersonDto updatedDto = new PersonDto(1000, "Jonathan", LocalDate.of(2005, 1, 1), new AddressDto("Berlin", "KantStr", 33));

        // Пример 2: Child
        Child child = new Child(2000, "Peter", LocalDate.of(2020, 5, 15), new Address("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");
        Child updatedChild = new Child(2000, "Petya", LocalDate.of(2020, 5, 15), new Address("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");
        ChildDto updatedChildDto = new ChildDto(2000, "Petya", LocalDate.of(2020, 5, 15), new AddressDto("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");

        // Пример 3: Employee
        Employee employee = new Employee(3000, "Karl", LocalDate.of(1990, 3, 10), new Address("Berlin", "KantStr", 63), "Apple GmbH", 8000);
        Employee updatedEmployee = new Employee(3000, "Carlos", LocalDate.of(1990, 3, 10), new Address("Berlin", "KantStr", 63), "Apple GmbH", 8000);
        EmployeeDto updatedEmployeeDto = new EmployeeDto(3000, "Carlos", LocalDate.of(1990, 3, 10), new AddressDto("Berlin", "KantStr", 63), "Apple GmbH", 8000);

        // Мокаем поведение репозитория
        when(personRepository.findById(1000)).thenReturn(Optional.of(person));
        when(personRepository.findById(2000)).thenReturn(Optional.of(child));
        when(personRepository.findById(3000)).thenReturn(Optional.of(employee));

        // Мокаем сохранение
        when(personRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Мокаем маппинг
        when(modelMapper.map(any(Person.class), ArgumentMatchers.<Class<PersonDto>>any()))
                .thenAnswer(invocation -> {
                    Person p = invocation.getArgument(0);
                    return new PersonDto(p.getId(), p.getName(), p.getBirthDate(),
                            new AddressDto(p.getAddress().getCity(), p.getAddress().getStreet(), p.getAddress().getBuilding()));
                });

        // Проверяем, что метод возвращает обновлённые DTO
        assertEquals("Jonathan", personService.updatePersonName(1000, "Jonathan").getName());
        assertEquals("Petya", personService.updatePersonName(2000, "Petya").getName());
        assertEquals("Carlos", personService.updatePersonName(3000, "Carlos").getName());
    }

    @Test
    void updatePersonAddress_test() {
        //Создаём новый адрес, который будет использоваться для обновления
        AddressDto newAddressDto = new AddressDto("Munich", "LudwigStr", 99);
        Address newAddress = new Address("Munich", "LudwigStr", 99);

        Person person = new Person(1000, "John", LocalDate.of(2005, 1, 1), new Address("Berlin", "KantStr", 33));
        Child child = new Child(2000, "Peter", LocalDate.of(2020, 5, 15), new Address("Berlin", "KantStr", 33), "Kindergarten Sonnenblume");
        Employee employee = new Employee(3000, "Karl", LocalDate.of(1990, 3, 10), new Address("Berlin", "KantStr", 63), "Apple GmbH", 8000);

        // Мокаем поведение репозитория: возвращаем соответствующие объекты по ID
        when(personRepository.findById(1000)).thenReturn(Optional.of(person));
        when(personRepository.findById(2000)).thenReturn(Optional.of(child));
        when(personRepository.findById(3000)).thenReturn(Optional.of(employee));

        //Мокаем преобразование AddressDto → Address
        when(modelMapper.map(newAddressDto, Address.class)).thenReturn(newAddress);

        //Мокаем сохранение: возвращаем тот же объект, который был передан
        when(personRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        //Мокаем преобразование Person → PersonDto с новым адресом
        when(modelMapper.map(any(Person.class), ArgumentMatchers.<Class<PersonDto>>any()))
                .thenAnswer(invocation -> {
                    Person p = invocation.getArgument(0);
                    Address a = p.getAddress();
                    return new PersonDto(p.getId(), p.getName(), p.getBirthDate(),
                            new AddressDto(a.getCity(), a.getStreet(), a.getBuilding()));
                });

        //Проверяем, что метод возвращает DTO с обновлённым адресом (город Munich)
        assertEquals("Munich", personService.updatePersonAddress(1000, newAddressDto).getAddress().getCity());
        assertEquals("Munich", personService.updatePersonAddress(2000, newAddressDto).getAddress().getCity());
        assertEquals("Munich", personService.updatePersonAddress(3000, newAddressDto).getAddress().getCity());
    }


    @Test
    void findPersonsByCity_test() {
        // Пример 1: Berlin
        List<Person> berlinPeople = List.of(
                new Person(1000, "John", LocalDate.of(2000, 1, 1), new Address("Berlin", "KantStr", 33)),
                new Child(2001, "Anna", LocalDate.of(2015, 6, 10), new Address("Berlin", "KantStr", 33), "Kindergarten A"),
                new Employee(2002, "Karl", LocalDate.of(1985, 3, 20), new Address("Berlin", "KantStr", 33), "Siemens", 7500)
        );

        // Пример 2: Munich
        List<Person> munichPeople = List.of(
                new Person(3000, "Lisa", LocalDate.of(1995, 7, 7), new Address("Munich", "LudwigStr", 12)),
                new Child(3001, "Tom", LocalDate.of(2016, 8, 8), new Address("Munich", "LudwigStr", 12), "Kindergarten B"),
                new Employee(3002, "Max", LocalDate.of(1980, 9, 9), new Address("Munich", "LudwigStr", 12), "BMW", 9000)
        );

        // Пример 3: Hamburg
        List<Person> hamburgPeople = List.of(
                new Person(4000, "Eva", LocalDate.of(1990, 2, 2), new Address("Hamburg", "AlsterStr", 5)),
                new Child(4001, "Ben", LocalDate.of(2014, 4, 4), new Address("Hamburg", "AlsterStr", 5), "Kindergarten C"),
                new Employee(4002, "Nina", LocalDate.of(1988, 12, 12), new Address("Hamburg", "AlsterStr", 5), "Otto", 8200)
        );

        // Мокаем поведение репозитория
        when(personRepository.findByAddressCityIgnoreCase("Berlin")).thenReturn(berlinPeople);
        when(personRepository.findByAddressCityIgnoreCase("Munich")).thenReturn(munichPeople);
        when(personRepository.findByAddressCityIgnoreCase("Hamburg")).thenReturn(hamburgPeople);

        // Мокаем маппинг каждого объекта в PersonDto
        when(modelMapper.map(any(Person.class), any())).thenAnswer(invocation -> {
            Person p = invocation.getArgument(0);
            return new PersonDto(p.getId(), p.getName(), p.getBirthDate(),
                    new AddressDto(p.getAddress().getCity(), p.getAddress().getStreet(), p.getAddress().getBuilding()));
        });


        // Проверяем, что метод возвращает массив из 3 DTO для каждого города
        assertEquals(3, personService.findPersonsByCity("Berlin").length);
        assertEquals(3, personService.findPersonsByCity("Munich").length);
        assertEquals(3, personService.findPersonsByCity("Hamburg").length);
    }

    @Test
    void findPersonsByName_test() {
        // Пример 1: "John"
        List<Person> johnList = List.of(
                new Person(1000, "John", LocalDate.of(1990, 1, 1), new Address("Berlin", "KantStr", 33)),
                new Employee(1001, "John", LocalDate.of(1985, 2, 2), new Address("Berlin", "KantStr", 33), "Siemens", 7000)
        );

        // Пример 2: "Anna"
        List<Person> annaList = List.of(
                new Child(2000, "Anna", LocalDate.of(2015, 6, 10), new Address("Munich", "LudwigStr", 12), "Kindergarten A")
        );

        // Пример 3: "Karl"
        List<Person> karlList = List.of(
                new Employee(3000, "Karl", LocalDate.of(1980, 3, 3), new Address("Hamburg", "AlsterStr", 5), "BMW", 9000),
                new Person(3001, "Karl", LocalDate.of(1995, 4, 4), new Address("Hamburg", "AlsterStr", 5))
        );

        // Мокаем поведение репозитория
        when(personRepository.findByNameIgnoreCase("John")).thenReturn(johnList);
        when(personRepository.findByNameIgnoreCase("Anna")).thenReturn(annaList);
        when(personRepository.findByNameIgnoreCase("Karl")).thenReturn(karlList);

        // Мокаем маппинг каждого объекта в PersonDto
        when(modelMapper.map(any(Person.class), ArgumentMatchers.<Class<PersonDto>>any()))
                .thenAnswer(invocation -> {
                    Person p = invocation.getArgument(0);
                    return new PersonDto(p.getId(), p.getName(), p.getBirthDate(),
                            new AddressDto(p.getAddress().getCity(), p.getAddress().getStreet(), p.getAddress().getBuilding()));
                });

        // Проверяем, что метод возвращает массив нужной длины
        assertEquals(2, personService.findPersonsByName("John").length);
        assertEquals(1, personService.findPersonsByName("Anna").length);
        assertEquals(2, personService.findPersonsByName("Karl").length);
    }

    @Test
    void findPersonsBetweenAge_test() {
        // Пример 1: возраст 5–10 лет
        List<Person> ageGroup1 = List.of(
                new Child(1000, "Tom", LocalDate.now().minusYears(6), new Address("Berlin", "KantStr", 33), "Kindergarten A"),
                new Child(1001, "Lena", LocalDate.now().minusYears(9), new Address("Berlin", "KantStr", 33), "Kindergarten B")
        );

        // Пример 2: возраст 20–30 лет
        List<Person> ageGroup2 = List.of(
                new Person(2000, "Alice", LocalDate.now().minusYears(25), new Address("Munich", "LudwigStr", 12)),
                new Employee(2001, "Bob", LocalDate.now().minusYears(28), new Address("Munich", "LudwigStr", 12), "BMW", 8500)
        );

        // Пример 3: возраст 60–70 лет
        List<Person> ageGroup3 = List.of(
                new Person(3000, "George", LocalDate.now().minusYears(65), new Address("Hamburg", "AlsterStr", 5)),
                new Employee(3001, "Martha", LocalDate.now().minusYears(68), new Address("Hamburg", "AlsterStr", 5), "Otto", 7200)
        );

        // Мокаем поведение репозитория
        when(personRepository.findByBirthDateBetween(
                LocalDate.now().minusYears(10), LocalDate.now().minusYears(5)))
                .thenReturn(ageGroup1);

        when(personRepository.findByBirthDateBetween(
                LocalDate.now().minusYears(30), LocalDate.now().minusYears(20)))
                .thenReturn(ageGroup2);

        when(personRepository.findByBirthDateBetween(
                LocalDate.now().minusYears(70), LocalDate.now().minusYears(60)))
                .thenReturn(ageGroup3);

        // Мокаем маппинг каждого объекта в PersonDto
        when(modelMapper.map(any(Person.class), ArgumentMatchers.<Class<PersonDto>>any()))
                .thenAnswer(invocation -> {
                    Person p = invocation.getArgument(0);
                    return new PersonDto(p.getId(), p.getName(), p.getBirthDate(),
                            new AddressDto(p.getAddress().getCity(), p.getAddress().getStreet(), p.getAddress().getBuilding()));
                });

        // Проверяем, что метод возвращает массив нужной длины
        assertEquals(2, personService.findPersonsBetweenAge(5, 10).length);
        assertEquals(2, personService.findPersonsBetweenAge(20, 30).length);
        assertEquals(2, personService.findPersonsBetweenAge(60, 70).length);
    }

    @Test
    void getCityPopulation_test() {
        // Пример 1: Berlin — 5 человек
        CityPopulationDto berlin = new CityPopulationDto("Berlin", 5L);

        // Пример 2: Munich — 3 человека
        CityPopulationDto munich = new CityPopulationDto("Munich", 3L);

        // Пример 3: Hamburg — 7 человек
        CityPopulationDto hamburg = new CityPopulationDto("Hamburg", 7L);

        List<CityPopulationDto> populationList = List.of(berlin, munich, hamburg);

        // Мокаем поведение репозитория
        when(personRepository.getCityPopulation()).thenReturn(populationList);

        // Вызываем метод
        Iterable<CityPopulationDto> result = personService.getCityPopulation();

        // Проверяем, что результат содержит все три города
        List<CityPopulationDto> resultList = StreamSupport.stream(result.spliterator(), false).toList();

        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(berlin));
        assertTrue(resultList.contains(munich));
        assertTrue(resultList.contains(hamburg));
    }

    @Test
    void findEmployeeBySalary_test() {
        // Пример 1: зарплата 3000–5000
        List<Employee> group1 = List.of(
                new Employee(1000, "Alice", LocalDate.of(1990, 1, 1), new Address("Berlin", "KantStr", 33), "Company A", 4000),
                new Employee(1001, "Bob", LocalDate.of(1985, 2, 2), new Address("Berlin", "KantStr", 33), "Company B", 5000)
        );

        // Пример 2: зарплата 6000–8000
        List<Employee> group2 = List.of(
                new Employee(2000, "Charlie", LocalDate.of(1980, 3, 3), new Address("Munich", "LudwigStr", 12), "Company C", 7000)
        );

        // Пример 3: зарплата 9000–12000
        List<Employee> group3 = List.of(
                new Employee(3000, "Diana", LocalDate.of(1975, 4, 4), new Address("Hamburg", "AlsterStr", 5), "Company D", 10000),
                new Employee(3001, "Ethan", LocalDate.of(1970, 5, 5), new Address("Hamburg", "AlsterStr", 5), "Company E", 11000)
        );

        // Мокаем поведение репозитория
        when(personRepository.findBySalaryBetween(3000, 5000)).thenReturn(group1);
        when(personRepository.findBySalaryBetween(6000, 8000)).thenReturn(group2);
        when(personRepository.findBySalaryBetween(9000, 12000)).thenReturn(group3);

        // Мокаем маппинг каждого объекта в EmployeeDto
        when(modelMapper.map(any(Employee.class), ArgumentMatchers.<Class<EmployeeDto>>any()))
                .thenAnswer(invocation -> {
                    Employee e = invocation.getArgument(0);
                    return new EmployeeDto(e.getId(), e.getName(), e.getBirthDate(),
                            new AddressDto(e.getAddress().getCity(), e.getAddress().getStreet(), e.getAddress().getBuilding()),
                            e.getCompany(), e.getSalary());
                });

        // Проверяем, что метод возвращает список нужной длины
        assertEquals(2, ((List<?>) personService.findEmployeeBySalary(3000, 5000)).size());
        assertEquals(1, ((List<?>) personService.findEmployeeBySalary(6000, 8000)).size());
        assertEquals(2, ((List<?>) personService.findEmployeeBySalary(9000, 12000)).size());
    }

    @Test
    void findAllChildren_test() {
        // Примеры детей
        Child child1 = new Child(1000, "Tom", LocalDate.of(2018, 1, 1), new Address("Berlin", "KantStr", 33), "Kindergarten A");
        Child child2 = new Child(1001, "Lena", LocalDate.of(2017, 2, 2), new Address("Munich", "LudwigStr", 12), "Kindergarten B");
        Child child3 = new Child(1002, "Max", LocalDate.of(2016, 3, 3), new Address("Hamburg", "AlsterStr", 5), "Kindergarten C");

        List<Child> children = List.of(child1, child2, child3);

        // Ожидаемые DTO
        ChildDto dto1 = new ChildDto(1000, "Tom", LocalDate.of(2018, 1, 1), new AddressDto("Berlin", "KantStr", 33), "Kindergarten A");
        ChildDto dto2 = new ChildDto(1001, "Lena", LocalDate.of(2017, 2, 2), new AddressDto("Munich", "LudwigStr", 12), "Kindergarten B");
        ChildDto dto3 = new ChildDto(1002, "Max", LocalDate.of(2016, 3, 3), new AddressDto("Hamburg", "AlsterStr", 5), "Kindergarten C");

        // Мокаем поведение репозитория
        when(personRepository.findChildBy()).thenReturn(children);

        // Мокаем маппинг каждого ребёнка
        when(modelMapper.map(child1, ChildDto.class)).thenReturn(dto1);
        when(modelMapper.map(child2, ChildDto.class)).thenReturn(dto2);
        when(modelMapper.map(child3, ChildDto.class)).thenReturn(dto3);

        // Вызываем метод
        Iterable<ChildDto> result = personService.findAllChildren();

        // Проверяем результат
        List<ChildDto> resultList = StreamSupport.stream(result.spliterator(), false).toList();

        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(dto1));
        assertTrue(resultList.contains(dto2));
        assertTrue(resultList.contains(dto3));
    }

    @Test
    void testRun_initializesDataWhenRepositoryIsEmpty() throws Exception {
        when(personRepository.count()).thenReturn(0L);

        personService.run();

        verify(personRepository, times(3)).save(any(Person.class));
    }
}