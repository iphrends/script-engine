const jp = load('jsonpath.js');

let json = {"data":{"obj":{"key":"value","price":10},"string":"string-value","integer":1234,"boolean":true,"float":1234.4,"list":[{"list_":"list-value"}]}};

let obj = jp.query(json, '$[?(@.integer == 12343 || @.boolean == true)]');
console.log(obj);