package com.json2groovy

import groovy.json.JsonSlurper
import groovy.util.logging.Log
import org.codehaus.groovy.runtime.StringBufferWriter
import spock.lang.Shared
import spock.lang.Specification

@Log
class Json2GroovyPrinterSpec extends Specification {

    @Shared
    def json = new JsonSlurper().parseText(
            '''

            {
                "foo" : "bar",
                "hey" : {
                    "bob" : "splat",
                    "numbers" : [ 1, 2, 3 ],
                    "objects" : [ { "quux" : "1$ hats" }, { "spoo" : "borg's" } ]
                }
            }

            '''
    )

    void 'test1'() {
        when:
        new Json2GroovyPrinter().printJson(json)
        then:
        true
    }

    void 'test2'() {
        setup:

        final StringBuffer stringBuffer = new StringBuffer()
        final StringBufferWriter stringBufferWriter = new StringBufferWriter(stringBuffer)
        final Json2GroovyPrinter printer = new Json2GroovyPrinter(stringBufferWriter)

        when:
        printer.printJson(json)
        log.info(stringBuffer.toString())

        then:
        true
    }
}
