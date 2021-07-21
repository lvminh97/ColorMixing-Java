package model;

public class Colors {
	
	private double[] data;
	
	public Colors(double[] _data) {
		this.data = _data;
	}
	
	public void setData(double[] _data) {
		this.data = _data;
	}
	
	public double[] getData() {
		return this.data;
	}
	
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
	
	public double[] getXYZ(){
		double[] resp = new double[] {0, 0, 0};
		double K = 0;
		for(int i = 0; i < this.data.length; i++) {
			K += ColorParam.D65_ILL[i] * ColorParam.OBSVR[1][i]; 
			resp[0] += ColorParam.D65_ILL[i] * ColorParam.OBSVR[0][i] * this.data[i];
			resp[1] += ColorParam.D65_ILL[i] * ColorParam.OBSVR[1][i] * this.data[i];
			resp[2] += ColorParam.D65_ILL[i] * ColorParam.OBSVR[2][i] * this.data[i];	
		}
		resp[0] /= K;
		resp[1] /= K;
		resp[2] /= K;
		return resp;
	}
	
	public double[] getLAB() {
		double[] resp = new double[] {0, 0, 0};
		double[] XYZ = getXYZ();
		resp[0] = 116 * rgbFx(XYZ[1] / ColorParam.D65_REF[1]) - 16;
		resp[1] = 500 * (rgbFx(XYZ[0] / ColorParam.D65_REF[0]) - rgbFx(XYZ[1] / ColorParam.D65_REF[1]));
		resp[2] = 200 * (rgbFx(XYZ[1] / ColorParam.D65_REF[1]) - rgbFx(XYZ[2] / ColorParam.D65_REF[2]));
		return resp;
	}
	
	public double[] getLCH() {
		double[] LAB = getLAB();
		double[] resp = new double[] {LAB[0], 0, 0};
		resp[1] = Math.pow(LAB[1] * LAB[1] + LAB[2] * LAB[2], 0.5);
		if(LAB[1] != 0)
			resp[2] = Math.atan(LAB[2] / LAB[1]) / Math.PI * 180;
		else if(LAB[2] >= 0)
			resp[2] = 90;
		else			resp[2] = 270;
		if(LAB[1] < 0)
			resp[2] += 180;
		return resp;
	}
	
	public int[] getRGB() {
		int[] resp = new int[] {0, 0, 0};
		double[] XYZ = getXYZ();
		double r = rgbAdjust(3.2404542 * XYZ[0] - 1.5371385 * XYZ[1] - 0.4985314 * XYZ[2]);
		double g = rgbAdjust(-0.969266 * XYZ[0] + 1.8760108 * XYZ[1] + 0.0415560 * XYZ[2]);
		double b = rgbAdjust(0.0556434 * XYZ[0] - 0.2040259 * XYZ[1] + 1.0572252 * XYZ[2]);
		resp[0] = (int) (r * 255);
		resp[1] = (int) (g * 255);
		resp[2] = (int) (b * 255);
		return resp;
	}
}
