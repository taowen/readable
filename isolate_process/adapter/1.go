package example

// bad
func (query *Query) doQuery() {
	if query.sdQuery != nil {
		query.sdQuery.clearResultSet()
	}
	// 5.2 version changed the api
	if query.sd52 {
		query.sdQuery = sdLoginSession.createQuery(SDQuery.OPEN_FOR_QUERY)
	} else {
		query.sdQuery = sdSession.createQuery(SDQuery.OPEN_FOR_QUERY)
	}
	query.executeQuery()
}

// good

type Query struct {
	sdQuery *SDQuery
	createNewQuery func() *SDQuery
}

func (query *Query) doQuery() {
	if query.sdQuery != nil {
		query.sdQuery.clearResultSet()
	}
	query.sdQuery = query.createNewQuery()
	query.executeQuery()
}

