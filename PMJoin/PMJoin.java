import java.io.*; 
import java.net.URI; 
import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PMJoin
{
	public static void main(String[] args) throws Exception
	{
		Configuration conf = new Configuration();

		if (args.length != 2)
		{
			System.err.println("Usage: PMJoin <input path> <output path>");
			System.exit(-1);
		}
    		Job job = Job.getInstance(conf, "Distributed Cache");
    		job.setJarByClass(PMJoin.class);
    		job.setJobName("Join Mortality and PM data");

		try
		{
			job.addCacheFile(new URI("/user/fh828_nyu_edu/PM25_DATA.txt"));
		}
		catch (Exception e) 
		{
            		System.out.println("File Not Added");
            		System.exit(1);
		}
    		FileInputFormat.addInputPath(job, new Path(args[0]));
    		FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    		job.setMapperClass(PMJoinMapper.class);

    		job.setOutputKeyClass(DeathCategoryWritable.class);
    		job.setOutputValueClass(DeathPMPair.class);

    		job.setNumReduceTasks(0);
    
    		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
