# java-9-modules-example

## Goals
This is a simple, modular java 9 application with three modules. 

It illustrates concepts described in [Praktyczny przykład - trzy moduły](https://kamilachyla.com/posts/java-9-praktyczny-przyklad-trzy-moduly/) blog post (modular application example).

Generates images with specified width, height, file name and type or rectangle generator.

## Requirements
**Requires java 16**
Originally the project was developed on java 9 (hence its gihub name). In the meantime, as new java versions had been arriving, I extended inital simple app and created a sandbox for checking out new features of subsequent Java releases (records, sealed classes, var ect). 

Currently it requires Java 16 and should be built with `--enable-preview` option.

## Using Make

*make* target would generate image in *image* directory with following scripts in /image/bin folder:

* cli - just prints to stdout a list of generated rectangles
* gcli - generates single image from commandline
* swingcli - shows Swing window
* fxcli - shows javafx window

### Fixing launcher scripts

In ordrer to be able to actually run them, I edit the scripts and add `--enable-preview` to each script's JLINK_VM_OPTIONS 
, so insted of 

```bash 
#!/bin/sh
JLINK_VM_OPTIONS=
DIR=`dirname $0`
$DIR/java $JLINK_VM_OPTIONS -m client/com.kamilachyla.Main "$@"
```
the script looks like this:

```bash 
#!/bin/sh
JLINK_VM_OPTIONS=--enable-preview
DIR=`dirname $0`
$DIR/java $JLINK_VM_OPTIONS -m client/com.kamilachyla.Main "$@"
```

The command that does the trick is simple loop with a `sed` call (double $ sign to refer to simple variable, not make variable; see [gnu make official docs](https://www.gnu.org/software/make/manual/html_node/Reference.html):

```bash
	for name in image/bin/fxcli image/bin/swingcli image/bin/cli image/bin/gcli ; do 
    sed -i 's/\(JLINK_VM_OPTIONS=\)/\1--enable-preview/' $$name; 
  done
```

## Images and screenshots
Resulting images:

![image - generator: squares](out.png)
![image2 - genertor: grid](out2.png)

Two GUIs:
![Swing gui](swing_client.png)
![Javafx gui](javafx_client.png)

