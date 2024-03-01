package org.library_gabs.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.library_gabs.validation.NotEmptyList;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    @NotNull(message = "{field.code-student.required}")
    private Integer student;
    @NotEmptyList(message = "{field.items-order.required}")
    private List<ItemLoanDTO> items;
}
