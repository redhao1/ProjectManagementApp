package com.example.KoreraProject.DatabaseModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Formula> formulas = new HashSet<>();

    //many to many
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ResourceToProject> resourceToProjects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "project_name")
    private String name;

    public Project(){};
    public Project(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Formula> getFormulas() {
        return formulas;
    }

    public void setFormulas(Set<Formula> formulas) {
        this.formulas = formulas;
    }

    public Set<ResourceToProject> getResourceToProjects() {
        return resourceToProjects;
    }

    public void setResourceToProjects(Set<ResourceToProject> resourceToProjects) {
        this.resourceToProjects = resourceToProjects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFormula(Formula formula) {
        this.formulas.add(formula);
        formula.setProject(this);
    }

    public void removeFormula(Formula formula) {
        this.formulas.remove(formula);
        formula.setProject(null);
    }
}
