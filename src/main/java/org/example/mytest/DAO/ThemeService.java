package org.example.mytest.DAO;

import org.example.mytest.dto.ThemeDto;
import org.example.mytest.entity.Theme;
import org.example.mytest.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Boolean save(@Valid ThemeDto dto) {
        Theme entity = new Theme();
        entity.setName(dto.getName());
        themeRepository.save(entity);
        return true;
    }

    public void delete(Long id) {
        themeRepository.deleteById(id);
    }
}
