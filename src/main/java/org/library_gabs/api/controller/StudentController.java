package org.library_gabs.api.controller;

import io.swagger.annotations.*;
import org.library_gabs.domain.entity.Student;
import org.library_gabs.domain.repository.StudentsRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@Api("Api Estudantes")
public class StudentController {

    private StudentsRepository studentsRepository;

    public StudentController(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @GetMapping("{id}")
    @ApiOperation("Obtem destalhes de um estudante.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estudante encontrado."),
            @ApiResponse(code = 404, message = "Estudante não encontrado.")
    })
    public Student getStudentById(@PathVariable @ApiParam("ID do Estudante") Integer id) {
        return studentsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Estudante não encontrado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo estudante.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estudante salvo com sucesso."),
            @ApiResponse(code = 404, message = "Estudante não encontrado.")
    })
    public Student save (@RequestBody @Valid Student student) {
        return studentsRepository.save(student);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Apaga o estudante do banco de dados.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estudante excluído com sucesso."),
            @ApiResponse(code = 404, message = "Estudante não encontrado.")
    })
    public void delete(@PathVariable Integer id) {
        studentsRepository.findById(id).map(student -> {
            studentsRepository.delete(student);
            return student;
        }).orElseThrow( () -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Estudante não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza as informações do estudante.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estudante atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Estudante não encontrado.")
    })
    public void update(@PathVariable Integer id, @RequestBody @Valid Student student){
        studentsRepository.findById(id).map(studentExist -> {
            student.setId(studentExist.getId());
            studentsRepository.save(student);
            return studentExist;
        }).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Estudante não encontrado."));
    }

    @GetMapping
    @ApiOperation("Lista todos os estudantes cadastrados")
    public List<Student> find (Student studentfilter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(studentfilter, matcher);
        return studentsRepository.findAll(example);
    }
}
