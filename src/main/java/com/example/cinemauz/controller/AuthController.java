package com.example.cinemauz.controller;

import com.example.cinemauz.entity.Admin;
import com.example.cinemauz.entity.Attachment;
import com.example.cinemauz.payload.LoginPayload;
import com.example.cinemauz.repository.AdminRepository;
import com.example.cinemauz.repository.MovieRepository;
import com.example.cinemauz.security.JwtTokenProvider;

import com.example.cinemauz.service.AttachmentService;
import com.example.cinemauz.service.JanrService;
import com.example.cinemauz.service.MovieServie;
import com.example.cinemauz.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AttachmentService attachmentService;
    private final MovieServie movieServie;
    private final TypeService typeService;
    private final JanrService janrService;

    @Value("${upload.folder}")
    private String uploadFolder;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginPayload payload)
    {
        Admin admin=adminRepository.findByUsername(payload.getUserName());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUserName(),payload.getPassword()));
        String token=jwtTokenProvider.createToken(admin.getUsername(),admin.getRoles());
        if (token==null)
        {
            return new ResponseEntity("Xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String,Object> login=new HashMap<>();
        login.put("token",token);
        login.put("username",admin.getUsername());
        login.put("success",true);
        return ResponseEntity.ok(login);


    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity preview(@PathVariable String hashId) throws MalformedURLException
    {
        Attachment file = attachmentService.findByHashId(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\""+ URLEncoder.encode(file.getName()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        file.getUploadPath())));
    }
    @GetMapping("/filter-cinema-janr/{janr}")
    public Object MovieByJanr(@PathVariable String janr)
    {
        return movieServie.findByJanrName(janr);
    }
    @GetMapping("/filter-cinema-type/{type}")
    public ResponseEntity MovieByType(@PathVariable String type)
    {
        return ResponseEntity.ok(movieServie.findByType(type));
    }

    @GetMapping("/filter-cinema-years/{years}")
    public ResponseEntity MovieByYears(@PathVariable Long years)
    {
        return ResponseEntity.ok(movieServie.findAllByYears(years));
    }
    @GetMapping("/filter-cinema-name/{name}")
    public ResponseEntity MovieByName(@PathVariable String name)
    {
        return ResponseEntity.ok(movieServie.findByName(name));
    }

    @GetMapping("/get-type")
    public ResponseEntity TypeName()
    {
        return  ResponseEntity.ok( typeService.getName());
    }

    @GetMapping("/get-janr")
    public ResponseEntity JanrName()
    {
        return  ResponseEntity.ok( janrService.getName());
    }
}
