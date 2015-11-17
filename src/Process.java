import java.util.ArrayList;

//Process class so we can create separate process objects
public class Process 
{
	private ArrayList<Integer> allWaitTimes = new ArrayList<Integer>();
	private ArrayList<Integer> allStopTimes = new ArrayList<Integer>();
	private int burstTime;
	private int totalWaitingTime = 0;
	private String name;
	
	public int getBurstTime() 
	{
		return burstTime;
	}
	
	public void setBurstTime(int burstTime) 
	{
		this.burstTime = burstTime;
	}
	
	public void deincrementBurstTime(int burstTime) 
	{
		this.burstTime -= burstTime;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}

	public int getWaitTimes(int index) 
	{
		return allWaitTimes.get(index);
	}
	
	public void incrementTotalWaitTime(int waitTime)
	{
		this.totalWaitingTime += waitTime;
	}
	
	public int getTotalWaitTime()
	{
		return this.totalWaitingTime;
	}
	
	public int getWaitTimeAmount() 
	{
		return allWaitTimes.size();
	}

	public void setWaitTimes(int waitTime) 
	{
		this.allWaitTimes.add(waitTime);
	}

	public int getStopTimes(int index) 
	{
		return allStopTimes.get(index);
	}

	public void setStopTimes(int stopTime) 
	{
		this.allStopTimes.add(stopTime);
	}
}
