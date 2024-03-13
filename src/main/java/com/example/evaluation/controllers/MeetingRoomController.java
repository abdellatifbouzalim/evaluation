package com.example.evaluation.controllers;

import com.example.evaluation.dto.responses.NextAvailableResponse;
import com.example.evaluation.entities.MeetingRoom;
import com.example.evaluation.services.MeetingRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meeting-rooms")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }
    /********work******/
    @PostMapping
    public ResponseEntity<MeetingRoom> createMeetingRoom(@Valid  @RequestBody MeetingRoom meetingRoom) {
        MeetingRoom createdMeetingRoom = meetingRoomService.createMeetingRoom(meetingRoom);
        return new ResponseEntity<>(createdMeetingRoom, HttpStatus.CREATED);
    }
    /********work******/
    @PutMapping("/{id}")
    public ResponseEntity<MeetingRoom> updateMeetingRoom( @Valid @PathVariable("id") Long meetingRoomId, @Valid @RequestBody MeetingRoom meetingRoom) {
        MeetingRoom updatedMeetingRoom = meetingRoomService.updateMeetingRoom(meetingRoomId, meetingRoom);
        return ResponseEntity.ok(updatedMeetingRoom);
    }
    /********work******/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeetingRoom(@Valid @PathVariable("id") Long meetingRoomId) {
        meetingRoomService.deleteMeetingRoom(meetingRoomId);
        String message = "Meeting room with ID " + meetingRoomId + " has been successfully deleted.";
        return ResponseEntity.ok(message);
    }

    /********work******/
    @GetMapping("/{id}")
    public ResponseEntity<MeetingRoom> getMeetingRoomById(@Valid @PathVariable("id") Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(meetingRoomId);
        return ResponseEntity.ok(meetingRoom);
    }

    /********work******/
    @GetMapping
    public ResponseEntity<List<MeetingRoom>> getAllMeetingRooms() {
        List<MeetingRoom> meetingRooms = meetingRoomService.getAllMeetingRooms();
        return ResponseEntity.ok(meetingRooms);
    }

    /********work******/
    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> isMeetingRoomAvailable(@Valid @PathVariable("id") Long meetingRoomId) {
        boolean isAvailable = meetingRoomService.isMeetingRoomAvailableNow(meetingRoomId);
        return ResponseEntity.ok(isAvailable);
    }

    // Endpoint pour obtenir la prochaine disponibilité de la salle de réunion
    @GetMapping("/{id}/next-available")
    public ResponseEntity<NextAvailableResponse> getNextAvailableDateTime(@PathVariable("id") Long meetingRoomId) {
        LocalDateTime nextAvailableDateTime = meetingRoomService.getNextAvailableDateTime(meetingRoomId).getNextAvailableDateTime();
        NextAvailableResponse response = new NextAvailableResponse(meetingRoomId, nextAvailableDateTime);
        return ResponseEntity.ok(response);
    }
}
