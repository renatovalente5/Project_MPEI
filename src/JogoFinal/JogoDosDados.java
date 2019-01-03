package JogoFinal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Teste_BloomFilter.CountingBloomFilter;
import Teste_ContadorEstocastico.ContadorEstocastico;
import Teste_MinHash.MinHash;


public class JogoDosDados extends JFrame{
	
	private static int t = 0;
	private static int times = 0;
	private static int maxTimes = 13;
	private static int total = 0, auxMax1=0, auxMax2=0, auxMax3=0, auxMax4=0, auxMax5=0, auxMax6=0;
	private static int n = 0;
	private static boolean seq4=false, seq5=false, full=true, same3=false, same4=false, fullen=false;
	private static int count=0, same=0, chance=0, somarMaxs=0;
	private static String bonus="active", notBonus="not active";
	protected static boolean stop1 = false, stop2 = false, stop3 = false, stop4 = false, stop5 = false, stop6 = false;
	protected static Dices dice;
	protected static int[] saveDices = new int[5];
	protected static int[] saveDices2 = new int[5];
	
	private JToggleButton buttonMax1, buttonMax2, buttonMax3,buttonMax4, buttonMax5, buttonMax6,
						  buttonSame5, buttonSequence4, buttonSequence5, buttonSame3, buttonSame4,buttonFullen,
						  buttonChance, buttonDice1, buttonDice2, buttonDice3, buttonDice4, buttonDice5;
	private JButton buttonRoll5, buttonRoll2;
	private JLabel labelTimes, labelResult, labelMaxs, labelCE;
	
	private static String[] guardaDados = new String[13];
	CountingBloomFilter b, b2;
	ContadorEstocastico ce;
	private JLabel labelJaSaiu;
	private int vezes = 0;
	private int nJogos = 0;
	private String[] arrayJogada = new String[13];
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JogoDosDados();
			}
		});
	}

	public JogoDosDados() {
		try {
			readNJogo();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		b = new CountingBloomFilter(7776);  // cria BloomFilter (6^5 = 7776)
		b.initialize();
		
		ce = new ContadorEstocastico(0.50);
		inserir500UltimasJogadas();  //Ler as 500 Ultimas Jogadas e inserir no BloomFilter
		
		dice = new Dices();
		saveDicesOfRound();
		creatView();
		
		setLocationByPlatform(true);
	    setSize(100, 500);
	    setMinimumSize(new Dimension(1350,400));
	    setMaximumSize(new Dimension(1350, 400)); 
	    setTitle("Jogo Dos Dados");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
	
	
	public void creatView() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);		
		
		
//North #######################################################################################################################
		JPanel panelNorth = new JPanel(new BorderLayout());
		panel.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setBorder(new EmptyBorder(0, 30, 0, 0)); 
	
		JButton buttonRoll = new JButton("Rulles");
		panelNorth.add(buttonRoll, BorderLayout.WEST);
		buttonRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  // ver slides como se faz com Lambda
				JOptionPane.showMessageDialog(getContentPane(),
				"Não vale fazer batota!!"
				+ "\n\nComo jogar:"
				+ "\n->Rodar os dados durante 3 vezes e fixar algum dado"
				+ "\n se necessário durante essas 3 vezes"
				+ "\n->Escolher uma opção em baixo"
				+ "\n->Rodar os dados...(Repetir este processo durante 13 vezes)"
				+ "\n"
				+ "\nValores:"
				+ "\n->Max's    = n * vezes"
				+ "\n->3 iguias = 15"
				+ "\n->4 iguias = 25"
				+ "\n->5 iguias = 50"
				+ "\n->Sequencia de 4 = 30"
				+ "\n->Sequencia de 5 = 40"
				+ "\n->Fullen = 25 (são 2 dados iguais + 3 dados iguais)"
				+ "\n->Chance = soma os 5 dados na mesa"
				+ "\n->Bonus = 35 (só se obtiveres mais/igual 65 pontos nos Max's)"
				+ "\n"
				);
				}
		});
		
		labelCE = new JLabel("    Sequences Inserted on BloomFilter: " + ce.getNumSequence());
		panelNorth.add(labelCE);
		
		
