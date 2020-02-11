package com.rayest.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ReduceTest extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        System.out.println("reduce key:" + key);
        int sum = 0;
        Iterator<IntWritable> it = values.iterator();
        while (it.hasNext()) {
            IntWritable val = it.next();
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
