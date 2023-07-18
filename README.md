# kfk - Kafka cli

Allows to run most of the Kafka binary scripts without leading "kafka-" and "--bootstrap-server localhost:9092" argument

## Example:

instead  : \<path-to-binary>/kafka-topics.sh --bootstrap-server localhost:9092 --list

just run : kfk topics --list

## Install and start:

- ./install-cli.sh - will dowlnload Kafka binaries v3.3.2 and extract it to your home directory (you might want to run it with sudo in order script been able to copy kfk to /usr/bin/ or copy it yourself)
- docker-compose up - will start dev environment with one Kafka server v3.3.2 and one Zookeeper server in docker 
- kfk topics - if you get output with help page than you good to go

## Usage:

- kfk topics --create --topic first_topic --partitions 3 --replication-factor 2 (for replication factor > 1 to work you should have more than one Kafka broker, try to use multi-docker-compose.yml to have 3 Kafka brokers)
- round-robin-producer.sh --topic \<topic-name> 
- kfk console-consumer --topic \<topic-name>

Docker images and Kafka version interoperability: 

https://docs.confluent.io/platform/current/installation/versions-interoperability.html#cp-and-apache-ak-compatibility