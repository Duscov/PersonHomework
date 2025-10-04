package cohort_65.java.personhomework.person.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildDto extends PersonDto {
    String kindergarten;

    public ChildDto(int id, String name, LocalDate of, AddressDto addressDto, String kindergartenName) {
    }
}
