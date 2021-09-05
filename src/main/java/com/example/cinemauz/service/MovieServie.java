package com.example.cinemauz.service;

import com.example.cinemauz.entity.Attachment;
import com.example.cinemauz.entity.Janr;
import com.example.cinemauz.entity.Movie;
import com.example.cinemauz.entity.Type;
import com.example.cinemauz.payload.MoviePayload;
import com.example.cinemauz.repository.AttachmentRepository;
import com.example.cinemauz.repository.JanrRepository;
import com.example.cinemauz.repository.MovieRepository;
import com.example.cinemauz.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServie {
    private final MovieRepository movieRepository;
    private final AttachmentRepository attachmentRepository;
    private final TypeRepository typeRepository;
    private final JanrRepository janrRepository;
    Logger logger= LoggerFactory.getLogger(MovieServie.class);

    public boolean addMovie(MoviePayload moviePayload)
    {
        Movie movie=new Movie();
        movie.setName(moviePayload.getName());
        movie.setDiscribtion(moviePayload.getDiscribtion());
        movie.setUrl(moviePayload.getUrl());
        movie.setYears(moviePayload.getYears());
        movie.setActors(moviePayload.getActors());
        movie.setCountry(moviePayload.getCountry());

        List<Attachment>attachmentList=new ArrayList<>();
        for (int i = 0; i < moviePayload.getHashId().size(); i++) {
            attachmentList.add(attachmentRepository.findByHashId(moviePayload.getHashId().get(i)));
        }
        movie.setAttachments(attachmentList);

        movie.setType(typeRepository.findByName(moviePayload.getType()));

        List<Janr>janrList=new ArrayList<>();
        for (int i = 0; i < moviePayload.getJanr().size(); i++) {
            janrList.add(janrRepository.findByName(moviePayload.getJanr().get(i)));
        }
        movie.setJanr(janrList);

        return movieRepository.save(movie)!=null;

    }

    public List<Movie> findByJanrName(String name)
    {
        Janr janr=janrRepository.findByName(name);
        List<Movie> movieList=movieRepository.findByJanrId(janr.getId());

        return movieList;
    }

    public List<Movie> findByType(String name)
    {
        Type type=typeRepository.findByName(name);

        return movieRepository.findByTypeId(type.getId());
    }

    public List<Movie> findAllByYears(Long years)
    {
        List<Movie>movieList=movieRepository.findAllByYears(years);

        return movieList;
    }

    public boolean editMovie(Long id,MoviePayload moviePayload)
    {
        try {
            Movie movie=movieRepository.getById(id);
            movie.setName(moviePayload.getName());
            System.out.println(moviePayload.getName());
            movie.setDiscribtion(moviePayload.getDiscribtion());
            movie.setUrl(moviePayload.getUrl());
            movie.setYears(moviePayload.getYears());
            movie.setActors(moviePayload.getActors());
            movie.setCountry(moviePayload.getCountry());

            List<Attachment>attachmentList=new ArrayList<>();
            for (int i = 0; i < moviePayload.getHashId().size(); i++) {
                attachmentList.add(attachmentRepository.findByHashId(moviePayload.getHashId().get(i)));
            }
            movie.setAttachments(attachmentList);

            movie.setType(typeRepository.findByName(moviePayload.getType()));

            List<Janr>janrList=new ArrayList<>();
            for (int i = 0; i < moviePayload.getJanr().size(); i++) {
                janrList.add(janrRepository.findByName(moviePayload.getJanr().get(i)));
            }
            movie.setJanr(janrList);

            return movieRepository.save(movie)!=null;
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        return false;
    }

    public boolean delete(Long id)
    {
        try {
            Movie movie=movieRepository.getById(id);
            movieRepository.delete(movie);
            return true;
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    public Movie findByName(String name)
    {
        Movie movie=movieRepository.findByName(name);

        return movie;
    }




}
