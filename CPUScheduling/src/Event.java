import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;


class  fcfsComparator implements Comparator<ProcessInfo>{ 
    
   
    public int compare(ProcessInfo p1, ProcessInfo p2) { 
        if (p1.arrivalTime >=p2.arrivalTime) 
            return 1;
        else if (p1.arrivalTime < p2.arrivalTime) 
            return -1;
        	return 0; 
        	
        	
       
        	
    
}
};

class  PriorityComparator implements Comparator<ProcessInfo>{ 
    
	   
    public int compare(ProcessInfo p1, ProcessInfo p2) { 
        if (p1.priority > p2.priority) 
            return 1; 
        else if (p1.priority < p2.priority) 
            return -1;
        	return 0;             
        } 
}

class  BurstComparator implements Comparator<ProcessInfo>{ 
    
	   
    public int compare(ProcessInfo p1, ProcessInfo p2) { 
        if (p1.burstTime >p2.burstTime) 
            return 1; 
        else if (p1.burstTime < p2.burstTime) 
            return -1;
         return 0;            
        } 
}






public class Event {
	

	
	
	private static  int size;
	private   PriorityQueue<ProcessInfo> fcfsq = new PriorityQueue<ProcessInfo>(10, new fcfsComparator());
	private   PriorityQueue<ProcessInfo> pq = new PriorityQueue<ProcessInfo>(10, new PriorityComparator());
	private   PriorityQueue<ProcessInfo> bq = new PriorityQueue<ProcessInfo>(10, new BurstComparator());
	private   PriorityQueue<ProcessInfo> pqrb = new PriorityQueue<ProcessInfo>(10, new PriorityComparator());
	private   PriorityQueue<ProcessInfo> rbArrival = new PriorityQueue<ProcessInfo>(10, new fcfsComparator());
	private   Queue<ProcessInfo> priorityq=new LinkedList<>();
	private   Queue<ProcessInfo> burstq=new LinkedList<>();
	private   Queue<ProcessInfo> rbQ=new LinkedList<>();
	public static   Vector<ProcessInfo> finalV =new Vector<>(size);
	
	
	Event()
	{
		finalV.removeAllElements();
	}
	
	
	public  void setSize(int i)
	{
		size=i;
	}
	
		
	private void fcfs(Vector v)
		{
		
		int count=0;
		for(int i=0;i<size;i++)
		{
			
			ProcessInfo p=new ProcessInfo();
			p.processName=((Vector)v.elementAt(i)).elementAt(0).toString();
			p.arrivalTime=(Double) ((Vector)v.elementAt(i)).elementAt(1);
			p.burstTime=(Double) ((Vector)v.elementAt(i)).elementAt(2);
			p.originalBurstTime=(Double) ((Vector)v.elementAt(i)).elementAt(2);
			if(((Vector)v.elementAt(i)).elementAt(3)!=null)
			p.priority=(Long) ((Vector)v.elementAt(i)).elementAt(3);
			p.timestamp=count+1;
			fcfsq.add(p);
		
			
			
			
			
			
			
		}
		
		
	
		
	
	
		}
	
	
	
	public void priority(Vector v)
	{
		fcfs(v);
		ProcessInfo p=new ProcessInfo();
		double now=fcfsq.peek().arrivalTime; 
		pq.add(fcfsq.peek()); 
		fcfsq.remove();    
		
		
		while(!pq.isEmpty()||!fcfsq.isEmpty())    
		{
			Boolean stop=false;
			 
			while(!stop && !fcfsq.isEmpty())
			{
			p=fcfsq.peek();
			if(p.arrivalTime<=now)  
			{
				pq.add(p); 
				fcfsq.remove(); 
				
			}
			
			if(p.arrivalTime>now)
				stop=true;
			}
			if (pq.isEmpty()) {pq.add(p);now=p.arrivalTime;pq.remove();}
			else {
			priorityq.add(pq.peek()); 
			now=now+pq.peek().burstTime; 
			pq.remove();  
						
		}
			}
		
		while(!priorityq.isEmpty())
		{
			finalV.add(priorityq.peek());
			priorityq.remove();
		}
		
		
		setTime();
		for(int i=0;i<finalV.size();i++)
		{
			System.out.println(finalV.elementAt(i).startTime);
			System.out.println(finalV.elementAt(i).processName);
			System.out.println(finalV.elementAt(i).finishTime);
		}
		
		
		
		
	}
	
