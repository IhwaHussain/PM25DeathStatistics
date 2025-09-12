import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.Map;
import java.util.TreeMap;

public class IHMEProfileReducer extends Reducer<NullWritable, FIPSDeathsWritable, NullWritable, FIPSDeathsWritable>
{
	// Stores a map of death rates to the record
	private TreeMap<Double, FIPSDeathsWritable> drToRecordMap = new TreeMap<Double, FIPSDeathsWritable>();

	@Override
	public void reduce(NullWritable key, Iterable<FIPSDeathsWritable> values,Context context) throws IOException, InterruptedException 
	{
		for (FIPSDeathsWritable value : values)
		{
			IntWritable state = new IntWritable(value.getState().get());
			IntWritable county = new IntWritable(value.getCounty().get());
			DoubleWritable deathRate = new DoubleWritable(value.getDeathRate().get());
			drToRecordMap.put(deathRate.get(), new FIPSDeathsWritable(state,county,deathRate));
			// If we have more than ten records, remove the
			// // one with the lowest death rate
			// As this tree map is sorted in ascending
			// order, the record with the lowest death rate is
			// the first key
			if (drToRecordMap.size() > 10)
			{
				drToRecordMap.remove(drToRecordMap.firstKey());
			}
		}
		for (FIPSDeathsWritable t : drToRecordMap.descendingMap().values())
		{
			// Output our ten records to the file system
			// with a null key
			context.write(NullWritable.get(), t);
		}
	}
}
