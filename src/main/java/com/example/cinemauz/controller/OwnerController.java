package com.example.cinemauz.controller;

import com.example.cinemauz.payload.AdminRequest;
import com.example.cinemauz.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final AdminService adminService;

    @PostMapping("/add-admin")
    public ResponseEntity saveAdmin(@RequestBody AdminRequest adminRequest)
    {
        return adminService.save(adminRequest)?ResponseEntity.ok("Saqlandi")
                :new ResponseEntity("Xatolik", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Long id)
    {
        return adminService.delete(id)?ResponseEntity.ok("O'chirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/get-admin")
    public ResponseEntity getAllAdmins()
    {
        return ResponseEntity.ok(adminService.getAll());
    }
    @GetMapping("/get-admin/{userName}")
    public ResponseEntity getAllAdminByUserName(@PathVariable String userName)
    {
        return ResponseEntity.ok(adminService.getAdminByUserName(userName));
    }
}
