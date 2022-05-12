package com.example.backend.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "museums")
@Access(AccessType.FIELD)
public class Museum {
    public Museum() {
    }

    public Museum(Long id) {
        this.id = id;
    }
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "location")
    public String location;

    @JsonIgnore
    @OneToMany(mappedBy = "museumid")
    public List<Painting> paintings = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "usermuseums", joinColumns = @JoinColumn(name = "museumid"), inverseJoinColumns = @JoinColumn(name = "userid"))
    public Set<Users> users = new HashSet<>();


}