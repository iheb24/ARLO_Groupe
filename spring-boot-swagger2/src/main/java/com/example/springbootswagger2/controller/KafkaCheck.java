package com.example.springbootswagger2.controller;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




@Service
public class KafkaCheck  {
	
	private Properties properties;
	
	public KafkaCheck(@Value("${spring.kafka.consumer.bootstrap-servers}") String serverLink) {
		this.properties = new Properties();
		properties.put("bootstrap.servers", serverLink);
		properties.put("connections.max.idle.ms", 10000);
		properties.put("request.timeout.ms", 5000);
		

		
	}
    
	
	public boolean check() {

		
		try (AdminClient client = KafkaAdminClient.create(properties))
		{
		    ListTopicsResult topics = client.listTopics();
		    Set<String> names = topics.names().get();
		    if (names.isEmpty())
		    {
		        return false;
		    }
		    return true;
		}
		catch (InterruptedException | ExecutionException e)
		{
		    // Kafka is not available
			return false;
		}
	}
	
}