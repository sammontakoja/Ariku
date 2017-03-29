#!/usr/bin/env bash

network=ariku_nw

# Create own network for ariku apps so they can talk with each other
sudo docker network create --driver bridge ${network}

# Create REST docker image
sudo docker build --no-cache -t ariku/rest:1.0 -f DockerRest ..

# Create HTTP server image
sudo docker build --no-cache -t ariku/html:1.0 -f DockerHttpServer ..