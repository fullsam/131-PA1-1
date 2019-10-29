package cs131.pa1.filter.sequential;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
/**
 * This class manages the parsing and execution of a command.
 * It splits the raw input into separated subcommands, 
 * creates subcommand filters, and links them into a list. 
 * @author cs131a
 *
 */
public class SequentialCommandBuilder {
	/**
	 * Creates and returns a list of filters from the specified command
	 * @param command the command to create filters from
	 * @return the list of SequentialFilter that represent the specified command
	 */
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		Carrot writeToFile = null;
		if(command.contains(">")) {
			int spot = command.lastIndexOf('>');
			String fileToWrite = command.substring(spot);
			while(fileToWrite.startsWith(" ") || fileToWrite.startsWith(">")) {
				fileToWrite = fileToWrite.substring(1);
			}
			try {
				writeToFile = new Carrot(fileToWrite);
			} catch (IOException e) {
			}
			if(spot == 0) {
				writeToFile.input.add("needinput");
			}
			command = command.substring(0, spot);
			while(command.endsWith(" ")) {
				spot--;
				command = command.substring(0, spot);
			}
			if(fileToWrite.contains("\\|")) {
				writeToFile.input.add("noinput");
			}
		}
		String[] commands = command.split("\\|");
		List<SequentialFilter> filters = new LinkedList<SequentialFilter>();
		String fileName = null;
		SequentialFilter prev = null;
		SequentialFilter curr;
		String[] tempFilter = commands[0].split(" ");
		if(tempFilter.length > 2) {
			fileName = tempFilter[2];
			commands[0] = tempFilter[0] + " " + tempFilter[1];
		}else if(tempFilter.length > 1) {
			fileName = tempFilter[1];
			commands[0] = tempFilter[0];
		} 
		try {
			if(fileName.equals("pwd")) {
				fileName = null;
				commands[0] = "pwd"; 
			}
		}catch(NullPointerException e) {
		}
		boolean first = true;
		for(int i = 0; i < commands.length; i++) {
			String currCommand = commands[i];
			while(currCommand.startsWith(" ")) {
				currCommand = currCommand.substring(1);
			}
			if(currCommand.contains("grep")) {
				int j = currCommand.lastIndexOf(' ');
				if(j == -1 || fileName == null) {
					curr = new Grep(fileName);
					curr.input.add("noinput");
				}else {
					String word = currCommand.substring(j);
					curr = new Grep(word);
					curr.input.add(fileName);
				}
			}else if(currCommand.contains("cat")) {
				curr = new Cat();
				if(fileName == null) {
					curr.input.add("noinput");
				}else {
					curr.input.add(fileName);
				}
			}else if(currCommand.contains("cd")) {
				curr = new CD();
				if(fileName == null) {
					curr.input.add(null);
				}else if(!first) {
					curr.input.add("input");
				}else {
					curr.input.add(fileName);
				}
			}else if(currCommand.contains("ls")) {
				curr = new LS();
				if(fileName != null) {
					curr.input.add("big yikes");
				}else {
					curr.input.add(null);
				}
			}else if(currCommand.contains("pwd")) {
				curr = new PWD();
				if(fileName != null) {
					curr.input.add("big yikes");
				}else {
					curr.input.add(null);
				}
			}else if(currCommand.contains("uniq")) {
				curr = new Uniq();
				if(fileName == null) {
					curr.input.add("noinput");
				}else {
					curr.input.add(fileName);
				}
			}else if(currCommand.contains("wc")) {
				curr = new WC();
				if(fileName == null) {
					curr.input.add("noinput");
				}else {
					curr.input.add(fileName);	
				}
			}else {
				curr = new Error();
				curr.input.add(currCommand);
			}
			if(prev != null) {
				prev.setNextFilter(curr);
				curr.setPrevFilter(prev);
			}
			prev = curr;
			filters.add(curr);
			first = false;
		}
		if(writeToFile != null) {
			filters.add(writeToFile);
		}
		return filters;
	}
	
	
	/**
	 * Returns the filter that appears last in the specified command
	 * @param command the command to search from
	 * @return the SequentialFilter that appears last in the specified command
	 */
	private static SequentialFilter determineFinalFilter(String command){
		return null;
	}
	
	/**
	 * Returns a string that contains the specified command without the final filter
	 * @param command the command to parse and remove the final filter from 
	 * @return the adjusted command that does not contain the final filter
	 */
	private static String adjustCommandToRemoveFinalFilter(String command){
		return null;
	}
	
	/**
	 * Creates a single filter from the specified subCommand
	 * @param subCommand the command to create a filter from
	 * @return the SequentialFilter created from the given subCommand
	 */
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		return null;
	}
	
	/**
	 * links the given filters with the order they appear in the list
	 * @param filters the given filters to link
	 * @return true if the link was successful, false if there were errors encountered.
	 * Any error should be displayed by using the Message enum.
	 */
	private static boolean linkFilters(List<SequentialFilter> filters){
		for(int i = 0; i < filters.size() - 1; i++) {
			filters.get(i+1).input = filters.get(i).output;
		}
		return true;
	}
	
	/* Bonus stuff
	 	String[] commands;
		if(command.contains("|")) {
			commands = command.split("|");
		}else {
			commands = new String[1];
			commands[0] = command;
		}
		String temp = commands[0];
		String [] temp1 = temp.split(" ");
		String file;
		if(temp1.length > 2) {
			String temp2 = temp1[0].toLowerCase();
			temp2 += " " + temp1[2];
			commands[0] = temp2;
			file = temp1[1];
		}else if(temp1.length > 1){
			String temp2 = temp1[0].toLowerCase();
			commands[0] = temp2;
			file = temp1[1];
		}else {
			file = null;
		}
		List<SequentialFilter> result = new LinkedList<SequentialFilter>();
		for(int i = 0; i < commands.length; i++) {
			String j = commands[i];
			if(j.contains("ls")) {
				LS list = new LS();
				if(file != null) {
					list.input.add("yikes");
				}
				list.input.add(null);
				result.add(list);
			}else if(j.contains("cat")) {
				Cat cat = new Cat();
				if(file == null) {
					cat.input.add("noinput");
				}else {
					cat.input.add(file);
				}
				result.add(cat);
			}else if(j.contains("grep")) {
				int k = j.indexOf(' ');
				Grep grep;
				if(k == -1 || file == null) {
					grep = new Grep(j);
					grep.input.add("noinput");
				}else {
					j = j.substring(k);
					grep = new Grep(j);
					grep.input.add(file);
				}
				result.add(grep);
			}else if(j.contains("pwd")) {
				PWD pwd = new PWD();
				if(file != null) {
					pwd.input.add("yikes");
				}
				pwd.input.add(null);
				result.add(pwd);
			}else if(j.contains("cd")) {
				CD cd = new CD();
				cd.input.add(file);
				result.add(cd);
			}else if(j.contains("uniq")) {
				int k = j.indexOf(' ');
				Uniq uniq = new Uniq();
				if(file == null) {
					uniq.input.add("noinput");
				}else {
					uniq.input.add(file);
				}
				result.add(uniq);
			}else if(j.contains("wc")) {
				WC wc = new WC();
				if(file == null) {
					wc.input.add("noinput");
				}else {
					wc.input.add(file);
				}
				result.add(wc);
			}else {
				Error error = new Error();
				error.input.add("nah fam");
				result.add(error);
			}
		}
		return result;
	 */
}
