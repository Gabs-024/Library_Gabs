package org.library_gabs.service;

import org.library_gabs.api.DTO.LoanDTO;
import org.library_gabs.domain.entity.Loan;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LoansService {
    @Transactional
    Loan save(LoanDTO dto);

    Optional<Loan> obtainAllOrder (Integer id);
}
