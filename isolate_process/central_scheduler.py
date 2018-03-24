# bad

def PrintStockTransactions():
stock_iter = db_read("SELECT time, ticker_symbol FROM ...")
price_iter = ...
num_shares_iter = ...
# Iterate through all the rows of the 3 tables in parallel.
while stock_iter and price_iter and num_shares_iter:
    stock_time = stock_iter.time
    price_time = price_iter.time
    num_shares_time = num_shares_iter.time
    # If all 3 rows don't have the same time, skip over the oldest row
    # Note: the "<=" below can't just be "<" in case there are 2 tied-oldest.
    if stock_time != price_time or stock_time != num_shares_time:
        if stock_time <= price_time and stock_time <= num_shares_time:
            stock_iter.NextRow()
        elif price_time <= stock_time and price_time <= num_shares_time:
            price_iter.NextRow()
        elif num_shares_time <= stock_time and num_shares_time <= price_time:
            num_shares_iter.NextRow()
        else:
            assert False # impossible
        continue
        
    assert stock_time == price_time == num_shares_time
    
    # Print the aligned rows.
    print "@", stock_time,
    print stock_iter.ticker_symbol,
    print price_iter.price,
    print num_shares_iter.number_of_shares
    stock_iter.NextRow()
    price_iter.NextRow()
    num_shares_iter.NextRow()
    
# good

def PrintStockTransactions():
    stock_iter = ...
    price_iter = ...
    num_shares_iter = ...
    while True:
        time = AdvanceToMatchingTime(stock_iter, price_iter, num_shares_iter)
        if time is None:
            return
        # Print the aligned rows.
        print "@", time,
        print stock_iter.ticker_symbol,
        print price_iter.price,
        print num_shares_iter.number_of_shares
        stock_iter.NextRow()
        price_iter.NextRow()
        num_shares_iter.NextRow()
        
def AdvanceToMatchingTime(row_iter1, row_iter2, row_iter3):
    while row_iter1 and row_iter2 and row_iter3:
        t1 = row_iter1.time
        t2 = row_iter2.time
        t3 = row_iter3.time
        if t1 == t2 == t3:
            return t1
        tmax = max(t1, t2, t3)
        # If any row is "behind," advance it.
        # Eventually, this while loop will align them all.
        if t1 < tmax: row_iter1.NextRow()
        if t2 < tmax: row_iter2.NextRow()
        if t3 < tmax: row_iter3.NextRow()
    return None # no alignment could be found
