package com.example.socialnetwork.models;

import lombok.*;

@Data
@ToString(of = {"text", "from"})
@EqualsAndHashCode(of = {"text", "from"})
@NoArgsConstructor
@AllArgsConstructor
public class InputMessage {

    private String text;
    private String from;
    private String to;
}
