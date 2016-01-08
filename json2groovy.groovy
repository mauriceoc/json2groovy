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
    printObject(json)
}

void printObject(def o) {
    if(o instanceof Map) {
        printMap(o)
    } else if(o instanceof List) {
        printList(o)
    } else if(o instanceof String){
        print "'$o'"
    } else {
        print o
    }
}

void printList(List list) {
    int last = list.size() - 1

    println "["
    list.eachWithIndex {
        v, i ->
            printObject(v)
            if(i != last) {
                println ","
            } else {
                print "\n"
            }
    }
    println "\n]"
}

void printMap(Map map) {
    int last = map.size() - 1
    
    println "["
    map.eachWithIndex {
        k, v, i ->
            print "$k: "
            printObject(v)
            if(i != last) {
                println ","
            } else {
                print "\n"
            }
    }
    print "]"
}
