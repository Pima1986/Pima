package telran.view;

public class ExitItem implements Item {

	@Override
	public String displayedName() {
		
		return "Exit";
	}

	@Override
	public void action() {
		

	}
	@Override
	public boolean isExit(){
		return true;
	}

}
