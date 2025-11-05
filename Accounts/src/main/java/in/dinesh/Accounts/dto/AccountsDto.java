package in.dinesh.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account details"
)
@Data
public class AccountsDto {

    @Pattern(regexp = "$|[0-9]{10}", message = "Account number must be 10 digits")
    @NotNull(message = "Account number cannot be null or empty")
    private Long accountNumber;

    @Schema(
            description = "Type of the account",
            example = "Savings"
    )
    @NotNull(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Branch address of the account",
            example = "101, Main Street, New York"
    )
    @NotNull(message = "Branch address cannot be null or empty")
    private String branchAddress;
}
