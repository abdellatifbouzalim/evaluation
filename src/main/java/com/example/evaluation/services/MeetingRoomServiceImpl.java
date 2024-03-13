package com.example.evaluation.services;

import com.example.evaluation.dto.responses.NextAvailableResponse;
import com.example.evaluation.entities.MeetingRoom;
import com.example.evaluation.entities.Reservation;
import com.example.evaluation.exeptions.NotFoundException;
import com.example.evaluation.repository.MeetingRoomRepository;
import com.example.evaluation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository, ReservationRepository reservationRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public MeetingRoom createMeetingRoom(MeetingRoom meetingRoom) {
        return meetingRoomRepository.save(meetingRoom);
    }

    @Override
    public MeetingRoom updateMeetingRoom(Long meetingRoomId, MeetingRoom meetingRoom) {
        MeetingRoom existingMeetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new NotFoundException("Meeting room not found with id: " + meetingRoomId));
        existingMeetingRoom.setName(meetingRoom.getName());
        existingMeetingRoom.setCapacity(meetingRoom.getCapacity());
        return meetingRoomRepository.save(existingMeetingRoom);
    }

    @Override
    public void deleteMeetingRoom(Long meetingRoomId) {
        MeetingRoom existingMeetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new NotFoundException("Meeting room not found with id: " + meetingRoomId));

        // Supprimer toutes les réservations associées à la salle de réunion
        List<Reservation> reservations = existingMeetingRoom.getReservations();
        for (Reservation reservation : reservations) {
            reservationRepository.delete(reservation);
        }

        // Ensuite, supprimer la salle de réunion
        meetingRoomRepository.delete(existingMeetingRoom);
    }

    @Override
    public MeetingRoom getMeetingRoomById(Long meetingRoomId) {
        return meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new NotFoundException("Meeting room not found with id: " + meetingRoomId));
    }

    @Override
    public List<MeetingRoom> getAllMeetingRooms() {
        return meetingRoomRepository.findAll();
    }


    @Override
    public boolean isMeetingRoomAvailableNow(Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new NotFoundException("Meeting room not found with id: " + meetingRoomId));

        List<Reservation> reservations = meetingRoom.getReservations();

        LocalDateTime now = LocalDateTime.now();
        for (Reservation reservation : reservations) {
            if (now.isAfter(reservation.getStartTime()) && now.isBefore(reservation.getEndTime())) {
                return false;
            }
        }

        return true;
    }


    @Override
    public NextAvailableResponse getNextAvailableDateTime(Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new NotFoundException("Meeting room not found with id: " + meetingRoomId));

        List<Reservation> reservations = meetingRoom.getReservations();
        reservations.sort(Comparator.comparing(Reservation::getStartTime));
        LocalDateTime now = LocalDateTime.now();

        for (Reservation reservation : reservations) {

            if (reservation.getStartTime().isAfter(now)) {
                return new NextAvailableResponse(meetingRoomId, reservation.getStartTime());
            }
        }
        return new NextAvailableResponse(meetingRoomId, now);
    }




}
