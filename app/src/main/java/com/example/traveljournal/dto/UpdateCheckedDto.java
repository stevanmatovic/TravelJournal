package com.example.traveljournal.dto;

public class UpdateCheckedDto {

    boolean checked;
    Long id;

    public UpdateCheckedDto(boolean checked, Long id) {
        this.checked = checked;
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
