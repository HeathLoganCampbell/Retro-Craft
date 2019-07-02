package com.craftclassic.play.schedule;

public class TaskEntry implements Runnable
{
	//Negative stats of period
	public static final int NO_REPEAT = -1;
	public static final int CANCELLED = -2;
	public static final int PROCESSING = -3;
	public static final int DONE = -4;
	
	
	private volatile TaskEntry next = null;
    private final int id;
    private long nextRun;
    private final Runnable task;
    private volatile long period;
	
    public TaskEntry(final int id, final Runnable task, final long period) 
    {
        this.task = task;
        this.id = id;
        this.period = period;
    }
    
	@Override
	public void run()
	{
		this.task.run();
	}
}
