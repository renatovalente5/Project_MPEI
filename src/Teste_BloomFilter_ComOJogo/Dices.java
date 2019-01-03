package Teste_BloomFilter_ComOJogo;
import javax.swing.*;

public class Dices extends JPanel{
	
	protected static int[] array = new int[5];
	
	public Dices() {
		roll();
	}
	
	public void roll() {
		for (int i = 0; i < array.length; i++) {
			array[i] = (int)(Math.random()*6)+1;
		}
	}

	public static int[] getArray() {
		return array;
	}

	public static void setArray(int[] array) {
		Dices.array = array;
	}
}