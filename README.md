# kfk - Kafka cli

Allows to run all the Kafka binary scripts without leading "kafka-" and "--bootstrap-server localhost:9092" argument

## Example:

instead  : <path to binary>/kafka-topics.sh --bootstrap-server localhost:9092 --list

just run : kfk topics --list
