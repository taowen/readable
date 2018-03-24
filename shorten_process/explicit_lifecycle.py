// bad

# No use of example_value up to this point.
if request:
 for value in request.values:
 if value > 0:
 example_value = value
 break
for logger in debug.loggers:
 logger.log("Example:", example_value)

// good

example_value = None
if request:
 for value in request.values:
   if value > 0:
     example_value = value
     break
if example_value:
 for logger in debug.loggers:
   logger.log("Example:", example_value)
