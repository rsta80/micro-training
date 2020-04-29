package org.rastap.microtraining.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Wraps exception data
 */
@AllArgsConstructor
@Data
public class ExceptionDto {

    private Instant timestamp;
    private int status;
    private String errorMsg;
    private String details;

}
