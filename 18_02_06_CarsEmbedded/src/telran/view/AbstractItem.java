package telran.view;

public abstract class AbstractItem implements Item{
	protected InputOutput inputOutput;

	public AbstractItem(InputOutput inputOutput) {
		super();
		this.inputOutput = inputOutput;
	}
	
}
