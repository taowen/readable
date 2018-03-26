package example

import "math"

// bad

type Location struct {
	Lat float64
	Lng float64
}

func radians(degree float64) float64 {
	return degree * math.Pi / 180
}

func findSphericalClosest(lat float64, lng float64, locations []Location) *Location {
	var closest *Location
	closestDistance := math.MaxFloat64
	for _, location := range locations {
		latRad := radians(lat)
		lngRad := radians(lng)
		lat2Rad := radians(location.Lat)
		lng2Rad := radians(location.Lng)
		// Use the "Spherical Law of Cosines" formula.
		var dist = math.Acos(math.Sin(latRad) * math.Sin(lat2Rad) +
			math.Cos(latRad) * math.Cos(lat2Rad) *
				math.Cos(lng2Rad - lngRad))
		if dist < closestDistance {
			closest = &location
			closestDistance = dist
		}
	}
	return closest
}

// good

func findSphericalClosest(lat float64, lng float64, locations []Location) *Location {
	target := Location{lat, lng}
	isCloser := func(left Location, right Location) int {
		leftDistance := sphericalDistance(left, target)
		rightDistance := sphericalDistance(right, target)
		if leftDistance < rightDistance {
			return -1
		} else if leftDistance > rightDistance {
			return 1
		} else {
			return 0
		}
	}
	closest := min(locations, isCloser)
	return closest
}

func sphericalDistance(a Location, b Location) float64 {
	latRad := radians(a.Lat)
	lngRad := radians(b.Lng)
	lat2Rad := radians(b.Lat)
	lng2Rad := radians(b.Lng)
	// Use the "Spherical Law of Cosines" formula.
	var dist = math.Acos(math.Sin(latRad) * math.Sin(lat2Rad) +
		math.Cos(latRad) * math.Cos(lat2Rad) *
			math.Cos(lng2Rad - lngRad))
	return dist
}

type compare func(left Location, right Location) int

func min(objects []Location, compare compare) *Location {
	var min *Location
	for _, object := range objects {
		if min == nil {
			min = &object
			continue
		}
		if compare(object, *min) < 0 {
			min = &object
		}
	}
	return min
}
