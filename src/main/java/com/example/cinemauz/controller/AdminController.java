package com.example.cinemauz.controller;

import com.example.cinemauz.entity.Attachment;
import com.example.cinemauz.payload.JanrPayload;
import com.example.cinemauz.payload.MoviePayload;
import com.example.cinemauz.payload.TypePayload;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AttachmentService attachmentService;
    private final TypeService typeService;
    private final JanrService janrService;
    private final MovieServie movieServie;

    @Value("${upload.folder}")
    private String uploadFolder;

    //  FILE
    @PostMapping("/add-file")
    public ResponseEntity addFile(@RequestParam(name = "file") MultipartFile multipartFile)
    {


        return attachmentService.save(multipartFile)? ResponseEntity.ok("Saqlandi")
                : new ResponseEntity("Xatolik", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity download(@PathVariable String hashId) throws MalformedURLException
    {
        Attachment file = attachmentService.findByHashId(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+ URLEncoder.encode(file.getName()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        file.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity deleteFile(@PathVariable String hashId) throws MalformedURLException
    {
        boolean isDelete = attachmentService.delete(hashId);
        if (!isDelete){
            return new ResponseEntity("not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("deleted");
    }

    // Type
    @PostMapping("/add-type")
    public ResponseEntity addType(@RequestBody TypePayload typePayload)
    {
        return typeService.save(typePayload)?ResponseEntity.ok("Saqlandi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/edit-type/{id}")
    public ResponseEntity editType(@RequestBody TypePayload typePayload,@PathVariable Long id)
    {
        return typeService.edit(id,typePayload)?ResponseEntity.ok("O'zgartirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete-type/{id}")
    public ResponseEntity deleteType(@PathVariable Long id)
    {
        return typeService.delete(id)?ResponseEntity.ok("O'chirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }

    // Janr
    @PostMapping("/add-janr")
    public ResponseEntity addJanr(@RequestBody JanrPayload janrPayload)
    {
        return janrService.save(janrPayload)?ResponseEntity.ok("Saqlandi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/edit-janr/{id}")
    public ResponseEntity editJanr(@RequestBody JanrPayload janrPayload,@PathVariable Long id)
    {
        return janrService.edit(id,janrPayload)?ResponseEntity.ok("O'zgartirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete-janr/{id}")
    public ResponseEntity deleteJanr(@PathVariable Long id)
    {
        return janrService.delete(id)?ResponseEntity.ok("O'chirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }

    //  MOVIE

    @PostMapping("/add-movie")
    public ResponseEntity addMovie(@RequestBody MoviePayload moviePayload)
    {
        return movieServie.addMovie(moviePayload)?ResponseEntity.ok("Saqlandi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete-movie/{id}")
    public ResponseEntity deleteMovie(@PathVariable Long id)
    {
        return movieServie.delete(id)?ResponseEntity.ok("O'chirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/edit-movie/{id}")
    public ResponseEntity editMovie(@PathVariable Long id,@RequestBody MoviePayload moviePayload)
    {
        return movieServie.editMovie(id,moviePayload)?ResponseEntity.ok("O'zgartirildi")
                :new ResponseEntity("Xatolik",HttpStatus.BAD_REQUEST);
    }




}
