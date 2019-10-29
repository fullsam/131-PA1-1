package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.LinkedList;

import cs131.pa1.filter.Message;
/**
 * Object to allow the changing of directories
 * @author samstanley
 *
 */
public class CD extends SequentialFilter {
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "cd";
	/**
	 * Creates the object and initializes the input and output queues
	 */
	public CD() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Changes the working directory to a new one
	 * If the directory is not found, returns an error.
	 * @param line Name of the new directory
	 * @return null
	 */
	@Override
	protected String processLine(String line) {
		String currentDirectory = SequentialREPL.currentWorkingDirectory;
		String newDirectory;
		if(line == null) {
			return Message.REQUIRES_PARAMETER.with_parameter("cd").toString();
		}else if(line.equals(".")) {
			newDirectory = currentDirectory;
		}else if(line.equals("..")) {
			int index = currentDirectory.lastIndexOf("/");
			newDirectory = currentDirectory.substring(0, index);
		}else if(line.equals("input")){
			return Message.CANNOT_HAVE_INPUT.with_parameter("cd").toString();
		}else {
			newDirectory = currentDirectory + SequentialREPL.separater + line;
			if(! new File(newDirectory).exists()) {
				return Message.DIRECTORY_NOT_FOUND.with_parameter("cd "+line).toString();
			}
		}
		SequentialREPL.currentWorkingDirectory = newDirectory;
		return null;
	}

}
