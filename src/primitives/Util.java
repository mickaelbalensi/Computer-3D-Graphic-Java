package primitives;

/**
 * primitives.Util class is used for some internal utilities, e.g. controlling accuracy
 * 
 * @author Dan
 */
public abstract class Util {
    // It is binary, equivalent to ~1/1,000,000,000,000 in decimal (12 digits)
    private static final int ACCURACY = -40;

    // double store format (bit level): seee eeee eeee (1.)mmmm � mmmm
    // 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
    // the number is m+2^e where 1<=m<2
    // NB: exponent is stored "normalized" (i.e. always positive by adding 1023)
    private static int getExp(double num) {
        // 1. doubleToRawLongBits: "convert" the stored number to set of bits
        // 2. Shift all 52 bits to the right (removing mantissa)
        // 3. Zero the sign of number bit by mask 0x7FF
        // 4. "De-normalize" the exponent by subtracting 1023
        return (int)((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
    }

    /**
     * Checks whether the number is [almost] zero
     * 
     * @param number number
     * @return true if the number is zero or almost zero, false otherwise
     */
    public static boolean isZero(double number) {
        return getExp(number) < ACCURACY;
    }

    /**
     * Aligns the number to zero if it is almost zero
     * @param number check if number is inferior to 40
     * @return 0.0 if the number is very close to zero, the number itself otherwise
     */
    public static double alignZero(double number) {
        return getExp(number) < ACCURACY ? 0.0 : number;
    }

    /**
     * calculate the minimum value between many value in parameters
     * @param mins min
     * @return sa race
     */
    public static double min(double... mins){
        double min= mins[0];
        for(double d :mins){
            min=min(min,d);
        }
        return min;
    }

    /**
     * calculate the maximum value between many value in parameters
     * @param mins min
     * @return  min
     */
    public static double max(double... mins){
        double min= mins[0];
        for(double d :mins){
            min=max(min,d);
        }
        return min;
    }
}
