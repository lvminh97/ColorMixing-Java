package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import javax.swing.JFileChooser;

import model.ColorParam;
import model.Colors;

import java.util.Scanner;

import view.View;

public class ViewController implements ActionListener{
	
	private View view;
	private double[] sampleData = null;
	
	public ViewController() {
		ColorParam.importBasicColor();
		this.view = new View();
		this.view.getColor1ChkBox().setText(ColorParam.NAME[0]);
		this.view.getColor2ChkBox().setText(ColorParam.NAME[1]);
		this.view.getColor3ChkBox().setText(ColorParam.NAME[2]);
		this.view.getColor4ChkBox().setText(ColorParam.NAME[3]);
		this.view.getImportBtn().addActionListener(this);
		this.view.getComputeBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.view.getImportBtn()){
			this.importFile();
		}
		else if(e.getSource() == this.view.getComputeBtn()) {
			this.compute();
		}
	}
	
	private void importFile(){
		JFileChooser importFile = new JFileChooser();
		importFile.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = importFile.showOpenDialog(this.view);
		if(result == JFileChooser.APPROVE_OPTION){
			File file = importFile.getSelectedFile();
			try {
				Scanner reader = new Scanner(file);
				int id = 0;
				this.sampleData = new double[31];
				while(reader.hasNext()) {
					if(id == 31) break;
					this.sampleData[id] = reader.nextFloat();
					this.view.getImportDataTbl().getModel().setValueAt("" + new DecimalFormat("#0.0000").format(this.sampleData[id]), id, 1);
					id++;
				}
				reader.close();
				if(id < 31) {
					System.out.println("The imported file is wrong format!");
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void compute(){
		if(this.sampleData == null){
			System.out.println("Please import the sample data file first");
			return;
		}
		int colorChooser = (this.view.getColor1ChkBox().isSelected() ? 1 : 0)
						| (this.view.getColor2ChkBox().isSelected() ? 2 : 0)
						| (this.view.getColor3ChkBox().isSelected() ? 4 : 0)
						| (this.view.getColor4ChkBox().isSelected() ? 8 : 0);
		
		ProcessController process = new ProcessController(colorChooser, this.sampleData, 10000);
		// Reset chart
		this.view.getSampleSeries().clear();
		this.view.getComputedSeries().clear();
		// Compute
		double[] ratio = process.compute();
		// Show the result
		this.showRatio(colorChooser, ratio);
		this.setColorBox(process.getSampleColor(), process.getComputedColor());
		this.drawChart(process.getSampleColor(), process.getComputedColor());
	}
	
	private void showRatio(int chooser, double[] ratio){
		String[] ratioTexts = new String[4];
		int id = 0;
		int bit = 1;
		for(int i = 0; i < 4; i++) {
			if((chooser & bit) != 0)
				ratioTexts[i] = new DecimalFormat("#0.00").format(ratio[id++] * 100) + "%";
			else 
				ratioTexts[i] = "0.00%";
			
			bit <<= 1;
		}
		this.view.getColor1RatioLbl().setText(ratioTexts[0]);
		this.view.getColor2RatioLbl().setText(ratioTexts[1]);
		this.view.getColor3RatioLbl().setText(ratioTexts[2]);
		this.view.getColor4RatioLbl().setText(ratioTexts[3]);
	}
	
	private void setColorBox(Colors sample, Colors computed){
		int[] sampleRGB = sample.getRGB();
		double[] sampleLAB = sample.getLAB();
		int[] computedRGB = computed.getRGB();
		double[] computedLAB = computed.getLAB();
		this.view.getSampleColorBox().setBackground(new Color(sampleRGB[0], sampleRGB[1], sampleRGB[2]));
		this.view.getComputedColorBox().setBackground(new Color(computedRGB[0], computedRGB[1], computedRGB[2]));
		this.view.getSampleCIELABLbl().setText("LAB = (" 
				+ new DecimalFormat("#0.00").format(sampleLAB[0])
				+ ", " 
				+ new DecimalFormat("#0.00").format(sampleLAB[1])
				+ ", "
				+ new DecimalFormat("#0.00").format(sampleLAB[2])
				+ ")");
		this.view.getComputedCIELABLbl().setText("LAB = (" 
				+ new DecimalFormat("#0.00").format(computedLAB[0])
				+ ", " 
				+ new DecimalFormat("#0.00").format(computedLAB[1])
				+ ", "
				+ new DecimalFormat("#0.00").format(computedLAB[2])
				+ ")");
	}
	
	private void drawChart(Colors sample, Colors computed) {
		double[] sampleData = sample.getData();
		double[] computedData = computed.getData();
		for(int i = 0; i < 31; i++) {
			this.view.getSampleSeries().add((double) (400 + 10 * i), sampleData[i]);
			this.view.getComputedSeries().add((double) (400 + 10 * i), computedData[i]);
		}
	}
}
