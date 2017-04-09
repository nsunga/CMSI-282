import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Integer;
import java.lang.Double;
import java.math.BigInteger;

public class MathMethods {
    public static void main(String[] args) {
        String functionCall = args[0];

        try {
            switch (functionCall) {
                case "factorial":
                    System.out.println(factorial(Integer.parseInt(args[1])));
                    break;
                case "fibonacci":
                    System.out.println(fibonacci(Integer.parseInt(args[1])));
                    break;
                case "gcd":
                    System.out.println(gcd(Long.parseLong(args[1]),
                        Long.parseLong(args[2])));
                    break;
                case "lcm":
                    System.out.println(lcm(Long.parseLong(args[1]),
                        Long.parseLong(args[2])));
                    break;
                case "poly":
                    double xValue = Double.parseDouble(args[1]);
                    double theCoefficients[] = new double[args.length - 2];

                    for (int i = 0; i < args.length; i++) {
                        if (i == 0 || i == 1) {
                            ;
                        } else {
                            theCoefficients[i - 2] = Double.parseDouble(args[i]);
                        }
                    }
                    System.out.println(poly(xValue, theCoefficients));
                    break;
                case "sqrt":
                    System.out.println(sqrt(Double.parseDouble(args[1]),
                        Double.parseDouble(args[2])));
                    break;
                case "root":
                    System.out.println(root(Integer.parseInt(args[1]),
                        Double.parseDouble(args[2]), Double.parseDouble(args[3])));
                    break;
                case "power":
                    System.out.println(power(Double.parseDouble(args[1]),
                        Integer.parseInt(args[2])));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("BAD DATA");
        }
    }

    public static BigInteger factorial(int n) {
        BigInteger answer = BigInteger.ONE;

        if (n < 0) {
            throw new IllegalArgumentException();
        }

        while (n > 1) {
            answer = answer.multiply(BigInteger.valueOf(n--));
        }

        return answer;
    }

    public static BigInteger fibonacci(int n) {
        BigInteger answer = BigInteger.ZERO;
        BigInteger zerothTerm = BigInteger.ZERO;
        BigInteger firstTerm = BigInteger.ONE;

        if (n < 0) {
            throw new IllegalArgumentException();
        } else if (n == 0) {
            return zerothTerm;
        } else if (n == 1) {
            return firstTerm;
        } else {
            for (int i = 1; i < n; i++) {
                answer = zerothTerm.add(firstTerm);
                zerothTerm = firstTerm;
                firstTerm = answer;
            }
        }

        return answer;
    }

    public static long gcd(long m, long n) {
        if (m < 0) {
            m *= -1;
        }

        if (n < 0) {
            n *= -1;
        }

        if (m < n) {
            long temp = m;
            m = n;
            n = temp;
        }

        if (m == 0 && n != 0) {
            return n;
        } else if (n == 0 && m!= 0) {
            return m;
        } else if (n == 0 && m == 0) {
             throw new IllegalArgumentException();
        }

        if ( (m % n) == 0) {
            return n;
        } else {
            return gcd(n, (m % n));
        }
    }

    public static long lcm(long m, long n) {
        if (m == 0 && n == 0) {
            throw new IllegalArgumentException();
        }

        if (m < 0) {
            m *= -1;
        } else if (m == 0) {
            return 0;
        }

        if (n < 0) {
            n *= -1;
        } else if (n == 0) {
            return 0;
        }
        return (m * n)/gcd(m, n);
    }

    public static double poly(double x, double[] coeff) {
        double answer = 0;

        for (int i = coeff.length - 1; i > 0; i--) {
            answer = coeff[i-1]+ x * coeff[i];
            coeff[i-1] = answer;
        }

        return answer;
    }

    public static double sqrt(double x, double epsilon) {
        double lowerBound = 0;
        double guess = 0;
        boolean withinEpsilon = false;

        if (x < 0) {
            throw new IllegalArgumentException();
        } else if (x == 0) {
            guess = 0;
        } else if (x == 1) {
            guess = 1;
        } else if (x > 1) {
            double upperBound = x;
            guess = (lowerBound + upperBound)/2;

            double potential = power(guess, 2);

            while (!false) {
                withinEpsilon = (potential <= (x + epsilon) ) && ( (x - epsilon) <= potential);
                if (withinEpsilon) {
                    break;
                } else if (potential > x) {
                    upperBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = guess * guess;
                } else {
                    lowerBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = guess * guess;
                }
            }
        } else {
            double upperBound = 1;
            guess = (lowerBound + upperBound)/2;

            double potential = power(guess, 2);

            while (!false) {
                withinEpsilon = (potential <= (x + epsilon) ) && ( (x - epsilon) <= potential);
                if (withinEpsilon) {
                    break;
                } else if (potential > x) {
                    upperBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = guess * guess;
                } else {
                    lowerBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = guess * guess;
                }
            }
        }

        return guess;
    }

    public static double power(double x, int n) {
        boolean wasNegative = false;

        if (n < 0) {
            wasNegative = true;
            n *= -1;
        }

        if (x == 0) {
            return 0;
        } else if (x == 1) {
            return 1;
        }

        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return x;
        } else if ( (n % 2) == 0) {
            double value = power(x, n/2);

            if (wasNegative) {
                return (1/(value*value));
            } else {
                return (value * value);
            }
        } else {
            if (wasNegative) {
                return 1/(x * power(x, n-1));
            } else {
                return x * power(x, n-1);
            }
        }
    }

    public static double root(int n, double x, double epsilon) {
        double lowerBound = 0;
        double guess = 0;
        boolean withinEpsilon = false;
        boolean wasNegative = false;

        if (x < 0 && (n % 2 == 0)) {
            throw new IllegalArgumentException();
        } else if (n == 0) {
            throw new IllegalArgumentException();
        }

        if (x < 0) {
            wasNegative = true;
            x *= -1;
        }

        if (x == 0) {
            guess = 0;
        } else if (x == 1) {
            guess = 1;
        } else if (x > 1) {
            double upperBound = x;
            guess = (lowerBound + upperBound)/2;

            double potential = power(guess, n);

            while (!false) {
                withinEpsilon = (potential <= (x + epsilon) ) && ( (x - epsilon) <= potential);
                if (withinEpsilon) {
                    break;
                } else if (potential > x) {
                    upperBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = power(guess, n);
                } else {
                    lowerBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = power(guess, n);
                }
            }
        } else {
            double upperBound = 1;
            guess = (lowerBound + upperBound)/2;

            double potential = power(guess, n);

            while (!false) {
                withinEpsilon = (potential <= (x + epsilon) ) && ( (x - epsilon) <= potential);
                if (withinEpsilon) {
                    break;
                } else if (potential > x) {
                    upperBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = power(guess, n);
                } else {
                    lowerBound = guess;
                    guess = (lowerBound + upperBound)/2;
                    potential = power(guess, n);
                }
            }
        }

        if (wasNegative) {
            return guess * -1;
        } else {
            return guess;
        }
    }
}
