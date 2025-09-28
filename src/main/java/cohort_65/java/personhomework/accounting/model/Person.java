package cohort_65.java.personhomework.accounting.model;

import jakarta.persistence.*;                 // Импорт аннотаций JPA
import lombok.*;                             // Импорт аннотаций Lombok
import java.time.LocalDate;                  // Импорт типа для даты рождения

@Entity                                    // Указывает, что класс является JPA-сущностью
@Table(name = "persons")                   // Имя таблицы в базе данных
@Data                     // Lombok: генерирует геттеры, сеттеры, toString, equals, hashCode
@NoArgsConstructor                         // Lombok: генерирует конструктор без аргументов
@AllArgsConstructor                    // Lombok: генерирует конструктор со всеми аргументами
public class Person {

    @Id                                    // Указывает, что поле — первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) //          Автоинкремент
    private Long id;

    private String name;

    private int age;

    private LocalDate birthDate;

    @Embedded                          // Встраивает объект Adress как часть таблицы Person
    private Adress adress;
}
