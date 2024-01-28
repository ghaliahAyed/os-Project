
public class Partition {
	private String partitionStatus;
	private String processID;
	private int partitionSize;
	private int startingAddress;
	private int endingAddress;
	private int processSize;
	private int fragmentationSize;

	public Partition(int partitionSize, int startingAddress, int endingAddress) {

		this.partitionSize = partitionSize;
		this.startingAddress = startingAddress;
		this.endingAddress = endingAddress;

		processSize = -1;
		fragmentationSize = -1;
		partitionStatus = "Free";
		processID = "Null";

	}

	public void calculateFragmentationSize() {

		if (!processID.equalsIgnoreCase("Null") && partitionStatus.equalsIgnoreCase("allocated"))
			setFragmentationSize(partitionSize - processSize);

	}

	public void setDefault() {
		processSize = -1;
		fragmentationSize = -1;
		partitionStatus = "Free";
		processID = "Null";

	}
	public void printStatus() {
		System.out.println(" Partition Status: "+ partitionStatus);
		System.out.println(" Size of a Partition: " + partitionSize);
		System.out.println(" Starting Address: " +  startingAddress + ", End Address: " + endingAddress );
		System.out.println(" Process Currently Allocated: " + processID );
		System.out.println(" Fragmentation Size: " + fragmentationSize);
		
	}
	public String getPartitionStatus() {
		return partitionStatus;
	}

	public void setPartitionStatus(String partitionStatus) {
		this.partitionStatus = partitionStatus;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
		partitionStatus = "allocated";
	}

	public int getPartitionSize() {
		return partitionSize;
	}

	public void setPartitionSize(int partitionSize) {
		this.partitionSize = partitionSize;
	
	}

	public int getStartingAddress() {
		return startingAddress;
	}

	public void setStartingAddress(int startingAddress) {
		this.startingAddress = startingAddress;
	}

	public int getEndingAddress() {
		return endingAddress;
	}

	public void setEndingAddress(int endingAddress) {
		this.endingAddress = endingAddress;
	}

	public int getProcessSize() {
		return processSize;
	}

	public void setProcessSize(int processSize) {
		this.processSize = processSize;
		calculateFragmentationSize();
	}

	public int getFragmentationSize() {
		return fragmentationSize;
	}

	public void setFragmentationSize(int fragmentationSize) {
		this.fragmentationSize = fragmentationSize;
	}

}
