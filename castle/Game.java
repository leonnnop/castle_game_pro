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
        createRooms();
    }

    private void createRooms()
    {
        Room outside, lobby, pub, study, bedroom;
      
        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        pub = new Room("小酒吧");
        study = new Room("书房");
        bedroom = new Room("卧室");
        
        //	初始化房间的出口
        outside.setExit("east",lobby);
        outside.setExit("south",study);
        outside.setExit("west",pub);
        lobby.setExit("west", outside);
        pub.setExit("east", outside);
        study.setExit("north",outside);
        study.setExit("east",bedroom);
        bedroom.setExit("west", study);
        pub.setExit("down", lobby);

        currentRoom = outside;  //	从城堡门外开始
    }

    private void printWelcome() {
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
            showPrompt();
        }
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

}
