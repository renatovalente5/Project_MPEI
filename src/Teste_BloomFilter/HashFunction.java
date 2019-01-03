package Teste_BloomFilter;

public class HashFunction{

	static int x;
	private static int[] hash;
	
	public HashFunction(int k) {
		hash = new int[k]; //pode ser 3 I think
	}
	
	public static int[] hashFunc(String str, int[] a, int[]b)
	{	
		int prime = nextPrime();
		x = Integer.parseInt(str);
		for (int i = 0; i < hash.length; i++) {
			hash[i] = (a[i]*x + b[i]) % prime;
		}
		//int index = (a*x + b) % prime; //Integer.parseInt(str) % prime;
		return hash;
	}
	
	public static boolean isPrime(int num) {
		if(num % 2 == 0) return false;
		for(int i = 3; i < num / 2; i = i + 2) {
			if(num % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	// Devolve o número primo imediatamente abaixo do tamanho do Bloom Filter
	public static int nextPrime() {
		if(!isPrime(CountingBloomFilter.m)) {
			for(int i = CountingBloomFilter.m; true; i--) {
				if(isPrime(i)) {
					return i;
				}
			}
		}
		else {
			return CountingBloomFilter.m;
		}
	}
}

