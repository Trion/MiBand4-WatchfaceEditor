package elements.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import org.json.JSONObject;

import display.TopalignedLabel;
import elements.Element;
import elements.basic.Number;
import helpers.StaticHelpers;
import helpers.UnequalDimensionsException;
import main.MiBand4Editor;

public class Calories extends Element{
	
	Number stepsGoal;
	int suffixImageIndex;
	
	public Calories(int x, int y, String textAlignment, int spacing, int imageIndex, int imagesCount, int suffixImageIndex) throws IOException, UnequalDimensionsException {
		stepsGoal = new Number(x,y,textAlignment,spacing,imageIndex,imagesCount,5);
		this.suffixImageIndex=suffixImageIndex;
	}

	public Calories(JSONObject jsonObject) {
		stepsGoal = new Number(jsonObject.getJSONObject("Number"));
		suffixImageIndex=jsonObject.getInt("SuffixImageIndex");
	}

	public JPanel getPreview(int w, int h) throws IOException {
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER,stepsGoal.getSpacing()*2,0);
		layout.setAlignOnBaseline(true);
		JPanel panel = new JPanel(layout);
		TopalignedLabel label1 = new TopalignedLabel(stepsGoal.getImage(1));
		TopalignedLabel label2 = new TopalignedLabel(stepsGoal.getImage(0));
		TopalignedLabel label3 = new TopalignedLabel(stepsGoal.getImage(0));
		TopalignedLabel label4 = new TopalignedLabel(stepsGoal.getImage(0));
		TopalignedLabel label5 = new TopalignedLabel(StaticHelpers.getJLabelFromImage(suffixImageIndex));
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		panel.add(label5);
		panel.setName(this.getClass().toString());
		panel.setOpaque(false);
		
		JPanel centerPanel = new JPanel(new GridBagLayout()); //Center horizontally if CENTER is selected
		centerPanel.setOpaque(false);
		centerPanel.add(panel,new GridBagConstraints());
		
		JPanel mainPanel = new JPanel(); //LEFT/CENTER/RIGHT
		BorderLayout mainLayout = new BorderLayout();
		mainPanel.setLayout(mainLayout);
		mainPanel.setOpaque(false);
		mainPanel.setBackground(Color.CYAN);
		mainLayout.setHgap(0);
		mainLayout.setVgap(0);
		
		JPanel belowMainPanel = new JPanel(); //TOP/CENTER/BOTTOM
		BorderLayout belowMainLayout = new BorderLayout();
		belowMainPanel.setLayout(belowMainLayout);
		belowMainLayout.setHgap(0);
		belowMainPanel.setOpaque(false);
		belowMainLayout.setVgap(0);
		
		Object[] alignment = stepsGoal.getAlignmentConstraints();
		
		belowMainPanel.add(centerPanel,alignment[0]);
		
		mainPanel.add(belowMainPanel,alignment[1]);
		mainPanel.setName(panel.getName());
		
		if(w!=0 && h!=0) {
			Dimension preferred = layout.preferredLayoutSize(panel);
			Dimension finalD = new Dimension(Math.max(preferred.width, w),Math.max(preferred.height, h));
			mainPanel.setSize(finalD);
		}
		else {
			mainPanel.setSize(layout.preferredLayoutSize(panel));
		}
		
		return mainPanel;
	}

	@Override
	public JPanel getPreview() throws IOException {
		return getPreview(0,0);
	}

	@Override
	public void setCoords(int x, int y) {
		stepsGoal.setCoords(x, y);
	}

	@Override
	public String getJSON() {
		StringBuilder builder = new StringBuilder();
		builder.append("    \"Calories\": {\r\n");
		builder.append(stepsGoal.getJSON());
		builder.append(",\r\n");
		builder.append("      \"SuffixImageIndex\": "+suffixImageIndex);
		builder.append("\r\n    }");
		return builder.toString();
	}

	@Override
	public File[] getUsedImgs() {
		File[] used = stepsGoal.getUsedImgs();
		File[] all = new File[used.length+1];
		for(int i=0;i<used.length;i++) {
			all[i]=used[i];
		}
		String absolutePath = MiBand4Editor.currentPath.getAbsolutePath();
		String iFormatted = String.format("%04d", suffixImageIndex);
		all[all.length-1]=new File(absolutePath+"\\"+iFormatted+".png");
		return all;
	}

	public void changeAlignment(String alignment) {
		stepsGoal.changeAlignment(alignment);
	}

	public void changeSpacing(int spacing) {
		stepsGoal.changeSpacing(spacing);
	}

	public void changeImageCount(int imageCount) {
		stepsGoal.changeImageCount(imageCount);
	}

	public void changeImageIndex(int imageIndex) {
		stepsGoal.changeImageIndex(imageIndex);
	}

	public void resizeToCoords(int x, int y) {
		stepsGoal.resizeToCoords(x, y);
	}

	public void changeSuffixIndex(int suffixImageIndex) {
		this.suffixImageIndex=suffixImageIndex;
	}

	public Point getLocation() {
		return stepsGoal.getLocation();
	}

	public Point getBottomRightPoint() {
		return stepsGoal.getBottomRightPoint();
	}

}
