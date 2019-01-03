package Teste_ContadorEstocastico;
import java.io.File;

public class TesteContadorEstocastico {

	public static void main(String[] args) {

		int numOfStrings = 1000000;
		System.out.println("Inicializar teste contador estocastico:");
		File f = new File("..//ProjetoFinalMPEI//src//Teste_ContadorEstocastico//Numbers.txt");
		double probContagem = 0.5;
		ContadorEstocastico cont = new ContadorEstocastico(probContagem);
		cont.writeNStringsToFile(numOfStrings, f);
		cont.readFromFile(f);
		cont.timesEqualTo();   // Numero de vezes que aparece uma sequencia aleatória
		cont.timesEqualTo("54123");  // Numero de vezes que aparece a sequencia "12345"
	}
}
