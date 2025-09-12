import java.io.*; 
import java.util.*; 
import java.net.URI; 
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class PMJoinMapper extends Mapper<LongWritable, Text, DeathCategoryWritable, DeathPMPair>
{
	private HashMap<String,Double> locToPM = new HashMap<String, Double>();

	private String joinType = null;

	@Override
	public void setup(Context context) throws IOException, InterruptedException
	{
		String line = null;
		URI[] files = context.getCacheFiles();

		if(files != null && files.length>0)
		{
			try
			{
				FileSystem fs = FileSystem.get(context.getConfiguration());
                		Path getFilePath = new Path(files[0].toString()); 

				BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(getFilePath)));

				while ((line = reader.readLine()) != null)
				{
					//Get FIPS from first five characters
					String loc = StringUtils.substring(line,0,5);
					//Get PM Level from double after last comma
					String pm = StringUtils.substringAfterLast(line, ",");
					locToPM.put(loc,Double.parseDouble(pm));
				}

				joinType = context.getConfiguration().get("join.type");
			}
			catch(Exception e)
			{
				System.out.println("Unable to read the File");
                		System.exit(1); 
			}
		}
	}

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String line = value.toString();
		String[] tokens = line.split(",",-1);
		if(tokens.length==6)
		{
			//Convert FIPS to 5 charcters FIPS
			String stateFIP = StringUtils.leftPad(tokens[0], 2, '0');
			String countyFIP = StringUtils.leftPad(tokens[1],3, '0');
			String FIP = stateFIP+countyFIP;

			//Get DeathCategoryWritable from mortality files
			IntWritable stateCode = new IntWritable(Integer.parseInt(tokens[0]));
			IntWritable countyCode = new IntWritable(Integer.parseInt(tokens[1]));
			IntWritable year = new IntWritable(Integer.parseInt(tokens[2]));
			Text race = new Text(tokens[3]);
			Text age = new Text(tokens[4]);

			//Get Death Rate from mortality files
			DoubleWritable deathRate = new DoubleWritable(Double.parseDouble(tokens[5]));
			//Get PM Level
			Double pm25Level = locToPM.get(FIP);

			if(pm25Level != null)
			{
				DoubleWritable pmWritable = new DoubleWritable(pm25Level);
				context.write(new DeathCategoryWritable(stateCode, countyCode, race, age, year), new DeathPMPair(deathRate,pmWritable));
			}
			else
			{
				DoubleWritable pmWritable = new DoubleWritable(0);
				context.write(new DeathCategoryWritable(stateCode, countyCode, race, age, year), new DeathPMPair(deathRate,pmWritable));
			}
		}
	}
}

