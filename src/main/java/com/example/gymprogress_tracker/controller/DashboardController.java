package com.example.gymprogress_tracker.controller;

import com.example.gymprogress_tracker.model.Workout;
import com.example.gymprogress_tracker.repository.WorkoutRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.time.LocalDateTime;

@Controller
public class DashboardController {

    private final WorkoutRepository workoutRepository;

    public DashboardController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        model.addAttribute("workoutLogs", workoutRepository.findAll()); // Pas aan voor workouts van een specifieke gebruiker
        return "dashboard"; // Zorg dat er een "dashboard.html" bestaat
    }

    @PostMapping("/dashboard/workouts/add") // Uniek pad voor deze controller
    public String addWorkout(Authentication authentication, @RequestParam String exercise,
                             @RequestParam int sets, @RequestParam int reps,
                             @RequestParam(required = false) Double weight) {
        // Haal de ingelogde gebruiker op
        String email = authentication.getName();

        // Maak een nieuwe Workout aan
        Workout workout = new Workout();
        workout.setExercise(exercise);
        workout.setSets(sets);
        workout.setReps(reps);
        workout.setWeight(weight);
        workout.setDateTime(LocalDateTime.now());

        // Sla de workout op in de database
        workoutRepository.save(workout);

        return "redirect:/dashboard"; // Redirect naar het dashboard om de toegevoegde workout te zien
    }
}
