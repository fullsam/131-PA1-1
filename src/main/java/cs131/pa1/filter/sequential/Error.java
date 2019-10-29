package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import cs131.pa1.filter.Message;
/**
 * Creates an object to allow the returning of errors when commands are not found
 * @author samstanley
 *
 */
public class Error extends SequentialFilter {
	/**
	 * Creates the object and initializes the input and output queues
	 */
	public Error() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Returns a command not found error
	 * @param line Not used
	 * @return Command not found error
	 */
	@Override
	protected String processLine(String line) {
		return Message.COMMAND_NOT_FOUND.with_parameter(line).toString();
	}

}
