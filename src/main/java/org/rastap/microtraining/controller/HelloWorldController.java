package org.rastap.microtraining.controller;

import org.rastap.microtraining.dto.IsAliveDto;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping
public class HelloWorldController {

    private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/is-alive")
    public ResponseEntity<?> isAlive() {
        return new ResponseEntity<>(LocalDate.now(), HttpStatus.OK);
    }

    @GetMapping("/path-variable/{message}")
    public ResponseEntity<?> isAliveClient(@PathVariable String message) {
        return new ResponseEntity<>(new IsAliveDto(message), HttpStatus.OK);
    }

    @GetMapping("/internationalization-training")
    public ResponseEntity<?> isAliveInternationalized() {
        String internationalizationMessage = messageSource.getMessage("good.morning.message",
                null, LocaleContextHolder.getLocale())
                + " " + LocalTime.now();
        return new ResponseEntity<>(internationalizationMessage, HttpStatus.OK);
    }


}
