package org.example.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Slot extends PanacheEntity {

    public int ora;
    public boolean esteLiber;
    public String rezervatDe;

    public Slot() {}

    public Slot(int ora) {
        this.ora = ora;
        this.esteLiber = true;
        this.rezervatDe = null;
    }
}