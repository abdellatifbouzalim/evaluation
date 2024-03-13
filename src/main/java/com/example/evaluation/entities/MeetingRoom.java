package com.example.evaluation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name length cannot exceed {max} characters")
    private String name;

    @PositiveOrZero(message = "Capacity must be a positive number or zero")
    private int capacity;

    @OneToMany(mappedBy = "meetingRoom")
    @JsonIgnore
    private List<Reservation> reservations;
}