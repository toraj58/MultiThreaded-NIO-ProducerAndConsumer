package com.touraj.creditsuisse.io;

import com.touraj.creditsuisse.util.Utility;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.BlockingQueue;

/**
 * Created by toraj on 03/21/2017.
 * */

public class FileReaderNIO implements Runnable {

    private static BlockingQueue<String> linesBLQueue =null;
    MutableBoolean isFileReadCompleted;

    public FileReaderNIO(BlockingQueue<String> linesBLQueue, MutableBoolean isFileReadCompleted) {
        this.linesBLQueue = linesBLQueue;
        this.isFileReadCompleted = isFileReadCompleted;

    }

    @Override
    public void run() {

        try {
          //  Thread.sleep(3000); << Just to Simulate Delay in Producer threads
//            [Touraj] :: Reading File using NIO : Non-Blocking IO
            RandomAccessFile aFile = new RandomAccessFile(Utility.getFilePath(), "r");
            FileChannel inChannel = aFile.getChannel();
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());

            StringBuffer line = new StringBuffer();
            for (int i = 0; i < buffer.limit(); i++) {
                byte read = buffer.get();

                if((char)read=='\r'){
//                    line.append((char)read);
                    linesBLQueue.put(line.toString());

                    line=new StringBuffer();
                }else if ((char)read!='\n'){
                    line.append((char)read);
                }

            }
            //[Touraj] :: Put Last Line to Blocking Queue
            linesBLQueue.put(line.toString());
            aFile.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (InterruptedException intexp) {
            intexp.printStackTrace();
        }

        isFileReadCompleted.setValue(true); // signal consumers to terminate
        System.out.println(Thread.currentThread().getName() + " producer is done");
    }

}
