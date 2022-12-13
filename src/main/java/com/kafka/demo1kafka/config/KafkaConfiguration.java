package com.kafka.demo1kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
	
	/**
	 * Propiedades del producer.
	 *
	 * @return
	 */
	public Map<String, Object> producerProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
		props.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
		props.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
		
		return props;
	}
	
	/**
	 * Clase producer.
	 * Este bean será usado como producer.
	 *
	 * @return KafkaTemplate
	 */
	@Bean
	public KafkaTemplate<String, String> createTemplate() {
		Map<String, Object>              senderProps = producerProperties();
		ProducerFactory<String, String> pf          = new DefaultKafkaProducerFactory<>( senderProps );
		
		return new KafkaTemplate<>( pf );
	}
	
	
	// De aquí hacia abajo son las configuraciones de los consumer
	
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
		props.put( ConsumerConfig.GROUP_ID_CONFIG, "devs4j-group" );
		props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
		props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
		
		return props;
	}
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>( consumerProperties() );
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String>
	kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
			new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory( consumerFactory() );
		
		return factory;
	}
}
