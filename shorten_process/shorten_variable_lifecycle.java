// bad

class LargeClass {
    string str_;
    void Method1() {
        str_ = ...;
        Method2();
    }
    void Method2() {
        // Uses str_
    }
    // Lots of other methods that don't use str_ ...
};

// good

class LargeClass {
    void Method1() {
        string str = ...;
        Method2(str);
    }
    void Method2(string str) {
        // Uses str
    }
    // Now other methods can't see str.
};
