package github.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoAssignSeek {
    public static void main(String[] args) {



        String bootstrapServers = "127.0.0.1:9092";
        String topic = "first_topic";

        Logger logger = LoggerFactory.getLogger(ConsumerDemoAssignSeek.class.getName());

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        TopicPartition partitionToReadFrom = new TopicPartition(topic , 0);
        long offsetToReadFrom = 15l;
        consumer.assign(Arrays.asList(partitionToReadFrom));
        consumer.seek(partitionToReadFrom,offsetToReadFrom);

        int numberOfMessages = 5;
        boolean keepOnReading = true;


        while(keepOnReading){
         ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));


         for(ConsumerRecord<String, String> record : records){
             numberOfMessages += 1;

             logger.info("Key : "+ record.key()+"Value : "+record.value());
             logger.info("Partition : "+
                     record.partition()+" Offsets : "+
                     record.offset());
             if(numberOfMessages >= numberOfMessages){
                 keepOnReading = false ;
                 break;
             }
         }
        }
        logger.info("Exiting the program");

       // consumer.subscribe(Collections.singleton(topic));
    }
}
