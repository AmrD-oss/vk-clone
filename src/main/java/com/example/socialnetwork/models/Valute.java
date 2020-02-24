package com.example.socialnetwork.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@ToString(of = {"id", "numCode", "charCode", "nominal", "name", "value", "previous"})
@EqualsAndHashCode(of = {"id", "numCode", "charCode", "nominal", "name", "value", "previous"})
@NoArgsConstructor
@AllArgsConstructor
public class Valute implements Serializable {

    @JsonProperty(value = "ID")
    private String id;
    @JsonProperty(value = "NumCode")
    private Integer numCode;
    @JsonProperty(value = "CharCode")
    private String charCode;
    @JsonProperty(value = "Nominal")
    private Integer nominal;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Value")
    private Double value;
    @JsonProperty(value = "Previous")
    private Double previous;
}
