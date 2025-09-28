package cohort_65.java.personhomework.configuartion;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурирует бин ModelMapper для автосвязывания в Spring-контейнере.
 */
@Configuration
public class PersonConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}