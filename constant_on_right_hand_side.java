// bad

if (10 <= length)
while (bytes_expected > bytes_received)

// good

if (length >= 10)
while (bytes_received < bytes_expected)
