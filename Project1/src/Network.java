import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This node represents a network with 2 nodes A & B where only A transmits. The
 * 2 nodes are connected by a bus. CSMA/CD has been implemented
 * 
 * @author MNDsouza
 * 
 */
public class Network {

	// The 2 nodes represent A & B respectively
	private Node nodes[] = new Node[2];

	// This is the current time for the BUs of the network
	private long currentTime = 0;

	// This function creates the 2 node objects & begins transmission
	public void transmit() {
		nodes[0] = new Node();
		nodes[1] = new Node();

		nodes[0].getNextPacket(currentTime);
		nodes[1].getNextPacket(currentTime);

		// begin transmission
		simulateTransmission();
	}

	// This function simulates transmission for total of 60 sec ie: 6 * 10^6
		// time slots(micro secs)
	private void simulateTransmission() {
		Packet packets[] = new Packet[2];

		int smaller;
		int larger;
		int timeDiff;

		while (currentTime <= 60000000) {
			
			// fetch the next packet scheduled for transmission(ie : packet at
			// head of the queue
			packets[0] = nodes[0].getCurrentPacket(currentTime);
			packets[1] = nodes[1].getCurrentPacket(currentTime);

			//determine which node has earlier packet
			smaller = (packets[0].getScheduledTime() < packets[1]
					.getScheduledTime()) ? 0 : 1;
			larger = 1 - smaller;
			timeDiff = (int) (packets[larger].getScheduledTime() - packets[smaller]
					.getScheduledTime());

			//if difference in time is greater than 10 then no collision occurs. We increment the bus clock time by 90, mark packet as completed and report success.
			//also next packet in queue is fetched
			if (timeDiff > 10) {
				currentTime = 90 + packets[smaller].getScheduledTime();
				packets[smaller].setCompletionTime(currentTime);
				nodes[smaller].reportSuccess(packets[smaller]);

				nodes[smaller].getNextPacket(currentTime);
			} else {
				
				currentTime = 10 - timeDiff
						+ packets[smaller].getScheduledTime();
				
				//accounting for the time required for the nodes to realize that collision has occured
				packets[smaller].setScheduledTime(packets[smaller]
						.getScheduledTime() + timeDiff + 10 );
				packets[larger].setScheduledTime(packets[larger]
						.getScheduledTime() - timeDiff + 10);
				
								
				//backoff both the packtes
				packets[smaller].binaryBackOffAlgorithm();
				packets[larger].binaryBackOffAlgorithm();
				
				

			}

		}

	}
	
	
	// This function writes the interarrival Time , queue Delay and access Delay
	// to file for all packets recieved by a particular node
	private void generateResults(Node node, String nodeName) {

		try {
			FileWriter fstream = new FileWriter(nodeName + ".dat");
			BufferedWriter out = new BufferedWriter(fstream);
			float cumulativeAccessDelay = 0;
			float cumulativeQueueDelay = 0;
			
			float cumulativeEndToEndDelay = 0;
			float cumulativeInterrarivalTime = 0;
			
			for (Packet packet : node.getSentPackets()) {
				int accessDelay = (int) (packet.getCompletionTime() - packet
						.getAccessTime());
				int queueDelay = (int) (packet.getCompletionTime() - packet
						.getArrivalTime()-90);
								
				out.write(Integer.toString(packet.getArrivalInterval()) + "\t"
						+ queueDelay + "\t" + accessDelay + "\n");
				cumulativeInterrarivalTime += packet.getArrivalInterval();
				cumulativeAccessDelay += accessDelay;
				cumulativeQueueDelay += queueDelay;
				cumulativeEndToEndDelay += (packet.getCompletionTime() - packet
						.getArrivalTime());
			}
			
			out.close();
			fstream = new FileWriter(nodeName + "_data.txt");
			out = new BufferedWriter(fstream);
			/*cumulativeAccessDelay /= node.getSentPackets().size();
			cumulativeQueueDelay /= node.getSentPackets().size();
			System.out.println("Avg Queue Delay: "
					+cumulativeQueueDelay);
			System.out.println("Avg Acess Delay: "
					+ cumulativeAccessDelay);
			System.out.println("Total Packets sent :" + node.getSentPackets().size());
				*/
			float meanArrivalTime = cumulativeInterrarivalTime
					/ node.getSentPackets().size();
			float meanAccessDelay = cumulativeAccessDelay
					/ node.getSentPackets().size();
			float meanQueueDelay = cumulativeQueueDelay
					/ node.getSentPackets().size();
			float meanEndToEndDelay = cumulativeEndToEndDelay
					/ node.getSentPackets().size();
			float throughput = (float)8000 * 1000000 / meanEndToEndDelay;
			
			out.write(Integer.toString(node.getSentPackets().size()));
			out.newLine();
			out.write(Float.toString(meanArrivalTime));
			out.newLine();
			out.write(Float.toString(meanQueueDelay));
			out.newLine();
			out.write(Float.toString(meanAccessDelay));
			out.newLine();
			out.write(Float.toString(throughput));
			out.newLine();
			out.write(Long.toString(currentTime));
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		Network network = new Network();
		network.transmit();

		network.generateResults(network.nodes[0], "A");
		network.generateResults(network.nodes[1], "B");

	}
}
