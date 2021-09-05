package com.example.cinemauz.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoviePayload {
    private String name;
    private String discribtion;
    private String country;
    private Long years;

    private List<String>actors;
    private String type;
    private List<String>janr;
    private String url;
    private List<String>hashId;

}
