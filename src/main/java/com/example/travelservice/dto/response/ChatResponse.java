package com.example.travelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ChatResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reply;

    public ChatResponse(String reply) {
        this.reply = reply;
    }
}