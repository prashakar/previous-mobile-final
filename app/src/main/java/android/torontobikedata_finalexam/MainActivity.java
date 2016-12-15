package android.torontobikedata_finalexam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DataDownloadedListener, AdapterView.OnItemSelectedListener{

    private List<Bike> bikeList = null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataDownloadedListener dataDownloadedListener = this;

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading..");
        dialog.show();

        DownloadDataTask downloadDataTask = new DownloadDataTask(this, dataDownloadedListener);
        downloadDataTask.execute("http://csundergrad.science.uoit.ca/csci3230u/data/Affordable_Housing.csv");
    }

    @Override
    public void dataDownloaded(List<Bike> bikes) {
        dialog.dismiss();
        this.bikeList = bikes;
        ArrayAdapter<Bike> adapter = new ArrayAdapter<Bike>(this, android.R.layout.simple_spinner_item, bikeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner list = (Spinner)findViewById(R.id.listBikes);
        list.setOnItemSelectedListener(this);
        list.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("testing");

        Bike bike = (Bike)parent.getItemAtPosition(position);
        setTextFieldValue(R.id.numOfBikes, "" + bike.getNumOfUnits());
        setTextFieldValue(R.id.address, "" + bike.getAddress());
        setTextFieldValue(R.id.latitude, "" + bike.getLatitude());
        setTextFieldValue(R.id.longitude, "" + bike.getLongitude());
        setTextFieldValue(R.id.municipality, "" + bike.getMunicipality());

    }

    private void setTextFieldValue(int id, String value) {
        EditText field = (EditText)findViewById(id);
        field.setText(value);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showOnMap(View v){
        Intent intent = new Intent(this, ShowMapsActivity.class);
        Double latitude = 43.6563589;
        Double longitude = -79.3909977;
        intent.putExtra("lat",latitude);
        intent.putExtra("long", longitude);
        startActivity(intent);
    }


}



