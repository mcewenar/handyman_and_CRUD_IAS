package com.handyman.handymanbe;

import com.handyman.handymanbe.repository.report.ReportRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootApplication
public class HandymanbeApplication {

	public ReportRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(HandymanbeApplication.class, args);
		HashMap<String, Long> hoursCalculated = new HashMap<>();

		hoursCalculated.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});


		/*

		HashMap<String, Long> hoursCalculated2;
		HashMap<String, Long> hoursCalculated3;
		HashMap<String, Long> hoursCalculated4;
		hoursCalculated = hoursCalculated(3, 23);
		hoursCalculated2 = hoursCalculated(3, 14);
		hoursCalculated3 = hoursCalculated(8, 16);
		hoursCalculated4 = hoursCalculated(20, 23);
		System.out.println(hoursCalculated4.get("night0_7"));

        /*hoursCalculated.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });*/

        /*hoursCalculated2.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });*/


        /*hoursCalculated3.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

		hoursCalculated4.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});
	}*/



	}

}
