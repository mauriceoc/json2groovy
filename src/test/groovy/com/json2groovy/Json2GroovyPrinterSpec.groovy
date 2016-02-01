package com.json2groovy

import groovy.json.JsonSlurper
import groovy.util.logging.Log
import org.codehaus.groovy.runtime.StringBufferWriter
import spock.lang.Specification
import spock.lang.Unroll

@Log
class Json2GroovyPrinterSpec extends Specification {

    @Unroll
    void 'test #input'(String input, String output) {
        setup:
        def json = new JsonSlurper().parseText(input)

        final StringBuffer stringBuffer = new StringBuffer()
        final StringBufferWriter stringBufferWriter = new StringBufferWriter(stringBuffer)
        final Json2GroovyPrinter printer = new Json2GroovyPrinter(stringBufferWriter)

        when:
        printer.printJson(json)

        then:
        stringBuffer.toString() == output

        where:
        input                                     | output
        JsonStringMother.arrayString              | '[\n1,\n2,\n3\n]'
        JsonStringMother.arrayString_empty        | '[\n]'
        JsonStringMother.objectString_empty       | '[\n]'
        JsonStringMother.objectString_stringValue | "[\nfoo: 'bar'\n]"
        JsonStringMother.objectString_nestedObject| "[\nfoo: [\nbob: 'cat'\n]\n]"
        JsonStringMother.objectString_dollarSign  | "[\nfoo: '\\\$bar'\n]"
        JsonStringMother.objectString_apostraphe  | "[\nfoo: 'bar\\\'s'\n]"
    }

}
