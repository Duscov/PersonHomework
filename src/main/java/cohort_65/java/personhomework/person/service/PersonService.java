package cohort_65.java.personhomework.person.service;

import cohort_65.java.personhomework.person.dto.AddressDto;
import cohort_65.java.personhomework.person.dto.CityPopulationDto;
import cohort_65.java.personhomework.person.dto.PersonDto;

public interface PersonService {
    boolean addPerson(PersonDto personDto);

    PersonDto findPersonById(Integer id);

    PersonDto removePersonById(Integer id);

    PersonDto updatePersonName(Integer id, String name);

    PersonDto updatePersonAddress(Integer id, AddressDto addressDto);

    PersonDto[] findPersonsByCity(String city);

    PersonDto[] findPersonsByName(String name);

    PersonDto[] findPersonsBetweenAge(Integer from, Integer to);

    Iterable<CityPopulationDto> getCityPopulation();
}
