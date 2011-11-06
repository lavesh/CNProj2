import java.util.ArrayList;
import java.util.List;



/**
 * This class simulates a Node on the network.
 * @author MNDsouza
 *
 */
public class Node {
	
	private NetworkLayer networkLayer = new NetworkLayer();
	private Packet currentPacket;
	private List<Packet> sentPackets = new ArrayList<Packet>();
	
	
	

	
	
	//Add packet to list of packets sucessfully received by node
	public void reportSuccess(Packet packet){
		sentPackets.add(packet);
	}
	
	//Get next packet from the queue of this node and set it as current packet(ie:packet at head of queue)
	public void getNextPacket(long currentTime){
		Packet packet = networkLayer.nextPacket();
		currentPacket = packet;
		
		//set access time (time at which packet reaches head of queue to max(current time,time at which packet arrived from network layer)
		long accessTime = (currentTime>packet.getArrivalTime()?currentTime:packet.getArrivalTime());
		
		packet.setAccessTime(accessTime);
		
		//time at which packet is scheduled to be transmitted by bus
		packet.setScheduledTime(accessTime);			
	}
	
	//This function is called whenever the packet is next scheduled by bus to be transmitted
	public Packet getCurrentPacket(long currentTime){
		
		long scheduleTime = (currentTime>currentPacket.getScheduledTime()?currentTime:currentPacket.getScheduledTime());
		
		currentPacket.setScheduledTime(scheduleTime);
		return currentPacket;
	}
	
	//returns list of packets recieved by node
	public List<Packet> getSentPackets(){
		return sentPackets;
	}
	

	
}
