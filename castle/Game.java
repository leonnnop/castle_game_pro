package castle;

import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private HashMap<String, Handler> handlers = new HashMap<String,Handler>();    
    public Game() 
    {
    	handlers.put("go", new HandlerGo());
    	handlers.put("bye", new HandlerBye());
    	handlers.put("help", new HandlerHelp());
    	handlers.put("map", new HandlerMap());
    	//handlers.put("role", new HandlerRole());
    	
        createRooms();
    }

    private void createRooms()
    {
        Room outside, lobby, pub, study, bedroom,rooftop,kitchen,secretRoom,bossRoom;
      
        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        pub = new Room("小酒吧");
        study = new Room("书房");
        bedroom = new Room("卧室");
        rooftop = new Room("屋顶");
        kitchen = new Room("厨房");
        secretRoom = new Room("密室");
        bossRoom = new Room("boss房间");
        
        //	初始化房间的出口
        outside.setExit("east",lobby);
        outside.setExit("south",study);
        outside.setExit("west",pub);
        lobby.setExit("west", outside);
        pub.setExit("east", outside);
        study.setExit("north",outside);
        study.setExit("east",bedroom);
        bedroom.setExit("west", study);
        pub.setExit("up", rooftop);
        rooftop.setExit("down", pub);
        lobby.setExit("north", kitchen);
        kitchen.setExit("south", lobby);
        bedroom.setExit("up", bossRoom);
        bossRoom.setExit("down", bedroom);
        bedroom.setExit("south", secretRoom);
        secretRoom.setExit("north", bedroom);

        currentRoom = outside;  //	从城堡门外开始
        currentRoom.setVisited();
    }

    private void printWelcome() {
    	System.out.println("欢迎来到城堡pro，这是一个枯燥的打怪游戏。");
    	System.out.println("你可以多次尝试，直到找到正确且唯一的打败boss的方法。（如果你找得到boss的话hhh）");
    	System.out.println("祝你好运。");
    	System.out.println("提示：你可以用help命令，查看游戏的具体玩法。");
    	System.out.println("p.s.这只是一个作业，所以地图非常小，每种模块仅展示基本功能。:)");
    	System.out.println("");
    	showPrompt();
    }

    public void goRoom(String direction) 
    {
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("那里没有门！");
        }
        else {
            currentRoom = nextRoom;
            currentRoom.setVisited();
            showPrompt();
        }
    }
    
    public void showMap(){
    	currentRoom.showMap();
    }
	
    public void showPrompt(){
        System.out.println("你在" + currentRoom);
        System.out.print("出口有: ");
        System.out.println(currentRoom.getExitDesc());
    }
    
    public void play(){
		Scanner in = new Scanner(System.in);
        while ( true ) {
    		String line = in.nextLine();
    		String[] words = line.split(" ");
    		Handler handler = handlers.get(words[0]);
    		
    		String value = "";
    		if(words.length > 1){
    			value = words[1];
    		}
    		if(handler!=null){
    			handler.doCmd(value);
    			if(handler.isBye()){
    				break;
    			}
    		}
        }
        in.close();
    }
    
	public static void main(String[] args) {
		Game game = new Game();
		game.printWelcome();
		game.play();
        System.out.println("感谢您的光临。再见！");
	}
	
	public class HandlerGo extends Handler {

		public HandlerGo(){
		}
		
		@Override
		public void doCmd(String word) {
			goRoom(word);
		}

	}
	
	public class HandlerMap extends Handler {

		@Override
		public void doCmd(String word) {
			// TODO Auto-generated method stub
			showMap();
			
		}
	}

}
