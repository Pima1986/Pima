package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface InputOutput {
String getString(String prompt);
void display(Object obj);
default String getString(String prompt,List<String> options){
	String response=null;
	String initialPrompt=prompt;
	prompt+=options;
	
	do {
		response=getString(prompt);
		prompt="Wrong input - see options "+initialPrompt+options;
	}while(!options.contains(response));
	return response;
}
default void displayLine(Object obj){
	String line=obj.toString()+"\n";
	display(line);
}
default int getInteger(String prompt){
	int response=0;
	String initialPrompt=prompt;
	boolean running=false;
	
	do {
		running=false;
		try {
			response=Integer.parseInt(getString(prompt));
			
		} catch (NumberFormatException e) {
			prompt="Wrong input - no number "+initialPrompt;
			running=true;
		}
	}while(running);
	return response;
}
default int getInteger(String prompt,int min, int max){
	int response=0;
	String initialPrompt=prompt+String.format(" from %d to %d ", min,max);
	prompt=initialPrompt;
	do {
		response=getInteger(prompt);
		prompt="Wrong input, see range - "+initialPrompt;
	}while(response<min || response>max);
	return response;
}
default long getLong(String prompt){
	long response=0;
	String initialPrompt=prompt;
	boolean running=false;
	do {
		running=false;
		try {
			response=Long.parseLong(getString(prompt));
			
		} catch (NumberFormatException e) {
			prompt="Wrong input - no number "+initialPrompt;
			running=true;
		}
	}while(running);
	return response;
}
default double getDouble(String prompt){
	double response=0;
	String initialPrompt=prompt;
	boolean running=false;
	do {
		running=false;
		try {
			response=Double.parseDouble(getString(prompt));
			
		} catch (NumberFormatException e) {
			prompt="Wrong input - no number "+initialPrompt;
			running=true;
		}
	}while(running);
	return response;
}
default LocalDate getDate(String prompt, String format){
	LocalDate response=null;
	DateTimeFormatter dtf=null;
	
	try {
		 dtf=DateTimeFormatter.ofPattern(format);
	} catch (Exception e) {
		dtf=DateTimeFormatter.ISO_DATE;
		format="yyyy-MM-dd";
	}
	String initialPrompt=prompt+" "+format;
	prompt=initialPrompt;
	boolean running;
	do {
		running=false;
		try {
			response=LocalDate.parse(getString(prompt), dtf);
			
		} catch (Exception e) {
			prompt="Wrong input - see date format "+initialPrompt;
			running=true;
		}
	}while(running);
	return response;
}
}
