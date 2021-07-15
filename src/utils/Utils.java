package utils;

import java.lang.Math;

public class Utils {
	
	private double rgbFx(double x){
		if(x > 0.008856)
			return Math.pow(x, 0.3333);
		else
			return (903.3 * x + 16) / 116;
	}
	
	private double rgbAdjust(double x){
		double res = 0;
		if(x <= -0.0031308)
			res = 0;
		else if(x <= 0.0031308)
			res = 12.92 * x;
		else
			res = 1.055 * Math.pow(x, 0.41666) - 0.055;

		if(res >= 0 && res <= 1)
			return res;
		else if(res < 0)
			return 0;
		else
			return 1;
	}
}
