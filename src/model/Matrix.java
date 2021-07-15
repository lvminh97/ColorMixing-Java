package model;

public class Matrix<T> {
	
	private int row, col;
	private T data[][];
	
	public Matrix(int r, int c){
		this.row = r;
		this.col = c;
		this.data = (T[][]) new Object[r][c];
	}
	
	public int[] getDim(){
		int[] res = new int[2];
		res[0] = this.row;
		res[1] = this.col;
		return res;
	}
	
	public void set(int i, int j, T value){
		this.data[i][j] = value;
	}
	
	public T get(int i, int j){
		return this.data[i][j];
	}
}
