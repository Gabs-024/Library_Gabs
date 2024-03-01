package org.library_gabs.domain.entity;

import java.util.Set;
import javax.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.library_gabs.domain.enums.StatusLoan;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    @NotEmpty(message = "{field.name.required}")
    private String title;
    @Column(name = "editor")
    @NotEmpty(message = "{field.editor.required}")
    private String editor;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusLoan status;
}
