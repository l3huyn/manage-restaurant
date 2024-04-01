package com.project.restaurant.services;

import com.project.restaurant.dtos.UserDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.DuplicateDataException;
import com.project.restaurant.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    User getUserById(long id) throws Exception;

    Page<User> getAllUsers(PageRequest pageRequest);

    User updateUser(long id, UserDTO userDTO) throws Exception;

    void deleteUser(long id);

}
