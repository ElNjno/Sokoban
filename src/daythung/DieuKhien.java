package daythung;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class DieuKhien implements Comparable<DieuKhien> {

	private String statestring;
	public char[][] level;	
	private int x;
	private int y;

	private int cost;
	private String path;


	private DieuKhien parent;
	public DieuKhien (char[][] level, int x, int y) {
		parent = null;
		this.level = new char[level.length][];
		for(int i = 0; i < level.length; i++){
			this.level[i] = new char[level[i].length];
                    System.arraycopy(level[i], 0, this.level[i], 0, level[i].length);
		}
		this.x = x;
		this.y = y;
		cost = 0;
		path = "";

		statestring = "";
		for(char[] row : level)
			for(char c : row)
				statestring += c;
	}

	public DieuKhien (DieuKhien par, char dir) {
		parent = par;
		this.cost = par.getCost() + 1;
		path = par.getPath() + dir;

		char[][] tmplevel = computeState(par, dir);

		level = new char[tmplevel.length][];
		for(int i = 0; i < tmplevel.length; i++){
			level[i] = new char[tmplevel[i].length];
                    System.arraycopy(tmplevel[i], 0, level[i], 0, tmplevel[i].length);
		}

		switch (dir) {
		case 'u': 
			x = par.getX() - 1;
			y = par.getY();
			break;
		case 'd': 
			x = par.getX() + 1;
			y = par.getY();
			break;
		case 'l': 
			x = par.getX();
			y = par.getY() - 1;
			break;
		case 'r': 
			x = par.getX();
			y = par.getY() + 1;
			break;	
		}

		statestring = "";
		for(char[] row : level)
			for(char c : row)
				statestring += c;
	}
	public int getCost() {
		return cost;
	}
	public int openGoals() {
		int opengoals = 0;
		for(char[] row : level)
			for(char c : row)
				if(c == '.')
					opengoals++;
		return opengoals;
	}

	public char[][] getState() {
		return level;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void logState() {
		try {
                    try (FileWriter fw = new FileWriter("log.txt", true)) {
                        for(char[] row : level){
                            for(char c : row){
                                fw.write(c);
                            }
                            fw.write('\n');
                        }
                    }
		} catch (IOException e) {
		}
	}

	public void printState() {
		for(char[] row : level){
			for(char c : row){
				System.out.print(c);
			}
			System.out.println();
		}
		System.out.println();

	}


	public void log(String line) {
		try {
                    try (FileWriter fw = new FileWriter("log.txt", true)) {
                        fw.write(line);
                    }
		} catch (IOException e) {
		}
	}


	public String getStateString() {
		return statestring;
	}

	public DieuKhien getParent() {
		return parent;
	}

	public String getPath() {
		return path;
	}

        @Override
	public int hashCode() {
		return statestring.hashCode();
	}

        @Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof DieuKhien))
			return false;
		return ( ((DieuKhien) obj).getStateString().equals(this.getStateString()) );
	}

	public boolean isGoal() {
		for(int i = 0; i < statestring.length(); i++) { 
			char c = statestring.charAt(i); 
			if(c == '.' || c == '+')
				return false;
		}
		return true;
	}


	public boolean isUpValid() {
		char up = level[x - 1][y];
		if(up == '#') 
			return false;
		if(up == ' ' || up == '.') 
			return true;
		if(level[x - 2].length <= y)
			return false;
		char up2 = level[x - 2][y];
		return (up == '$' || up == '*') && (up2 == ' ' || up2 == '.');
	}
	public boolean isDownValid() {
		
		
		char down = level[x + 1][y];
		
		if(down == '#') 
			
			return false;
		
		if(down == ' ' || down == '.') 
			return true;
		
		
		if(level[x + 2].length <= y)
			
			
			return false;
		
		char down2 = level[x + 2][y];
		
		return (down == '$' || down == '*') && (down2 == ' ' || down2 == '.');
	}
	public boolean isLeftValid() {
		
		
		char left = level[x][y - 1];
		
		if(left == '#') 
			
			return false;
		
		if(left != ' ' && left != '.') {
            } else {
                    return true;
            }
		
		if(y <= 1)
			
			
			return false;
		
		char left2 = level[x][y - 2];
		
		if( left != '$' && left != '*' || (left2 != ' ' && left2 != '.') ) {
            } else {
                    return true;
            }
		
		
		return false;
	}	
	public boolean isRightValid() {
		
		
		char right = level[x][y + 1];
		
		if(right == '#') 
			
			return false;
		
		if(right == ' ' || right == '.') 
			return true;
		
		if(level[x].length <= y + 2)
			
			
			return false;
		
		char right2 = level[x][y + 2];
		
		if( right != '$' && right != '*' || (right2 != ' ' && right2 != '.') ) {
            } else {
                    return true;
            }
		
		
		return false;
	}
	public ArrayList<Character> getValidMoves() {

		ArrayList<Character> moves = new ArrayList<>();

		if(isUpValid()) 
			moves.add('u');

		if(isDownValid()) 
			moves.add('d');

		if(isLeftValid()) 
			moves.add('l');

		if(isRightValid()) 
			moves.add('r');

		return moves;
	}
	private char[][] computeState(DieuKhien par, char dir) {
		char[][] oldlevel = par.getState();
		int x = par.getX();
		int y = par.getY();

		
		char[][] newlevel = new char[oldlevel.length][];
		for(int i = 0; i < oldlevel.length; i++){
			newlevel[i] = new char[oldlevel[i].length];
                    System.arraycopy(oldlevel[i], 0, newlevel[i], 0, oldlevel[i].length);
		}

		switch (dir) {
		
		case 'u': 
			
			if(newlevel[x - 1][y] == ' ')
				
				newlevel[x - 1][y] = '@';
			
			if(newlevel[x - 1][y] == '.')
				
				newlevel[x - 1][y] = '+';
			
			if(newlevel[x - 1][y] == '$') {
				cost++;
				
				if(newlevel[x - 2][y] == ' '){
					
					newlevel[x - 2][y] = '$';
					
					newlevel[x - 1][y] = '@';
				}
				
				else{
					
					newlevel[x - 2][y] = '*';
					
					newlevel[x - 1][y] = '@';
				}	
			}
			
			if(newlevel[x - 1][y] == '*') {
				cost++;
				
				if(newlevel[x - 2][y] == ' '){
					
					newlevel[x - 2][y] = '$';
					
					newlevel[x - 1][y] = '+';
				}
				
				else{
					
					newlevel[x - 2][y] = '*';
					
					newlevel[x - 1][y] = '+';
				}	
			}
			
			
			
			if(newlevel[x][y] == '@')
				
				newlevel[x][y] = ' ';
			
			else
				
				newlevel[x][y] = '.';
			break;	
		case 'd': 
			
			if(newlevel[x + 1][y] == ' ')
				
				newlevel[x + 1][y] = '@';
			
			if(newlevel[x + 1][y] == '.')
				
				newlevel[x + 1][y] = '+';
			
			if(newlevel[x + 1][y] == '$') {
				cost++;
				
				if(newlevel[x + 2][y] == ' '){
					
					newlevel[x + 2][y] = '$';
					
					newlevel[x + 1][y] = '@';
				}
				
				else{
					
					newlevel[x + 2][y] = '*';
					
					newlevel[x + 1][y] = '@';
				}	
			}
			
			if(newlevel[x + 1][y] == '*') {
				cost++;
				
				if(newlevel[x + 2][y] == ' '){
					
					newlevel[x + 2][y] = '$';
					
					newlevel[x + 1][y] = '+';
				}
				
				else{
					
					newlevel[x + 2][y] = '*';
					
					newlevel[x + 1][y] = '+';
				}	
			}
			
			
			
			if(newlevel[x][y] == '@')
				
				newlevel[x][y] = ' ';
			
			else
				
				newlevel[x][y] = '.';
			break;	
		case 'l': 
			
			if(newlevel[x][y - 1] == ' ')
				
				newlevel[x][y - 1] = '@';
			
			if(newlevel[x][y - 1] == '.')
				
				newlevel[x][y - 1] = '+';
			
			if(newlevel[x][y - 1] == '$') {
				cost++;
				
				if(newlevel[x][y - 2] == ' '){
					
					newlevel[x][y - 2] = '$';
					
					newlevel[x][y - 1] = '@';
				}
				
				else{
					
					newlevel[x][y - 2] = '*';
					
					newlevel[x][y - 1] = '@';
				}	
			}
			
			if(newlevel[x][y - 1] == '*') {
				cost++;
				
				if(newlevel[x][y - 2] == ' '){
					
					newlevel[x][y - 2] = '$';
					
					newlevel[x][y - 1] = '+';
				}
				
				else{
					
					newlevel[x][y - 2] = '*';
					
					newlevel[x][y - 1] = '+';
				}	
			}
			
			
			
			if(newlevel[x][y] == '@')
				
				newlevel[x][y] = ' ';
			
			else
				
				newlevel[x][y] = '.';
			break;	
		case 'r': 
			
			if(newlevel[x][y + 1] == ' ')
				
				newlevel[x][y + 1] = '@';
			
			if(newlevel[x][y + 1] == '.')
				
				newlevel[x][y + 1] = '+';
			
			if(newlevel[x][y + 1] == '$') {
				cost++;
				
				if(newlevel[x][y + 2] == ' '){
					
					newlevel[x][y + 2] = '$';
					
					newlevel[x][y + 1] = '@';
				}
				
				else{
					
					newlevel[x][y + 2] = '*';
					
					newlevel[x][y + 1] = '@';
				}	
			}
			
			if(newlevel[x][y + 1] == '*') {
				cost++;
				
				if(newlevel[x][y + 2] == ' '){
					
					newlevel[x][y + 2] = '$';
					
					newlevel[x][y + 1] = '+';
				}
				
				else{
					
					newlevel[x][y + 2] = '*';
					
					newlevel[x][y + 1] = '+';
				}	
			}

			
			
			
			if(newlevel[x][y] == '@')
				
				newlevel[x][y] = ' ';
			
			else
				
				newlevel[x][y] = '.';
			break;
		}
		return newlevel;
	}
	@Override
	public int compareTo(DieuKhien o) {
		if(this.getCost() == o.getCost())
			return 0;
		return (getCost() < o.getCost() ? -1 : 1);
	}
        @Override
	public String toString() {	
		return this.statestring+" [x]: "+x+" [y]:"+y;
	}

}