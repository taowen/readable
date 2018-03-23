// bad

for (int i = 0; i < results.size(); i++) {
    if (results[i] != NULL) {
        non_null_count++;
        if (results[i] - > name != "") {
            cout << "Considering candidate..." << endl;
            ...
        }
    }
}

// good

for (int i = 0; i < results.size(); i++) {
    if (results[i] == NULL) continue;
    non_null_count++;
    if (results[i] - > name == "") continue;
    cout << "Considering candidate..." << endl;
    ...
}
