package com.json2groovy

import groovy.json.JsonException
import groovy.json.JsonSlurper

public class Main {

    public static void main(String[] args) {
        def cli = new CliBuilder(usage: 'json2groovy -[fh]')

        cli.with {
            f longOpt: 'file', args: 1, argName: 'filename', 'Read from a file'
            h longOpt: 'help', 'Show usage information'
        }

        def options = cli.parse(args)

        if (options.h) {
            cli.usage()
            return
        }

        def json = options.f ? convertJson(new File(options.f)) :
                convertJson(System.in)

        if (json) {
            final StringWriter writer = new StringWriter()
            new Json2GroovyPrinter(writer).printJson(json)

            println writer.toString()
        } else {
            System.exit(-1)
        }
    }

    private static def convertJson(def src) {
        final def json
        try {
            json = src.withReader new JsonSlurper().&parse
        } catch (JsonException e) {
            System.err.println("Invalid JSON")
            json = null
        }
        return json
    }
}