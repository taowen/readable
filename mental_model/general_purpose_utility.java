// bad

ajax_post({
    url: 'http://example.com/submit',
    data: data,
    on_success: function(response_data) {
        var str = "{\n";
        for (var key in response_data) {
            str += " " + key + " = " + response_data[key] + "\n";
        }
        alert(str + "}");

        // Continue handling 'response_data' ...
    }
});

// good

var format_pretty = function(obj) {
    var str = "{\n";
    for (var key in obj) {
        str += " " + key + " = " + obj[key] + "\n";
    }
    return str + "}";
};

var format_pretty = function(obj, indent) {
    // Handle null, undefined, strings, and non-objects.
    if (obj === null) return "null";
    if (obj === undefined) return "undefined";
    if (typeof obj === "string") return '"' + obj + '"';
    if (typeof obj !== "object") return String(obj);
    if (indent === undefined) indent = "";
    // Handle (non-null) objects.
    var str = "{\n";
    for (var key in obj) {
        str += indent + " " + key + " = ";
        str += format_pretty(obj[key], indent + " ") + "\n";
    }
    return str + indent + "}";
};
