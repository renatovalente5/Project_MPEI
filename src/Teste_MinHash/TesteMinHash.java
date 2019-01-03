package Teste_MinHash;

import java.io.*;
import java.util.*;

public class TesteMinHash {

	protected static String[] lerDadosJogo = new String[13];
	protected static String[] LerDados_4iguais = new String[13];
	protected static String[] LerDados_10iguais = new String[13];
	protected static String[] LerDados_13iguais = new String[13];
	
	public static void main(String[] args) throws IOException {
		
		criarSetAtual();
		readDataBase();
		
		MinHash minhash1 = new MinHash();
		MinHash minhash2 = new MinHash();
		MinHash minhash3 = new MinHash();
		
		
		//Para 4 iguais em 13
		minhash1.similares(LerDados_4iguais, lerDadosJogo);  //Calcular Assinaturas
		minhash1.CompararMinSimilares();  	//Calcular Distancia entre Assinaturas
		paraImprimir(minhash1.MinHashesIdenticos, minhash1.distJacard, 4);  //Imprimir
		
		//Para 10 iguais em 13
		minhash2.similares(LerDados_10iguais, lerDadosJogo);
		minhash2.CompararMinSimilares();
		paraImprimir(minhash2.MinHashesIdenticos, minhash2.distJacard, 10);
		
		//Para todos iguais em 13
		minhash3.similares(LerDados_13iguais, lerDadosJogo);
		minhash3.CompararMinSimilares();
		paraImprimir(minhash3.MinHashesIdenticos, minhash3.distJacard, 13);
		
	}
	
	public static void criarSetAtual() {
		lerDadosJogo[0] = "51615";
		lerDadosJogo[1] = "12136";
		lerDadosJogo[2] = "52134";
		lerDadosJogo[3] = "66234";
		lerDadosJogo[4] = "11112";
		lerDadosJogo[5] = "12426";
		lerDadosJogo[6] = "31445";
		lerDadosJogo[7] = "24111";
		lerDadosJogo[8] = "24411";
		lerDadosJogo[9] = "24425";
		lerDadosJogo[10] = "21413";
		lerDadosJogo[11] = "61216";
		lerDadosJogo[12] = "54153";
	}
	
	public static void readDataBase() throws IOException {			

		int i = 0;
		File f1 = new File("..//ProjetoFinalMPEI//src//Teste_MinHash//RegistoDados_4iguais.txt");
		Scanner sc1 = new Scanner(f1);
		while(sc1.hasNextLine()) {
			LerDados_4iguais[i] = sc1.nextLine();
			i++;
		}
		sc1.close();
		
		i = 0;
		File f2 = new File("..//ProjetoFinalMPEI//src//Teste_MinHash//RegistoDados_10iguais.txt");
		Scanner sc2 = new Scanner(f2);
		while(sc2.hasNextLine()) {
			LerDados_10iguais[i] = sc2.nextLine();
			i++;
		}
		sc2.close();
		
		i = 0;
		File f3 = new File("..//ProjetoFinalMPEI//src//Teste_MinHash//RegistoDados_13iguais.txt");
		Scanner sc3 = new Scanner(f3);
		while(sc3.hasNextLine()) {
			LerDados_13iguais[i] = sc3.nextLine();
			i++;
		}
		sc3.close();
	}
	
	
	
	public static void paraImprimir(double MinHashesIdenticos, double distJacard, int n){
		System.out.println("-----------------------------------------");
		System.out.println("Set1 | Set2");
		for (int j = 0; j < 20; j++) {
			System.out.printf("%4d - %d\n", MinHash.minHashValores[0][j], MinHash.minHashValores[1][j]);
		}
		System.out.println("\nOs Sets têm " + n + " sequencias iguais em 13: ");
		System.out.println("-> MinHashesIdenticos: " + MinHashesIdenticos);
		System.out.println("-> Distancia: " + distJacard);
		System.out.println("-----------------------------------------");
		
		
	}

}
