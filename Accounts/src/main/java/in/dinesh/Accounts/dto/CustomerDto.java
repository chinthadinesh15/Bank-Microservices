package in.dinesh.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer & Account details"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer",
            example = "Dinesh Babu"
    )
    @NotNull(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "dinesh.babu@gmail.com"
    )
    @NotNull(message = "Email cannot be null or empty")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "6300151568"
    )
    @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
