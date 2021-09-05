package com.example.cinemauz.repository;

import com.example.cinemauz.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    @Query(value="select * from movie f inner join movie_janr  a on f.id=a.movie_id where a.janr_id=:idJanr",nativeQuery=true)
    List<Movie> findByJanrId(@Param("idJanr") Long id);

    @Query(value="select * from movie  where type_id=:idType",nativeQuery=true)
    List<Movie> findByTypeId(@Param("idType") Long id);
    List<Movie> findAllByYears (Long years);
    Movie findByName(String name);
}
