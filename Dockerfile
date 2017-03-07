FROM maven:3.3.3-jdk-8
RUN mkdir -p /tmp/ariku 
COPY build /tmp/ariku/build
WORKDIR /tmp/ariku/build
# Compile
RUN ["mvn","clean","install","-DskipTests"]
# Run unit tests
RUN ["mvn","test"]