#!/bin/bash

kill_port() {
    local port=$1
    local service_name=$2
    local pid=$(lsof -t -i:$port 2>/dev/null)
    if [ -n "$pid" ]; then
        echo "Killing $service_name on port $port with PID $pid..."
        kill -9 $pid
        sleep 0.5
    else
        echo "No application running on port $port"
    fi
}

echo "Stopping services..."

kill_port 8082 "PaymentsService"
kill_port 8081 "UserService"
kill_port 8083 "DepositService"

# Stop Redis
echo "Stopping Redis..."
brew services stop redis 2>/dev/null || kill_port 6379 "Redis"

echo "All services stopped successfully!"