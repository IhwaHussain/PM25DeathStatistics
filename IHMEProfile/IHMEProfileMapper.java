import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;


import java.util.Map;
import java.util.TreeMap;

public class IHMEProfileMapper extends Mapper<LongWritable, Text, NullWritable, FIPSDeathsWritable>
{
	private TreeMap<Double, FIPSDeathsWritable> drToRecordMap = new TreeMap<Double, FIPSDeathsWritable>();

	@Override
	public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException 
	{
		String line = value.toString();
		String[] tokens = line.split("[\\s,]+");
		if(tokens[3].equals("Total") && tokens[4].length()>0
			&& tokens[4].substring(0,1).equals("<")
			&& tokens[0].length() > 0 && tokens[1].length()>0)
		{
			// Add this record to our map with the death rate
			// as the key
			IntWritable state = new IntWritable(Integer.parseInt(tokens[0]));
			IntWritable county = new IntWritable(Integer.parseInt(tokens[1]));
			DoubleWritable deathRate = new DoubleWritable(Double.parseDouble(tokens[tokens.length-1]));
			drToRecordMap.put(deathRate.get(), new FIPSDeathsWritable(state,county,deathRate));
		}
		// If we have more than ten records, remove the
		// one with the lowest death rate
		// As this tree map is sorted in ascending order,
		// the county with the lowest death rate is the
		// first key
		if (drToRecordMap.size() > 10) 
		{
			drToRecordMap.remove(drToRecordMap.firstKey());
		}
	}

	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
	// Output our ten records to the reducers with a
	// null key
		for (FIPSDeathsWritable t : drToRecordMap.values()) {
			context.write(NullWritable.get(), t);
		}
	}
}
