package com.example.socialnetwork.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@ToString(of = {"date", "previousDate", "previousUrl", "timeStamp", "valute"})
@EqualsAndHashCode(of = {"date", "previousDate", "previousUrl", "timeStamp", "valute"})
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRates implements Serializable {

    @JsonProperty(value = "Date")
    private Date date;
    @JsonProperty(value = "PreviousDate")
    private Date previousDate;
    @JsonProperty(value = "PreviousURL")
    private String previousUrl;
    @JsonProperty(value = "Timestamp")
    private Date timeStamp;
    @JsonProperty(value = "Valute")
    private Map<String, Valute> valute;
}
