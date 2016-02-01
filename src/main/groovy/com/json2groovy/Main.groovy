package com.json2groovy

import groovy.json.JsonException
import groovy.json.JsonSlurper

public class Main {

    private static int SUCCESS_CODE = 0

    private static int INVALID_JSON_CODE = 1
    private static int FILE_NOT_FOUND_CODE = 2

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

        if(options.f) {
            final File f = new File(options.f as String)
            if(f.exists()) {
                final FileInputStream fis = new FileInputStream(f)
                json = parseJson(fis)
            } else {
                System.err.println("File does not exist: ${options.f}")
                System.exit(FILE_NOT_FOUND_CODE)
            }
        } else {
            json = parseJson(System.in)
        }

        if (json != null) {

            final StringWriter writer = new StringWriter()
            new Json2GroovyPrinter(writer, 4).printJson(json)

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
        } catch(JsonException e) {
            System.err.println('Invalid Json')
            return null
        }
    }
}