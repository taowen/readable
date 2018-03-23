// bad

int d; // elapsed time in days 

// good

int elapsedTimeInDays;

// bad

public List<int[]> getThem() {   
  List<int[]> list1 = new ArrayList<int[]>();    
  for (int[] x : theList) { 
    if (x[0] == 4) { 
      list1.add(x); 
    }
  }
  return list1;  
}

// good

public List<Cell> getFlaggedCells() {    
  List<Cell> flaggedCells = new ArrayList<Cell>();    
  for (Cell cell : gameBoard) {
    if (cell.isFlagged()) {
      flaggedCells.add(cell); 
    }
  }
  return flaggedCells;  
}

// bad

// Check to see if the employee is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) &&
 (employee.age > 65)) 
  
// good
  
if (employee.isEligibleForFullBenefits())
