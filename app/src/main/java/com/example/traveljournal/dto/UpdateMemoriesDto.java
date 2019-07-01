package com.example.traveljournal.dto;

public class UpdateMemoriesDto {

    private Long tripId;
    private String memories;

    public UpdateMemoriesDto(Long tripId, String memories) {
        this.tripId = tripId;
        this.memories = memories;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getMemories() {
        return memories;
    }

    public void setMemories(String memories) {
        this.memories = memories;
    }
}
