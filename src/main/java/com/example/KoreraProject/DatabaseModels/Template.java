package com.example.KoreraProject.DatabaseModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "Template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "formula_id")
    @JsonIgnore
    private Formula formula;

    @Column(name = "template_name")
    private String name;

    @Column(name = "template_type")
    private String type;

    @Column(name = "template_formula")
    private String t_formula;

    public Template(){};
    public Template(String name, String type, String t_formula) {
        this.name = name;
        this.type = type;
        this.t_formula = t_formula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getT_formula() {
        return t_formula;
    }

    public void setT_formula(String t_formula) {
        this.t_formula = t_formula;
    }
}
