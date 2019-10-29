package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;
/**
 * Creates a Cat object used to read from files 
 * @author samstanley
 *
 */
public class Cat extends SequentialFilter {
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "cat";
	/**
	 * Instantiates the Cat object, as well as its input and output queues
	 */
	public Cat() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**.
	 * Processes the input from the user.
	 * If given a file name to read from, it will read from that file
	 * If no file name is given, returns an error requiring input.
	 * If file cannot be found, warns user as such
	 * @param line The name of the file to be accessed.
	 */
	@Override
	protected String processLine(String line) {
		if(line.contentEquals("noinput")) {
			return Message.REQUIRES_PARAMETER.with_parameter("cat").toString();
		}
		String result = null;
		try {
			File file = new File(line);
			Scanner readFile = new Scanner(file);
			result = "";
			while(readFile.hasNextLine()) {
				result += readFile.nextLine();
				if(readFile.hasNextLine()) {
					result += "\n";
				}
			}
			readFile.close();
			result+="\n";
		}catch(FileNotFoundException f) {
			return Message.FILE_NOT_FOUND.with_parameter("cat "+line).toString();
		}
		return result;
	}

}
