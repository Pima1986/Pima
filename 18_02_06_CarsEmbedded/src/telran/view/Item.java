package telran.view;

public interface Item {
String displayedName();
void action();
default boolean isExit(){
	return false;
}
}
