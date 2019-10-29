package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.IOException;
import java.util.*;

import cs131.pa1.filter.Message;

/**
 * The main implementation of the REPL loop (read-eval-print loop).
 * It reads commands from the user, parses them, executes them and displays the result.
 * @author cs131a
 * @author Sam Stanley
 *
 */
public class SequentialREPL {
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;
	/**
	 * Marker of if the loop should exit
	 */
	static int exit;
	/**
	 * Makes available the file separator for a given system for use elsewhere in a program.
	 */
	static String separater = File.separator;
	/**
	 * The main method that will execute the REPL loop
	 * Calls other methods to create a list of filters, then executes those filters.
	 * @param args not used
	 */
	public static void main(String[] args){
		exit = 0;
		Scanner input = new Scanner(System.in);
		Message welcome = Message.WELCOME;
		System.out.print(welcome.toString());
		Message newCommand = Message.NEWCOMMAND;
		currentWorkingDirectory = System.getProperty("user.dir");
		while(exit == 0) {
			System.out.print(newCommand.toString());
			String command = input.nextLine();
			if(command.equalsIgnoreCase("exit")) {
				exit = 1;
			}else {
				List<SequentialFilter> filters = SequentialCommandBuilder.createFiltersFromCommand(command);
				runCommands(filters, command);
			}
			if(command.contains("exit")) {
				exit = 1;
			}
		}
		System.out.print( Message.GOODBYE.toString());	
	}
	/**
	 * Executes the commands created elsewhere, and prints the results to a file or the console as requested
	 * @param commands The list of commands requested by the user
	 * @param orig The original request, used to fill in error statements
	 */
	private static void runCommands(List<SequentialFilter> filters, String command) {
		SequentialFilter curr = null;
		String[] commands = command.split("\\|");
		for(int i = 0; i < filters.size(); i++) {
			curr = filters.get(i);
			curr.process();
			if(i < filters.size() - 1) {
				filters.get(i+1).input = curr.output;
			}	
		}
		for(int i = 0; i < curr.output.size(); i++) {
			String temp = curr.output.poll().toString();
			/*String type = curr.getClass().toString();
			if(type.contains("grep")) {
				type = "grep";
			}else if(type.contains("cat")) {
				type = "cat";
			}else if(type.contains("cd")) {
				type = "cd";
			}else if(type.contains("ls")) {
				type = "ls";
			}else if(type.contains("pwd")) {
				type = "pwd";
			}else if(type.contains("uniq")) {
				type = "uniq";
			}else if(type.contains("wc")) {
				type = "wc";
			}else {
				type = "";
			}*/
			System.out.print(temp);
		}
	}
	/**
	 * Executes the commands created elsewhere, and prints the results to a file or the console as requested
	 * @param commands The list of commands requested by the user
	 * @param orig The original request, used to fill in error statements
	 */
	/*private static void runCommands(List<SequentialFilter> commands, String orig) {
		if(orig.contains(">")) {
			int meh = orig.lastIndexOf('>');
			String filename;
			try {
				int doublemeh = orig.indexOf('|', meh);
				filename = orig.substring(meh, doublemeh);
			}catch(StringIndexOutOfBoundsException e) {
				filename = orig.substring(meh+1);
			}
			for(int i = 0; i < commands.size(); i++) {
				commands.get(i).process();
				Carrot write = null;
				try {
					write = new Carrot(filename);
				} catch (IOException e) {
				}
				int j = 0;
				while(!commands.get(i).output.isEmpty()) {
					write.input.add(commands.get(i).output.poll());
					j++;
				}
				write.process();
			}
		}else {
			for(int i = 0; i < commands.size(); i++) {
				commands.get(i).process();
				while(!commands.get(i).output.isEmpty()){
					System.out.print(Message.NEWCOMMAND.toString());
					System.out.printf(commands.get(i).output.poll(), orig);
				}
			}
			System.out.println();
		}
	}*/

}
