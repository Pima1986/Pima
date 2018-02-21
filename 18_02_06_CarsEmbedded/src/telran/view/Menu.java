package telran.view;

import java.util.ArrayList;

public class Menu {
	protected ArrayList<Item> items;
	InputOutput inputOutput;


	public Menu(ArrayList<Item> items, InputOutput inputOutput) {
		super();
		this.items = items;
		this.inputOutput = inputOutput;
	}


	public void runMenu(){
		int size=items.size();
		while (true) {
			for (int i = 0; i < size; i++) {
				inputOutput.displayLine((i+1) + ". " + items.get(i).displayedName());
			}
			int itemNumber = inputOutput.getInteger("Enter item number",1,size);
			try {
				items.get(itemNumber - 1).action();
			} catch (Throwable e) {
				inputOutput.displayLine(e.getMessage());
			}
			if(items.get(itemNumber-1).isExit()){
				break;
			}
		}
		inputOutput.displayLine("thanks & see you more");
	}
}
