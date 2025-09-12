import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.WritableComparable;


public class DeathPMPair implements WritableComparable<DeathPMPair>
{  
	
	public DoubleWritable deathRate;
	public DoubleWritable PM25Average;

	public  DeathPMPair(){
		this.deathRate = new DoubleWritable();
		this.PM25Average = new DoubleWritable();
	}
	
	public DeathPMPair(DoubleWritable deathRate, DoubleWritable PM25Average)
	{
		this.deathRate = deathRate;
		this.PM25Average = PM25Average;
	}
	public DeathPMPair(double deathRate, double PM25Average)
	{
		this.deathRate = new DoubleWritable(deathRate);
		this.PM25Average = new DoubleWritable(PM25Average);
	}
	
	public DoubleWritable getDeathRate()
	{
		return deathRate;
	}

	public DoubleWritable getPMLevels()
	{
		return PM25Average;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = hash*31 + (int)(deathRate.get()*1000);
		hash = hash*31 + (int)PM25Average.get();
	    	return hash;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
	    if(obj instanceof DeathPMPair){
	        DeathPMPair dpp = (DeathPMPair)obj;
	        return deathRate.equals(dpp.getDeathRate())&&PM25Average.equals(dpp.getPMLevels());
	    }
	    return false;
	}
	
	@Override
	public String toString() {
	    return deathRate+","+PM25Average;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException 
	{
	    deathRate.readFields(in);
	    PM25Average.readFields(in);
	}
	
	@Override
	public void write(DataOutput out) throws IOException
	{
	    deathRate.write(out);
	    PM25Average.write(out);
	}
	
	@Override
	public int compareTo(DeathPMPair dpp)
	{
	    return PM25Average.compareTo(dpp.getPMLevels());
	}
	
}
