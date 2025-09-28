package cohort_65.java.personhomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/* Аннотация @SpringBootApplication объединяет:
 * - @Configuration: позволяет определять бины в Spring контейнере.
 * - @EnableAutoConfiguration: включает автоматическую конфигурацию Spring.
 * - @ComponentScan: автоматически сканирует пакеты для поиска компонентов. */
@SpringBootApplication
public class PersonHomeWorkApplication {
    /* Метод main запускает приложение, используя SpringApplication.run().
     * @param args параметры командной строки*/
    public static void main(String[] args) {
        SpringApplication.run(PersonHomeWorkApplication.class, args);
    }
}