package pl.patrykkukula.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ErrorResponseDto {
    private String statusCode;
    private String statusMessage;
    private String errorMessage;
    private String path;
    private LocalDateTime timestamp;
}
