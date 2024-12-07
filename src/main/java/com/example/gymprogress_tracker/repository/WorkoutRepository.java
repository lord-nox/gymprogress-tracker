package com.example.gymprogress_tracker.repository;

import com.example.gymprogress_tracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserId(Integer user_id);
}
