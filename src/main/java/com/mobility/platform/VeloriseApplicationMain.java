package com.mobility.platform;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class VeloriseApplicationMain {

	public static void main(String[] args) {
		SpringApplication.run(VeloriseApplicationMain.class, args);
	}

}