	public  void fcfsV(Vector v)
	{
		fcfs(v);
		while(!fcfsq.isEmpty())
		{
			finalV.add(fcfsq.peek());
		    fcfsq.remove();
		}
		
		
		setTime();
	}
	
	
	public void sjf(Vector v)
	{
		fcfs(v); 
		ProcessInfo p=new ProcessInfo();
		double now=fcfsq.peek().arrivalTime; 
		bq.add(fcfsq.peek()); 
		fcfsq.remove();    
		while(!bq.isEmpty()||!fcfsq.isEmpty())    
		{
			
			Boolean stop=false;
			 
			while(!stop && !fcfsq.isEmpty())
			{
			p=fcfsq.peek();
			if(p.arrivalTime<=now)  
			{
				bq.add(p); 
				fcfsq.remove(); 
				
			}
			
			if(p.arrivalTime>now)
				stop=true;
			}
			if (bq.isEmpty()) now=p.arrivalTime;
			else {
			burstq.add(bq.peek()); 
			now=now+bq.peek().burstTime; 
			bq.remove();  
			
		}}
		
		while(!burstq.isEmpty())
		{
			finalV.add(burstq.peek());
			burstq.remove();
		}
		setTime();
	}
	
	public void roundRobin(Vector v,double qTime)
	{
		fcfs(v); //readyQueue
		Queue<ProcessInfo> readyQueue=new LinkedList<>();
		Queue<ProcessInfo> runningQueue=new LinkedList<>();
		readyQueue.add(fcfsq.peek());
		double now=fcfsq.peek().arrivalTime; //now=0
		fcfsq.remove();
		while(!readyQueue.isEmpty()||!fcfsq.isEmpty()) //p1
		{	
			ProcessInfo p=new ProcessInfo();
			if (!readyQueue.isEmpty())
			{	p=readyQueue.peek();
			runningQueue.add(p); //
			p.burstTime=p.burstTime-qTime;
			   
			readyQueue.remove();
		}
			now=now+qTime;//now=2
			while(!fcfsq.isEmpty()&&fcfsq.peek().arrivalTime<=now)
			{
				readyQueue.add(fcfsq.peek());
				fcfsq.remove();
			}
					
			
			if(p.burstTime>0)
			{
				ProcessInfo p2=new ProcessInfo();
				p2.processName=p.processName;
				p2.arrivalTime=p.arrivalTime;
				p2.originalBurstTime=p.originalBurstTime;
				p2.burstTime=p.burstTime;
				p2.finishTime=p.finishTime;
				readyQueue.add(p2);
			}
			
		}
		
		while(!runningQueue.isEmpty())
		{
			finalV.add(runningQueue.peek());
			runningQueue.remove();
		}
		
	
	
		setTime2(qTime);
		setWaitingTimeRB();	
	}
	

	
	private void setTime()
	{
		finalV.elementAt(0).startTime=finalV.elementAt(0).arrivalTime;
		finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+finalV.elementAt(0).burstTime;
		finalV.elementAt(0).waitingTime=finalV.elementAt(0).startTime-finalV.elementAt(0).arrivalTime;
		
		
		
		for(int i=1;i<finalV.size();i++)
		{
			double now=finalV.elementAt(i-1).finishTime;
			if(finalV.elementAt(i).arrivalTime<=now)
			{
				finalV.elementAt(i).startTime=finalV.elementAt(i-1).finishTime;
				finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime;
				finalV.elementAt(i).waitingTime=finalV.elementAt(i).startTime-finalV.elementAt(i).arrivalTime;
			}
			else
			{
				finalV.elementAt(i).startTime=finalV.elementAt(i).arrivalTime;
				finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime;
				finalV.elementAt(i).waitingTime=finalV.elementAt(i).startTime-finalV.elementAt(i).arrivalTime;
			}
				
				
			
				
				
		}
		
		
	}
	public void sjfPreemptive(Vector v) //hadeer
	{
	 

		double now;
		double pnow =0;
	    fcfs(v);
		
		Queue<ProcessInfo> runningQueue=new LinkedList<>();
		
		now=fcfsq.peek().arrivalTime;
		bq.add(fcfsq.peek());
		fcfsq.remove();
		
		
		while(!fcfsq.isEmpty())
		{	
			
			pnow=now;
			
			while(!fcfsq.isEmpty()&&fcfsq.peek().arrivalTime<=now)
			{
				bq.add(fcfsq.peek());
				fcfsq.remove();
			} 
			
			if(fcfsq.isEmpty())
				break;
				
			
			if(!fcfsq.isEmpty())
				now=fcfsq.peek().arrivalTime;
			ProcessInfo p=new ProcessInfo();
			if(!bq.isEmpty())
			{
			p=bq.peek();
			p.burstNow=p.burstTime;
			p.burstTime=p.burstTime-(now-pnow);
			bq.remove();
			runningQueue.add(p);
			}
			while(p.burstTime<0&&!bq.isEmpty())
			{
				if(!bq.isEmpty())
				{
				pnow=p.burstTime+now;
				p=bq.peek();
				p.burstNow=p.burstTime;
				p.burstTime=p.burstTime-(now-pnow);
				bq.remove();
				runningQueue.add(p);
				}
				
			}
							
			if(p.burstTime>0)
			{
				ProcessInfo p2=new ProcessInfo();
				p2.processName=p.processName;
				p2.arrivalTime=p.arrivalTime;
				p2.burstTime=p.burstTime;
				p2.burstNow=p.burstTime;
				p2.originalBurstTime=p.originalBurstTime;
				bq.add(p2);
			}
						
			
		}
		
		while(!bq.isEmpty())
		{
			bq.peek().exit=1;
			runningQueue.add(bq.peek());
			bq.remove();
			
		}
		
		while(!runningQueue.isEmpty())
		{
			finalV.add(runningQueue.peek());
			runningQueue.remove();
		}
		
	
	
		setTime3();
		setWaitingTimeRB();	
	}
	
