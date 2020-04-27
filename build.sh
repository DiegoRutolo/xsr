#!/usr/bin/env bash

docker build -t xsr:latest .
docker run --rm xsr