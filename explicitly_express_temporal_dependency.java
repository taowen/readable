// bad

public class MoogDiver {
    Gradient gradient;
    List < Spline > splines;
    public void dive(String reason) {
        saturateGradient();
        reticulateSplines();
        diveForMoog(reason);
    }
    ...
}

// good 

public class MoogDiver {
    Gradient gradient;
    List < Spline > splines;
    public void dive(String reason) {
        Gradient gradient = saturateGradient();
        List < Spline > splines = reticulateSplines(gradient);
        diveForMoog(splines, reason);
    }
    ...
}
