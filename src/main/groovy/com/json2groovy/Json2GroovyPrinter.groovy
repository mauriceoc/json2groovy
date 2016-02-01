package com.json2groovy

public class Json2GroovyPrinter {

    final IndentPrinter printer

    public Json2GroovyPrinter(PrintStream printStream) {
        this(new PrintWriter(printStream))
    }

    public Json2GroovyPrinter(Writer writer) {
        this.printer = new IndentPrinter(writer, '    ')
    }

    public void printJson(def json) {
        if (json instanceof Map) {
            printMap(json as Map)
        } else if (json instanceof List) {
            printList(json as List)
        } else if (json instanceof String) {
            printString(json as String)
        } else {
            printer.print(json as String)
        }
    }

    private void printString(String string) {
        final String escaped = string
                .replace('\'', '\\\'')
                .replace('\$', '\\\$')
        printer.print("'$escaped'")
    }

    private void printList(List list) {
        printer.println('[')

        printer.incrementIndent()
        list.eachWithIndex {
            v, i ->
                printer.printIndent()
                printJson(v)
                if (i != (list.size() - 1)) {
                    printer.println(',')
                } else {
                    printer.println()
                }
        }
        printer.decrementIndent()

        printer.printIndent()
        printer.print(']')
    }

    private void printMap(Map map) {
        printer.println('[')

        printer.incrementIndent()
        map.eachWithIndex {
            k, v, i ->
                printer.printIndent()
                printer.print(k)
                printer.print(': ')
                printJson(v)
                if (i != (map.size() - 1)) {
                    printer.println(',')
                } else {
                    printer.println()
                }
        }
        printer.decrementIndent()

        printer.printIndent()
        printer.print(']')
    }

}
