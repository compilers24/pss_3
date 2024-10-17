$(eval TMP := $(shell mktemp))
JAVAC = "javac"
JAVA = 'java'                
SCANNER = "Scanner"
PARSER = "Parser"
COMPILER = "Compiler"
GENERATOR = "CodeGenerator"
INP0 = "./compiler.txt"

all: compile
compile:
	$(JAVAC) -Xlint $(SCANNER).java $(PARSER).java $(COMPILER).java $(GENERATOR).java

test:
			$(JAVA) $(COMPILER) $(INP0)
			./addition
			echo $?