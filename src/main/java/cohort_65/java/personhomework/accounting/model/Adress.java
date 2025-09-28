package cohort_65.java.personhomework.accounting.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Embeddable                  // Позволяет использовать Address как часть другой сущности
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adress {
    private String city;
    private String street;
    private String building;
}
