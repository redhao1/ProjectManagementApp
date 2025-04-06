package com.example.KoreraProject.DatabaseModels;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private int id;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private Set<ResourceDetail> resourceDetails = new HashSet<>();

    //many to many
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private Set<ResourceToProject> resourceToProjects = new HashSet<>();

    @Column(name = "resource_name")
    private String name;

    @Column(name = "resource_code")
    private String code;

    public Resource(){};
    public Resource(String name) {
        this.name = name;
    }
    public Resource(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Set<ResourceToProject> getResourceToProjects() {
        return resourceToProjects;
    }

    public void setResourceToProjects(Set<ResourceToProject> resourceToProjects) {
        this.resourceToProjects = resourceToProjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ResourceDetail> getResourceDetails() {
        return resourceDetails;
    }

    public void setResourceDetails(Set<ResourceDetail> resourceDetails) {
        this.resourceDetails = resourceDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addResourceDetail(ResourceDetail resourceDetail) {
        this.resourceDetails.add(resourceDetail);
        resourceDetail.setResource(this);
    }

    public void removeResourceDetail(ResourceDetail resourceDetail) {
        this.resourceDetails.remove(resourceDetail);
        resourceDetail.setResource(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return this.getId() == resource.id;
    }

}
