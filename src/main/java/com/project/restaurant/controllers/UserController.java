package com.project.restaurant.controllers;

import com.project.restaurant.dtos.UserDTO;
import com.project.restaurant.models.User;
import com.project.restaurant.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {

        try {
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            User newUser =  userService.createUser(userDTO);

            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            User existingUser = userService.getUserById(id);
            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getUser(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sắp xếp theo thứ tự createdAt giảm dần
                Sort.by("hireDate").descending());

        Page<User> userPage = userService.getAllUsers(pageRequest);

        int totalPages = userPage.getTotalPages();

        List<User> users = userPage.getContent();

        return ResponseEntity.ok(users);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        try {
            User updateUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updateUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            User existingEmployee = userService.getUserById(id);
            if(existingEmployee != null) {
                userService.deleteUser(id);
            }
            return ResponseEntity.ok("Deleted user successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
