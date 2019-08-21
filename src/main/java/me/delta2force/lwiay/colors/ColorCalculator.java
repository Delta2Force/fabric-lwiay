package me.delta2force.lwiay.colors;

import java.awt.Color;
import java.util.HashMap;

public class ColorCalculator {
	public HashMap<Byte, Integer> colors = new HashMap<>();
	
	public ColorCalculator() {
		colors.put((byte)0, 0);
		colors.put((byte)1, 8368696);
		colors.put((byte)2, 16247203);
		colors.put((byte)3, 13092807);
		colors.put((byte)4, 16711680);
		colors.put((byte)5, 10526975);
		colors.put((byte)6, 10987431);
		colors.put((byte)7, 31744);
		colors.put((byte)8, 16777215);
		colors.put((byte)9, 10791096);
		colors.put((byte)10, 9923917);
		colors.put((byte)11, 7368816);
		colors.put((byte)12, 4210943);
		colors.put((byte)13, 9402184);
		colors.put((byte)14, 16776437);
		colors.put((byte)15, 14188339);
		colors.put((byte)16, 11685080);
		colors.put((byte)17, 6724056);
		colors.put((byte)18, 15066419);
		colors.put((byte)19, 8375321);
		colors.put((byte)20, 15892389);
		colors.put((byte)21, 5000268);
		colors.put((byte)22, 10066329);
		colors.put((byte)23, 5013401);
		colors.put((byte)24, 8339378);
		colors.put((byte)25, 3361970);
		colors.put((byte)26, 6704179);
		colors.put((byte)27, 6717235);
		colors.put((byte)28, 10040115);
		colors.put((byte)29, 1644825);
		colors.put((byte)30, 16445005);
		colors.put((byte)31, 6085589);
		colors.put((byte)32, 4882687);
		colors.put((byte)33, 55610);
		colors.put((byte)34, 8476209);
		colors.put((byte)35, 7340544);
		colors.put((byte)36, 13742497);
		colors.put((byte)37, 10441252);
		colors.put((byte)38, 9787244);
		colors.put((byte)39, 7367818);
		colors.put((byte)40, 12223780);
		colors.put((byte)41, 6780213);
		colors.put((byte)42, 10505550);
		colors.put((byte)43, 3746083);
		colors.put((byte)44, 8874850);
		colors.put((byte)45, 5725276);
		colors.put((byte)46, 8014168);
		colors.put((byte)47, 4996700);
		colors.put((byte)48, 4993571);
		colors.put((byte)49, 5001770);
		colors.put((byte)50, 9321518);
		colors.put((byte)51, 2430480);
	}
	
	public byte getNearestColor(int col) {
		int nearestIndex = 0;
		int nearScore = Integer.MAX_VALUE;
		int ourRGB = col;
		
		int index = 0;
		for(Integer rgb : colors.values()) {
			int score = difference(new Color(ourRGB), new Color(rgb));
			int templ = nearScore;
			if(nearScore < 0) {
				templ = -nearScore;
			}
			int tempd = score;
			if(score < 0) {
				tempd = -score;
			}
			if(tempd < templ) {
				nearScore = score;
				nearestIndex = index;
			}
			index++;
		}
		
		byte result = colors.keySet().toArray(new Byte[] {})[nearestIndex];
		
		if(result == 0) {
			result = 29;
		}
		
		return (byte) (result*4);
	}
	
	public static int difference(Color from, Color to) {
		int fr = from.getRed();
		int fg = from.getGreen();
		int fb = from.getBlue();
		
		int tr = to.getRed();
		int tg = to.getGreen();
		int tb = to.getBlue();
		
		int dr = tr-fr;
		int dg = tg-fg;
		int db = tb-fb;
		
		return (int) (Math.pow(dr, 2)+Math.pow(dg, 2)+Math.pow(db, 2));
	}
}
