package com.example.mikhail.stockstore.AsyncClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by mikhail on 19.12.15.
 */

public class AsyncGetPhoto extends AsyncTask<String, Integer, Bitmap>
{
    // Метод выполняется перед началом doInBackground()
    protected void onPreExecute(){

    }

    // Здесь делайте всю длительную по времени работу.
    protected Bitmap doInBackground(String... urls)
    {
       // long totalSize = 0;
        URL newurl = null;

        int count = urls.length;
        for (int i = 0; i < count; i++)
        {
            try {
                newurl = new URL(urls[i]);
                return BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

          //  totalSize += Downloader.downloadFile(urls[i]);
            publishProgress((int) ((i / (float) count) * 100));
            // Ранний выход, если был вызван cancel().
            if (isCancelled())
                break;
        }
        return null;

    }

    // Этот метод будет вызван каждый раз, когда в потоке
    // выполнится publishProgress().
    protected void onProgressUpdate(Integer... progress)
    {
        //setProgressPercent(progress[0]);
    }

    // Этот метод будет вызван, когда doInBackground() завершится.
    protected void onPostExecute(Bitmap result)
    {
        //showNotification("Downloaded " + result + " bytes");
    }
}
