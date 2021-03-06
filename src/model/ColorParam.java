package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ColorParam {
	
	public static String[] NAME;
	public static double[][] COLOR;
	
	public static double[] D65_ILL = {
		82.75, 91.49, 93.43, 86.68, 104.86, 117.01, 117.81, 
		114.86, 115.92, 108.81, 109.35, 107.8, 104.79, 107.69,
		104.41, 104.05, 100, 96.33, 95.79, 88.69, 90.01, 89.6,
		87.7,83.29,83.7,80.03,80.21,82.28,78.28,69.72,71.61
	};
	public static double[] D65_REF = {0.9505, 1.0, 1.0888};
	
	public static double[][] OBSVR = {
		{
			0.0191, 0.0847, 0.2045, 0.3147, 0.3837, 0.3707, 0.3023, 0.1956, 0.0805, 0.0162, 0.0038, 0.0375, 0.1177, 0.2365, 0.3768, 
			0.5298, 0.7052, 0.8787, 1.0142, 1.1185, 1.124, 1.0305, 0.8563, 0.6475, 0.4316, 0.2683, 0.1526, 0.0813, 0.0409, 0.0199, 0.0096	
		},
		{
			0.002, 0.0088, 0.0214, 0.0387, 0.0621, 0.0895, 0.1282, 0.1852, 0.2536, 0.3391, 0.4608, 0.6067, 0.7618, 0.8752, 0.962, 
			0.9918, 0.9973, 0.9556, 0.8689, 0.7774, 0.6583, 0.528, 0.3981, 0.2835, 0.1798, 0.1076, 0.0603, 0.0318, 0.0159, 0.0077, 0.0037
		},
		{
			0.086, 0.3894, 0.9725, 1.5535, 1.9673, 1.9948, 1.7454, 1.3176, 0.7721, 0.4153, 0.2185, 0.112, 0.0607, 0.0305,
			0.0137, 0.004, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
		}
	};
	
	public static void importBasicColor(){
		File nameCfg = new File("config/name.conf");
		File colorCfg = new File("config/color.conf");
		try {
			Scanner reader = new Scanner(nameCfg);
			int num = reader.nextInt();
			reader.nextLine(); // clear '\n' in buffer
			int id = 0;
			ColorParam.NAME = new String[num];
			while(reader.hasNext()){
				ColorParam.NAME[id] = reader.nextLine();
				id++;
				if(id == num) break;
			}
			reader.close();
			ColorParam.COLOR = new double[num][31];
			reader = new Scanner(colorCfg);
			int id1 = 0, id2 = 0;
			while(reader.hasNext()) {
				ColorParam.COLOR[id1][id2] = reader.nextDouble();
				id2++;
				if(id2 == 31) {
					id1++;
					id2 = 0;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error read config file");
			e.printStackTrace();
		}
	}
	
}
