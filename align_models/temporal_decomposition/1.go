package example

import "strconv"

// bad

const REGULAR = 1
const NEW_RELEASE = 2
const CHILDRENS = 3

type Movie struct {
	title     string
	priceCode int
}

type Rental struct {
	movie      *Movie
	daysRented int
}

type Customer struct {
	name    string
	rentals []Rental
}

func (customer *Customer) statement() string {
	totalAmount := float64(0)
	frequentRenterPoints := 0
	result := "Rental Record for " + customer.name + "\n"
	for _, rental := range customer.rentals {
		thisAmount := float64(0)
		switch rental.movie.priceCode {
		case REGULAR:
			thisAmount += 2
			if rental.daysRented > 2 {
				thisAmount += (float64(rental.daysRented) - 2) * 1.5
			}
		case NEW_RELEASE:
			thisAmount += float64(rental.daysRented) * 3
			if rental.daysRented > 1 {
				frequentRenterPoints++
			}
		case CHILDRENS:
			thisAmount += 1.5
			if rental.daysRented > 3 {
				thisAmount += (float64(rental.daysRented) - 3) * 1.5
			}
		}
		frequentRenterPoints++
		result += "\t" + rental.movie.title + "\t" +
			strconv.FormatFloat(thisAmount, 'g', 10, 64) + "\n"
		totalAmount += thisAmount
	}
	result += "Amount owed is " +
		strconv.FormatFloat(totalAmount, 'g', 10, 64) + "\n"
	result += "You earned " +
		strconv.Itoa(frequentRenterPoints)+ " frequent renter points"
	return result
}

// good

func statement(custom *Customer) string {
	bill := calcBill(custom)
	statement := bill.print()
	return statement
}

type RentalBill struct {
	rental Rental
	amount float64
}

type Bill struct {
	customer *Customer
	rentals []RentalBill
	totalAmount float64
	frequentRenterPoints int
}

func calcBill(customer *Customer) Bill {
	bill := Bill{}
	for _, rental := range customer.rentals {
		rentalBill := RentalBill{
			rental: rental,
			amount: calcAmount(rental),
		}
		bill.frequentRenterPoints += calcFrequentRenterPoints(rental)
		bill.totalAmount += rentalBill.amount
		bill.rentals = append(bill.rentals, rentalBill)
	}
	return bill
}

func (bill Bill) print() string {
	result := "Rental Record for " + bill.customer.name + "\n"
	for _, rental := range bill.rentals {
		result += "\t" + rental.rental.movie.title + "\t" +
			strconv.FormatFloat(rental.amount, 'g', 10, 64) + "\n"
	}
	result += "Amount owed is " +
		strconv.FormatFloat(bill.totalAmount, 'g', 10, 64) + "\n"
	result += "You earned " +
		strconv.Itoa(bill.frequentRenterPoints)+ " frequent renter points"
	return result
}

func calcAmount(rental Rental) float64 {
	thisAmount := float64(0)
	switch rental.movie.priceCode {
	case REGULAR:
		thisAmount += 2
		if rental.daysRented > 2 {
			thisAmount += (float64(rental.daysRented) - 2) * 1.5
		}
	case NEW_RELEASE:
		thisAmount += float64(rental.daysRented) * 3
	case CHILDRENS:
		thisAmount += 1.5
		if rental.daysRented > 3 {
			thisAmount += (float64(rental.daysRented) - 3) * 1.5
		}
	}
	return thisAmount
}

func calcFrequentRenterPoints(rental Rental) int {
	frequentRenterPoints := 1
	switch rental.movie.priceCode {
	case NEW_RELEASE:
		if rental.daysRented > 1 {
			frequentRenterPoints++
		}
	}
	return frequentRenterPoints
}