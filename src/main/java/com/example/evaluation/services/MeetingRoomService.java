package com.example.evaluation.services;

import com.example.evaluation.dto.responses.NextAvailableResponse;
import com.example.evaluation.entities.MeetingRoom;

import java.time.LocalDateTime;
import java.util.List;

import com.example.evaluation.entities.MeetingRoom;
import java.util.List;

public interface MeetingRoomService {
    MeetingRoom createMeetingRoom(MeetingRoom meetingRoom);
    MeetingRoom updateMeetingRoom(Long meetingRoomId, MeetingRoom meetingRoom);
    void deleteMeetingRoom(Long meetingRoomId);
    MeetingRoom getMeetingRoomById(Long meetingRoomId);
    List<MeetingRoom> getAllMeetingRooms();
    boolean isMeetingRoomAvailableNow(Long meetingRoomId);

    NextAvailableResponse getNextAvailableDateTime(Long meetingRoomId);

}
