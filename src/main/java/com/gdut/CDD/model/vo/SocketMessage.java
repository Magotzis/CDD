package com.gdut.CDD.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocketMessage {
    private String name;

    private Object message;

    private String date;

    public SocketMessage(String message, String date) {
        this.message = message;
        this.date = date;
    }
}