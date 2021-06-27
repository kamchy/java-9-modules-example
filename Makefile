API := $(shell find api -name \*.java)
GEN := $(shell find generator -name \*.java)
CLI := $(shell find client -name \*.java)
GCLI := $(shell find guiclient -name \*.java)
GCLIFX := $(shell find fxclient -name \*.java)
RELEASE := 16
all: clean dist

clean:
	rm -rf mlib
	rm -rf image

mkmlib:
	mkdir -p mlib

compile_api: mkmlib 
	echo "<--Compile api"
	javac -d mlib/api $(API)

compile_generator: compile_api
	echo "<--compile generator"
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/generator $(GEN)

compile_client: compile_generator
	echo "<--compile client"
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/client $(CLI)

compile_guiclient: compile_generator
	echo "<--compile guiclient"
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/guiclient $(GCLI)

compile_fxclient: compile_generator
	echo "<--compile fxclient"
	#javac --enable-preview --release $(RELEASE) --module-path /usr/share/openjfx/lib/;mlib/ --add-modules javafx.controls,javafx.swing,javafx.graphics,api,generator -d mlib/fxclient $(GCLIFX)
	cp /usr/share/openjfx/lib/*.jar mlib
	javac --enable-preview --release $(RELEASE) --module-path mlib -d mlib/fxclient $(GCLIFX)

compile: compile_api compile_client compile_guiclient compile_generator compile_fxclient

run: compile
	java --enable-preview  --module-path mlib -m client/com.kamilachyla.Main

rungui: compile_guiclient
	java --enable-preview  --module-path mlib -m guiclient/com.kamilachyla.gui.Main

runswing: compile_guiclient
runfx: compile_fxclient
	java --enable-preview  --module-path mlib -m fxclient/com.kamilachyla.fxclient.gui.Main

dist: compile
	jlink  --module-path mlib/ \
		--add-modules client,api,generator,guiclient,fxclient \
		--output image \
		--launcher cli=client/com.kamilachyla.Main  \
		--launcher gcli=guiclient/com.kamilachyla.guigen.ImageGenerator  \
		--launcher swingcli=guiclient/com.kamilachyla.gui.Main  \
		--launcher fxcli=fxclient/com.kamilachyla.fxclient.gui.Main \
		--no-man-pages \
		--no-header-files \
		--compress 2 \
		--strip-debug

	for name in image/bin/fxcli image/bin/swingcli image/bin/cli image/bin/gcli ; do sed -i 's/\(JLINK_VM_OPTIONS=\)/\1--enable-preview/' $$name; done
