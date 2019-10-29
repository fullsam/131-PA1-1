package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.LinkedList;

import cs131.pa1.filter.Message;
/**
 * Object that allows printing the contents of the current directory
 * @author samstanley
 */
public class LS extends SequentialFilter{
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "ls";
	/**
	 * Instantiates the LS object, as well as it's input and output queues.
	 */
	public LS() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Creates an Array of all files in the directory, and prints said list to the console.
	 * If presented with an input, gives an error.
	 * @param line Causes an error to be thrown if given an input, otherwise, not used.
	 */
	@Override
	public String processLine(String line) {
		File workingDirectory = new File(SequentialREPL.currentWorkingDirectory);
		File[] files = workingDirectory.listFiles();
		String stuff = "";
		if (line != null) {
			return Message.CANNOT_HAVE_INPUT.with_parameter("ls").toString();
		}
		for(int i = 0; i < files.length; i++) {
			String temp = "";
			temp += files[i];
			int temp1= temp.lastIndexOf(SequentialREPL.separater);
			temp = temp.substring(temp1+1);
			stuff += temp;
			if(i<files.length-1) {
				stuff+="\n"+Message.NEWCOMMAND.toString();
			}
		}
		return stuff + "\n";
	}

}
