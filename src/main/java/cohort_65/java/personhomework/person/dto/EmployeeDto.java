package cohort_65.java.personhomework.person.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmployeeDto extends PersonDto {
    String company;
    int salary;

    public EmployeeDto(int id, String name, LocalDate of, AddressDto addressDto, String firmenName, int salary) {
    }
}
