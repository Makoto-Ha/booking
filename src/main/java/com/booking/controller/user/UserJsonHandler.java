package com.booking.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.booking.bean.dto.user.UserDTO;

import com.booking.service.user.UserService;
import com.booking.utils.Result;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class UserJsonHandler {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Integer id) {
        Result<UserDTO> result = userService.findUserById(id);
        if (result.isFailure()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getData(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> findUserByName(@RequestParam String name) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(name);
        Result<PageImpl<UserDTO>> result = userService.findUsers(userDTO);
        if (result.isFailure()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getData().getContent(), HttpStatus.OK);
    }

    @GetMapping("/api/user/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "userId") String orderBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPageNumber(page);
        userDTO.setAttrOrderBy(orderBy);
        userDTO.setSelectedSort(ascending);
        Result<PageImpl<UserDTO>> result = userService.findUserAll(userDTO);
        if (result.isFailure()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result.getData().getContent(), HttpStatus.OK);
    }

    
}