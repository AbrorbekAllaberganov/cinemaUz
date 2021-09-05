package com.example.cinemauz.repository;


import com.example.cinemauz.entity.Attachment;
import com.example.cinemauz.entity.Janr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface JanrRepository extends JpaRepository<Janr, Long> {
    Janr findByName (String name);
}
