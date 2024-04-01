package com.project.restaurant.controllers;

import com.project.restaurant.models.Role;
import com.project.restaurant.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Annotation đánh dấu một lớp Java như là một RESTful Controller.
@RequestMapping("${api.prefix}/roles") //Annotation xử lý các yêu cầu HTTP trong một RESTful Controller là @RequestMapping
@RequiredArgsConstructor
//Annotation tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.
public class RoleController {

    //DI
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
