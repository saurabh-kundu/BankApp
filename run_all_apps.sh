#!/bin/bash

echo "Building services..."
mvn clean install

# Kill application running on port 8081
PID_8081=$(lsof -t -i:8081)
if [ -n "$PID_8081" ]; then
    echo "Killing application on port 8081 with PID $PID_8081..."
    kill -9 $PID_8081
fi

# Kill application running on port 8082
PID_8082=$(lsof -t -i:8082)
if [ -n "$PID_8082" ]; then
    echo "Killing application on port 8082 with PID $PID_8082..."
    kill -9 $PID_8082
fi

# Kill application running on port 8083
PID_8083=$(lsof -t -i:8083)
if [ -n "$PID_8083" ]; then
    echo "Killing application on port 8083 with PID $PID_8083..."
    kill -9 $PID_8083
fi

java -jar ./UserService/target/UserService-0.0.1-SNAPSHOT.jar --server.port=8081 &
java -jar ./DepositService/target/DepositService-0.0.1-SNAPSHOT.jar --server.port=8082 &
java -jar ./PaymentsService/target/PaymentsService-0.0.1-SNAPSHOT.jar --server.port=8083


echo "all services started..."