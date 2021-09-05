package com.example.cinemauz.loader;



import com.example.cinemauz.entity.Admin;
import com.example.cinemauz.entity.Role;
import com.example.cinemauz.repository.AdminRepository;
import com.example.cinemauz.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
        @Value("${spring.jpa.hibernate.ddl-auto}")
        private String init;

        private final RoleRepository roleRepository;
        private final AdminRepository adminRepository;
        private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {


        try {
            if (init.equalsIgnoreCase("create"))
            {
                Role roleUser=new Role();
                roleUser.setId(1L);
                roleUser.setName("ROLE_USER");

                Role roleAdmin=new Role();
                roleAdmin.setId(2L);
                roleAdmin.setName("ROLE_ADMIN");

                List<Role> roleList=new ArrayList<>(Arrays.asList(roleUser,roleAdmin));
                roleList=roleRepository.saveAll(roleList);

                Admin admin=new Admin();
                admin.setRoles(roleList);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("111"));
                admin.setFullName("Abror Allaberganov");
                admin.setEmail("abror.developer@gmail.com");
                admin.setPhoneNumber("+998974200730");

                adminRepository.save(admin);
            }


        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
