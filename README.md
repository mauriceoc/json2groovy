# json2groovy

Converts JSON to the Groovy native format. 

`{ "foo" : "bar" } -> [ foo : 'bar' ]`

Useful for pasting into an IDE or whatever.

## Usage

To build: 

`gradle shadowJar`

To run:

`java -jar build/libs/json2groovy-all.jar`

For further usage, reading from a file etc. try:

`java -jar build/libs/json2groovy-all.jar -h`
