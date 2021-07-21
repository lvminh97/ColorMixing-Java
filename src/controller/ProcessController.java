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
	private double[] LAB_ref;
	
	public ProcessController(Matrix mat, int resol) {
		this.basicMat = mat;
		this.resolution = resol;
	}
	
	public Matrix getBasicMat() {
		return basicMat;
	}

	public void setBasicMat(Matrix basicMat) {
		this.basicMat = basicMat;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	private double deltaE(double[] lab1, double[] lab2) {
		double s = Math.pow(lab1[0] - lab2[0], 2) + Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2);
		return Math.sqrt(s);
	}
	
	private double deltaAB(double[] lab1, double[] lab2) {
		double s = Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2);
		return Math.sqrt(s);
	}
	
	private void backtrack(int pos, int curSum, int[][] bound, int step) {
		for(int a = bound[pos][0]; a <= bound[pos][1]; a++) {
			if(a < 0) 
				continue;
			else if(curSum + a <= resolution) {
				ratio[pos] = a;
				curSum += a;
				if(pos < len - 2)
					backtrack(pos + 1, curSum, bound, step);
				else if(curSum <= resolution) {
					ratio[len - 1] = resolution - curSum;
					Matrix ratioMat = new Matrix(len, 1);
					for(int i = 0; i < len; i++) 
						ratioMat.set(i, 0, ((double) ratio[i]) / resolution);
					double[] computedData = basicMat.mul(ratioMat).toArray1D();
					Colors computedColor = new Colors(computedData);
					double[] LAB_computed = computedColor.getLAB();
					if(deltaAB(LAB_ref, LAB_computed) < 5.0 && minDiff > Math.abs(LAB_ref[0] - LAB_computed[0])) {
						minDiff = Math.abs(LAB_ref[0] - LAB_computed[0]);
						
					}
					else if(minDiff2 > deltaAB(LAB_ref, LAB_computed)) {
						minDiff2 = deltaAB(LAB_ref, LAB_computed);
					}
				}
			}
			else
				return;
		}
	}
	
	public Matrix compute(int colorChooser) {
//		double[][]  basicColor = new double[4][31];
//		len = 0;
//		if((colorChooser & 1) != 0){
//			basicColor[len] = ColorParam.COLOR[0];
//			len++;
//		}
//		if((colorChooser & 2) != 0){
//			basicColor[len] = ColorParam.COLOR[1];
//			len++;
//		}
//		if((colorChooser & 4) != 0){
//			basicColor[len] = ColorParam.COLOR[2];
//			len++;
//		}
//		if((colorChooser & 8) != 0){
//			basicColor[len] = ColorParam.COLOR[3];
//			len++;
//		}
//		basicMat = new Matrix(basicColor, len, 31).transpose();
//			backtrack(basicMat, 0, bound, step, resolution);
		return null;
	}
}
