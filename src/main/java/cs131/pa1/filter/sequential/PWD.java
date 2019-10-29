package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import cs131.pa1.filter.Message;
/**
 * Object to give the current working directory for the user.
 * @author samstanley
 *
 */
public class PWD extends SequentialFilter {
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "pwd";
	/**
	 * Instantiates the object, as well as it's input and output queues
	 */
	public PWD() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Returns the user's current working directory.
	 * Returns an error if an input is given.
	 * @param line If an input is given with the command, gives an error, otherwise not used
	 * @return The current working directory of the user
	 */
	@Override
	protected String processLine(String line) {
		if(line != null) {
			return Message.CANNOT_HAVE_INPUT.with_parameter("pwd").toString();
		}
		return SequentialREPL.currentWorkingDirectory+"\n";
	}

}
