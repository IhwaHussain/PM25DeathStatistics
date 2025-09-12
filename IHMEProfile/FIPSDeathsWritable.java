import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.WritableComparable;


public class FIPSDeathsWritable implements WritableComparable<FIPSDeathsWritable>
{  
  	public IntWritable stateFIPS;
	public IntWritable countyFIPS;
	public DoubleWritable deathRate;
	
	public  FIPSDeathsWritable(){
	    	this.stateFIPS = new IntWritable();
	    	this.countyFIPS = new IntWritable();
		this.deathRate = new DoubleWritable();
	}
	
	public FIPSDeathsWritable(IntWritable stateFIPS, IntWritable countyFIPS, DoubleWritable deathRate)
	{
		this.stateFIPS = stateFIPS;
		this.countyFIPS = countyFIPS;
		this.deathRate = deathRate;
	}
	public FIPSDeathsWritable(int stateFIPS, int countyFIPS, double deathRate)
	{
		this.stateFIPS = new IntWritable(stateFIPS);
		this.countyFIPS = new IntWritable(countyFIPS);
		this.deathRate = new DoubleWritable(deathRate);
	}
	
	public IntWritable getState()
	{
		return stateFIPS;
	}
	
	public IntWritable getCounty()
	{
		return countyFIPS;
	}

	public DoubleWritable getDeathRate()
	{
		return deathRate;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = hash*31 + stateFIPS.get();
		hash = hash*31 + countyFIPS.get();
	    	return hash;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
	    if(obj instanceof FIPSDeathsWritable){
	        FIPSDeathsWritable fdw = (FIPSDeathsWritable)obj;
	        return stateFIPS.equals(fdw.getState())&&countyFIPS.equals(fdw.getCounty());
	    }
	    return false;
	}
	
	@Override
	public String toString() {
	    return stateFIPS+","+countyFIPS+","+deathRate;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException 
	{
	    stateFIPS.readFields(in);
	    countyFIPS.readFields(in);
	    deathRate.readFields(in);
	}
	
	@Override
	public void write(DataOutput out) throws IOException
	{
	    stateFIPS.write(out);
	    countyFIPS.write(out);
	    deathRate.write(out);
	}
	
	@Override
	public int compareTo(FIPSDeathsWritable fdw)
	{
	    return deathRate.compareTo(fdw.getDeathRate());
	}
	
}
