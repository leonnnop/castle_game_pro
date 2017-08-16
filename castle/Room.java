package castle;

import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

public class Room {
	private Map map = new Map();
	
    private String description;
    private HashMap<String, Room> exits = new HashMap<String,Room>();
    private boolean visited = false;

    public Room(String description) 
    {
        this.description = description;
    }
    
    public boolean isVisited(){
    	return this.visited;
    }
    
    public void setVisited(){
    	visited = true;
    }

    public void setExit(String dir,Room room) 
    {
    	exits.put(dir, room);
    }
    
    public String getDescription(){
    	return description;
    }

    @Override
    public String toString()
    {
        return description;
    }
    
    public String getExitDesc(){
    	StringBuffer sb = new StringBuffer();
    	
    	for(String dir : exits.keySet()){
    		sb.append(dir);
    		sb.append(' ');
    	}
    	return sb.toString();
    }
    
    public String getVisitedDesc(){
    	StringBuffer sb = new StringBuffer();
    	
    	for(String dir : exits.keySet()){
    		if((exits.get(dir)).isVisited()){
        		sb.append(dir);
    			sb.append("是");
    			sb.append((exits.get(dir)).getDescription());
        		sb.append(' ');
    		}
    	}
    	if(sb.length()==0){
    		sb.append("啥也没有，很可惜，这里的出口你哪个都没去过。");
    	}
    	return sb.toString();
    }
    
    public Room getExit(String dir){	
    	return exits.get(dir);    
    }
    
    public void showMap(){	
    	map.showRoomDetail();    
    }
    
    public class Map {

    	public void showRoomDetail(){
            System.out.print("已知的出口有: ");
            System.out.println(getVisitedDesc());
    	}
    	
    }
}
