#!/bin/bash
wget https://archive.apache.org/dist/kafka/3.3.2/kafka_2.13-3.3.2.tgz \
  && tar -xvzf kafka_2.13-3.3.2.tgz -C ~/
  && rm kafka_2.13-3.3.2.tgz
cp kfk /usr/bin/
