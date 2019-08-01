package pers.xiaoming.elasticsearch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ExceptionResolver {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("blog not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("search must contains either author or title", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleOtherException(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>("Internal Server error" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
