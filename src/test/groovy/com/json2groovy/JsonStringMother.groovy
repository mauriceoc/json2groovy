package com.json2groovy

class JsonStringMother {

    final static String objectString =
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

    final static String objectString_NestedObject = '{ "foo" : { "bob" : "cat" }}'

    final static String objectString_StringValue = '{ "foo" : "bar" }'

    final static String objectString_empty = '{}'

    final static String arrayString = '[1,2,3]'

    final static String arrayString_empty = '[]'

}
