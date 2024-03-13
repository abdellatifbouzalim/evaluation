package com.example.evaluation.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;



@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    @NotNull(message = "Meeting room must be specified")
    private MeetingRoom meetingRoom;

    @NotNull(message = "Start time must be specified")
    @Future(message = "Start time must be in the future")

    private LocalDateTime startTime;

    @NotNull(message = "End time must be specified")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

}