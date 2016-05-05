package com.json2groovy

public class Json2GroovyPrinter {

    final IndentPrinter indentPrinter

    public Json2GroovyPrinter(IndentPrinter indentPrinter) {
        this.indentPrinter = indentPrinter
    }

    public void printJson(def json) {
        if (json instanceof Map) {
            printMap(json as Map)
        } else if (json instanceof List) {
            printList(json as List)
        } else if (json instanceof String) {
            printString(json as String)
        } else {
            indentPrinter.print(json as String)
        }
    }

    private void printString(String string) {
        final String escaped = string
                .replace('\'', '\\\'')
                .replace('\$', '\\\$')
        indentPrinter.print("'$escaped'")
    }

    private void printList(List list) {

        if(list.isEmpty()) {
            indentPrinter.print('[]')
        } else {
            indentPrinter.println('[')
            indentPrinter.incrementIndent()
            list.eachWithIndex {
                v, i ->
                    indentPrinter.printIndent()
                    printJson(v)
                    if (i != (list.size() - 1)) {
                        indentPrinter.println(',')
                    } else {
                        indentPrinter.println()
                    }
            }
            indentPrinter.decrementIndent()
            indentPrinter.printIndent()
            indentPrinter.print(']')
        }
    }

    private void printMap(Map map) {

        if(map.isEmpty()) {
            indentPrinter.print('[:]')
        } else {
            indentPrinter.println('[')
            indentPrinter.incrementIndent()
            map.eachWithIndex {
                k, v, i ->
                    indentPrinter.printIndent()
                    indentPrinter.print(k)
                    indentPrinter.print(': ')
                    printJson(v)
                    if (i != (map.size() - 1)) {
                        indentPrinter.println(',')
                    } else {
                        indentPrinter.println()
                    }
            }
            indentPrinter.decrementIndent()
            indentPrinter.printIndent()
            indentPrinter.print(']')
        }
    }

}
