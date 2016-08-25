package com.json2groovy

import groovy.json.JsonException
import groovy.json.JsonSlurper

public class Main {

    public static void main(String[] args) {

        final CliBuilder cli = new CliBuilder(usage: 'json2groovy -[fh]')

        cli.with {
            f longOpt: 'file', args: 1, argName: 'filename', 'Read from a file'
            h longOpt: 'help', 'Show usage information'
        }

        def options = cli.parse(args)

        if (options.h) {
            cli.usage()
            System.exit(ExitCode.SUCCESS.code)
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

            json = new JsonSlurper().parse(inputStream)

            final StringWriter writer = new StringWriter()
            final IndentPrinter indentPrinter = new IndentPrinter(writer, ' ' * 4)

            new Json2GroovyPrinter(indentPrinter).printJson(json)

            println writer.toString()

            System.exit(ExitCode.SUCCESS.code)

        } catch (FileNotFoundException e) {
            System.err.println("File not found: ${options.f as String}")
            System.exit(ExitCode.FILE_NOT_FOUND.code)
        } catch (JsonException e) {
            System.err.println('Invalid Json')
            System.exit(ExitCode.INVALID_JSON.code)
        } catch (Exception e) {
            System.err.println(e.message)
            System.exit(ExitCode.GENERAL_ERROR.code)
        }

    }

}