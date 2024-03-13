package com.example.evaluation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NextAvailableResponse {
    private Long roomId;
    private LocalDateTime nextAvailableDateTime;
    private String message;

    // Ajoutez un constructeur surchargé pour deux arguments
    public NextAvailableResponse(Long roomId, LocalDateTime nextAvailableDateTime) {
        this.roomId = roomId;
        this.nextAvailableDateTime = nextAvailableDateTime;
        this.message = null; // Ou un message par défaut si nécessaire
    }
}
