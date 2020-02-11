package com.rayest.hadoop;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new WordCount(), args);
    }

    @Override
    public int run(String[] strings) throws Exception {
        Job job = new Job(getConf());
        job.setJarByClass(WordCount.class);
        job.setJobName("word count");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(MapTest.class);
        job.setReducerClass(ReduceTest.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path("files/input"), new Path("files/input2"));
        FileOutputFormat.setOutputPath(job, new Path("files/output"));

        boolean success = job.waitForCompletion(true);

        return success ? 0 : 1;
    }
}
