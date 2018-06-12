package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A Production.
 */
@Entity
@Table(name = "T_PRODUCTION")
public class Production implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_established")
    private Integer year_established;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear_established() {
        return year_established;
    }

    public void setYear_established(Integer year_established) {
        this.year_established = year_established;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Production production = (Production) o;

        if (id != null ? !id.equals(production.id) : production.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Production{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", year_established='" + year_established + "'" +
                '}';
    }
}
