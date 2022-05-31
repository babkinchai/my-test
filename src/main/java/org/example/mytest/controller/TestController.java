package org.example.mytest.controller;

import org.example.mytest.dto.ThemeDto;
import org.example.mytest.entity.Theme;
import org.example.mytest.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    ThemeRepository themeRepository;
    @PostMapping("/api/themes")
    public ResponseEntity<ThemeDto> appendTheme(@ModelAttribute("theme") ThemeDto dto) {
        Theme entity = new Theme();
        entity.setName_theme(dto.getName());
        entity = themeRepository.save(entity);
        dto.setId(entity.getId());
        return new ResponseEntity<ThemeDto>(dto,HttpStatus.OK);
    }
}
