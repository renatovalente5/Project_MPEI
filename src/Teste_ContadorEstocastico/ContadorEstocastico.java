package Teste_ContadorEstocastico;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContadorEstocastico {

	private int numOfEqualValues = 0, numOfValues = 0;
	public int totalCountValues = 0;
	private ArrayList<String> storeSeq = new ArrayList<String>();
	private double probContagem;
	
	public ContadorEstocastico(double probContagem)
	{
		this.probContagem = probContagem;
	}

	public void writeNStringsToFile(int numOfStrings, File f)
	{
		try
		{
			System.out.println("A escrever " + numOfStrings + " strings aleatórias num ficheiro");
			FileWriter fw = new FileWriter(f);
			PrintWriter pw = new PrintWriter(fw);
			for(int i = 0; i < numOfStrings; i++) 
			{
				String rand = generateRandomString();
				pw.println(rand);
			}
			pw.close();
			fw.close();
			System.out.println("Adicionado com sucesso");	
		} catch(Exception e)
		{
			System.out.println("Error");
		}
		
	}
	
	// Gera uma string aleatória
	public void timesEqualTo()
	{
		numOfEqualValues = 0;
		String toCompare = generateRandomString();
		for(String str : storeSeq)
		{
			if(str.equals(toCompare))
			{
				int a = (int)(Math.random() * (1 / probContagem)) +  1;
				if(a == 1)
				{
					numOfEqualValues++;
				}
			}
		}
		numOfEqualValues *= (1 / probContagem);
		System.out.println("A sequência " + toCompare + " aparece " + numOfEqualValues + " vezes");
	}
	
	// Recebe a string que queremos contar
	public void timesEqualTo(String toCompare)
	{
		numOfEqualValues = 0;
		for(String str : storeSeq)
		{
			if(str.equals(toCompare))
			{
				int a = (int)(Math.random() * (1 / probContagem)) +  1;
				if(a == 1)
				{
					numOfEqualValues++;
				}
			}
		}
		numOfEqualValues *= (1 / probContagem);
		System.out.println("A sequência " + toCompare + " aparece " + numOfEqualValues + " vezes");
	}
	
	public void readSequence() 
	{	
		totalCountValues = 0;
		int a = (int)(Math.random() * (1 / probContagem)) +  1;
		if(a == 1)
		{
			numOfValues++;
		}
	}
	
	public int getNumSequence() {
		return numOfValues *= (1 / probContagem);
	}
	
	public String generateRandomString()
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
	
	public ArrayList<String> readFromFile(File f)
	{
		try 
		{
			Scanner read = new Scanner(f);	
			while(read.hasNextLine())
			{
				storeSeq.add(read.nextLine());
			}
			read.close();
		}
		catch(Exception e)
		{
			System.out.print("Can't read file");
		}
		return storeSeq;
	}
}
