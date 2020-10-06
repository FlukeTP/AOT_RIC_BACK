package baiwa.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {
	
	public static BigDecimal toBigDecimal(String input) {
		if (StringUtils.isNotBlank(input)) {
			return new BigDecimal(input.replaceAll(",", ""));
		} else {
			return null;
		}
	}
	
	public static BigDecimal nullToZero(BigDecimal val){
		return (val != null) ? val : BigDecimal.ZERO;
	}
	
	public static BigDecimal ZeroToOne(BigDecimal val){
		return (val.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : val;
	}
	
	public static boolean isGreaterThan(BigDecimal compareBase, BigDecimal compareValue) {
        return compareValue.compareTo(compareBase) > 0;
    }

	public static boolean isGreaterThanOrEquals(BigDecimal compareBase, BigDecimal compareValue) {
        return compareValue.compareTo(compareBase) >= 0;
    }

	public static boolean isEquals(BigDecimal compareBase, BigDecimal compareValue) {
        return compareValue.compareTo(compareBase) == 0;
    }

	public static boolean isLessThanOrEquals(BigDecimal compareBase, BigDecimal compareValue) {
        return compareValue.compareTo(compareBase) <= 0;
    }

	public static boolean isLessThan(BigDecimal compareBase, BigDecimal compareValue) {
        return compareValue.compareTo(compareBase) < 0;
    }
	
	public static BigDecimal calculatePercent(BigDecimal baseBigDecimal, BigDecimal compareBigDecimal) {
		BigDecimal percent = null;
		if ((baseBigDecimal.compareTo(BigDecimal.ZERO) == 0) && (compareBigDecimal.compareTo(BigDecimal.ZERO) == 0)) {
			percent = BigDecimal.ZERO;
		} else if (compareBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
			percent = new BigDecimal("100");
		} else if (baseBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
			percent = new BigDecimal("-100");
		} else {
			percent = (baseBigDecimal.subtract(compareBigDecimal)).multiply(new BigDecimal("100")).divide(NumberUtils.ZeroToOne(compareBigDecimal), 2, BigDecimal.ROUND_HALF_UP);
		}
		return percent;
	}
	
	public static BigDecimal stringEmptytoZero(String strEmpty) {
        return StringUtils.isBlank(strEmpty)? BigDecimal.ZERO: new BigDecimal(strEmpty);
    }
	
	public static String toDecimalFormat(BigDecimal number, Boolean dot) {
		String format = null;
		if (dot) {
			format = "###,##0.00";
		} else {
			format = "###,##0";
		}
		return number != null ? new DecimalFormat(format).format(number) : null;
	}
	
	/* ##################### Round Up Two Digit #######################
	 * 2.994 => 2.99
	 * 2.995 || 2.999 => 3.00
	 * */
	public static BigDecimal roundUpTwoDigit(BigDecimal number) {
		return number.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal roundUpTwoDigit(String number) {
		return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal roundUpTwoDigit(double number) {
		return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	/* ################################################################ */
}
