package org.example.mytest.repository;

import org.example.mytest.entity.Theme;
import org.hibernate.boot.model.source.internal.hbm.AttributesHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Theme findThemeByName(String name);
}
