package JogoFinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FazerJogadas {

	private static String[] arrayrandom = new String[13];
	private static int nJogos=0;
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10000; i++) {
			for (int j = 0; j < 13; j++) {
				arrayrandom[j] = generateRandomString();
			}
			try {
				writeJogada(arrayrandom);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Criado!!");
		
	}
	
	public static void writeJogada(String[] arrayrandom) throws IOException {
	    PrintWriter pw = new PrintWriter(new FileWriter("..//ProjetoFinalMPEI//src//JogoFinal//Jogadas//" + "Jogada" + nJogos + ".txt"));
		for (int i = 0; i < arrayrandom.length; i++) {
	    	pw.println(arrayrandom[i]);
		}
		pw.close();
		nJogos++;
	}
	
	public static String generateRandomString()
	{
		int[] rand = new int[5];
		String fiveNums = "";
		for(int j = 0; j < rand.length; j++)
		{
			rand[j] = (int)(Math.random() * 6) + 1;
		}
		for(int j = 0; j < rand.length; j++)
		{
			fiveNums = fiveNums + Integer.toString(rand[j]);
		}
		return fiveNums;
	}
}
