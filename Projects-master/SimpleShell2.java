import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
public class SimpleShell2{
	public static void main(String []args) throws IOException{

		System.out.println("Press commands like ls, history, cd, home");
		System.out.println("Press space and enter to exit");
		String commandLine;
		//creating a list for all the commands typed
		List<String> history = new ArrayList<String>();
		
		BufferedReader reader =
			new BufferedReader (new InputStreamReader(System.in));

		ProcessBuilder pb = new ProcessBuilder();
		//creating an index so that we can add commands to list
		int z=0;
		while(true)
			{
			System.out.print("jsh>");
			commandLine = reader.readLine();
			
			//commandLine.split splits String command to two substrings
			String [] commands = commandLine.split(" ");
			
			//creating a list for all the commands
			List<String> list = new ArrayList<String>();
			
			for (int i=0; i<commands.length; i++){
				list.add(commands[i]);
			}
		//stores all previously typed code in the history list
		history.addAll(list);
		try{
			if(list.get(list.size()-1).equals("history")){
				for (String s: history)
					System.out.println((z++) + " " + s);
				continue;
			}
			

		//create directory command
		//move between directories
			if(list.contains("cd")){
			    if(list.get(list.size()-1).equals("cd")){
			    File home = new  File(System.getProperty("user.home"));
			    System.out.println("home:" + home);
			    pb.directory(home);
			    } 
			else if (list.contains("cd ..")){
			File parentDir = new File(pb.directory().getParent());
			System.out.println(parentDir);
			pb.directory(parentDir);
			}
			else if (list.contains("cd ~")){
                        if(list.get(list.size()-1).equals("cd")){
                        File home = new  File(System.getProperty("user.home"));
                        System.out.println("home " + home);
                        pb.directory(home);
				}
			}
			else{
				String dir = list.get(1);
				File newPath = new File(dir);
				boolean exists = newPath.exists();
			//prints the current path of the file
				if(exists){
				    System.out.println("/" + dir);
				    pb.directory(newPath);
				    continue;
				}
				else{
				    System.out.println(dir + " ");
				}
			    
				}
				}
		  if(list.get(list.size()-1).equals ("!!")){
                        pb.command(history.get(history.size() -2));
                        }                                                  
                         else if (list.get(list.size()-1).charAt(0) =='!'){

                                int b = Character.getNumericValue(list.get(list.size()-1).charAt(1));
                                if(b<=history.size())
                                pb.command(history.get(b));
				}

                        else{
                                pb.command(list);
                        }

		Process process = pb.start();

		InputStream input = process.getInputStream();
		InputStreamReader input_reader = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(input_reader);
		
		String line;
		while((line = br.readLine()) != null)
			System.out.println(line);
		br.close();
		if(commandLine.equals(" "))
		continue;
		}
		
		catch (IOException e){
			System.out.println();
		}
		}
	}
}
