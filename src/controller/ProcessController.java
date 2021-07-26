package controller;

import model.ColorParam;
import model.Colors;
import model.Matrix;

public class ProcessController {
	private Matrix basicMat;
	private int[] ratio;
	private int len;
	private int resolution;
	
	private double minDiff, minDiff2;
	private double[] finalRatio;
	private Colors sampleColor = null, computedColor = null;
	
	public ProcessController(int choose, double[] sample, int resol) {
		double[][]  basicColor = new double[4][31];
		this.len = 0;
		if((choose & 1) != 0){
			basicColor[this.len] = ColorParam.COLOR[0];
			this.len++;
		}
		if((choose & 2) != 0){
			basicColor[this.len] = ColorParam.COLOR[1];
			this.len++;
		}
		if((choose & 4) != 0){
			basicColor[this.len] = ColorParam.COLOR[2];
			this.len++;
		}
		if((choose & 8) != 0){
			basicColor[this.len] = ColorParam.COLOR[3];
			this.len++;
		}
		this.basicMat = new Matrix(basicColor, len, 31).transpose();
		this.sampleColor = new Colors(sample);
		this.resolution = resol;
	}
	
	public Matrix getBasicMat() {
		return basicMat;
	}

	public void setBasicMat(Matrix basicMat) {
		this.basicMat = basicMat;
	}

	private double deltaE(double[] lab1, double[] lab2) {
		double s = Math.pow(lab1[0] - lab2[0], 2) + Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2);
		return Math.sqrt(s);
	}
	
	private double deltaAB(double[] lab1, double[] lab2) {
		double s = Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2);
		return Math.sqrt(s);
	}
	
	private void backtrack(int pos, int curSum, int[][] bound, int step, double[] LAB_ref) {
		for(int a = bound[pos][0]; a <= bound[pos][1]; a++) {
			if(a < 0) 
				continue;
			else if(curSum + a <= this.resolution) {
				this.ratio[pos] = a;
				curSum += a;
				if(pos < this.len - 2)
					this.backtrack(pos + 1, curSum, bound, step, LAB_ref);
				else if(curSum <= this.resolution) {
					this.ratio[this.len - 1] = this.resolution - curSum;
					Matrix ratioMat = new Matrix(len, 1);
					for(int i = 0; i < len; i++) 
						ratioMat.set(i, 0, ((double) this.ratio[i]) / this.resolution);
					
					double[] computedData = basicMat.mul(ratioMat).toArray1D();
					Colors computedColor = new Colors(computedData);
					double[] LAB_computed = computedColor.getLAB();
					if(deltaAB(LAB_ref, LAB_computed) < 5.0 && minDiff > Math.abs(LAB_ref[0] - LAB_computed[0])) {
						minDiff = Math.abs(LAB_ref[0] - LAB_computed[0]);
						this.computedColor = new Colors(computedData);
						finalRatio = ratioMat.toArray1D();
					}
					else if(minDiff2 > deltaAB(LAB_ref, LAB_computed)) {
						minDiff2 = deltaAB(LAB_ref, LAB_computed);
						this.computedColor = new Colors(computedData);
						finalRatio = ratioMat.toArray1D();
					}
				}
				curSum -= a;
			}
			else
				return;
		}
	}
	
	public double[] compute(int[][] bound, int step) {
		this.minDiff = 9999999;
		this.minDiff2 = 9999999;
		this.ratio = new int[this.len];
		for(int i = 0; i < this.ratio.length; i++) 
			this.ratio[i] = 0;
		this.finalRatio = new double[this.len];
		double[] LAB_ref = this.sampleColor.getLAB();
		
		this.backtrack(0, 0, bound, step, LAB_ref);
		
		return this.finalRatio;
	}
	
	public Colors getSampleColor() {
		return this.sampleColor;
	}
	
	public Colors getComputedColor() {
		return this.computedColor;
	}
}
