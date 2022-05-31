package org.example.mytest.controller;

import org.example.mytest.dto.ThemeDto;
import org.example.mytest.entity.Theme;
import org.example.mytest.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.expression.Themes;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    ThemeRepository themeRepository;
    @GetMapping("/home")
    public String getMainPage(Model model) {
        List<Theme> themeList = themeRepository.findAll();
        model.addAttribute("themeDto", new ThemeDto());
        model.addAttribute("themes", themeList);
        return "/index";
    }


}
