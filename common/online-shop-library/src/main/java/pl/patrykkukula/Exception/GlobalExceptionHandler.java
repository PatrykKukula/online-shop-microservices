package pl.patrykkukula.Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import pl.patrykkukula.Constants.StatusResponse;
import pl.patrykkukula.Dto.ErrorResponseDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        StatusResponse.STATUS_404,
                        StatusResponse.STATUS_404_MESSAGE,
                        ex.getMessage(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(InvalidIDException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidIDException(InvalidIDException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(
                        StatusResponse.STATUS_400,
                        StatusResponse.STATUS_400_MESSAGE,
                        ex.getMessage(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(
                        StatusResponse.STATUS_400,
                        StatusResponse.STATUS_400_MESSAGE,
                        ex.getMessage(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMethodNotReadableException(HttpMessageNotReadableException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(
                        StatusResponse.STATUS_400,
                        StatusResponse.STATUS_400_MESSAGE,
                        ex.getMessage(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(
                        StatusResponse.STATUS_400,
                        StatusResponse.STATUS_400_MESSAGE,
                        ex.getMessage(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }
}
