
import java.util.Random;



/**
 * This class models the Network layer and generates packets according to poisson process with mean = 0.5/ time slot
 * 
 * @author MNDsouza
 *
 */
public class NetworkLayer {
	private long currentTime = 0;
	private float mean = 1000;
	
	//generates uniformly generated random varables  
	private static Random randomGen = new Random();

	//This function generates and returns the next packet 
	public Packet nextPacket() {
		Packet packet = new Packet();
		float u;
		//drop u if u is 0
		while ((u = randomGen.nextFloat()) == 0)
			;
		//arrival interval is a exponentaily distributed random varaible with mean 1000. This is given by formula -mean*ln(u) where u is uniform RV
		packet.setArrivalInterval(-(int) (mean * Math.log(u)));
		//advance cumulative time by frame interarrival time
		currentTime += packet.getArrivalInterval();
		packet.setArrivalTime(currentTime);
		return packet;

	}
	
	

	
}
