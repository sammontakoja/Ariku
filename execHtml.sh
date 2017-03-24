#!/usr/bin/env bash
psLine=$(sudo docker ps | grep ariku/html)

if [ -z "$psLine" ]
    then
        echo "Process do not exist";
        exit;
fi

id=$(echo $psLine | awk '{print$1}')

sudo docker exec $id "$@"