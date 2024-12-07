package com.example.gymprogress_tracker.repository;

import com.example.gymprogress_tracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {}