	public void priorityPreemptive(Vector v)   //hadeer
	{

		double now;
		double pnow =0;
	    fcfs(v);
		
		Queue<ProcessInfo> runningQueue=new LinkedList<>();
		
		now=fcfsq.peek().arrivalTime;
		pq.add(fcfsq.peek());
		fcfsq.remove();
		
		
		while(!fcfsq.isEmpty())
		{	
			
			pnow=now;
			
			while(!fcfsq.isEmpty()&&fcfsq.peek().arrivalTime<=now)
			{
				pq.add(fcfsq.peek());
				fcfsq.remove();
			} 
			
			if(fcfsq.isEmpty())
				break;
				
			
			if(!fcfsq.isEmpty())
				now=fcfsq.peek().arrivalTime;
			
			ProcessInfo p=new ProcessInfo();
			if(!pq.isEmpty())
			{
			p=pq.peek();
			p.burstNow=p.burstTime;
			p.burstTime=p.burstTime-(now-pnow);
			pq.remove();
			runningQueue.add(p);
			}
			while(p.burstTime<0&&!pq.isEmpty())
			{
				if(!pq.isEmpty())
				{
					
				pnow=p.burstTime+now;
				p=pq.peek();
				p.burstNow=p.burstTime;
				p.burstTime=p.burstTime-(now-pnow);
				pq.remove();
				runningQueue.add(p);
				}
				
			}
							
			if(p.burstTime>0)
			{
				ProcessInfo p2=new ProcessInfo();
				p2.processName=p.processName;
				p2.burstNow=p.burstTime;
				p2.arrivalTime=p.arrivalTime;
				p2.burstTime=p.burstTime;
				p2.originalBurstTime=p.originalBurstTime;
				p2.priority=p.priority;
				pq.add(p2);
			}
						
			
		}
		
		while(!pq.isEmpty())
		{
			pq.peek().exit=1;
			runningQueue.add(pq.peek());
			pq.remove();
			
		}
		
		while(!runningQueue.isEmpty())
		{
			finalV.add(runningQueue.peek());
			runningQueue.remove();
		}
		
		setTime3();
		setWaitingTimeRB();
	}
	
