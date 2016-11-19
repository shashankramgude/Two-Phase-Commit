#!/bin/bash +vx
LIB_PATH=$"/home/shank/workspace/Project2-Thrift/lib/libthrift-0.9.1.jar:/home/shank/workspace/Project2-Thrift/lib/slf4j-api-1.7.12.jar"
#port
#port
java -classpath bin/server_classes:$LIB_PATH Server $1
