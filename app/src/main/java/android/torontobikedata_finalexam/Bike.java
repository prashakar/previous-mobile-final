package android.torontobikedata_finalexam;

public class Bike {

    private long id = -1;
    private int numOfUnits = -1;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String municipality = null;
    private String address = null;

    public Bike(int numOfUnits, double latitude, double longitude,
                String municipality, String address){
        setNumOfUnits(numOfUnits);
        setLatitude(latitude);
        setLongitude(longitude);
        setMunicipality(municipality);
        setAddress(address);
    }

    public long getId(){
        return id;
    }

    public int getNumOfUnits(){
        return numOfUnits;
    }

    public String getMunicipality(){
        return municipality;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getAddress(){
        return address;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setNumOfUnits(int numOfUnits){
        this.numOfUnits = numOfUnits;
    }

    public void setMunicipality(String municipality){
        this.municipality = municipality;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String toString(){
        return municipality + " (" + address + ")";
    }
}