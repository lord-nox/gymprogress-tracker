package com.example.gymprogress_tracker.controller;

import com.example.gymprogress_tracker.model.User;
import com.example.gymprogress_tracker.model.Workout;
import com.example.gymprogress_tracker.repository.UserRepository;
import com.example.gymprogress_tracker.repository.WorkoutRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutController(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    // Voeg een workout toe zonder redirect
    @PostMapping("/add")
    @Transactional
    public String addWorkout(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam String exercise,
                             @RequestParam int sets,
                             @RequestParam int reps,
                             @RequestParam(required = false) Double weight,
                             Model model) {
        // Log de binnenkomende request-data
        System.out.println("Received request data: " + exercise + ", " + sets + " sets, " + reps + " reps, " + (weight != null ? weight + "kg" : "no weight"));

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Log de gevonden gebruiker
        System.out.println("Found user: " + user.getEmail());

        Workout workout = new Workout();
        workout.setUser(user);
        workout.setExercise(exercise);
        workout.setSets(sets);
        workout.setReps(reps);
        workout.setWeight(weight);
        workout.setDateTime(LocalDateTime.now());

        // Log de workout die wordt opgeslagen
        System.out.println("Saving workout: " + workout);

        workoutRepository.save(workout);

        // Log succesvolle opslag
        System.out.println("Workout saved successfully!");

        model.addAttribute("workoutLogs", workoutRepository.findByUserId(user.getId()));

        return "dashboard";
    }

    // Verkrijg de workouts voor de dashboardpagina
    @GetMapping
    public String getWorkouts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Voeg workout logs toe aan het model
        model.addAttribute("workoutLogs", workoutRepository.findByUserId(user.getId()));
        return "dashboard";
    }
}
