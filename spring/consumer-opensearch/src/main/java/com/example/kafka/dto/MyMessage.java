package com.example.kafka.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyMessage {
    private String key;
    private String msg;
    private String author;
}
