package com.json2groovy

import groovy.json.JsonException
import groovy.json.JsonSlurper

public class Main {

    public static void main(String[] args) {

        final CliBuilder cli = new CliBuilder(usage: 'json2groovy -[fih]')
        final ExitCode exitCode

        cli.with {
            f longOpt: 'file', args: 1, argName: 'filename', 'Read from a file'
            i longOpt: 'indent', args: 1, argName: 'indentation level', 'Indentation level'
            h longOpt: 'help', 'Show usage information'
        }

        def options = cli.parse(args)

        if (options && options.h) {

            cli.usage()
            exitCode = ExitCode.SUCCESS

        } else {

            final int indentation

            if(options && options.i) {
                indentation = Integer.parseInt(options.i as String)
            } else {
                indentation = 4
            }

            final InputStream inputStream

            if (options && options.f) {
                final File f = new File(options.f as String)
                inputStream = new FileInputStream(f)
            } else {
                inputStream = System.in
            }
            
            try {

                final def json = new JsonSlurper().parse(inputStream)

                final StringWriter writer = new StringWriter()
                final IndentPrinter indentPrinter = new IndentPrinter(writer, ' ' * indentation)

                new Json2GroovyPrinter(indentPrinter).printJson(json)

                println writer.toString()

                exitCode = ExitCode.SUCCESS

            } catch (FileNotFoundException e) {

                System.err.println("File not found: ${options.f as String}")
                exitCode = ExitCode.FILE_NOT_FOUND

            } catch (JsonException e) {

                System.err.println('Invalid Json')
                exitCode = ExitCode.INVALID_JSON

            } catch (Exception e) {

                System.err.println(e.message)
                exitCode = ExitCode.GENERAL_ERROR

            }
        }

        System.exit(exitCode.code)
    }
    
}