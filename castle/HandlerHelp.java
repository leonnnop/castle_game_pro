package castle;

public class HandlerHelp extends Handler {

	public HandlerHelp(Game game){
		super(game);
	}
	
	@Override
	public void doCmd(String word) {
        System.out.print("��·������������������У�go bye help");
        System.out.println("\t�磺go east");
	}

}
