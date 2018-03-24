// bad

public class VacationPolicy {
 public void accrueUSDivisionVacation() {
 // code to calculate vacation based on hours worked to date
 // ...
 // code to ensure vacation meets US minimums
 // ...
 // code to apply vaction to payroll record
 // ...
 }
 public void accrueEUDivisionVacation() {
 // code to calculate vacation based on hours worked to date
 // ...
 // code to ensure vacation meets EU minimums
 // ...
 // code to apply vaction to payroll record
 // ...
 }
}

// good

abstract public class VacationPolicy {
    public void accrueVacation() {
        calculateBaseVacationHours();
        alterForLegalMinimums();
        applyToPayroll();
    }
    private void calculateBaseVacationHours() { /* ... */ };
    abstract protected void alterForLegalMinimums();
    private void applyToPayroll() { /* ... */ };
}

public class USVacationPolicy extends VacationPolicy {
    @Override protected void alterForLegalMinimums() {
        // US specific logic
    }
}
public class EUVacationPolicy extends VacationPolicy {
    @Override protected void alterForLegalMinimums() {
        // EU specific logic
    }
}
