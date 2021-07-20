package model;

public class Matrix {
	
	private int row, col;
	private double data[][];
	
	public Matrix(int r, int c){
		this.row = r;
		this.col = c;
		this.data = new double[r][c];
	}
	
	public Matrix(double[][] m){
		this.row = m.length;
		this.col = m[0].length;
		this.data = new double[this.row][this.col];
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < this.col; j++){
				this.data[i][j] = m[i][j];
			}
		}
	}
	
	public Matrix(double[][] m, int r, int c){
		this.row = r;
		this.col = c;
		this.data = new double[r][c];
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < this.col; j++){
				if(i < m.length && j < m[0].length)
					this.data[i][j] = m[i][j];
				else 
					this.data[i][j] = 0;
			}
		}
	}
	
	public Matrix(Matrix m){
		this.row = m.row;
		this.col = m.col;
		this.data = new double[this.row][this.col];
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < this.col; j++){
				this.data[i][j] = m.get(i, j);
			}
		}
	}
	
	public int[] getDim(){
		int[] res = new int[2];
		res[0] = this.row;
		res[1] = this.col;
		return res;
	}
	
	public void set(int i, int j, double value){
		this.data[i][j] = value;
	}
	
	public double get(int i, int j){
		return this.data[i][j];
	}
	
	public Matrix mul(Matrix b){
		if(this.col != b.row)
			return null;
		Matrix resp = new Matrix(this.row, b.col);
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < b.col; j++){
				resp.data[i][j] = 0;
				for(int k = 0; k < this.col; k++){
					resp.data[i][j] += this.data[i][k] * b.data[k][j];
				}
			}
		}
		return resp;
	}
	
	public Matrix mul(double k){
		Matrix resp = new Matrix(this);
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < this.col; j++){
				resp.data[i][j] = this.data[i][j] * k;
			}
		}
		return resp;
	}
	
	public Matrix transpose(){
		Matrix resp = new Matrix(this.col, this.row);
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < this.col; j++){
				resp.data[j][i] = this.data[i][j];
			}
		}
		return resp;
	}
}
