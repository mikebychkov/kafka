#!/bin/bash
kfk console-producer --producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner $@
