package com.json2groovy

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

        final JsonSlurper jsonSlurper = new JsonSlurper()

        final def json = options.f ? jsonSlurper.parse(new File(options.f as String)) : jsonSlurper.parse(System.in)

        if (json != null) {

            println()

            final StringWriter writer = new StringWriter()
            new Json2GroovyPrinter(writer, 4).printJson(json)

            println writer.toString()

        } else {
            System.exit(-1)
        }
    }

}