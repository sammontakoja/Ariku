#!/usr/bin/env bash

network=ariku_nw

# Start REST server
sudo docker run --name arikurest --network=${network} ariku/rest:1.0  &

# Start html server
sudo docker run --dns=172.18.0.3 --network=${network} -p 80:80 ariku/html:1.0  &