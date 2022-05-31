package org.example.mytest.repository;

import org.example.mytest.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
