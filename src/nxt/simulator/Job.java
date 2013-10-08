package nxt.simulator;

import programs.LejosCode;


public class Job implements Runnable {
	
	private EnvironmentTest env;
	
    public EnvironmentTest getEnv() {
		return env;
	}

	public void setEnv(EnvironmentTest env) {
		this.env = env;
	}
	
	public Job(EnvironmentTest env) {
		setEnv(env);
	}

    public void run() {
		try {
			new LejosCode();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
    }


	
}
