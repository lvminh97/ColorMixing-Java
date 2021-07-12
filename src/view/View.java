package view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class View extends JFrame{

	private static final long serialVersionUID = 2118299654730994785L;
	
	private JCheckBox color1ChkBox, color2ChkBox, color3ChkBox, color4ChkBox;
	private JButton importBtn;
	private JTable importData;

	public View() {
		this.initUI();
	}
	
	private void initUI() {
		this.setTitle("Color Mixing Tool");
		this.setBounds(100, 100, 1200, 700);
		
		JLabel label1 = new JLabel("Basic colors");
		label1.setBounds(20, 20, 150, 20);
		this.add(label1);
		
		color1ChkBox = new JCheckBox("Color 1", true);
		color1ChkBox.setBounds(20, 60, 150, 20);
		this.add(color1ChkBox);
		
		color2ChkBox = new JCheckBox("Color 2", true);
		color2ChkBox.setBounds(180, 60, 150, 20);
		this.add(color2ChkBox);
		
		color3ChkBox = new JCheckBox("Color 3", true);
		color3ChkBox.setBounds(20, 90, 150, 20);
		this.add(color3ChkBox);
		
		color4ChkBox = new JCheckBox("Color 4", true);
		color4ChkBox.setBounds(180, 90, 150, 20);
		this.add(color4ChkBox);
		
		importBtn = new JButton("Import color data");
		importBtn.setBounds(25, 140, 200, 30);
		this.add(importBtn);
		
		importData = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		importData.setBounds(20, 200, 280, 450);
		importData.setModel(new DefaultTableModel(new String[]{"Bước sóng", "Hệ số phản xạ"}, 0));
		for(int i = 0; i < 31; i++){
			((DefaultTableModel) importData.getModel()).addRow(new Object[] {(400 + 10 * i) + "", "0.0"});
		}
		this.add(importData);
		
		LegendPanel ratioPanel = new LegendPanel("Ratio");
		ratioPanel.setBounds(340, 20, 250, 300);
		this.add(ratioPanel);
		
		LegendPanel colorPanel = new LegendPanel("Color");
		colorPanel.setBounds(650, 20, 300, 200);
		
		this.add(colorPanel);
		
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
}
