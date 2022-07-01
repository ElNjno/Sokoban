package daythung;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map 
{
	public char[][] levelmap;

	public Map (File levelsource) 
        {
		parseRowList(loadRowList(levelsource));
	}

	public DieuKhien init() 
        {
		return new DieuKhien(levelmap, getX(), getY());
	}

	private ArrayList<String> loadRowList(File levelsource) 
        {
		Scanner input;
		ArrayList<String> rowlist = new ArrayList<>();
		try 
                {
			input = new Scanner(levelsource);
			
			if(input.hasNextInt())
                        {
				int height = Integer.parseInt(input.nextLine());
				
				for(int i = 0; i < height; i++) 
                                {
					
					if(input.hasNextLine())
                                        {						
						rowlist.add(input.nextLine());
					}
					else 
                                        {
						System.out.println("Không tìm thấy trò chơi");
					}
				}
			}
			input.close();
                        
		} catch (FileNotFoundException e) 
                {
			
			System.out.println("Error");
                        e.printStackTrace();
		}
		return rowlist;
	}


	private void parseRowList(ArrayList<String> rowlist) 
        {
		int height = rowlist.size();
		levelmap = new char[height][];
		for(int i = 0; i < height; i++) 
                {
			levelmap[i] = rowlist.get(i).toCharArray();
		}
	}


	private void printLevel() {
		for(char[] row : levelmap){
			for(char c : row){
				System.out.print(c);
			}
			System.out.println();
		}
        }

	private int[] getPlayerLocation()
        {
		for(int i = 0; i < levelmap.length; i++) 
                {
			for(int j = 0; j < levelmap[i].length; j++) 
                        {
				if(levelmap[i][j] == '@' || levelmap[i][j] == '+')
					return new int[] {i, j};
			}
		}
		return new int[2];
	}

	private int getX() 
        {
		return getPlayerLocation()[0];
	}
	private int getY() 
        {
		return getPlayerLocation()[1];
	}

}