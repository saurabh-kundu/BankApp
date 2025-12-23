#!/bin/bash

set -e

echo "Starting Redis..."
brew services start redis
echo "Redis server started..."

echo "Building services..."
mvn clean install

kill_port() {
    local port=$1
    local pid=$(lsof -t -i:$port 2>/dev/null)
    if [ -n "$pid" ]; then
        echo "Killing application on port $port with PID $pid..."
        kill -9 $pid
        sleep 1  # Give time for port to be released
    else
        echo "No application running on port $port"
    fi
}

kill_port 8081
kill_port 8082
kill_port 8083

echo "Starting services..."

java -jar ./UserService/target/UserService-0.0.1-SNAPSHOT.jar --server.port=8081 > /dev/null 2>&1 &
echo "UserService started on port 8081 (PID: $!)"

java -jar ./PaymentsService/target/PaymentsService-0.0.1-SNAPSHOT.jar --server.port=8082 > /dev/null 2>&1 &
echo "PaymentsService started on port 8082 (PID: $!)"

java -jar ./DepositService/target/DepositService-0.0.1-SNAPSHOT.jar --server.port=8083 > /dev/null 2>&1 &
echo "DepositService started on port 8083 (PID: $!)"

echo "All services started successfully!"