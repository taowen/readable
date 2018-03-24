// bad

boolean done = false;
while ( /* condition */ && !done) {
    ...
    if (...) {
        done = true;
        continue;
    }
}

// good

while ( /* condition */ ) {
    ...
    if (...) {
        break;
    }
}
