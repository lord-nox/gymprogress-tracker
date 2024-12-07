package com.example.gymprogress_tracker.controller;

import com.example.gymprogress_tracker.model.User;
import com.example.gymprogress_tracker.model.Workout;
import com.example.gymprogress_tracker.repository.UserRepository;
import com.example.gymprogress_tracker.repository.WorkoutRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final UserRepository userRepository;

    private final WorkoutRepository workoutRepository;

    public DashboardController(UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        List<Workout> workouts = workoutRepository.findByUserId(user.getId());

        model.addAttribute("workoutLogs", workouts);
        return "dashboard"; // Verwijst naar dashboard.html
    }
}
