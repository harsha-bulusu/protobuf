package com.harsha;

import com.harsha.util.Helper;
import com.harsha.util.MyHttpClient;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws InterruptedException
    {
        MyHttpClient httpClient = new MyHttpClient();

        int size = 10_00_000;

        for (int i = 0; i < 50; i++)
            Helper.invokeForProtBuf(httpClient, i, size);

        Thread.sleep(3000);

        for (int i = 0; i < 50; i++)
            Helper.invokeForJson(httpClient, i, size);
    }

    

}
