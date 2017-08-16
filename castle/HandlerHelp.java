package castle;

public class HandlerHelp extends Handler {

	public HandlerHelp(){
	}
	
	@Override
	public void doCmd(String word) {
        System.out.print("迷路了吗？你可以做的命令有：go bye help");
        System.out.println("\t如：go east");
	}

}
