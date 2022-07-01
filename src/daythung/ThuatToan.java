package daythung;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Stack;

public class ThuatToan 
{
	private DieuKhien root;
	static boolean JUSTKEEPSWIMMING = true;

	public ThuatToan(DieuKhien r)
        {
		root = r;

	}
        private void cleanLog() {
		try {
			FileWriter fw = new FileWriter("log.txt");
			fw.write(new Date().toString() + '\n');
			fw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public DieuKhien getRoot() 
        {
		return root;
	}

	
	private String[] solution(DieuKhien solution, int generated, int repeated, int seen, long start) 
        {
		JUSTKEEPSWIMMING = false;

		double time = (System.nanoTime() - start) / 1000000000.0;
                String[] data = {   solution.getPath(),
                                    "\n",
                                    "Số lần tạo: " + Integer.toString(generated), 
                                    "Số lần lặp: " + Integer.toString(repeated),                                    
                                    "Số lược thăm dò: " + Integer.toString(seen), 
                                    "Thời gian chạy: " + String.valueOf((time)) 
		};
                return data;
	}

	private String[] failure() 
        {
		String[] fail = {"Tìm kiếm thất bại"};
		return fail;
	}


	public String[] DFS() 
        {
		JUSTKEEPSWIMMING = true;
		
		long start = System.nanoTime();
		int rep = 0;
		int gen = 1;

		DieuKhien node = root;
		HashSet<String> explored = new HashSet<>();
		Stack<DieuKhien> frontier = new Stack<>();
		
		frontier.push(node);
		while(JUSTKEEPSWIMMING) 
                {
			if(frontier.peek() == null)
				return failure();
			node = frontier.pop();
			if( !explored.contains(node.getStateString()) ) 
                        {
				explored.add(node.getStateString());
				if(node.isGoal())
					return solution(node, gen, rep, explored.size(), start);
				for(char c : node.getValidMoves()) 
                                {
					DieuKhien child = new DieuKhien(node, c);
					gen++;
					if( !explored.contains(child.getStateString()) ) 
                                        {
						frontier.add(child);
					}
					else
						rep++;
				}
			}
			else
				rep++;
		}
		return new String[0];
	}

}
