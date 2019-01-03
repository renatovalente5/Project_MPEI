package Teste_MinHash;

public class MinHash {
	
	protected int[] arrayMin;
	private static int[] hash;
	protected static int x;
	protected int min;
	protected static int[][] minHashValores;
	public double MinHashesIdenticos = 0;
	public double distJacard;
	protected String[] doc;
	
	protected int[] a = new int[200];
	protected int[] b = new int[200];
	
	
	public MinHash() {
		hash = new int[200];
		initialize();
		minHashValores = new int[2][200];
	}
	
	public void similares(String[] Set1, String[] Set2) {
		
		for (int i = 0; i < 2; i++) { //numero de documentos
			if (i==0) { 
				doc = Set1;
			}else {
				doc = Set2;
			}
			
			for (int j = 0; j < 200; j++) {
				
				min = Integer.MAX_VALUE;
				
				int prime = 397;  
				
				for (int k = 0; k < 13; k++) {
					x = Integer.parseInt(doc[k]);
					hash[j] = (a[j]*x + b[j]) % prime;
					
					if(hash[j] < min) {
						min = hash[j];
					}
				}
				
				minHashValores[i][j] = min; //j = ao numero da hash
				
			}
		}			
	}
	
	public void initialize() {
		for (int i = 0; i < 200; i++) {
			a[i]= (int) (Math.random()*397)+1;
			b[i]= (int) (Math.random()*397)+1;
		}
	}

	
	public void CompararMinSimilares() {
		for (int i = 0; i < 200; i++) { //6 = numero de hashFunctions
			
			if(minHashValores[0][i] == minHashValores[1][i]) {
				
				MinHashesIdenticos++;
			}
		}
		distJacard = 1 - (MinHashesIdenticos / 200);
	}
}

