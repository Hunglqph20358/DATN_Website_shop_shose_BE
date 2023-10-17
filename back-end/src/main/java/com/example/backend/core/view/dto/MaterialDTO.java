package com.example.backend.core.view.dto;


import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaterialDTO {
    private Long id;
    private String name;
    private Instant createDate;
    private Instant updateDate;
    private String description;
    private Integer status;

}
