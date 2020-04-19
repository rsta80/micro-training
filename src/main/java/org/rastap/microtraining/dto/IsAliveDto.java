package org.rastap.microtraining.dto;

import lombok.Data;

@Data
public class IsAliveDto {

    public IsAliveDto(String message){
        this.message = message;
    }

    private String message;
}
