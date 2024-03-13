package com.example.evaluation.services;

import com.example.evaluation.entities.MeetingRoom;
import com.example.evaluation.entities.Reservation;
import com.example.evaluation.exeptions.NotFoundException;
import com.example.evaluation.exeptions.ValidationException;
import com.example.evaluation.repository.MeetingRoomRepository;
import com.example.evaluation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, MeetingRoomRepository meetingRoomRepository) {
        this.reservationRepository = reservationRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    }

    @Override
    public Reservation createReservation(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> conflictingReservations = reservationRepository.findByMeetingRoomIdAndEndTimeGreaterThanEqualAndStartTimeLessThanEqual(meetingRoomId, startTime, endTime);
        if (!conflictingReservations.isEmpty()) {
            throw new ValidationException("Another reservation already exists for the given time period.");
        }

        if (!endTime.isAfter(startTime)) {
            throw new ValidationException("End time must be after start time.");
        }

        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new NotFoundException("Meeting room not found with id: " + meetingRoomId));

        Reservation newReservation = new Reservation();
        newReservation.setMeetingRoom(meetingRoom);
        newReservation.setStartTime(startTime);
        newReservation.setEndTime(endTime);

        return reservationRepository.save(newReservation);
    }

    @Override
    public Reservation updateReservation(Long reservationId, LocalDateTime startTime, LocalDateTime endTime) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + reservationId));

        existingReservation.setStartTime(startTime);
        existingReservation.setEndTime(endTime);

        return reservationRepository.save(existingReservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + reservationId));

        reservationRepository.delete(existingReservation);
    }

    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + reservationId));
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsForMeetingRoom(Long meetingRoomId) {
        return reservationRepository.findByMeetingRoomId(meetingRoomId);
    }





}
