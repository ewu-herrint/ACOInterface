package acg.project.cli.parser;
import java.util.List;


public class StringUtils {
	
	public static int indexOfIgnoreCase(String[] array, String find){
		
		for(int i = 0; i < array.length; i++){
			
			if(array[i].equalsIgnoreCase(find)){
				return i;
			}
		}
		
		return -1;
	}
	
	public static int indexOfIgnoreCase(List<String> list, String find){
		
		for(int i = 0; i < list.size(); i++){
			
			if(list.get(i).equalsIgnoreCase(find)){
				return i;
			}
			
		}
		
		
		return -1;
	}
	
	public static boolean containsIgnoreCase(List<String> list, String contains){
		
		return indexOfIgnoreCase(list, contains) != -1;
		
	}

}
