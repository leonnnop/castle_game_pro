package castle;

import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private boolean gameOver = false;
    private Role hero = new Role(100,10,null);
    private HashMap<String, Handler> handlers = new HashMap<String,Handler>();
    private HashMap<String, Role> monsters = new HashMap<String,Role>();
    Room outside, lobby, pub, study, bedroom,rooftop,kitchen,secretRoom,bossRoom;
    
    public Game() 
    {
    	handlers.put("go", new HandlerGo());
    	handlers.put("bye", new HandlerBye());
    	handlers.put("help", new HandlerHelp());
    	handlers.put("map", new HandlerMap());
    	handlers.put("role", new HandlerRole());
    	
        createRooms();
    }

    private void createRooms()
    {
      
        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        monsters.put("大堂", new Role(10,20,lobby));
        pub = new Room("小酒吧");
        monsters.put("小酒吧", new Role(10,20,pub));
        study = new Room("书房");
        monsters.put("书房", new Role(10,20,study));
        bedroom = new Room("卧室");
        monsters.put("卧室", new Role(10,20,bedroom));
        rooftop = new Room("屋顶");
        monsters.put("屋顶", new Role(10,20,rooftop));
        kitchen = new Room("厨房");
        monsters.put("厨房", new Role(10,20,kitchen));
        secretRoom = new Room("密室");
        monsters.put("密室", new Role(10,20,secretRoom));
        bossRoom = new Room("boss房间");
        monsters.put("boss房间", new Role(10,20,bossRoom));
        
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
        boolean isInBossRoom = false;

        if (nextRoom == null) {
            System.out.println("那里没有门！");
        }
        else if(nextRoom.equals(kitchen)){
            currentRoom = nextRoom;
            currentRoom.setVisited();
        	hero.increaseHP(100);
        	System.out.println("恭喜你找到了厨房！这里有绝世灵药一瓶，只有拿到他你才能打败boss哦！");
        	showPrompt();
        }
        else {
            currentRoom = nextRoom;
            currentRoom.setVisited();
            if(monsters.containsKey(currentRoom.getDescription())){
            	if(currentRoom.equals(bossRoom)){
            		System.out.println("这个房间有一只大BOSS！准备展开殊死搏斗吧！");
            		isInBossRoom = true;
            	}
            	else{
            		System.out.println("这个房间有一只怪物。");
            	}
            	
                if(monsters.get(currentRoom.getDescription()).isAlive()){
                	hero.decreaseHP(monsters.get(currentRoom.getDescription()).getATK());
                	monsters.get(currentRoom.getDescription()).decreaseHP(hero.getATK());
                	if(hero.isAlive()){
                		System.out.println("恭喜你打败了他。");
                		if(isInBossRoom){
                			System.out.println("最终BOSS被成功击杀！");
                			System.out.println("You Win.");
                			gameOver = true;
                		}
                		else{
                			showPrompt();
                		}
                	}
                	else{
                		System.out.println("很遗憾你的血槽空了，" + "你死在了" + currentRoom.getDescription() + "建议下次时刻role一下看看血量。");
                		System.out.println("Game Over.");
                		System.out.println("You Lose.");
                		gameOver = true;
                	}
                }
                else{
                	System.out.println("他已经被打败过了。");
                    showPrompt();
                }
            }
            else{
            	showPrompt();
            }
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
    			if(gameOver){
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
	
	public class HandlerRole extends Handler {

		@Override
		public void doCmd(String word) {
			// TODO Auto-generated method stub
			System.out.println("当前角色的血量为:" + hero.getHP());
		}
	}

}
