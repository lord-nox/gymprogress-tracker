package com.example.gymprogress_tracker.controller;

import com.example.gymprogress_tracker.model.User;
import com.example.gymprogress_tracker.model.Workout;
import com.example.gymprogress_tracker.repository.UserRepository;
import com.example.gymprogress_tracker.repository.WorkoutRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/add")
    public String addWorkout(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam String exercise,
                             @RequestParam int sets,
                             @RequestParam int reps,
                             @RequestParam(required = false) Double weight) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Workout workout = new Workout();
        workout.setUser(user);
        workout.setExercise(exercise);
        workout.setSets(sets);
        workout.setReps(reps);
        workout.setWeight(weight);
        workout.setDateTime(LocalDateTime.now());

        workoutRepository.save(workout);

        return "redirect:/dashboard";
    }

    @GetMapping
    public String getWorkouts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model.addAttribute("workoutLogs", workoutRepository.findByUserId(user.getId()));
        return "dashboard";
    }
}
