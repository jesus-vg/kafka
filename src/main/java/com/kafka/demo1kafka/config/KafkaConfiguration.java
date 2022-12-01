package com.kafka.demo1kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
	
	/**
	 * - BOOTSTRAP_SERVERS_CONFIG : Lista de brokers de kafka en el cluster.
	 * - GROUP_ID_CONFIG: Consumer group que consumirá los mensajes
	 * -ENABLE_AUTO_COMMIT_CONFIG: Determina si se hará commit al offset de forma periódica
	 * -AUTO_COMMIT_INTERVAL_MS_CONFIG: Determina la frecuencia en milisegundos
	 * en la que se hará commit a los offsets, solo es necesaria si
	 * ENABLE_AUTO_COMMIT_CONFIG = true.
	 * -SESSION_TIMEOUT_MS_CONFIG: Timeout utilizado para determinar errores en los clientes.
	 * -KEY_DESERIALIZER_CLASS_CONFIG: Clase a utilizar para deserializar la llave
	 * -VALUE_DESERIALIZER_CLASS_CONFIG: Clase a utilizar para deserializar el mensaje
	 *
	 * @return
	 */
	public Map<String, Object> consumerProperties() {
		Map<String, Object> props = new HashMap<>();
		
		props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
		props.put( ConsumerConfig.GROUP_ID_CONFIG, "grupo1" );
		props.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true );
		props.put( ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100" );
		props.put( ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000" );
		props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class );
		props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
		
		return props;
	}
	
	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>( consumerProperties() );
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, String>
	kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String>
			factory = new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory( consumerFactory() );
		
		return factory;
	}
}
