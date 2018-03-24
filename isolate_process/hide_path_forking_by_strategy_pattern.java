// bad

public Money calculatePay(Employee e)
throws InvalidEmployeeType {
    switch (e.type) {
        case COMMISSIONED:
            return calculateCommissionedPay(e);
        case HOURLY:
            return calculateHourlyPay(e);
        case SALARIED:
            return calculateSalariedPay(e);
        default:
            throw new InvalidEmployeeType(e.type);
    }
}

// good

public abstract class Employee {
    public abstract boolean isPayday();
    public abstract Money calculatePay();
    public abstract void deliverPay(Money pay);
}
-- -- -- -- -- -- -- -- -
public interface EmployeeFactory {
    public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType;
}
-- -- -- -- -- -- -- -- -
public class EmployeeFactoryImpl implements EmployeeFactory {
    public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
        switch (r.type) {
            case COMMISSIONED:
                return new CommissionedEmployee(r);
            case HOURLY:
                return new HourlyEmployee(r);
            case SALARIED:
                return new SalariedEmploye(r);
            default:
                throw new InvalidEmployeeType(r.type);
        }
    }
}

// bad

public class Loan...
    private double notional;
    private double outstanding;
    private int rating;
    private double unusedPercentage;
    private Date start;
    private Date expiry;
    private Date maturity;
    private static final int MILLIS_PER_DAY = 86400000;
    public double calcCapital() {
        return riskAmount() * duration() * RiskFactor.forRiskRating(rating);
    }
    private double calcUnusedRiskAmount() {
        return (notional - outstanding) * unusedPercentage;
    }
    private double duration() {
        if (expiry == null)
            return ((maturity.getTime() - start.getTime()) / MILLIS_PER_DAY) / 365;
        else if (maturity == null)
            return ((expiry.getTime() - start.getTime()) / MILLIS_PER_DAY) / 365;
        else {
            long millisToExpiry = expiry.getTime() - start.getTime();
            long millisFromExpiryToMaturity = maturity.getTime() - expiry.getTime();
            double revolverDuration = (millisToExpiry / MILLIS_PER_DAY) / 365;
            double termDuration = (millisFromExpiryToMaturity / MILLIS_PER_DAY) / 365;
            return revolverDuration + termDuration;
        }
    }
    private double riskAmount() {
        if (unusedPercentage != 1.00)
            return outstanding + calcUnusedRiskAmount();
        else
            return outstanding;
    }
    public void setOutstanding(double newOutstanding) {
        outstanding = newOutstanding;
    }
    private void setUnusedPercentage() {
        if (expiry != null && maturity != null) {
            if (rating > 4)
                unusedPercentage = 0.95;
            else
                unusedPercentage = 0.50;
        } else if (maturity != null) {
            unusedPercentage = 1.00;
        } else if (expiry != null) {
            if (rating > 4)
                unusedPercentage = 0.75;
            else
                unusedPercentage = 0.25;
        }
    }
}

// good

public class Loan...
    public static Loan newRCTL(double notional, Date start, Date expiry,
        Date maturity, int rating) {
        return new Loan(notional, start, expiry, maturity, rating, new RCTLCapital());
    }
public static Loan newRevolver(double notional, Date start, Date expiry,
    int rating) {
    return new Loan(notional, start, expiry, null, rating, new RevolverCapital());
}
public static Loan newTermLoan(double notional, Date start, Date maturity,
    int rating) {
    return new Loan(notional, start, null, maturity, rating, new TermLoanCapital());
}
public abstract class CapitalStrategy {
    protected Loan loan;
    protected static final int MILLIS_PER_DAY = 86400000;
    public double calc(Loan loan) {
        this.loan = loan;
        return riskAmount() * duration() * RiskFactor.forRiskRating(loan.getRating());
    }
    protected abstract double duration();
    protected abstract double riskAmount();
}
public class TermLoanCapital extends CapitalStrategy {
    protected double duration() {
        return (
                (loan.getMaturity().getTime() - loan.getStart().getTime()) / MILLIS_PER_DAY) /
            365;
    }
    protected double riskAmount() {
        return loan.getOutstanding();
    }
}
public class RevolverCapital extends CapitalStrategy {
    private double calcUnusedPercentage() {
        if (loan.getRating() > 4) return 0.75;
        else return 0.25;
    }
    private double calcUnusedRiskAmount() {
        return (loan.getNotional() - loan.getOutstanding()) * calcUnusedPercentage();
    }
    protected double duration() {
        return (
                (loan.getExpiry().getTime() - loan.getStart().getTime()) / MILLIS_PER_DAY) /
            365;
    }
    protected double riskAmount() {
        return loan.getOutstanding() + calcUnusedRiskAmount();
    }
}
public class RCTLCapital extends CapitalStrategy {
    private double calcUnusedPercentage() {
        if (loan.getRating() > 4) return 0.95;
        else return 0.50;
    }
    private double calcUnusedRiskAmount() {
        return (loan.getNotional() - loan.getOutstanding()) * calcUnusedPercentage();
    }
    protected double duration() {
        long millisToExpiry = loan.getExpiry().getTime() - loan.getStart().getTime();
        long millisFromExpiryToMaturity =
            loan.getMaturity().getTime() - loan.getExpiry().getTime();
        double revolverDuration = (millisToExpiry / MILLIS_PER_DAY) / 365;
        double termDuration = (millisFromExpiryToMaturity / MILLIS_PER_DAY) / 365;
        return revolverDuration + termDuration;
    }
    protected double riskAmount() {
        return loan.getOutstanding() + calcUnusedRiskAmount();
    }
}
