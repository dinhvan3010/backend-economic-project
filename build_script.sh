#!/bin/bash

mvn install

cp ./target/SpringJwtAuthExample-0.0.1-SNAPSHOT.jar ./docker/docker-economic-backend/SpringJwtAuthExample-0.0.1-SNAPSHOT.jar

cd docker/

docker-compose up -d --build economic-server