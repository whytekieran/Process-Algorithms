import java.util.*;

public class ProgramRunner 
{
	static Scanner console = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		//Global Variables
		String[] processNames;
		int[] processBurstTimes;
		int[] waitTimes;
		int processAmount;
		int i = 0;
		int j = 0;
		int choice;
		double sum = 0;
		double avg = 0;
		
		//User decides which type
		System.out.println("Enter a number: (1=FCFS) (2=SJF) (3=RR)");
		choice = console.nextInt();
		
		switch(choice)
		{
			//If its FCFS or SJF (No process class used for these two)
			case 1:
			case 2:
				
				//How many process
				System.out.println("How many process are you scheduling? ");
				processAmount = console.nextInt();
				
				//Set up arrays for nessesary values
				processNames = new String[processAmount]; 
				processBurstTimes = new int[processAmount];
				waitTimes = new int[processAmount];
				
				//User enter names and burst times for each process
				for(i = 0; i < processAmount; ++i)
				{
					System.out.println("Enter Process Name: ");
					processNames[i] = console.next();
					
					System.out.println("Enter process " +processNames[i]+ " burst time: ");
					processBurstTimes[i] = console.nextInt();
				}
				
				//If user choose SJF we sort the array
				if(choice == 2)//If SJF
				{
					Arrays.sort(processBurstTimes); //Sort the array so now the smallest burst time is in first index and so on.
				}
				
				for(i = 0; i < processAmount; ++i)
				{
					if(i == 0)//First process in is always wait time = 0
					{
						waitTimes[i] = 0;
					}
					else if(i > 0)//otherwise wait time is all burst times added together except for burst time of the current process.
					{
						for(j = 0; j < i; ++j)
						{
							waitTimes[i] += processBurstTimes[j];
						}
					}
				}
				
				for(i = 0; i < processAmount; ++i)//sum the wait times
				{
				   sum += waitTimes[i];
				}
				
				avg = sum / processAmount;//get wait time average
				
				//Two different outputs depending on format user choose
				if(choice == 1)//FCFS
				{
					for(i = 0; i < processAmount; ++i)
					{
						System.out.println("Process: " +processNames[i]+ " Wait Time: " +waitTimes[i]);
					}
					
					System.out.println("Average Wait Time FCFS: " +avg);
				}
				else//SJF
				{
					for(i = 0; i < processAmount; ++i)
					{
						System.out.println("Process: " +i+ " Wait Time: " +waitTimes[i]);
					}
					
					System.out.println("Average Wait Time SJF: " +avg);
				}
				break;
			case 3://RR
				int index = 0;
				int currTime = 0;
				int quantumTime;
				int burstTime;
				String name;
				Process p;
				ArrayList<Process> processArrayList = new ArrayList<Process>(); //Create an array list of processes
				
				//How many processes and the quantum time should be entered here
			    System.out.println("How many process are you scheduling? ");
				processAmount = console.nextInt();
				
				System.out.println("Enter Quantum time? ");
				quantumTime = console.nextInt();

				//create a specific amount of process objects and add them all to the process array list
				for(i = 0; i < processAmount; ++i) 
				{
					p = new Process();
					processArrayList.add(p);
				}
				
				//Grab each process from the list then give it info provided by the user.
				for(i = 0; i < processAmount; ++i)
				{
					p = processArrayList.get(i);
					
					System.out.println("Enter Process Name: ");
					name = console.next();
					p.setName(name);
					
					System.out.println("Enter process " +p.getName()+ " burst time: ");
					burstTime = console.nextInt();
					p.setBurstTime(burstTime);
				}
				
				//Using the algorithm you provided with some added parts (Yes i think it works better than my original monster code :P)
				while(sum(processArrayList) != 0)//the sum function is defined below this main() method
				{
					p = processArrayList.get(index);//Gets the current process
					
					if(p.getBurstTime() > 0)//Only include a process that still has burst time remaining
					{
						p.setWaitTimes(currTime);
						currTime += Math.min(quantumTime, p.getBurstTime());
						p.setStopTimes(currTime);
						p.deincrementBurstTime(Math.min(quantumTime, p.getBurstTime()));
						System.out.println("Name: "+p.getName()+" WaitTime: "+p.getWaitTimes(j)+" LastStop: "+p.getStopTimes(j)+
								" TimeRemaining: "+p.getBurstTime());
					}	
					++index;
						
				    if(index == processAmount)//Makes sure we dont get out of bound exceptions by resetting the index.
				    {
						index = 0;
						++j;
					}
				}
				
				//Calculates the total waiting time of each process
				for(i = 0; i < processAmount; ++i)//loop over each process
				{
					p = processArrayList.get(i);
					
					for(j = 0; j < p.getWaitTimeAmount(); ++j)//loop over each processes wait/stop times and calculate total wait time
					{
						if(i == 0 && j == 0)//first process first time in
						{
							p.incrementTotalWaitTime(p.getWaitTimes(j));
						}
						else if(i == 0 && j > 0)//first process 2nd time or greater in
						{
							p.incrementTotalWaitTime(p.getWaitTimes(j) - p.getStopTimes(j - 1));
						}
						if(i > 0 && j == 0)//not the first process in but first time in
						{
							p.incrementTotalWaitTime(p.getWaitTimes(j));
						}
						else if(i > 0 && j > 0)//not first process in and not first time in
						{
							p.incrementTotalWaitTime(p.getWaitTimes(j) - p.getStopTimes(j - 1));
						}
					}
				}
				
				//Sum each processes total wait time
				for(i = 0; i < processAmount; ++i)
				{
					p = processArrayList.get(i);
					sum += p.getTotalWaitTime();
				}
				
				//Get the average and output :)
				avg = sum / processAmount;
				System.out.println("Avg: "+avg);
				break;
		}//end switch
	}//end main
	
	//Sums the burst times of all the processes in the process array list
	public static int sum(ArrayList<Process> array)
	{
		int i;
		int sum = 0;
		
		for(i = 0; i < array.size(); ++i)
		{
			Process p = array.get(i);
			sum += p.getBurstTime();
		}

		return sum;
	}
}//end class