//Center #######################################################################################################################
		JPanel panelCenter = new JPanel(new BorderLayout());
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setBorder(new EmptyBorder(100, 10, 100, 10)); 
		panelCenter.setLayout(new GridLayout(1,7));
		panelCenter.setSize(50, 50);
		
		buttonRoll5 = new JButton(t + " of 3");
		panelCenter.add(buttonRoll5, BorderLayout.CENTER);
		buttonRoll5.setEnabled(false);
		
		JLabel label0 = new JLabel();
		panelCenter.add(label0, BorderLayout.CENTER);
		
		buttonDice1 = new JToggleButton("Dice");
		buttonDice1.setEnabled(false);
		panelCenter.add(buttonDice1, BorderLayout.CENTER);
		
		buttonDice2 = new JToggleButton("Dice");
		buttonDice2.setEnabled(false);
		panelCenter.add(buttonDice2, BorderLayout.CENTER);
		
		buttonDice3 = new JToggleButton("Dice");
		buttonDice3.setEnabled(false);
		panelCenter.add(buttonDice3, BorderLayout.CENTER);
		
		buttonDice4 = new JToggleButton("Dice");
		buttonDice4.setEnabled(false);
		panelCenter.add(buttonDice4, BorderLayout.CENTER);
		
		buttonDice5 = new JToggleButton("Dice");
		buttonDice5.setEnabled(false);
		panelCenter.add(buttonDice5, BorderLayout.CENTER);
		

		buttonDice1.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			    	  saveDices2[0] = saveDices[0];
			      }
			  }
		});
		
		buttonDice2.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			    	  saveDices2[1] = saveDices[1];
			      }
			  }
		});
		
		buttonDice3.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			    	  saveDices2[2] = saveDices[2];
			      }
			  }
		});
		
		buttonDice4.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			    	  saveDices2[3] = saveDices[3];
			      }
			  }
		});
		
		buttonDice5.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			    	  saveDices2[4] = saveDices[4];
			      }
			  }
		});
		
		buttonRoll5.setFont(new Font("Arial" , 1, 20));
		buttonDice1.setFont(new Font("Arial" , 1, 35));
		buttonDice2.setFont(new Font("Arial" , 1, 35));
		buttonDice3.setFont(new Font("Arial" , 1, 35));
		buttonDice4.setFont(new Font("Arial" , 1, 35));
		buttonDice5.setFont(new Font("Arial" , 1, 35));

		//set button size
	    Dimension d = new Dimension(10,10);

	    buttonDice1.setSize(d);
	    buttonDice2.setSize(d);
	    buttonDice3.setSize(d); 
	    buttonDice4.setSize(d);
	    buttonDice5.setSize(d);
		

