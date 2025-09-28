package cohort_65.java.personhomework.accounting.dao;

import cohort_65.java.personhomework.accounting.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностями Person.
 * Все CRUD-операции реализуются автоматически Spring Data JPA.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
