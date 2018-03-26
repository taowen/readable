package example

import "time"

type Loan struct {
	start time.Time
	expiry *time.Time
	maturity *time.Time
	rating int
}

func (loan *Loan) duration() float64 {
	if loan.expiry == nil {
		return float64(loan.maturity.Unix() - loan.start.Unix()) / 365 * 24 * float64(time.Hour)
	} else if loan.maturity == nil {
		return float64(loan.expiry.Unix() - loan.start.Unix()) / 365 * 24 * float64(time.Hour)
	}
	toExpiry := float64(loan.expiry.Unix() - loan.start.Unix())
	fromExpiryToMaturity := float64(loan.maturity.Unix() - loan.expiry.Unix())
	revolverDuration := toExpiry / 365 * 24 * float64(time.Hour)
	termDuration := fromExpiryToMaturity / 365 * 24 * float64(time.Hour)
	return revolverDuration + termDuration
}

func (loan *Loan) unusedPercentage() float64 {
	if loan.expiry != nil && loan.maturity != nil {
		if loan.rating > 4 {
			return 0.95
		} else {
			return 0.50
		}
	} else if loan.maturity != nil {
		return 1
	} else if loan.expiry != nil {
		if loan.rating > 4 {
			return 0.75
		} else {
			return 0.25
		}
	}
	panic("invalid loan")
}

type LoanApplication struct {
	start time.Time
	expiry *time.Time
	maturity *time.Time
	rating int
}

type CapitalStrategy interface {
	duration() float64
	unusedPercentage() float64
}

func createLoanStrategy(loanApplication LoanApplication) CapitalStrategy {
	if loanApplication.expiry != nil && loanApplication.maturity != nil {
		return createRCTL(loanApplication)
	}
	if loanApplication.expiry != nil {
		return createRevolver(loanApplication)
	}
	if loanApplication.maturity != nil {
		return createTermLoan
	}
	panic("invalid loan application")
}