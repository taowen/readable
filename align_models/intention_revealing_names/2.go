package example

// bad

func getThem(theList [][]int) [][]int {
	var list1 [][]int
	for _, x := range theList {
		if x[0] == 4 {
			list1 = append(list1, x)
		}
	}
	return list1
}

// good

func getFlaggedCells(gameBoard []Cell) []Cell {
	var flaggedCells []Cell
	for _, cell := range gameBoard {
		if cell.isFlagged() {
			flaggedCells = append(flaggedCells, cell)
		}
	}
	return flaggedCells
}

type Cell []int

func (cell Cell) isFlagged() bool {
	return cell[0] == 4
}