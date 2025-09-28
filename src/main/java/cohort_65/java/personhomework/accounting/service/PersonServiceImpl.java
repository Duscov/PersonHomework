package cohort_65.java.personhomework.accounting.service;

import cohort_65.java.personhomework.accounting.dto.PersonDto;
import cohort_65.java.personhomework.accounting.model.Person;
import cohort_65.java.personhomework.accounting.dao.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        // Маппинг PersonDto → Person через ModelMapper
        Person person = modelMapper.map(personDto, Person.class);
        person =  personRepository.save(person);
        // id в Person будет взят из personDto, adress — из personDto.getAdress
        return modelMapper.map(person, Person.class);
    }
}