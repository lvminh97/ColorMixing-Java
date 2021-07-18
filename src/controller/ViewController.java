package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import model.Color;

import java.util.Scanner;

import view.View;

public class ViewController implements ActionListener{
	
	private View view;
	private double[] sampleData;
	
	public ViewController() {
		this.loadConf();
		this.view = new View();
		this.view.getColor1ChkBox().setText(Color.NAME[0]);
		this.view.getColor2ChkBox().setText(Color.NAME[1]);
		this.view.getColor3ChkBox().setText(Color.NAME[2]);
		this.view.getColor4ChkBox().setText(Color.NAME[3]);
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
	
	private void loadConf() {
		File nameCfg = new File("data/name.conf");
		File colorCfg = new File("data/color.conf");
		try {
			Scanner reader = new Scanner(nameCfg);
			int num = reader.nextInt();
			reader.nextLine(); // clear '\n' in buffer
			int id = 0;
			Color.NAME = new String[num];
			while(reader.hasNext()){
				Color.NAME[id] = reader.nextLine();
				id++;
				if(id == num) break;
			}
			reader = new Scanner(colorCfg);
			//
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error read config file");
			e.printStackTrace();
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
					this.view.getImportDataTbl().getModel().setValueAt("" + this.sampleData[id], id, 1);
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
		int colorChooser = 0;
		if(this.view.getColor1ChkBox().isSelected() == true)
			colorChooser |= 1;
		if(this.view.getColor2ChkBox().isSelected() == true)
			colorChooser |= 2;
		if(this.view.getColor3ChkBox().isSelected() == true)
			colorChooser |= 4;
		if(this.view.getColor4ChkBox().isSelected() == true)
			colorChooser |= 8;
//		double[] ratio = Utils.compute();
	}
	
	private void showRatio(){
		
	}
	
	private void setColorBox(){
		
	}
	
}
