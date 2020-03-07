package com.example.socialnetwork.models;


import lombok.*;

import java.time.LocalDate;

@Data
@ToString(of = {"text", "from", "date"})
@EqualsAndHashCode(of = {"text", "from", "date"})
@NoArgsConstructor
@AllArgsConstructor
public class OutputMessage {

    private String text;
    private String from;
    private LocalDate date;
}
