package com.rexxeagle.habittracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HabittrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabittrackerApplication.class, args);
		System.out.println("Habit Tracker Application has started successfully!");
		System.out.println("Visit http://localhost:9090 to access the application.");
	}

}