//West #######################################################################################################################
		JPanel panelWest = new JPanel(new BorderLayout());
		panel.add(panelWest, BorderLayout.WEST);
		
		panelWest.setBorder(new EmptyBorder(30, 30, 30, 30)); 
		panelWest.setLayout(new GridLayout(3,1));
		
		labelTimes = new JLabel("Times Roled: "+ times);
		buttonRoll2 = new JButton("Roll the Dices");
		labelMaxs = new JLabel("<html><body><br>Max1 = 0 &emsp;&emsp;&emsp; Max4 = 0"+
							   "<br>Max2 = 0 &emsp;&emsp;&emsp; Max5 = 0"+
							   "<br>Max3 = 0 &emsp;&emsp;&emsp; Max6 = 0"+
							   " &emsp;&emsp; Bonus " +notBonus+ "</body></html>");
		
		panelWest.add(labelTimes, BorderLayout.NORTH);
		panelWest.add(buttonRoll2);
		panelWest.add(labelMaxs);
		
		
		labelTimes.setFont(new Font("Arial" , 1, 30));
		buttonRoll2.setFont(new Font("Sansserif", Font.PLAIN, 24));
		
		buttonRoll2.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(buttonRoll2.isEnabled()) {	
					dice = new Dices();
					saveDicesOfRound();
					t++;
					buttonRoll5.setText(t + " of 3");
					labelJaSaiu.setVisible(false);
					
					buttonDice1.setEnabled(true);
					buttonDice2.setEnabled(true);
					buttonDice3.setEnabled(true);
					buttonDice4.setEnabled(true);
					buttonDice5.setEnabled(true);
					
					if(t==3) {
		
						buttonRoll2.setEnabled(false);
						buttonMax1.setEnabled(true);
						buttonMax2.setEnabled(true);
						buttonMax3.setEnabled(true);
						buttonMax4.setEnabled(true);
						buttonMax5.setEnabled(true);
						buttonMax6.setEnabled(true);
						buttonChance.setEnabled(true);
						buttonSame3.setEnabled(true);
						buttonSame4.setEnabled(true);
						buttonSame5.setEnabled(true);
						buttonSequence4.setEnabled(true);
						buttonSequence5.setEnabled(true);
						buttonFullen.setEnabled(true);
						
					}
					
					if(buttonDice1.isSelected()) saveDices[0]=saveDices2[0];
					if(buttonDice2.isSelected()) saveDices[1]=saveDices2[1];
					if(buttonDice3.isSelected()) saveDices[2]=saveDices2[2];
					if(buttonDice4.isSelected()) saveDices[3]=saveDices2[3];
					if(buttonDice5.isSelected()) saveDices[4]=saveDices2[4];
					
					buttonDice1.setText("" + saveDices[0]);
					buttonDice2.setText("" + saveDices[1]);
					buttonDice3.setText("" + saveDices[2]);
					buttonDice4.setText("" + saveDices[3]);
					buttonDice5.setText("" + saveDices[4]);
					
					if(t==3) {
						labelJaSaiu.setVisible(true);
						verifyIsMember();
						guardaDados[times] = "" + saveDices[0] + saveDices[1] + saveDices[2] + saveDices[3] + saveDices[4];
						
					}
				}
			}
		});
		
			
//East #######################################################################################################################
		JPanel panelEast = new JPanel(new BorderLayout());
		panel.add(panelEast, BorderLayout.EAST);
		panelEast.setBorder(new EmptyBorder(10, 10, 10, 30)); 
		
		labelJaSaiu = new JLabel();
		labelResult = new JLabel();
		labelResult.setText("TOTAL:  " + total);
		labelResult.setFont(new Font("Arial" , 1, 30));
		panelEast.add(labelResult, BorderLayout.SOUTH);
		panelEast.add(labelJaSaiu);
		
		
