package com.kafka.demo1kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class Demo1kafkaApplication {
	
	public static void main( String[] args ) {
		SpringApplication.run( Demo1kafkaApplication.class, args );
	}
	
	@KafkaListener( topics = "devs4j-topic", groupId = "devs4j-group" )
	public void listen( String message ) {
		System.out.println( "Received Messasge in group foo:" + message );
	}
}
