package com.rayest.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ReduceTest extends Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text key, Iterator<IntWritable> values, Context context) throws IOException, InterruptedException {
        System.out.println("reduce context:" + context);
        int sum = 0;
        Iterator<IntWritable> it = values;
        while (it.hasNext()) {
            IntWritable val = it.next();
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
