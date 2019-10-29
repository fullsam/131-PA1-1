package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import cs131.pa1.filter.Message;
/**
 * This is the class to pass any results of a command into a file
 * It takes the inputs of any results and prints them into a file created with the object.
 * @author samstanley
 *
 */
public class Carrot extends SequentialFilter {
	/**
	 * The file to be written to
	 */
	private File f;
	private String fileName;
	/**
	 * Creates the carrot object, ensuring the file is available and empty, and initializes the input and output queues
	 * @param fileName The name of the file to be written to
	 * @throws IOException Exception exists if the file cannot be found, but if not found, the file will be created
	 */
	public Carrot(String fileName) throws IOException {
		this.fileName = fileName;
		f = new File(SequentialREPL.currentWorkingDirectory+SequentialREPL.separater+fileName);
		f.delete();
		f.createNewFile();
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Takes in a line to be written to the file, and writes it
	 * @param line The line to be written
	 * @return null a Null string denoting that the command has finished.
	 */
	@Override
	protected String processLine(String line){
		if(line.equals("noinput")) {
			return Message.CANNOT_HAVE_OUTPUT.with_parameter("> "+fileName).toString();
		}else if(line.equals("needinput")) {
			return Message.REQUIRES_INPUT.with_parameter("> "+fileName).toString();
		}
		try {
			PrintStream out = new PrintStream(f);
			out.print(line);
			out.print("\n");
		}catch(IOException e) {
		}
		return null;
	}

}
