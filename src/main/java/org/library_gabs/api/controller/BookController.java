package org.library_gabs.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.library_gabs.domain.entity.Book;
import org.library_gabs.domain.repository.BooksRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private BooksRepository booksRepository;

    public BookController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping("{id}")
    @ApiOperation("Obtem os detalhes do livro")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Livro encontrado."),
            @ApiResponse(code = 404, message = "Livro não encontrado.")
    })
    public Book getBookById(@PathVariable Integer id) {
        return booksRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    @GetMapping("{editor}")
    @ApiOperation("Obtem os livros pelo editor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Editor encontrado."),
            @ApiResponse(code = 404, message = "Editor não encontrado.")
    })
    public Book getBookByEditor(@PathVariable String editor) {
        return (Book) booksRepository
                .findbyEditor(editor);
    }

    @GetMapping("{book}")
    @ApiOperation("Obtem os livros pelo nome")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Livro encontrado."),
            @ApiResponse(code = 404, message = "Livro não encontrado.")
    })
    public Book getBookByName(@PathVariable String name) {
        return (Book) booksRepository
                .findbyBookName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo livro")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Livro criado com sucesso."),
            @ApiResponse(code = 404, message = "Livro não encontrado.")
    })
    public Book save(@RequestBody @Valid Book book) {
        return booksRepository.save(book);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Apaga o livro do banco de dados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Livro encontrado."),
            @ApiResponse(code = 404, message = "Livro não encontrado.")
    })
    public void delete(@PathVariable Integer id) {
        booksRepository.findById(id).map( book -> {
            booksRepository.delete(getBookById(id));
            return book;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza os detalhes do livro")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Livro atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Livro não encontrado.")
    })
    public void update(@PathVariable Integer id, @RequestBody @Valid Book book) {
        booksRepository.findById(id).map(bookExist -> {
            book.setId(bookExist.getId());
            booksRepository.save(book);
            return bookExist;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));
    }

    @GetMapping
    @ApiOperation("Lista todos os livros cadastrados")
    public List<Book> findAll (Book bookFilter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(bookFilter, matcher);
        return booksRepository.findAll(example);
    }
}
