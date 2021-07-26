package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import model.ColorParam;

public class View extends JFrame{

	private static final long serialVersionUID = 2118299654730994785L;
	
	private JCheckBox color1ChkBox, color2ChkBox, color3ChkBox, color4ChkBox;
	private JButton importBtn, computeBtn;
	private JScrollPane importDataScrl;
	private JTable importDataTbl;
	private JLabel color1RatioLbl, color2RatioLbl, color3RatioLbl, color4RatioLbl;
	private JLabel sampleColorBox, computedColorBox;
	private JLabel sampleCIELABLbl, computedCIELABLbl;
	
	private ChartPanel chartPanel;
	private XYSeries sampleSeries, computedSeries;

	public View() {
		this.initUI();
	}
	
	private void initUI() {
		this.setTitle("Color Mixing Tool");
		this.setBounds(100, 100, 1100, 700);
		
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
		importBtn.setBounds(25, 140, 150, 30);
		this.add(importBtn);
		
		computeBtn = new JButton("Compute");
		computeBtn.setBounds(185, 140, 110, 30);
		this.add(computeBtn);
		
		importDataScrl = new JScrollPane();
		importDataScrl.setBounds(20, 200, 280, 450);
		this.add(importDataScrl);
		
		importDataTbl = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		importDataTbl.setBounds(20, 200, 280, 450);
		importDataTbl.setModel(new DefaultTableModel(new String[]{"Wavelength (nm)", "Reflection"}, 0));
		for(int i = 0; i < 31; i++){
			((DefaultTableModel) importDataTbl.getModel()).addRow(new Object[] {(400 + 10 * i) + "", ""});
		}
		importDataScrl.setViewportView(importDataTbl);
		
		LegendPanel ratioPanel = new LegendPanel("Ratio");
		ratioPanel.setBounds(340, 20, 320, 200);
		this.add(ratioPanel);
		JLabel label3 = new JLabel(ColorParam.NAME[0] + ":");
		label3.setBounds(60, 40, 80, 20);
		ratioPanel.add(label3);
		color1RatioLbl = new JLabel();
		color1RatioLbl.setText("0.00%");
		color1RatioLbl.setBounds(150, 40, 60, 20);
		ratioPanel.add(color1RatioLbl);
		JLabel label4 = new JLabel(ColorParam.NAME[1] + ":");
		label4.setBounds(60, 70, 80, 20);
		ratioPanel.add(label4);
		color2RatioLbl = new JLabel();
		color2RatioLbl.setText("0.00%");
		color2RatioLbl.setBounds(150, 70, 60, 20);
		ratioPanel.add(color2RatioLbl);
		JLabel label5 = new JLabel(ColorParam.NAME[2] + ":");
		label5.setBounds(60, 100, 80, 20);
		ratioPanel.add(label5);
		color3RatioLbl = new JLabel();
		color3RatioLbl.setText("0.00%");
		color3RatioLbl.setBounds(150, 100, 60, 20);
		ratioPanel.add(color3RatioLbl);
		JLabel label6 = new JLabel(ColorParam.NAME[3] + ":");
		label6.setBounds(60, 130, 80, 20);
		ratioPanel.add(label6);
		color4RatioLbl = new JLabel();
		color4RatioLbl.setText("0.00%");
		color4RatioLbl.setBounds(150, 130, 60, 20);
		ratioPanel.add(color4RatioLbl);
		
		LegendPanel colorPanel = new LegendPanel("Color");
		colorPanel.setBounds(680, 20, 360, 200);
		this.add(colorPanel);
		JLabel label7 = new JLabel("Sample color: ");
		label7.setBounds(30, 50, 100, 30);
		colorPanel.add(label7);
		JLabel label8 = new JLabel("Computed color: ");
		label8.setBounds(30, 110, 100, 30);
		colorPanel.add(label8);
		sampleColorBox = new JLabel();
		sampleColorBox.setBounds(140, 55, 180, 20);
		sampleColorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		sampleColorBox.setOpaque(true);
		colorPanel.add(sampleColorBox);
		sampleCIELABLbl = new JLabel();
		sampleCIELABLbl.setBounds(140, 85, 180, 20);
		sampleCIELABLbl.setText("LAB = (0, 0, 0)");
		colorPanel.add(sampleCIELABLbl);
		computedColorBox = new JLabel();
		computedColorBox.setBounds(140, 115, 180, 20);
		computedColorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		computedColorBox.setOpaque(true);
		colorPanel.add(computedColorBox);
		computedCIELABLbl = new JLabel();
		computedCIELABLbl.setBounds(140, 145, 180, 20);
		computedCIELABLbl.setText("LAB = (0, 0, 0)");
		colorPanel.add(computedCIELABLbl);
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		sampleSeries = new XYSeries("sample data");
		computedSeries = new XYSeries("computed data");
		dataset.addSeries(sampleSeries);
		dataset.addSeries(computedSeries);
		JFreeChart chart = ChartFactory.createXYLineChart("Color's reflection", "Wavelength(nm)", "Reflection", dataset);
		XYPlot xyPlot = (XYPlot) chart.getXYPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		ValueAxis domainAxis = xyPlot.getDomainAxis();
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		domainAxis.setRange(400, 720);
		rangeAxis.setRange(0, 1.5);
		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(340, 250, 700, 400);
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);
		this.add(chartPanel);
		
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}

	public JCheckBox getColor1ChkBox(){
		return color1ChkBox;
	}
	
	public JCheckBox getColor2ChkBox(){
		return color2ChkBox;
	}
	
	public JCheckBox getColor3ChkBox(){
		return color3ChkBox;
	}
	
	public JCheckBox getColor4ChkBox(){
		return color4ChkBox;
	}
	
	public JButton getImportBtn() {
		return importBtn;
	}	
	
	public JTable getImportDataTbl() {
		return importDataTbl;
	}
	
	public JButton getComputeBtn() {
		return computeBtn;
	}

	public JLabel getColor1RatioLbl() {
		return color1RatioLbl;
	}

	public JLabel getColor2RatioLbl() {
		return color2RatioLbl;
	}

	public JLabel getColor3RatioLbl() {
		return color3RatioLbl;
	}

	public JLabel getColor4RatioLbl() {
		return color4RatioLbl;
	}	
	
	public JLabel getSampleColorBox() {
		return sampleColorBox;
	}
	
	public JLabel getComputedColorBox() {
		return computedColorBox;
	}
	
	public JLabel getSampleCIELABLbl() {
		return sampleCIELABLbl;
	}
	
	public JLabel getComputedCIELABLbl() {
		return computedCIELABLbl;
	}
	
	public XYSeries getSampleSeries() {
		return sampleSeries;
	}
	
	public XYSeries getComputedSeries() {
		return computedSeries;
	}
}
