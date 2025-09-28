package cohort_65.java.personhomework.accounting.dao;

import cohort_65.java.personhomework.accounting.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для работы с сущностями Person.
 * Все CRUD-операции реализуются автоматически Spring Data JPA.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByAdress_City(String city);

    List<Person> findByBirthDateBetween(LocalDate from, LocalDate to);
}
