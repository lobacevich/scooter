package by.senla.training.lobacevich.scooter.exceptionhandler;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.UpdateException;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Log4j2
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class,
            CreationException.class, UpdateException.class})
    public ResponseEntity<MessageResponse> getCustomException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse> getAccessDeniedException(Exception e) {
        log.error("{} : {}",e.getClass(), e.getMessage());
        return new ResponseEntity<>(new MessageResponse(
                "User does not have permission to access this resource"), HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> getException(Exception e) {
        log.error("{} : {}",e.getClass(), e.getMessage());
        return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        log.error("Request parameter {} is required", ex.getParameterName());
        return new ResponseEntity<>(new MessageResponse(ex.getParameterName() + " is required"),
                HttpStatus.BAD_REQUEST);
    }
}