//South #######################################################################################################################
		JPanel panelSouth = new JPanel();
		panel.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setBorder(new EmptyBorder(10, 10, 0, 10));
		
		buttonMax1 = new JToggleButton("Max1");
		buttonMax1.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				n=0;
				if(buttonMax1.isEnabled()) {
					for (int i = 0; i < saveDices.length; i++) {
						if(saveDices[i] == 1) n++;
					}
					auxMax1 = 1*n;
					total += auxMax1;
					System.out.println("Max1 -> " + auxMax1 + " points");
					stop1 = true;
					countVars();
					buttonMax1.setVisible(false);
					verifyBonus();
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonMax2 = new JToggleButton("Max2");
		buttonMax2.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				n=0;
				if(buttonMax2.isEnabled()) {
					for (int i = 0; i < saveDices.length; i++) {
						if(saveDices[i] == 2) n++;
					}
					auxMax2 = 2*n;
					total += auxMax2;
					System.out.println("Max2 -> " + auxMax2 + " points");
					stop2 = true;
					countVars();
					buttonMax2.setVisible(false);
					verifyBonus();
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonMax3 = new JToggleButton("Max3");
		buttonMax3.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				n=0;
				if(buttonMax3.isEnabled()) {
					for (int i = 0; i < saveDices.length; i++) {
						if(saveDices[i] == 3) n++;
					}
					auxMax3 = 3*n;
					total += auxMax3;
					System.out.println("Max3 -> " + auxMax3 + " points");
					stop3 = true;
					countVars();
					buttonMax3.setVisible(false);
					verifyBonus();
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonMax4 = new JToggleButton("Max4");
		buttonMax4.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				n=0;
				if(buttonMax4.isEnabled()) {
					for (int i = 0; i < saveDices.length; i++) {
						if(saveDices[i] == 4) n++;
					}
					auxMax4 = 4*n;
					total += auxMax4;
					System.out.println("Max4 -> " + auxMax4 + " points");
					stop4 = true;
					countVars();
					buttonMax4.setVisible(false);
					verifyBonus();
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonMax5 = new JToggleButton("Max5");
		buttonMax5.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				n=0;
				if(buttonMax5.isEnabled()) {
						for (int i = 0; i < saveDices.length; i++) {
							if(saveDices[i] == 5) n++;
						}
						auxMax5 = 5*n;
						total += auxMax5;
						System.out.println("Max5 -> " + auxMax5 + " points");
						stop5 = true;
						countVars();
						buttonMax5.setVisible(false);
						verifyBonus();
						buttonsDisabled();
						verifyFinished();
						ButtonsDicesDescelect();
						ButtonsDicesDisabled();
				}
			}
		});
		
		buttonMax6 = new JToggleButton("Max6");
		buttonMax6.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				n=0;
				if(buttonMax6.isEnabled()) {
					for (int i = 0; i < saveDices.length; i++) {
						if(saveDices[i] == 6) n++;
					}
					auxMax6 = 6*n;
					total += auxMax6;
					System.out.println("Max6 -> " + auxMax6 + " points");
					stop6 = true;
					countVars();
					buttonMax6.setVisible(false);
					verifyBonus();
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonSame3 = new JToggleButton("3 iguais");
		buttonSame3.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				verifySames();
				if(buttonSame3.isEnabled()) {
					if (same3==true) {
						total += 15;
						System.out.println("Same3 -> 15 points");
					} else {
						System.out.println("Same3 -> 0 points");
					}
					countVars();
					buttonSame3.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonSame4 = new JToggleButton("4 iguais");
		buttonSame4.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				verifySames();
				if(buttonSame4.isEnabled()) {
					if (same4==true) {
						total += 25;
						System.out.println("Same4 -> 25 points");
					} else {
						System.out.println("Same4 -> 0 points");
					}
					countVars();
					buttonSame4.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonSame5 = new JToggleButton("5 iguais");
		buttonSame5.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				verFull();
				if(buttonSame5.isEnabled()) {
					if(full==true) {
						total += 50;
						System.out.println("Same5 -> 50 points");
					} else {
						System.out.println("Same5 -> 0 points");
					}
					countVars();
					buttonSame5.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});

		buttonSequence4 = new JToggleButton("Sequence of 4");
		buttonSequence4.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				verSequencia();
				if(buttonSequence4.isEnabled()) {
					if (seq4==true) {
						total += 30;
						System.out.println("Seq4 -> 30 points");
					} else {
						System.out.println("Seq4 -> 0 points");
					}
							countVars();
					buttonSequence4.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonSequence5 = new JToggleButton("Sequence of 5");
		buttonSequence5.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				verSequencia();
				if(buttonSequence5.isEnabled()) {
					if (seq5==true) {
						total += 40;
						System.out.println("Seq5 -> 40 points");
					} else {
						System.out.println("Seq5 -> 0 points");
					}
					countVars();
					buttonSequence5.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonFullen = new JToggleButton("Fullen");
		buttonFullen.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				verifyFullen();
				if(buttonFullen.isEnabled()) {
					if (fullen==true) {
						total += 25;
						System.out.println("Fullen -> 25 points");
					} else {
						System.out.println("Fullen -> 0 points");
					}
					countVars();
					buttonFullen.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		buttonChance = new JToggleButton("Chance"); //Soma todos os dados 
		buttonChance.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(buttonChance.isEnabled()) {
					for (int i = 0; i < saveDices.length; i++) {
						chance += saveDices[i];
					}
						total += chance;
						System.out.println("Extra -> " + chance + " points");
						
					countVars();
					buttonChance.setVisible(false);
					buttonsDisabled();
					verifyFinished();
					ButtonsDicesDescelect();
					ButtonsDicesDisabled();
				}
			}
		});
		
		panelSouth.add(buttonMax1);
		panelSouth.add(buttonMax2);
		panelSouth.add(buttonMax3);
		panelSouth.add(buttonMax4);
		panelSouth.add(buttonMax5);
		panelSouth.add(buttonMax6);
		panelSouth.add(buttonChance);
		panelSouth.add(buttonSame3);
		panelSouth.add(buttonSame4);
		panelSouth.add(buttonSame5);
		panelSouth.add(buttonSequence4);
		panelSouth.add(buttonSequence5);
		panelSouth.add(buttonFullen);
		
		buttonsDisabled();
	}
	
	
	public void buttonsDisabled() {
		buttonMax1.setEnabled(false);
		buttonMax2.setEnabled(false);
		buttonMax3.setEnabled(false);
		buttonMax4.setEnabled(false);
		buttonMax5.setEnabled(false);
		buttonMax6.setEnabled(false);
		buttonChance.setEnabled(false);
		buttonSame3.setEnabled(false);
		buttonSame4.setEnabled(false);
		buttonSame5.setEnabled(false);
		buttonSequence4.setEnabled(false);
		buttonSequence5.setEnabled(false);
		buttonFullen.setEnabled(false);
	}
	
	public void ButtonsDicesDescelect(){
		buttonDice1.setSelected(false);
		buttonDice2.setSelected(false);
		buttonDice3.setSelected(false);
		buttonDice4.setSelected(false);
		buttonDice5.setSelected(false);
	}
	
	public void ButtonsDicesDisabled() {
		buttonDice1.setEnabled(false);
		buttonDice2.setEnabled(false);
		buttonDice3.setEnabled(false);
		buttonDice4.setEnabled(false);
		buttonDice5.setEnabled(false);
	}
	
	public void ButtonsDescelect(){
		buttonMax1.setSelected(false);
	}
	
	public void countVars() {
		t=0;
		times++;
		buttonRoll5.setText(t + " of 3");
		labelTimes.setText("Times Roled: "+ times);
		labelResult.setText("TOTAL:  " + total);
		buttonRoll2.setEnabled(true);
		labelMaxs.setText("<html><body><br>Max1 = "+auxMax1+ " &emsp;&emsp;&emsp; Max4 = "+auxMax4+
				   "<br>Max2 = "+auxMax2+" &emsp;&emsp;&emsp; Max5 = "+auxMax5+
				   "<br>Max3 = "+auxMax3+" &emsp;&emsp;&emsp; Max6 = "+auxMax6+
				   "&emsp;&emsp; Bonus not active</body></html>");
	}
	
	public void saveDicesOfRound() {
		for (int i = 0; i < saveDices.length; i++) {
			saveDices[i] = dice.array[i];
		}
	}

	public void verFull() {
		for (int i = 0; i < saveDices.length-1; i++) {
			if(saveDices[i] != saveDices[i+1]) full=false;
		}
	}
	
	public void verSequencia() {
		count = 0;
		seq4=false;
		seq5=false;
		
		int[] arrayAux = new int[saveDices.length];
		System.arraycopy(saveDices, 0, arrayAux, 0, saveDices.length);
		Arrays.sort(arrayAux);
		
		for (int i = 0; i < arrayAux.length-1; i++) {
			if(arrayAux[i] <= arrayAux[i+1]){
				if(arrayAux[i] == arrayAux[i+1]-1) {
					count +=1;
				}
			} else {
				count = 0;
			}
		}
		if(count >= 4) seq5 = true;
		if(count >= 3) seq4 = true;
	}
	
	public void verifySames() {
		int[] arrayAux = new int[saveDices.length];
		System.arraycopy(saveDices, 0, arrayAux, 0, saveDices.length);
		Arrays.sort(arrayAux);
		same=0;
		for (int i = 0; i < arrayAux.length-1; i++) {
			if(arrayAux[i]==arrayAux[i+1]) {
				same++;
				if(same>=2) same3=true;
				if(same>=3) same4=true;
			}else same = 0;
		}
	}
	
	public void verifyFullen() {
		int[] arrayAux = new int[saveDices.length];
		System.arraycopy(saveDices, 0, arrayAux, 0, saveDices.length);
		Arrays.sort(arrayAux);
		if(	(arrayAux[0]==arrayAux[1] && arrayAux[2]==arrayAux[3] && arrayAux[3]==arrayAux[4]) || 
			(arrayAux[0]==arrayAux[1] && arrayAux[1]==arrayAux[2] && arrayAux[3]==arrayAux[4])) {
				fullen=true;
		}
	}
	
	public void verifyBonus() {		
		somarMaxs = auxMax1 + auxMax2 + auxMax3 + auxMax4 +auxMax5 +auxMax6;
		if(stop1 == true && stop2 == true && stop3 == true && stop4 == true && stop5 == true && stop6 == true) {
			if(somarMaxs >= 65) {
				total += 35;
				labelResult.setText("TOTAL:  " + total);
				labelMaxs.setText("<html><body><br>Max1 = "+auxMax1+ " &emsp;&emsp;&emsp; Max4 = "+auxMax4+
						   "<br>Max2 = "+auxMax2+" &emsp;&emsp;&emsp;   Max5 = "+auxMax5+
						   "<br>Max3 = "+auxMax3+" &emsp;&emsp;&emsp;   Max6 = "+auxMax6+
						   "&emsp;&emsp; Bonus active</body></html>");
				System.out.println("Bonus -> 35 points");
			}
		}
	}
	
	public void sortArrayDices() {
		int x, y, aux;
		for(x = 0; x < saveDices.length; x++) {
			for(y = 0; y < saveDices.length; y++) {
				if(saveDices[y] > saveDices[x]) {
					aux = saveDices[y];
					saveDices[y] = saveDices[x];
					saveDices[x] = aux;
				}
			}
		}
	}
	
	public void verifyFinished() {		
		if(times >= maxTimes) {
			if(somarMaxs < 65) {
				System.out.println("Bonus -> 0 points");
			}
			try {
				writeJogada();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			MinHash minhash = new MinHash();
			
			double MinHashesIdenticos_Maior = 0;   //maior = mais semelhantes
			double distJacard = 0;
			int save = 0;
			int iguais = 0;
			int[] helpIguais = new int[13];
			int[] helpIguais2 = new int[13];
			String[] arrayJogadaSemelhante = new String[13];
			for (int i = 0; i < nJogos - 1; i++) {   // Para comparar a Similiaridade com todos os jogos anteriores
				
				try {
					readJogada(i);
					System.out.println("Jogo -> " + i);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				//Calcular Assinaturas
				minhash.similares(arrayJogada, guardaDados);  //guardaDados = dados do Jogo atual
				
				//Calcular Distancia entre Assinaturas
				minhash.CompararMinSimilares();
				
				System.out.println("semelhantes: " + (int)minhash.MinHashesIdenticos);
				if(MinHashesIdenticos_Maior < minhash.MinHashesIdenticos) {
					MinHashesIdenticos_Maior = minhash.MinHashesIdenticos;
					distJacard = minhash.distJacard;
					arrayJogadaSemelhante = arrayJogada.clone();
					save = i;
				}
				minhash.MinHashesIdenticos = 0;  //para nao somar infinitivamente
			}
			
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 13; j++) {
					if(guardaDados[j].equals(arrayJogadaSemelhante[i])) {
						iguais++;
						helpIguais[j] = 1;
						helpIguais2[i] = 1;
					}
				}
			}
			
			System.out.println("\nEste jogo foi o mais parecido com o jogo "+ save);
			System.out.println("-> MinHashesIdenticos = " + MinHashesIdenticos_Maior);
			System.out.println("-> DistJacard = " + distJacard);
			if(iguais == 1) System.out.println("\n Tem " + iguais + " par igual!\n");
			if(iguais < 1 || iguais > 1) System.out.println("\n Tem " + iguais + " par iguais!\n");
			for (int i = 0; i < guardaDados.length; i++) {
				if(helpIguais[i] == 1)
				{
					System.out.println("JogoAtual: " + guardaDados[i] + " <--");
				}
				else
				{
					System.out.println("JogoAtual: " + guardaDados[i]);
				}
			}
			System.out.println();
			for (int i = 0; i < 13; i++) {
				if(helpIguais2[i] == 1)
				{
					System.out.println("LerDadosDoJogoMaisParecido: " + arrayJogadaSemelhante[i] + " <--");
				}
				else 
				{
					System.out.println("LerDadosDoJogoMaisParecido: " + arrayJogadaSemelhante[i]);	
				}
			}
			
			JOptionPane.showMessageDialog(getContentPane(),
			"Jogo terminou, fez "+ total + " pontos!!\n\n"
					+ "Este jogo foi o mais parecido com o jogo: "+ save
					+ "\n -> MinHashesIdenticos = " + MinHashesIdenticos_Maior
					+ "\n -> Distancia = " + distJacard);
			System.exit(1);
		}
	}
	
	public void readJogada(int n) throws FileNotFoundException {
		
		File fr = new File("..//ProjetoFinalMPEI//src//JogoFinal//Jogadas//" + "Jogada" + n + ".txt");
		Scanner scr = new Scanner(fr);
		int i = 0;
		while(i < 13) {
			arrayJogada[i] = scr.nextLine();
			i++;
		}
		scr.close();
	}
	
	public void writeJogada() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("..//ProjetoFinalMPEI//src//JogoFinal//Jogadas//" + "Jogada" + nJogos + ".txt"));
		for (int i = 0; i < guardaDados.length; i++) {
	    	pw.println(guardaDados[i]);
		}
		pw.close();
	}
	
	public void verifyIsMember() {
		guardaDados[times] = "" + saveDices[0] + saveDices[1] + saveDices[2] + saveDices[3] + saveDices[4];
		if (b.isMember(guardaDados[times])) {
			vezes = b.numVezes;
			if(vezes == 1){
				labelJaSaiu.setText("Pertence " + 1 +" vez");
			}else {
				labelJaSaiu.setText("Pertence " + vezes +" vezes");
			}
			labelJaSaiu.setText("Pertence " + vezes +" vezes");
		} else {
			labelJaSaiu.setText("Não Pertence");
		}
	}
	
	public void readNJogo() throws FileNotFoundException {
		File file = new File("..//ProjetoFinalMPEI//src//JogoFinal//Jogadas");
		nJogos = file.listFiles().length;
	}

	
	public void inserir500UltimasJogadas() {
		for (int i = nJogos-500; i < nJogos; i++) {  //Para ver só os ultimos 500 jogos
			try {									 // ou seja, 500*13= 6500, logo como temos 7776 sequecias
				readJogada(i);						 // diferentes deve dar em média 1 vez que Pertence!
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < arrayJogada.length; j++) {
				b.insert(arrayJogada[j]);
				ce.readSequence();
			}
		}
	}
}
