#!/bin/bash
docker-compose -f kafka-compose.yml down \
  && docker-compose -f kafka-compose.yml up
