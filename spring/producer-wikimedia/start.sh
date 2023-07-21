#!/bin/bash
docker-compose down \
  && ./mvnw clean package -DskipTests \
  && docker-compose up
