package cohort_65.java.personhomework.person.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "employee", value = EmployeeDto.class),
        @JsonSubTypes.Type(name = "child", value = ChildDto.class),
        @JsonSubTypes.Type(name = "person", value = PersonDto.class)
})
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    Integer id;
    String name;
    LocalDate birthDate;
    AddressDto address;
}