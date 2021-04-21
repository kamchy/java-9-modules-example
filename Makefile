API := $(shell find api -name \*.java)
GEN := $(shell find generator -name \*.java)
CLI := $(shell find client -name \*.java)
GCLI := $(shell find guiclient -name \*.java)
RELEASE := 15
all: clean dist

clean:
	rm -rf mlib
	rm -rf image

mkmlib:
	mkdir -p mlib

compile_api: mkmlib 
	javac -d mlib/api $(API)

compile_generator: compile_api
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/generator $(GEN)

compile_client: compile_generator
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/client $(CLI)

compile_guiclient: compile_generator
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/guiclient $(GCLI)

compile_fxclient: compile_generator
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/fxclient $(GCLIFX)

compile: compile_api compile_client compile_guiclient compile_generator compile_fxclient

run: compile
	java --enable-preview  --module-path mlib -m client/com.kamilachyla.Main

rungui: compile_guiclient
	java --enable-preview  --module-path mlib -m guiclient/com.kamilachyla.gui.Main

runswing: compile_guiclient

dist: compile
	jlink  --module-path mlib/ \
		--add-modules client,api,generator,guiclient \
		--output image \
		--launcher cli=client/com.kamilachyla.Main  \
		--launcher gcli=guiclient/com.kamilachyla.guigen.ImageGenerator  \
		--launcher swingcli=guiclient/com.kamilachyla.gui.Main  \
		--no-man-pages \
		--no-header-files \
		--compress 2 \
		--strip-debug \

