package com.example.evaluation.repository;

import com.example.evaluation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMeetingRoomId(Long meetingRoomId);
    List<Reservation> findByMeetingRoomIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Long meetingRoomId, LocalDateTime endTime, LocalDateTime startTime);

    List<Reservation> findByMeetingRoomIdAndEndTimeGreaterThanEqualAndStartTimeLessThanEqual(
            Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime);

}
