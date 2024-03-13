package com.example.evaluation.dto.requestes;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ReservationRequest {

    @NotNull(message = "Meeting room ID is required")
    private Long meetingRoomId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

}
