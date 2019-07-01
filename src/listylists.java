import com.craftclassic.play.Main;

public class listylists 
{
	public static void main(String[] av) 
	{
		int[] listOfIsabellesFavNums = {3, 1, 9, 4, 2, 0, 0, 0, 0, 0, 0};
		for(int num : listOfIsabellesFavNums)
		{
			listOfIsabellesFavNums[num] = 10;
			System.out.println(num);
		}
		
		//0x0000: 3
		//0x0001: 10
		//0x0002: 9
		//..
		
	}
}
