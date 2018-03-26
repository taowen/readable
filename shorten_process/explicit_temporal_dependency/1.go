package example

type Gradient struct {
}

type Spline struct {
}

type MoogDriver struct {
	gradient Gradient
	splines []Spline
}

func (driver *MoogDriver) drive(reason string) {
	driver.saturateGradient()
	driver.reticulateSplines()
	driver.diveForMoog(reason)
}

func (driver *MoogDriver) saturateGradient() {
}

func (driver *MoogDriver) reticulateSplines() {
}

func (driver *MoogDriver) diveForMoog(reason string) {
}

type ExplicitDriver struct {
}

func (driver *ExplicitDriver) drive(reason string) {
	gradient := driver.saturateGradient()
	splines := driver.reticulateSplines(gradient)
	driver.diveForMoog(splines, reason)
}


func (driver *ExplicitDriver) saturateGradient() Gradient {
}

func (driver *ExplicitDriver) reticulateSplines(gradient Gradient) []Spline {
}

func (driver *ExplicitDriver) diveForMoog(splines []Spline, reason string) {
}
