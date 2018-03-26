package example

type Reply struct {
}

func (reply *Reply) Done() {
}

func (reply *Reply) WriteErrors(error string) {
}

const SUCCESS = 0

// bad

func handleResult(reply *Reply, userResult int, permissionResult int) {
	if userResult == SUCCESS {
		if permissionResult != SUCCESS {
			reply.WriteErrors("error reading permissions")
			reply.Done()
			return
		}
		reply.WriteErrors("")
	} else {
		reply.WriteErrors("user result")
	}
	reply.Done()
}

// good

func handleResult(reply *Reply, userResult int, permissionResult int) {
	defer reply.Done()
	if userResult != SUCCESS {
		reply.WriteErrors("user result")
		return
	}
	if permissionResult != SUCCESS {
		reply.WriteErrors("error reading permissions")
		return
	}
	reply.WriteErrors("")
}
