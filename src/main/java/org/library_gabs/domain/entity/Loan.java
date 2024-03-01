package org.library_gabs.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.library_gabs.domain.enums.StatusLoan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Column(name = "date_loan")
    private LocalDate dateLoan;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusLoan status;
    @OneToMany(mappedBy = "loan")
    private List<ItemLoan> items;

    public List<ItemLoan> getItems() {
        if(this.items == null) {
            this.items = new ArrayList<>();
        }
        return this.items;
    }
}
