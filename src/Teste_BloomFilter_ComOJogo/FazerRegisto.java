package Teste_BloomFilter_ComOJogo;

import java.io.*;

public class FazerRegisto {
	
	public static void main(String[] args) throws IOException {
		
		PrintWriter pw = new PrintWriter("..//ProjetoFinalMPEI//src//Teste_BloomFilter_ComOJogo//RegistoDados.txt");
		
		String s = "";
		int[] array = {1, 1, 1, 1, 1};
		
		for(int i = 1; i <= 2; i++)
		{
			array[0] = i;
			for(int j = 1; j <= 6; j++)
			{
				array[1] = j;
				for(int k = 1; k <= 6; k++)
				{
					array[2] = k;
					for(int l = 1; l <= 6; l++)
					{
						array[3] = l;
						for(int m = 1; m <= 6; m++)
						{
							array[4] = m;
							for(int n = 0; n < array.length; n++)
							{
								s = s + array[n];
							}
							int register = Integer.parseInt(s);
							pw.println(register);
							s = "";
						}
					}
				}
			}
		}
		System.out.println("Dados registados com sucesso");
		pw.close();
	}
}
