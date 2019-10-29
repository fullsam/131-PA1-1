package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;
/**
 * An object to allow the searching of a file for lines containing a specific word
 * @author samstanley
 *
 */
public class Grep extends SequentialFilter {
/**
 * The word to be searched for
 */
	private String word;
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "grep";
	/**
	 * Instantiates the object, stores the word to be searched for, and initializes the input and output queues
	 * @param word
	 */
	public Grep(String word) {
		this.word = word;
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Processes the file and finds lines that contain the selected word
	 * If not input is given, returns an error as such
	 * If an invalid parameter, i.e. word to search for, is given, warns the user.
	 * Also warns the user if the requested file cannot be found. 
	 * @param The filename to check for
	 * @return The lines containing the selected word, or one of the above errors.
	 */
	@Override
	protected String processLine(String line) {
		if(line.equals("noinput") || !line.contains(".txt")){
			return Message.REQUIRES_PARAMETER.with_parameter("grep ").toString();
		}
		File file = new File(line);
		String result = "";
		if(this.word == null || this.word.contentEquals(" ")) {
			return Message.INVALID_PARAMETER.with_parameter("grep").toString();
		}else {
			try {
				Scanner readFile = new Scanner(file);
				while(readFile.hasNextLine()) {
					String temp = readFile.nextLine();
					if(temp.contains(word)) {
						result+= temp + "\n";
					}
				}
			} catch (FileNotFoundException e) {
				return Message.FILE_NOT_FOUND.with_parameter("grep "+line).toString();
			}
		}
		return result;
	}
	

}
