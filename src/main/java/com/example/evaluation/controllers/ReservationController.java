package com.example.evaluation.controllers;

import com.example.evaluation.entities.Reservation;
import com.example.evaluation.dto.requestes.ReservationRequest;
import com.example.evaluation.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /********work******/
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation createdReservation = reservationService.createReservation(request.getMeetingRoomId(), request.getStartTime(), request.getEndTime());
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }


    /********work******/
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@Valid @PathVariable("id") Long reservationId,
                                                         @Valid @RequestParam LocalDateTime startTime,
                                                         @Valid @RequestParam LocalDateTime endTime) {
        Reservation updatedReservation = reservationService.updateReservation(reservationId, startTime, endTime);
        return ResponseEntity.ok(updatedReservation);
    }

    /********work******/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@Valid @PathVariable("id") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@Valid @PathVariable("id") Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }


}
