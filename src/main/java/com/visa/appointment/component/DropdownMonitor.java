package com.visa.appointment.component;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
class DropdownMonitor implements CommandLineRunner {

    private boolean notification = false;

    @Override
    public void run(String... args) {
        // Create a ScheduledExecutorService
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 2 seconds
        scheduler.scheduleAtFixedRate(this::checkDropDownOptions, 0, 2, TimeUnit.SECONDS);
    }

    public void checkDropDownOptions() {

        // URL of the webpage with the dropdown
        String url = "https://service2.diplo.de/rktermin/extern/appointment_showForm.do?locationCode=isla&realmId=108&categoryId=1600";

        // Load the webpage using Jsoup
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("Webpage is Down: \n{}", e.getMessage());
        }

        // Find the dropdown element by its ID
        Element dropdown = doc.getElementById("appointment_newAppointmentForm_fields_3__content");

        // Check the number of options in the dropdown
        Elements options = dropdown.getElementsByTag("option");
        int numberOfOptions = options.size();
        log.info("Number of options is " + numberOfOptions);

        // If number of options is greater than 5, send email and WhatsApp messages
        if (numberOfOptions > 5) {
            log.info("GERMANY BHAGO BC");
            if (!notification) {
                notification = WhatsApp.sendNotification();
            }
        }
    }
}
