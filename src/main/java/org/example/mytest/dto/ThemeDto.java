package org.example.mytest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ThemeDto {
    private Long id;
    @NotEmpty
    private String name;


}
