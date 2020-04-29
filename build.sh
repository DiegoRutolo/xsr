#!/usr/bin/env bash

docker build -t xsr:latest .
docker run $@ -d -p 10097:10097 xsr