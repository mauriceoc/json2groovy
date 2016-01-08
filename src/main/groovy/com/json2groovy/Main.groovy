package com.json2groovy

import groovy.json.JsonSlurper

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

options.f ? convertJson(new File(options.f)) : convertJson(System.in)

void convertJson(def src) {
    def json = src.withReader new JsonSlurper().&parse
    new Json2GroovyPrinter().printJson(json)
}