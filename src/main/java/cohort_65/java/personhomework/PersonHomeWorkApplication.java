package cohort_65.java.personhomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/* Аннотация @SpringBootApplication объединяет:
 * - @Configuration: позволяет определять бины в Spring контейнере.
 * - @EnableAutoConfiguration: включает автоматическую конфигурацию Spring.
 * - @ComponentScan: автоматически сканирует пакеты для поиска компонентов. */
@SpringBootApplication
public class PersonHomeWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonHomeWorkApplication.class, args);
    }

}