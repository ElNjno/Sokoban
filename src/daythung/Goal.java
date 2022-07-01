package daythung;

import java.util.Comparator;

public class Goal implements Comparator<DieuKhien> 
{
	@Override
	public int compare(DieuKhien o1, DieuKhien o2) 
        {
		if(o1.openGoals() == o2.openGoals())
			return 0;
		return (o1.openGoals() < o2.openGoals() ? -1 : 1);
	}

}
