package com.ohgiraffers.crud_back.program.controller;

import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;
import com.ohgiraffers.crud_back.program.service.Program2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/program")
public class Program2Controller {
    private final Program2Service program2Service;

    @Autowired
    public Program2Controller(Program2Service program2Service) {
        this.program2Service = program2Service;
    }

    @GetMapping("/allprogram2")
    public List<Program2DTO> getEduProgram() {
        return program2Service.getEduProgram();
    }

}