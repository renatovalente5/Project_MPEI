package Teste_BloomFilter;

public class TesteCountingBloomFilter {
	
	public static void main(String[] args) {
		
		CountingBloomFilter b = new CountingBloomFilter(7776);
		b.initialize();
		
		System.out.println("-----------------------------------------");
		System.out.println("----           Inserir               ----");
		System.out.println("-----------------------------------------");
		b.insert("12345");
		b.insert("12345");
		b.insert("12345");
		b.insert("12345");
		b.insert("32413");
		b.insert("25321");
		b.insert("54143");
		b.insert("64351");
		b.insert("12253");
		
		System.out.println("\n-----------------------------------------");
		System.out.println("----           IsMember              ----");
		System.out.println("-----------------------------------------");

		b.isMember("25321");  // Pertence
		b.isMember("31231");  // N�o Pertencer
		
		System.out.println("-----------------------------------------");
		System.out.println("----            Delete               ----");
		System.out.println("-----------------------------------------");
		
		// Deve verificar se pertence e se sim, remove-o do Counting Bloom Filter
		b.delete("12345"); 	  // Aparece 4 vezes e deve eliminar uma vez
		b.delete("12345"); 	  // Agora aparece 3 vezes e deve eliminar mais uma vez
		b.delete("12344"); 	  // Neste caso como n�o pertece vai dizer que n�o o pode remover porque nao existe
    }
}
