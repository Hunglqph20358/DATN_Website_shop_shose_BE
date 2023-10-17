package com.example.backend.core.view.dto;

import jakarta.persistence.Column;

import java.time.Instant;

public class ColorDTO {
    private Long id;
    private String name;
    private Instant createDate;

    public ColorDTO() {
    }

    public ColorDTO(Long id, String name, Instant createDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }

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

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }
}
