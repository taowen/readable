package example

import "fmt"

const NO_BANK_CARD_STATE = 0
const HAS_BANK_CARD_STATE = 1
const DECODING_STATE = 2
const FETCH_MONEY_STATE = 3
const OUT_MONEY_STATE = 4
const INSUFFICIENT_BALANCE_STATE = 5

type ATM struct {
	state int
}

func (atm *ATM) insertBankCard() {
	switch atm.state {
	case NO_BANK_CARD_STATE:
		atm.state = HAS_BANK_CARD_STATE
		fmt.Println("inserted bank card, please input password")
	case HAS_BANK_CARD_STATE:
		fmt.Println("please input password")
	case DECODING_STATE:
		fmt.Println("decoding, please input amount")
	case FETCH_MONEY_STATE:
		fmt.Println("please confirm amount")
	case OUT_MONEY_STATE:
		fmt.Println("transaction completed. another one?")
	case INSUFFICIENT_BALANCE_STATE:
		fmt.Println("not enough balance")
	}
}

func (atm *ATM) takeOutBankCard() {
	switch atm.state {
	case NO_BANK_CARD_STATE:
		fmt.Println("no bank card")
	case HAS_BANK_CARD_STATE:
		fallthrough
	case DECODING_STATE:
		fallthrough
	case FETCH_MONEY_STATE:
		fallthrough
	case INSUFFICIENT_BALANCE_STATE:
		fallthrough
	case OUT_MONEY_STATE:
		atm.state = NO_BANK_CARD_STATE
		fmt.Println("bank card taken out")
	}
}

func (atm *ATM) inputPassword() {
	// ...
}

func (atm *ATM) inputMoney() {
	// ...
}

func (atm *ATM) tapOKButton() {
	// ...
}

type ATMState interface {
	insertBankCard() ATMState
	takeOutBankCard() ATMState
	inputPassword() ATMState
	inputMoney() ATMState
	tapOKButton() ATMState
}

type NoBankCardState struct {
}

