package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs131.pa1.filter.Message;
/**
 * An object to obtain all the unique lines in a file
 * @author samstanley
 *
 */
public class Uniq extends SequentialFilter {
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "uniq";
	/**
	 * Initializes the input and output queues, and instantiates the object.
	 */
	public Uniq() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Takes in the file to be read, and determines which lines are unique.
	 * If no input is given or the file cannot be found, warns the user of the error.
	 * @param line The name of the file to be searched
	 * @return All unique lines in the file
	 */
	@Override
	protected String processLine(String line) {
		if(line.contentEquals("noinput")) {
			return Message.REQUIRES_INPUT.with_parameter("uniq").toString();
		}
		List<String> lines = new LinkedList<String>();
		String result = "";
		try {
			File f = new File(line);
			Scanner file = new Scanner(f);
			while(file.hasNextLine()) {
				String temp = file.nextLine();
				if(!lines.contains(temp)){
					lines.add(temp);
					result += temp + "\n";
				}
			}
		}catch(FileNotFoundException e) {
			return Message.FILE_NOT_FOUND.with_parameter("uniq "+line).toString();
		}
		
		return result;
	}

}
