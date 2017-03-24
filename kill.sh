#!/usr/bin/env bash

oldIFS="$IFS"
IFS='
'
IFS=${IFS:0:1}
containerIds=( $(sudo docker ps -q -a) )
IFS="$oldIFS"

for containerId in "${containerIds[@]}"
    do
        echo "killed container $(sudo docker kill $containerId)";
done