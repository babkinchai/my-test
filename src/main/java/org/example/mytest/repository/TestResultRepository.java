package org.example.mytest.repository;

import org.example.mytest.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult,Long> {
}
