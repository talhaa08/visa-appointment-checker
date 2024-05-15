package com.visa.appointment.component;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class WhatsApp {

    public static boolean sendNotification() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gate.whapi.cloud/messages/text"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("authorization", "Bearer <YOUR TOKEN HERE>")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"to\":\"<WHATSAPP CHAT ID HERE>\",\"body\":\"*GERMANY BHAGO*\"}"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
        } catch (IOException | InterruptedException e) {
            return false;
        }
        return true;
    }

}
