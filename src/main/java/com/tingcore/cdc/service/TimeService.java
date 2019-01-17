package com.tingcore.cdc.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TimeService {

    public Instant now() {
        return Instant.now();
    }

    public LocalTime currentTime() {
        return LocalTime.now();
    }

    public LocalDate currentDate() {
        return LocalDate.now();
    }

    public DayOfWeek currentDayOfWeek() {
        return LocalDate.now().getDayOfWeek();
    }

}
