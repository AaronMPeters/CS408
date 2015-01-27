import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class HighScore extends JFrame implements ActionListener{
	private JPanel mainPanel;
	private JButton okButton;
	private JTextField tx1 = null;
	private JTextField tx2 = null;
	private ArrayList<scoreEntry> scoreList;
	private int de;
	
	public HighScore(int sc1, int sc2, int de) {
		// TODO Auto-generated constructor stub
		this.de = de;
		
		
		
		scoreList = new ArrayList<scoreEntry>();
		BufferedReader br;
		String current;
		try {
			br = new BufferedReader(new FileReader("top"));
			while ((current = br.readLine()) != null) {
				if(current.split(" ").length > 1){
				scoreList.add(new scoreEntry(Integer.parseInt(current.split(" ")[0]),current.split(" ")[1]));
				}
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		scoreList.add(new scoreEntry(sc1,"to be"));
		scoreList.add(new scoreEntry(sc2,"to be"));
		mainPanel = new JPanel(new GridLayout(scoreList.size() + 1, 4));
		Collections.sort(scoreList);
		
		for(int i = 0; i < 10; i++){
			if(scoreList.size() <= i) break;
			mainPanel.add(new JLabel(""+(i+1)));
			mainPanel.add(new JLabel(""+scoreList.get(i).score));
			if(scoreList.get(i).name.equals("to be")){
				if(tx1 == null){
					tx1 = new JTextField(5);
					mainPanel.add(tx1);
				} else {
					tx2 = new JTextField(5);
					mainPanel.add(tx2);
				}
			}else{
				mainPanel.add(new JLabel(scoreList.get(i).name));
			}
		}
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		mainPanel.add(okButton);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400,300);
		this.add(mainPanel);
		this.setTitle("Top 10");
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		try {
			PrintWriter writer = new PrintWriter("top", "UTF-8");
			Boolean s1 = false;
			for(scoreEntry s: scoreList){
				if(s.name.equals("to be")){
					if(s1 == false){
						writer.write(s.score + " " + tx1.getText() + "\n");
						s1 = true;
					} else {
						writer.write(s.score + " " + tx2.getText() + "\n");
					}
				}else{
					writer.write(s.score + " " + s.name + "\n");
				}
			}
			writer.close();
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		this.setVisible(false);
		if(de == 0){
			System.exit(0);
		}
	}

	class scoreEntry implements Comparable{
		int score;
		String name;

		public scoreEntry(int score, String name) {
			// TODO Auto-generated constructor stub
			this.score = score;
			this.name = name;

		}
		@Override
	    public int compareTo(Object i1) {
	        return -(new Integer(this.score)).compareTo(new Integer(((scoreEntry)i1).score));
	    }
	}
}
