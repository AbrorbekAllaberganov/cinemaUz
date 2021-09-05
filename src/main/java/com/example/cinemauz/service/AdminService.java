package com.example.cinemauz.service;

import com.example.cinemauz.entity.Admin;
import com.example.cinemauz.payload.AdminRequest;
import com.example.cinemauz.repository.AdminRepository;
import com.example.cinemauz.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final Logger logger= LoggerFactory.getLogger(AdminService.class);

    public boolean save(AdminRequest adminRequest)
    {
        Admin admin=new Admin();
        admin.setEmail(adminRequest.getEmail());
        admin.setFullName(adminRequest.getFullname());
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        admin.setRoles(new ArrayList<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        admin.setUsername(adminRequest.getUsername());
        admin.setPhoneNumber(adminRequest.getPhoneNumber());

        return adminRepository.save(admin)!=null;

    }

    public boolean delete(Long id)
    {
        try {
            Admin admin=adminRepository.getById(id);
            adminRepository.delete(admin);
            return true;
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        return false;
    }

    public List<Admin> getAll()
    {
        List<Admin>admins=adminRepository.findAll();
        return admins;
    }
    public Admin getAdminByUserName(String userName)
    {
        Admin admin=adminRepository.findByUsername(userName);
        return admin;
    }



}
