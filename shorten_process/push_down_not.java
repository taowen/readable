// bad

if (!(file_exists && !is_protected)) Error("Sorry, could not read file.");

// good

if (!file_exists || is_protected) Error("Sorry, could not read file.");
