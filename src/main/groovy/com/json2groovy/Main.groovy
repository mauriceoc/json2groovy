package com.json2groovy

import groovy.json.JsonException
import groovy.json.JsonSlurper

public class Main {

    private static int SUCCESS_CODE = 0
    private static int INVALID_JSON_CODE = 1
    private static int FILE_NOT_FOUND_CODE = 2
    private static int GENERAL_ERROR_CODE = 3

    public static void main(String[] args) {

        final CliBuilder cli = new CliBuilder(usage: 'json2groovy -[fh]')

        cli.with {
            f longOpt: 'file', args: 1, argName: 'filename', 'Read from a file'
            h longOpt: 'help', 'Show usage information'
        }

        def options = cli.parse(args)

        if (options.h) {
            cli.usage()
            System.exit(SUCCESS_CODE)
        }

        final def json

        try {
            final InputStream inputStream
            if (options.f) {
                final File f = new File(options.f as String)
                inputStream = new FileInputStream(f)
            } else {
                inputStream = System.in
            }
            json = parseJson(inputStream)
        } catch (FileNotFoundException e) {
            System.err.println("File not found: ${options.f as String}")
            System.exit(FILE_NOT_FOUND_CODE)
        } catch (Exception e) {
            System.err.println(e.message)
            System.exit(GENERAL_ERROR_CODE)
        }

        if (json != null) {

            final StringWriter writer = new StringWriter()
            final IndentPrinter indentPrinter = new IndentPrinter(writer, ' ' * 4)

            new Json2GroovyPrinter(indentPrinter).printJson(json)

            println writer.toString()

            System.exit(SUCCESS_CODE)
        } else {
            System.exit(INVALID_JSON_CODE)
        }
    }

    private static def parseJson(InputStream json) {
        final JsonSlurper jsonSlurper = new JsonSlurper()
        try {
            jsonSlurper.parse(json)
        } catch (JsonException e) {
            System.err.println('Invalid Json')
            return null
        }
    }
}