package cohort_65.java.personhomework.accounting.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private AdressDto adress;
}
