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

if line.split(':')[0].strip() == "root":

// good

username = line.split(':')[0].strip()
if username == "root":

// bad

// Check to see if the employee is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) &&
 (employee.age > 65)) 
  
// good
  
if (employee.isEligibleForFullBenefits())
  
// bad
  
if (request.user.id == document.owner_id) {
 // user can edit this document...
}

// good

final boolean user_owns_document = (request.user.id == document.owner_id);
if (user_owns_document) {
    // user can edit this document...
}
...
if (!user_owns_document) {
    // document is read-only...
}

// bad

var update_highlight = function (message_num) {
 if ($("#vote_value" + message_num).html() === "Up") {
 $("#thumbs_up" + message_num).addClass("highlighted");
 $("#thumbs_down" + message_num).removeClass("highlighted");
 } else if ($("#vote_value" + message_num).html() === "Down") {
 $("#thumbs_up" + message_num).removeClass("highlighted");
 $("#thumbs_down" + message_num).addClass("highlighted");
 } else {
 $("#thumbs_up" + message_num).removeClass("highighted");
 $("#thumbs_down" + message_num).removeClass("highlighted");
 }
};

// good

var update_highlight = function (message_num) {
 var thumbs_up = $("#thumbs_up" + message_num);
 var thumbs_down = $("#thumbs_down" + message_num);
 var vote_value = $("#vote_value" + message_num).html();
 var hi = "highlighted";
 if (vote_value === "Up") {
 thumbs_up.addClass(hi);
 thumbs_down.removeClass(hi);
 } else if (vote_value === "Down") {
 thumbs_up.removeClass(hi);
 thumbs_down.addClass(hi);
 } else {
 thumbs_up.removeClass(hi);
 thumbs_down.removeClass(hi);
 }
};

