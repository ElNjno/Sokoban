package daythung;
import java.io.File;
import java.util.Scanner;
public class DayThung 
{
	public static void main(String[] args) 
        {
		String levelpath;
		int searchtype;
		char stats;
		if(args.length < 3)
                {
			Scanner input = new Scanner(System.in);	
			levelpath = input.nextLine();
			searchtype = input.nextInt();
			stats = input.next().charAt(0);
		}
		else 
                {
			levelpath = args[0];
			searchtype = Integer.parseInt(args[1]);
			stats = args[2].charAt(0);
		}
		File lvl_src = new File(levelpath);
		Map ll = new Map(lvl_src);		
		ThuatToan tree = new ThuatToan(ll.init());

		String searchstring;
		switch(searchtype) 
                {
		case 1:
			searchstring = "DFS";
			break;		
		default:
			searchstring = "Không Thấy";
		}
		System.out.println("Đang Chạy " + searchstring + " Trên,");
		tree.getRoot().printState();		
		switch(searchtype) 
                {
                    case 1:
                        break;
                    case 2:
                            if(stats == 'y')
                                    for(String s : tree.DFS())
                                            System.out.println(s);
                            else
                                    System.out.println(tree.DFS()[0]);
                            break;		
		}
			
	}
}