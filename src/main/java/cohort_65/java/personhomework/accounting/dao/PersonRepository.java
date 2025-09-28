package cohort_65.java.personhomework.accounting.dao;

import cohort_65.java.personhomework.accounting.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
