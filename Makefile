LIB_PATH=/home/shank/workspace/Project2-Thrift/lib/libthrift-0.9.1.jar:/home/shank/workspace/Project2-Thrift/lib/slf4j-api-1.7.12.jar
all: clean
	mkdir bin
	mkdir bin/client_classes
	mkdir bin/server_classes
	javac -classpath $(LIB_PATH) -d bin/client_classes/ src/Client.java gen-java/*
	javac -classpath $(LIB_PATH) -d bin/server_classes/ src/Handler.java src/Server.java src/Coordinator.java gen-java/*

clean:
	rm -rf bin/


