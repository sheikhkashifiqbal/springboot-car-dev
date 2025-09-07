package com.car.carservices.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeSlotUtil {

    public static List<String> generateTimeSlots(String workingHours) {
        if (workingHours == null || !workingHours.contains("-")) {
            return Collections.emptyList();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        String[] parts = workingHours.split("-");

        LocalTime start = LocalTime.parse(parts[0].trim(), formatter);
        LocalTime end = LocalTime.parse(parts[1].trim(), formatter);

        List<String> slots = new ArrayList<>();
        while (!start.isAfter(end.minusMinutes(30))) {
            slots.add(start.format(formatter));
            start = start.plusMinutes(30);
        }
        return slots;
    }
}
