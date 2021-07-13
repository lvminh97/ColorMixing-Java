package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import java.util.Scanner;

import view.View;

public class Controller implements ActionListener{
	
	private View view;
	
	public Controller() {
		this.view = new View();
		this.view.getImportBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.view.getImportBtn()){
			JFileChooser importFile = new JFileChooser(new File(System.getProperty("user.home")));
			importFile.setCurrentDirectory(null);
			int result = importFile.showOpenDialog(this.view);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = importFile.getSelectedFile();
				try {
					Scanner reader = new Scanner(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		}
	}
	
	
}
