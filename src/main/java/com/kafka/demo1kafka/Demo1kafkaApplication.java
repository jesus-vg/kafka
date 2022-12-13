package com.kafka.demo1kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.security.auth.callback.Callback;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

@SpringBootApplication
public class Demo1kafkaApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger( Demo1kafkaApplication.class );
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@KafkaListener( topics = "devs4j-topic", groupId = "devs4j-group" )
	public void listen( String message ) {
		log.info( "Mensaje recibido = {} ", message );
	}
	
	public static void main( String[] args ) {
		SpringApplication.run( Demo1kafkaApplication.class, args );
	}
	
	/**
	 * Método para enviar un mensaje al consumer.
	 *
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run( String... args ) throws Exception {
		
		/*CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send( "devs4j-topic", "Mensaje simple" );
		SendResult<String, String>                    result;
		try {
			result = future.get();
			String msj = "ok ---- " + result.getProducerRecord().value();
			log.info( msj );
		}
		catch ( InterruptedException | ExecutionException e ) {
			String msj = "error ******************** " + e.getMessage();
			log.error( msj );
		}*/
		
		/*ListenableFuture<SendResult<String, String>>future=
			( ListenableFuture<SendResult<String, String>> ) kafkaTemplate.send("devs4j-topic","Sample message ");
		future.addCallback(new KafkaSendCallback<String, String>() {
			@Override
			public void onSuccess(SendResult<String, String>result) {
				log.info("Message sent");
			}
			@Override
			public void onFailure(Throwable ex) {
				log.error("Error sending message ",ex);
			}
			@Override
			public void onFailure(KafkaProducerException ex) {
				log.error("Error sending message ",ex);
			}
		});*/
		CompletableFuture<SendResult<String, String>> completableFuture =
			kafkaTemplate.send( "devs4j-topic", "Mensaje simple" );
		kafkaTemplate.send( "devs4j-topic", "Mensaje simple" );
		
	}
}
