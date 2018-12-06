package com.login.GP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.login.GP.Repository.ClassroomRepository;

@RestController
public class ClassroomController {
	@Autowired
	ClassroomRepository classroomRepo;
}
