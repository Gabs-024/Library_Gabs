package org.library_gabs.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationLoanDTO {
    private Integer codigo;
    private String student;
    private String status;
    private String dateLoan;
    private List<InformationItemLoanDTO> items;
}
