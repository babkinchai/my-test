package org.example.mytest.controller;

import org.example.mytest.DAO.QuestionService;
import org.example.mytest.DAO.ThemeService;
import org.example.mytest.DAO.UserService;
import org.example.mytest.dto.QuestionDto;
import org.example.mytest.dto.ThemeDto;
import org.example.mytest.entity.Question;
import org.example.mytest.entity.Student;
import org.example.mytest.entity.Theme;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Validated
public class MainController {

    private final ThemeService themeService;
    private final QuestionService questionService;
    private final UserService userService;
    public MainController(ThemeService themeService, QuestionService questionService, UserService userService) {
        this.themeService = themeService;
        this.questionService = questionService;
        this.userService = userService;
    }
    @GetMapping("/registration")
    public String getRegPage(Model model) {
        model.addAttribute("user", new Student());
        return "/registration";
    }
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid Student userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new Student());
        return "/login";
    }

    @GetMapping({"/home", "/"})
    public String getMainPage(Model model) {
        List<Theme> themeList = themeService.getAllThemes();
        List<Question> questionList = questionService.getAllQuestions();
        model.addAttribute("themeDto", new ThemeDto());
        model.addAttribute("questionDto", new QuestionDto());
        model.addAttribute("themes", themeList);
        model.addAttribute("questions", questionList);
        return "/index";
    }
    @GetMapping("/themes")
    public String getThemesPage(Model model) {
        List<Theme> themeList = themeService.getAllThemes();
        model.addAttribute("themeDto", new ThemeDto());
        model.addAttribute("themes", themeList);
        return "/themes";
    }
    @PostMapping("/themes/delete")
    public String deleteTheme(@RequestParam(value = "id", required = true) Long id) {
        themeService.delete(id);
        return "redirect:/themes";
    }

    @PostMapping("/api/themes")
    public String appendTheme(@ModelAttribute("theme") @Valid ThemeDto dto) {
        themeService.save(dto);
        return "redirect:/home";
    }
    @PostMapping("/api/questions")
    public String appendQuestion(@ModelAttribute("questionDto") QuestionDto dto) {
        questionService.save(dto);
        return "redirect:/home";
    }
    @PostMapping("/api/questions/delete")
    public String deleteQuestion(@RequestParam(value = "id", required = true) Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/home";
    }
}
