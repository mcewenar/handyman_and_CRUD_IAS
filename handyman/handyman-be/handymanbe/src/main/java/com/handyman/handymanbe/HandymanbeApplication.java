package com.handyman.handymanbe;

import com.handyman.handymanbe.domain.report.Report;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HandymanbeApplication {

	public static void main(String[] args) {

		SpringApplication.run(HandymanbeApplication.class, args);

		/*Report report = new Report("ddff6c8c-3e98-45a9-91c2-36e496bf3939","0b83587e-b8dc-4f24-ac56-25270df4a6fb",
				LocalDateTime.of(2021,11,23,8,40,45),
				LocalDateTime.of(2021,12,23,8,40,45));*/

		//System.out.println(report.getWeekOfYear());



	}

}
