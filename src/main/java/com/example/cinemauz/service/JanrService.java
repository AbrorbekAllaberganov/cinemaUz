package com.example.cinemauz.service;

import com.example.cinemauz.entity.Janr;
import com.example.cinemauz.entity.Type;
import com.example.cinemauz.payload.JanrPayload;
import com.example.cinemauz.repository.JanrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JanrService {
    private final JanrRepository janrRepository;

    public boolean save(JanrPayload janrPayload)
    {
        Janr janr=new Janr();
        janr.setName(janrPayload.getName());

        return janrRepository.save(janr)!=null;
    }

    public boolean edit(Long id,JanrPayload janrPayload)
    {
        Janr janr=janrRepository.getById(id);
        janr.setName(janrPayload.getName());

        return janrRepository.save(janr)!=null;
    }

    public boolean delete(Long id)
    {
        try {
            Janr janr=janrRepository.getById(id);
            janrRepository.delete(janr);
            return true;
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return false;
    }

    public List<String> getName()
    {
        List<String> janrName=new ArrayList<>();
        List<Janr>janrList=janrRepository.findAll();
        for (int i = 0; i < janrList.size(); i++) {
            janrName.add(janrList.get(i).getName());
        }

        return janrName;
    }

}
