package com.example.evaluation.services;

import com.example.evaluation.dto.responses.NextAvailableResponse;
import com.example.evaluation.entities.Reservation;

import java.util.List;

import com.example.evaluation.entities.Reservation;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime);
    Reservation updateReservation(Long reservationId, LocalDateTime startTime, LocalDateTime endTime);
    void deleteReservation(Long reservationId);
    Reservation getReservationById(Long reservationId);
    List<Reservation> getAllReservations();

    List<Reservation> getReservationsForMeetingRoom(Long meetingRoomId);
}

