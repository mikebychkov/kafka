# kfk - Kafka cli

Allows to run all the Kafka binary scripts without leading "kafka-" and "--bootstrap-server localhost:9092" argument

## Example:

instead  : <path to binary>/kafka-topics.sh --bootstrap-server localhost:9092 --list

just run : kfk topics --list

## How to:

- ./install-cli.sh - will dowlnload Kafka binaries v3.3.2 and extract it to your home directory (you might want to run it with sudo in order script been able to copy kfk to /usr/bin/ or copy it yourself)
- docker-compose up - will start dev environment with one Kafka server v3.3.2 and one Zookeeper server in docker 
- kfk topics - if you get output with help page than you good to go

Docker images and Kafka version interoperability: 

'''
https://docs.confluent.io/platform/current/installation/versions-interoperability.html#cp-and-apache-ak-compatibility
'''