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
        // Haal de ingelogde gebruikersnaam (email) op uit de authenticatie
        String email = authentication.getName();

        // Zoek de gebruiker op basis van email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        // Haal alle workouts van de gebruiker op
        List<Workout> workouts = workoutRepository.findByUserId(user.getId());

        // Voeg workouts toe aan het model
        model.addAttribute("workoutLogs", workouts);

        // Verwijs naar de dashboard-pagina (dashboard.html)
        return "dashboard";
    }
}
