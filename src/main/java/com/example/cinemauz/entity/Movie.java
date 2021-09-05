package com.example.cinemauz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String discribtion;
    private Long years;
    private String country;

    @ElementCollection
    private List<String> actors;

    @OneToOne
    @JsonIgnore
    private Type type;

    @OneToMany
    @JsonIgnore
    private List<Janr>janr;

    private String url;

    @OneToMany
    @JsonIgnore
    private List<Attachment>attachments;
}
