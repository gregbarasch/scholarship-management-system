clean:
	@mvn clean 1>/dev/null 2>/dev/null

build:
	@mvn install


run: build
	@mvn exec:java
