# Delivery Semantics 

## At-Most-Once Delivery

- In order to enable At-Most-Once semantics in Kafka, we'll need to set “enable.auto.commit” to “true” at the consumer.

## At-Least-Once Delivery

- set the property “ack” to value “1” on the producer side.
- set “enable.auto.commit” property to value “false” on the consumer side.
- set “enable.idempotence” property to value “true“ on producer side.
- attach the sequence number and producer id to each message from the producer.

## Exactly-Once Delivery

- enable idempotent producer and transactional feature on the producer by setting the unique value for “transaction.id” for each producer.
- enable transaction feature at the consumer by setting property “isolation.level” to value “read_committed“.

Article to read: https://www.baeldung.com/kafka-message-delivery-semantics

