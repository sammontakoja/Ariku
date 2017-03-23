#!/usr/bin/env bash

function installDockerMachine {
curl -L https://github.com/docker/machine/releases/download/v0.10.0/docker-machine-`uname -s`-`uname -m` >/tmp/docker-machine && chmod +x /tmp/docker-machine && sudo cp /tmp/docker-machine /usr/local/bin/docker-machine
}

dockerCommandOutput=$(docker-machine -v)

case "$dockerCommandOutput" in
  *version*) echo "docker-machine already installed. ('docker-machine' command found from PATH)" ;;
  *)         installDockerMachine ;;
esac