import java.util.Random;


/**
 * This class implements a packet
 * @author MNDsouza
 *
 */
public class Packet {
	
	//Frame inter arrival time
	private int arrivalInterval;
	
	//Frame arrival time
	private long arrivalTime;
	
	//Time at which packet gets access to channel
	private long accessTime;
	
	//Time at which the packet reaches destination
	private long completionTime;
	
	//Counter for the current time of packet (ie:packets current state)
	private long scheduledTime;
	
	//Number of times packet has collided
	private int attemptNumber = 1;
	

	private static Random randomGen = new Random();

	public int getArrivalInterval() {
		return arrivalInterval;
	}

	public void setArrivalInterval(int arrivalInterval) {
		this.arrivalInterval = arrivalInterval;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public long getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(long accessTime) {
		this.accessTime = accessTime;
	}

	public long getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(long completionTime) {
		this.completionTime = completionTime;
	}

	public long getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(long scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public void binaryBackOffAlgorithm() {
		
		//maximum slot number during current collison 
		//nextInt(i) func generates random num betw 0 and i-1
		int maxSlots = Math.min((int) Math.pow(2, attemptNumber), 7 + 1);
		
		//number of slots the packet will wait
		int numSlots = randomGen.nextInt(maxSlots);
		
		//calculating and using the wait time
		int waitTime = (int) (numSlots * Constants.FRAME_SLOT.getConstants());
		setScheduledTime(getScheduledTime() + waitTime);
		attemptNumber += 1;

	}

}
