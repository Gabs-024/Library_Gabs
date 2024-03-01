package org.library_gabs.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLoanDTO {
    private Integer id;
    private String book;
    private String item;
}
