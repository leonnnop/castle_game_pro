package castle;

public class HandlerHelp extends Handler {

	public HandlerHelp(){
	}
	
	@Override
	public void doCmd(String word) {
        System.out.print("迷路了吗？你可以做的命令有：go bye help map");
        System.out.println("map用于查看已经走过的屋子的信息。");
        System.out.println("role用于查看当前角色的血量。");
        System.out.println("go xxx用于前往你想去的地方。");
        System.out.println("\t如：go east");
	}

}
