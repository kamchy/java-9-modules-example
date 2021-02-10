API := $(shell find api -name \*.java)
GEN := $(shell find generator -name \*.java)
CLI := $(shell find client -name \*.java)
clean:
	rm -rf mlib

mkmlib:
	mkdir -p mlib

compile_api: mkmlib 
	javac -d mlib/api $(API)

compile_generator: compile_api
	javac --module-path mlib -d mlib/generator $(GEN)

compile_client: compile_generator
	javac --module-path mlib -d mlib/client $(CLI)

compile: compile_api compile_client compile_generator

run: compile
	java --module-path mlib -m client/com.kamilachyla.Main
  
  
