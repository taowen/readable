package example

import "net/http"

func checkAuth(req *http.Request) error {
}

func buyProduct(req *http.Request) error {
	err := checkAuth(req)
	if err != nil {
		return err
	}
	// ...
}

func sellProduct(req *http.Request) error {
	err := checkAuth(req)
	if err != nil {
		return err
	}
	// ...
}

func init() {
	buyProduct = checkAuthDecorator(buyProduct)
	sellProduct = checkAuthDecorator(sellProduct)
}
func checkAuthDecorator(f func(req *http.Request) error) func(req *http.Request) error {
	return func(req *http.Request) error {
		err := checkAuth(req)
		if err != nil {
			return err
		}
		return f(req)
	}
}
var buyProduct = func(req *http.Request) error {
	// ..
}
var sellProduct = func(req *http.Request) error {
	// ..
}