// bad 

return exponent >= 0 ? mantissa * (1 << exponent) : mantissa / (1 << -exponent);

// good

if (exponent >= 0) {
 return mantissa * (1 << exponent);
} else {
 return mantissa / (1 << -exponent);
}
