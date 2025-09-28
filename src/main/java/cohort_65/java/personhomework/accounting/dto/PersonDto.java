package cohort_65.java.personhomework.accounting.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private String name;
    private int age;
    private LocalDate birthDate;
    private AdressDto address;
}