	private void setTime2(double qtime)  //hadeer
	{
		double now=finalV.elementAt(0).arrivalTime;
		finalV.elementAt(0).startTime=finalV.elementAt(0).arrivalTime;
		
		if(finalV.elementAt(0).burstTime>=0) //hadeer
			finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+qtime;
		else 
			finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+finalV.elementAt(0).burstTime+qtime;
		
		if(finalV.elementAt(0).burstTime==0)  //increased
			finalV.elementAt(0).exit=now+qtime;
		else if(finalV.elementAt(0).burstTime<0)
			finalV.elementAt(0).exit=now+finalV.elementAt(0).burstTime+qtime;
		
	
		
		
		for(int i=1;i<finalV.size();i++)
		{
			now=finalV.elementAt(i-1).finishTime;
			
			if(finalV.elementAt(i).arrivalTime<=now)
			{
				finalV.elementAt(i).startTime=finalV.elementAt(i-1).finishTime;
				if(finalV.elementAt(i).burstTime>=0)
				finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+qtime;
				else 
				finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime+qtime;
				
				if(finalV.elementAt(i).burstTime==0)  //increased
					finalV.elementAt(i).exit=finalV.elementAt(i).startTime+qtime;
				else if(finalV.elementAt(i).burstTime<0)
					finalV.elementAt(i).exit=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime+qtime;
				
					
			}
			else
			{
				finalV.elementAt(i).startTime=finalV.elementAt(i).arrivalTime;
				
				    if(finalV.elementAt(i).burstTime>=0)
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+qtime;
					else 
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime+qtime;
					
				    if(finalV.elementAt(i).burstTime==0)  //increased
						finalV.elementAt(i).exit=finalV.elementAt(i).startTime+qtime;
					else if(finalV.elementAt(i).burstTime<0)
						finalV.elementAt(i).exit=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime+qtime;
				
			}				
		}
		
		
	}
	
	
	
	
	void setTime3() //hadeer
	{
		finalV.elementAt(0).startTime=finalV.elementAt(0).arrivalTime;
		if(finalV.elementAt(0).originalBurstTime==finalV.elementAt(0).burstTime)
		{
			finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+finalV.elementAt(0).burstTime;
			finalV.elementAt(0).exit=finalV.elementAt(0).finishTime;
		}
		else if(finalV.elementAt(0).burstTime<=0)
		{
			finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+finalV.elementAt(0).burstNow;
			finalV.elementAt(0).exit=finalV.elementAt(0).finishTime;
		}
		else if(finalV.elementAt(0).burstTime>0&&finalV.elementAt(0).exit!=1)
			finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+(finalV.elementAt(0).burstNow-finalV.elementAt(0).burstTime);
		else if(finalV.elementAt(0).exit==1)
		{
			finalV.elementAt(0).finishTime=finalV.elementAt(0).startTime+finalV.elementAt(0).burstTime;
			finalV.elementAt(0).exit=finalV.elementAt(0).finishTime;
		}	
		for(int i=1;i<finalV.size();i++)
		{
			double now=finalV.elementAt(i-1).finishTime;
			if(finalV.elementAt(i).arrivalTime<=now)
			{
				finalV.elementAt(i).startTime=finalV.elementAt(i-1).finishTime;
				if(finalV.elementAt(i).originalBurstTime==finalV.elementAt(i).burstTime)
				{
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime;
					finalV.elementAt(i).exit=finalV.elementAt(i).finishTime;
					
				}
				else if(finalV.elementAt(i).burstTime<=0)
				{
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstNow;
					finalV.elementAt(i).exit=finalV.elementAt(i).finishTime;
				}
				else if(finalV.elementAt(i).burstTime>0&&finalV.elementAt(i).exit!=1)
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+(finalV.elementAt(i).burstNow-finalV.elementAt(i).burstTime);
				else if(finalV.elementAt(i).exit==1)
				{
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime;
					finalV.elementAt(i).exit=finalV.elementAt(i).finishTime;
				}
								
			}
			else
			{
				finalV.elementAt(i).startTime=finalV.elementAt(i).arrivalTime;
				if(finalV.elementAt(i).originalBurstTime==finalV.elementAt(i).burstTime)
				{
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime;
					finalV.elementAt(i).exit=finalV.elementAt(i).finishTime;
				}
				else if(finalV.elementAt(i).burstTime<=0)
				{
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstNow;
					finalV.elementAt(i).exit=finalV.elementAt(i).finishTime;
				}
				else if(finalV.elementAt(i).burstTime>0&&finalV.elementAt(i).exit!=1)
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+(finalV.elementAt(i).burstNow-finalV.elementAt(i).burstTime);
				else if(finalV.elementAt(i).exit==1)
				{
					finalV.elementAt(i).finishTime=finalV.elementAt(i).startTime+finalV.elementAt(i).burstTime;
					finalV.elementAt(i).exit=finalV.elementAt(i).finishTime;
				}
			
			}
			
		}
	}
	
	public double getWaitingTime()
	{
		double waitingTime=0;
		double avaregeWaitingTime;
		for(int i=0;i<finalV.size();i++)
		{
			if(finalV.elementAt(i).waitingTime!=-1)
			waitingTime=waitingTime+finalV.elementAt(i).waitingTime;
		}
		
		avaregeWaitingTime=waitingTime/size;
		return avaregeWaitingTime;
	}
	
	public void setWaitingTimeRB()
	{
		for(int i=0;i<finalV.size();i++)
		{
			if(finalV.elementAt(i).exit!=-1)
			{
				finalV.elementAt(i).waitingTime=finalV.elementAt(i).exit-finalV.elementAt(i).arrivalTime-finalV.elementAt(i).originalBurstTime;
			}
		}
	}
	 
	
	
	

};
