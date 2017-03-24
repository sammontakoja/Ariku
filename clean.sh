#!/usr/bin/env bash

# Create container and image arrays
oldIFS="$IFS"
IFS='
'
IFS=${IFS:0:1}
containerIds=( $(sudo docker ps -q -a) )
imageIds=( $(sudo docker images | grep ariku | awk '{print $3}') )
IFS="$oldIFS"

# Clean containers (kill and rm)
for containerId in "${containerIds[@]}"
    do
        echo "killed container $(sudo docker kill $containerId)";
        echo "removed container $(sudo docker rm $containerId)";
done

# Remove images
for imageId in "${imageIds[@]}"
    do
        sudo docker rmi -f $imageId;
done