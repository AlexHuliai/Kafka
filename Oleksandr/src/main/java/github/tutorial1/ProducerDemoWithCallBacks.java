package github.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallBacks {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBacks.class);
//        System.out.println("Hello world");
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0; i < 10; i++) {
            final ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello World" + Integer.toString(i));

            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        logger.info("Recieved new metadata: \n"
                                + "Topic: " + recordMetadata.topic() + "\n" +
                                "Partition : " + recordMetadata.partition() + "\n"
                                + "Offset : " + recordMetadata.offset() + "\n"
                                + "Timestamp  :" + recordMetadata.timestamp());

                    } else {
                        logger.error("Error while producing");

                    }
                }
            });
        }
            producer.flush();
            producer.close();

    }
}
