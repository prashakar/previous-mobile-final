package android.torontobikedata_finalexam;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DownloadDataTask extends AsyncTask<String, Void, List<Bike>> {
    private Exception exception = null;
    private DataDownloadedListener listener = null;
    private Context ctx;

    public DownloadDataTask(Context ctx, DataDownloadedListener listener) {
        this.listener = listener;
        this.ctx = ctx;
    }

    private List<String> loadCSVLines(InputStream inStream) throws IOException {
        List<String> lines = new ArrayList<>();

        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

        String line = null;
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    @Override
    protected List<Bike> doInBackground(String... params) {
        //do background tasks here
        List<String> linesReturn = new ArrayList<>();
        List<Bike> bikeList = new ArrayList<Bike>();
        BikeDBHelper bikeDBHelper = new BikeDBHelper(ctx);

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(ctx, Locale.getDefault());

            URL url = new URL(params[0]);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            int result = conn.getResponseCode();
            if (result == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                linesReturn = loadCSVLines(inputStream);
                for (String lines : linesReturn) {
                    String[] cols = lines.split(",");
                    if (!cols[0].equals("X")) {
                        float latitude = Float.parseFloat(cols[1]);
                        float longitude = Float.parseFloat(cols[0]);
                        String municipality = cols[6];
                        int numOfUnits = Integer.valueOf(cols[9]);

                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                        Bike bike = bikeDBHelper.createBike(numOfUnits, latitude, longitude, municipality, address);

                        bikeList.add(bike);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bikeList;
    }

    @Override
    protected void onPostExecute(List<Bike> result) {
        if (exception != null) {
            exception.printStackTrace();
        }
        listener.dataDownloaded(result);
    }
}
