package cohort_65.java.personhomework.accounting.model;

import jakarta.persistence.Embeddable;                  // Указывает, что класс можно встроить
import lombok.*;

@Embeddable                  // Позволяет использовать Address как часть другой сущности
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adress {
    private String city;
    private String street;
    private String building;
}
