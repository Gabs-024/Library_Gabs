package org.library_gabs.service.impl;

import lombok.RequiredArgsConstructor;
import org.library_gabs.api.DTO.InformationItemLoanDTO;
import org.library_gabs.api.DTO.ItemLoanDTO;
import org.library_gabs.api.DTO.LoanDTO;
import org.library_gabs.domain.entity.Book;
import org.library_gabs.domain.entity.ItemLoan;
import org.library_gabs.domain.entity.Loan;
import org.library_gabs.domain.entity.Student;
import org.library_gabs.domain.enums.StatusLoan;
import org.library_gabs.domain.repository.BooksRepository;
import org.library_gabs.domain.repository.ItensLoanRepository;
import org.library_gabs.domain.repository.LoanRepository;
import org.library_gabs.domain.repository.StudentsRepository;
import org.library_gabs.exception.BusinessRuleException;
import org.library_gabs.service.LoansService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoansService {

    private final LoanRepository loanRepository;
    private final StudentsRepository studentsRepository;
    private final BooksRepository booksRepository;
    private final ItensLoanRepository itensLoanRepository;

    @Override
    @Transactional
    public Loan save(LoanDTO dto) {
        Integer idStudent = dto.getStudent();
        Student student = studentsRepository
                .findById(idStudent)
                .orElseThrow(() -> new BusinessRuleException("Código de estudante inválido!"));

        Loan loan = new Loan();
        loan.setDateLoan(LocalDate.now());
        loan.setStudent(student);
        loan.setStatus(StatusLoan.REALIZADO);

        List<ItemLoan> itemLoan = convertItems(loan, dto.getItems());
        loanRepository.save(loan);
        itensLoanRepository.saveAll(itemLoan);
        loan.setItems(itemLoan);
        return loan;
    }

    @Override
    public Optional<Loan> obtainAllOrder(Integer id) {
        return loanRepository.findById(id);
    }

    private List<ItemLoan> convertItems(Loan loan, List<ItemLoanDTO> items) {
        if(items == null || items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return items.stream()
                .map(dto -> {
                    Integer id = dto.getId();
                    String title = dto.getBook();
                    Book book = booksRepository
                            .findById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                    ItemLoan itemLoan = new ItemLoan();
                    itemLoan.setLoan(loan);
                    itemLoan.setBook(book);
                    return itemLoan;
                }).collect(Collectors.toList());
    }
}
