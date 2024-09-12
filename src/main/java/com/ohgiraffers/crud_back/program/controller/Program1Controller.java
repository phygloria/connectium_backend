package com.ohgiraffers.crud_back.program.controller;

import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import com.ohgiraffers.crud_back.program.service.Program1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/program")
public class Program1Controller {

    private final Program1Service program1Service;

    @Autowired
    public Program1Controller(Program1Service program1Service) {
        this.program1Service = program1Service;
    }

    @GetMapping("/allprogram1")
    public List<Program1DTO> getCulProgram() {
        return program1Service.getCulProgram();
    }
}
