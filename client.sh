#!/bin/bash +vx
LIB_PATH=$"/home/shank/workspace/Project2-Thrift/lib/libthrift-0.9.1.jar:/home/shank/workspace/Project2-Thrift/lib/slf4j-api-1.7.12.jar"
#port
#simple/secure ip port
java -classpath bin/client_classes:$LIB_PATH Client $1 $2 $3
