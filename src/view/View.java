package view;

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

public class View extends JFrame{

	private static final long serialVersionUID = 2118299654730994785L;
	
	private JCheckBox color1ChkBox, color2ChkBox, color3ChkBox, color4ChkBox;
	private JButton importBtn, computeBtn;
	private JScrollPane importDataScrl;
	private JTable importDataTbl;
	private JLabel ratioLbl;
	
	private ChartPanel chartPanel;
	private XYSeries importSeries, computeSeries;

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
			((DefaultTableModel) importDataTbl.getModel()).addRow(new Object[] {(400 + 10 * i) + "", "0.0"});
		}
		importDataScrl.setViewportView(importDataTbl);
		
		LegendPanel ratioPanel = new LegendPanel("Ratio");
		ratioPanel.setBounds(340, 20, 320, 200);
		this.add(ratioPanel);
		ratioLbl = new JLabel();
		ratioLbl.setText("<html>Color1: 0%<br/><br/>Color2: 0%<br/><br/>Color3: 0%<br/><br/>Color4: 0%</html>");
		ratioLbl.setBounds(60, 20, 280, 160);
		ratioPanel.add(ratioLbl);
		
		LegendPanel colorPanel = new LegendPanel("Color");
		colorPanel.setBounds(680, 20, 360, 200);
		this.add(colorPanel);
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		importSeries = new XYSeries("sample data");
		computeSeries = new XYSeries("computed data");
		dataset.addSeries(importSeries);
		dataset.addSeries(computeSeries);
		JFreeChart chart = ChartFactory.createXYLineChart("Color's reflection", "Wavelength(nm)", "Reflection", dataset);
		XYPlot xyPlot = (XYPlot) chart.getXYPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		ValueAxis domainAxis = xyPlot.getDomainAxis();
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		domainAxis.setRange(400, 760);
		rangeAxis.setRange(0, 1.5);
		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(340, 250, 700, 400);
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
}
