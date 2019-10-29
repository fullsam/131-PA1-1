package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;
/**
 * An object to allow the counting of words, lines, and characters in a file.
 * @author samstanley
 *
 */
public class WC extends SequentialFilter {
	/**
	 * Stores the name of the object for use in errors
	 */
	public String name = "wc";
	/**
	 * Creates the WC object, and initializes the input and output queues.
	 */
	public WC() {
		input = new LinkedList<String>();;
		output = new LinkedList<String>();
	}
	/**
	 * Traverses through the selected file, and counts the number of words, lines, and characters
	 * If not given an input or the file isn't found, warns the user of the error.
	 * @param line The name of the file to search
	 * @return A string of the word, line, and character count.
	 */
	@Override
	protected String processLine(String line) {
		int words = 0, lines = 0, chars = 0;
		if(line.contentEquals("noinput")) {
			return Message.REQUIRES_INPUT.with_parameter("wc").toString();
		}else {
			try {
				File doc = new File(line);
				Scanner file = new Scanner(doc);
				while(file.hasNextLine()) {
					words++;
					String temp = file.nextLine();
					for(int i = 0; i < temp.length(); i++) {
						char temp1 = temp.charAt(i);
						if(temp1 == ' ') {
							words++;
						}else {
							chars++;
						}
					}
					lines++;
				}
			}catch(FileNotFoundException f) {
				return Message.FILE_NOT_FOUND.with_parameter("wc "+line).toString();
			}
			String result = lines + " " + words + " " + chars;
			return result+"\n";
		}
	}

}
