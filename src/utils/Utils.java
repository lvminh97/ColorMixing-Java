package utils;

import java.lang.Math;

import model.Color;
import model.Matrix;

public class Utils {
	
	private static double rgbFx(double x){
		if(x > 0.008856)
			return Math.pow(x, 0.3333);
		else
			return (903.3 * x + 16) / 116;
	}
	
	private static double rgbAdjust(double x){
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
	
	public static double[] getXYZ(double[] sample){
		double[] resp = new double[] {0, 0, 0};
		double K = 0;
		for(int i = 0; i < sample.length; i++) {
			K += Color.D65_ILL[i] * Color.OBSVR[1][i]; 
			resp[0] += Color.D65_ILL[i] * Color.OBSVR[0][i] * sample[i];
			resp[1] += Color.D65_ILL[i] * Color.OBSVR[1][i] * sample[i];
			resp[2] += Color.D65_ILL[i] * Color.OBSVR[2][i] * sample[i];	
		}
		resp[0] /= K;
		resp[1] /= K;
		resp[2] /= K;
		return resp;
	}
	
	public static double[] getLAB(double[] sample) {
		double[] resp = new double[] {0, 0, 0};
		double[] XYZ = getXYZ(sample);
		resp[0] = 116 * rgbFx(XYZ[1] / Color.D65_REF[1]) - 16;
		resp[1] = 500 * (rgbFx(XYZ[0] / Color.D65_REF[0]) - rgbFx(XYZ[1] / Color.D65_REF[1]));
		resp[2] = 200 * (rgbFx(XYZ[1] / Color.D65_REF[1]) - rgbFx(XYZ[2] / Color.D65_REF[2]));
		return resp;
	}
}
