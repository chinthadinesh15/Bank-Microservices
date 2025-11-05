package in.dinesh.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Schema(
        name = "ErrorResponseDto",
        description = "Schema to hold Error details"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema( description = "API Path")
    private String apiPath;

    @Schema( description = "Error Code")
    private HttpStatus errorCode;

    private String errorMessage;

    private String errorTime;
}
