package com.booking.controller.admin;

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

import com.booking.bean.dto.admin.AdminDTO;
import com.booking.service.admin.AdminService;
import com.booking.utils.Result;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class AdminJsonHandler {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/{id}")
    public ResponseEntity<AdminDTO> findAdminById(@PathVariable Integer id) {
        Result<AdminDTO> result = adminService.findAdminById(id);
        if (result.isFailure()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getData(), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<AdminDTO>> findAdminByName(@RequestParam String name) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName(name);
        Result<PageImpl<AdminDTO>> result = adminService.findAdmins(adminDTO);
        if (result.isFailure()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getData().getContent(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdminDTO>> getAllAdmins(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "adminId") String orderBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setPageNumber(page);
        adminDTO.setAttrOrderBy(orderBy);
        adminDTO.setSelectedSort(ascending);
        Result<PageImpl<AdminDTO>> result = adminService.findAdminAll(adminDTO);
        if (result.isFailure()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result.getData().getContent(), HttpStatus.OK);
    }

    @GetMapping("/check-account")
    public ResponseEntity<Boolean> checkAccountExists(@RequestParam String account) {
        Result<Boolean> result = adminService.checkAccountExists(account);
        return new ResponseEntity<>(result.getData(), HttpStatus.OK);
    }
}