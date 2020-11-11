package com.cip.model.cip;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cip2 extends Cip {

    @Id
    private Long id;



    public Cip2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
