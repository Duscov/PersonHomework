package cohort_65.java.personhomework.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 ошибка
public class InvalidAdressException extends RuntimeException {
    public InvalidAdressException(String message) {
        super(message);
    }
}
