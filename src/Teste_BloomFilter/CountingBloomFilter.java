package Teste_BloomFilter;
	
public class CountingBloomFilter {
	
	private int size;  //numero de elementos
	private int[] index;
	private int[] array;
	private static double p = 0.01;
	protected int n;
	protected static int m;
	protected static int k;
	protected static int[] a;
	protected static int[] b;
	public static int numVezes;
	HashFunction hashFunction;
	
	public CountingBloomFilter(int n)
	{
		this.n = n;
		m = (int)Math.round((-n * Math.log(p)) / Math.pow(Math.log(2),2));
		k = (int)((m/n)*Math.log(2));
		array = new int[m];
		a = new int[k];
		b = new int[k];
		hashFunction = new HashFunction(k);
	}
	
	public void initialize() { 
		for (int i = 0; i < k; i++) {
			a[i]= (int) (Math.random()*31)+1;
			b[i]= (int) (Math.random()*31)+1;
		}
	}
	
	public void insert(String str) 
	{
		index = HashFunction.hashFunc(str,a,b);  //index é a Hash
		for (int i = 0; i < k; i++) {
			array[index[i]] += 1;
			System.out.println("hash"+i+" -> " + str + " -> " + index[i]);
		}
		size++;
	}
	
	public boolean isMember(String str)
	{
		int count=0;
		index = HashFunction.hashFunc(str,a,b);
		System.out.println("\nAnalizar:");
		for (int i = 0; i < k; i++) {
			if(array[index[i]] >= 1) {
				count++;
				System.out.println("isMember -> " + str + " for the hash" +i+ " -> "+  index[i] + " is True");
			}
			else {
				System.out.println("isMember -> " + str + " for the hash" +i+ " -> "+  index[i] + " is False");
				System.out.println("Nao Pertence\n");
				return false;
			}
		}
		if(count==k) 
		{
			System.out.println("Pertence!!"); 
			if(array[index[0]] == 1)
			{
				System.out.println("Aparece " + array[index[0]] + " vez!");
			}
			else {
				System.out.println("Aparece " + array[index[0]] + " vezes!");	
			}
			numVezes = array[index[0]];
		}
		return true;
	}
	
	public void delete(String str) {
		if(isMember(str))
		{
			System.out.println();
			for (int i = 0; i < k; i++) {
				array[index[i]] -= 1;
				System.out.println("Remove hash" + i + " -> " + str + " -> " + index[i]);
			}
			if(array[index[0]] == 1)
			{
				System.out.println("Removido com sucesso, aparece " + array[index[0]] + " vez!");
			}
			else {
				System.out.println("Removido com sucesso, aparece " + array[index[0]] + " vezes!");	
			}
			size--;
		}
		else 
		{
			System.out.println("O elemento " + str + " não pertence ao Counting Bloom Filter logo nao pode ser removido");
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
}

