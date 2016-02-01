package com.json2groovy

class JsonStringMother {

    final static String objectString_apostraphe = '{ "foo" : "bar\'s" }'

    final static String objectString_dollarSign = '{ "foo" : "$bar" }'

    final static String objectString_nestedObject = '{ "foo" : { "bob" : "cat" }}'

    final static String objectString_stringValue = '{ "foo" : "bar" }'

    final static String objectString_empty = '{}'

    final static String arrayString = '[1,2,3]'

    final static String arrayString_empty = '[]'

}
