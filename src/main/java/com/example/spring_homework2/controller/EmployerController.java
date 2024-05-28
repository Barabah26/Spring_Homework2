package com.example.spring_homework2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employer")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class EmployerController {
}
