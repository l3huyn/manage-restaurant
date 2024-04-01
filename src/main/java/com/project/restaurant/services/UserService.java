package com.project.restaurant.services;

import com.project.restaurant.dtos.UserDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.DuplicateDataException;
import com.project.restaurant.exceptions.PermissionDenyException;
import com.project.restaurant.models.Role;
import com.project.restaurant.models.User;
import com.project.restaurant.repositories.RoleRepository;
import com.project.restaurant.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {

        String phoneNumber = userDTO.getPhoneNumber();

        //Kiểm tra sđt có tồn tại hay không
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateDataException("Phone number already exists");
        }

        String email = userDTO.getEmail();

        //Kiểm tra email có tồn tại hay không
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateDataException("Email already exists");
        }

        //Kiểm tra role
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role does not exists"));

        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You cannot register an admin account");
        }

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .position(userDTO.getPosition())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .hireDate(userDTO.getHireDate())
                .salary(userDTO.getSalary())
                .build();
        //set role
        newUser.setRole(role);

        //save DB
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(long id) throws Exception {

        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with ID: " + id));
    }

    @Override
    public Page<User> getAllUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User updateUser(long id, UserDTO employeeDTO) throws Exception {
        User existingUser = getUserById(id);

        if(existingUser != null) {
            existingUser.setFullName(employeeDTO.getFullName());
            existingUser.setPosition(employeeDTO.getPosition());
            existingUser.setPhoneNumber(employeeDTO.getPhoneNumber());
            existingUser.setEmail(employeeDTO.getEmail());
            existingUser.setAddress(employeeDTO.getAddress());
            existingUser.setHireDate(employeeDTO.getHireDate());
            existingUser.setSalary(employeeDTO.getSalary());
        }
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> deleteEmployee = userRepository.findById(id);

       deleteEmployee.ifPresent(userRepository::delete);
    }
}
