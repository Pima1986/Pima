package telran.view;

import java.util.ArrayList;

public class MenuWithExit extends Menu {

	public MenuWithExit(ArrayList<Item> items, InputOutput inputOutput) {
		super(items, inputOutput);
		items.add(new ExitItem());
	}

}
