package org.library_gabs.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.library_gabs.api.DTO.InformationItemLoanDTO;
import org.library_gabs.api.DTO.InformationLoanDTO;
import org.library_gabs.api.DTO.LoanDTO;
import org.library_gabs.domain.entity.ItemLoan;
import org.library_gabs.domain.entity.Loan;
import org.library_gabs.service.LoansService;
import org.library_gabs.service.impl.LoanServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
@Api("Api Emprestimos")
public class LoanController {

    private LoansService loansService;

    public LoanController(LoanServiceImpl loanServiceImplt) {
        this.loansService = loanServiceImplt;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido criado com sucesso."),
            @ApiResponse(code = 404, message = "Estudante não encontrado.")
    })
    public Integer save(@RequestBody @Valid LoanDTO dto) {
        Loan loan = loansService.save(dto);
        return loan.getId();
    }

    @GetMapping("{id}")
    @ApiOperation("Obtem detalhes de um pedido")
    public InformationLoanDTO getById(@PathVariable Integer id) {
        return loansService
                .obtainAllOrder(id)
                .map(p -> convert(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    private InformationLoanDTO convert (Loan loan) {
        return InformationLoanDTO
                .builder()
                .codigo(loan.getId())
                .student(loan.getStudent().getEnrollment())
                .dateLoan(loan.getDateLoan().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .status(loan.getStatus().toString())
                .items(convert(loan.getItems()))
                .build();
    }

    private List<InformationItemLoanDTO> convert(List<ItemLoan> orders) {
        if(CollectionUtils.isEmpty(orders)){
            return Collections.emptyList();
        }
        return (List<InformationItemLoanDTO>) orders.stream().map(
                itemLoan -> InformationItemLoanDTO
                        .builder()
                        .editor((itemLoan.getBook().getEditor()))
                        .title((itemLoan.getBook().getTitle()))
                        .build()
        ).collect(Collectors.toList());
    }
}
