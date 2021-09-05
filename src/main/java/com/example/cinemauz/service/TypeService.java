package com.example.cinemauz.service;

import com.example.cinemauz.entity.Type;
import com.example.cinemauz.payload.TypePayload;
import com.example.cinemauz.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    public boolean save(TypePayload typePayload)
    {
        Type type=new Type();
        type.setName(typePayload.getName());

        return typeRepository.save(type)!=null;
    }

    public boolean edit(Long id,TypePayload typePayload)
    {
        Type type=typeRepository.getById(id);
        type.setName(typePayload.getName());

        return typeRepository.save(type)!=null;
    }

    public boolean delete(Long id)
    {
        try {
            Type type=typeRepository.getById(id);
            typeRepository.delete(type);
            return true;
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return false;
    }

    public List<String> getName()
    {
        List<String> typeName=new ArrayList<>();
        List<Type>typeList=typeRepository.findAll();
        for (int i = 0; i < typeList.size(); i++) {
            typeName.add(typeList.get(i).getName());
        }

        return typeName;
    }

}
