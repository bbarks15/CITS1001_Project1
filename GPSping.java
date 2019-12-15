import java.util.ArrayList; // IMPORTS THE ARRAY LIBRARY

/**
 * THE GPSPING CLASS TAKES A INPUT OF LATITUDE, LONGITUDE AND TIME AND RETURNS THE FOLLOWING OUTPUTS
 * LATITUDE
 * LONGITUDE
 * TIME
 * LATITUDE, LONGITUDE AND TIME AS A STRING
 * TIME BETWEEN PING'S
 * DISTANCE BETWEEN PING'S
 *
 * @author Brandon Barker
 * @version 12/09/2019
 */

public class GPSping // CLASS FOR GPS PING
{
    // DEFINED INPUT VALUES AS ATTRIBUTES
    private double latitude; // DEFINES LATITUDE
    private double longitude; // DEFINES LONGITUDE
    private int ping_time; // DEFINES PING_TIME



    public GPSping(double lat, double lon, int time) // CONSTRUCTOR FOR GPSping. Takes lat, lon, time AND DEFINES THEM AS latitude, longitude AND ping_time RESPECTIVELY
    {
        double max_latitude = 90; // MAXIMUM POSSIBLE VALUE OF LATITUDE
        double min_latitude = -90; // MINIMUM POSSIBLE VALUE OF LATITUDE
        double max_longitude = 180; // MAXIMUM POSSIBLE VALUE OF LONGITUDE
        double min_longitude = -180; // MINIMUM	POSSIBLE VALUE OF LONGITUDE
        double max_time = Math.pow(2,31)-1; // MAXIMUM POSSIBLE VALUE OF TIME
        double min_time = -Math.pow(2,31); // MINIMUM POSSIBLE VALUE OF TIME

        if (min_latitude>lat || lat>max_latitude ||
        min_longitude>lon || lon>max_longitude ||
        min_time>time || time>max_time) // CHECKS IF ANY OF THE INPUTS ARE OUTSIDE THE RANGE OF EXCEPTED VALUES. IF TRUE IT SETS ALL THE VALUES TO 0
	{
	  latitude = 0; // SETS LATITUDE TO ZERO
          longitude = 0; // SETS LONGITUDE TO ZERO
          ping_time = 0; // SETS TIME TO ZERO
        }
        else  // IF ALL THE VALUES ARE IN THE EXCEPTED RANGE IT DEFINES THEM NORMALLY
	{
	  latitude = lat;
          longitude = lon;
          ping_time = time; }
    }

    public double getLat() // METHOD RETURNS THE LATITUDE FROM THE PING AS A DOUBLE
    {
        return latitude;
    }

    public double getLon() // METHOD RETURNS THE LONGITUDE FROM THE PING AS A DOUBLE
    {
        return longitude;
    }

    public int getTime() // METHOD RETURNS THE TIME FROM THE PING AS AN INTEGER
    {
        return ping_time;
    }

    public String toString() // METHOD RETURNS THE LATITUDE, LONGITUDE AND TIME AS A STRING
    {
        return latitude + ","+ longitude + "," + ping_time;
    }

    public int timeTo(GPSping anotherping) // RETURNS THE TIME BETWEEN TWO PING'S AS AN INTEGER
    {
        return Math.abs(anotherping.getTime() - getTime()); // ABSOLUTE VALUE IS USED TO MAKE TIME POSITIVE IF ANOTHER PING > PING
    }

    public int distTo(GPSping anotherping) // RETURNS THE DISTANCE BETWEEN TWO POINTS AS A DOUBLE
    {
        double degree_length = 110.25*1000; // CIRCUMFERENCE OF ROUGHLY 60 DEGREES OF LONGITUDE AROUND THE EQUATOR

        double lat_change = Math.abs(anotherping.getLat() - getLat()); // FINDS THE CHANGE IN LATITUDE AS |LAT2 - LAT1|. TO MAKE SURE THE ANSWER IS POSITIVE THE ABSOLUTE VALUE IS TAKEN
        double average_lat = (anotherping.getLat() + getLat())/2 ; // FINDS THE AVERAGE LATITUDE BETWEEN TWO PING'S

        double lon_change = Math.abs(anotherping.getLon()
        - getLon())*Math.cos(average_lat); // FINDS THE CHANGE IN LONGITUDE. TO MAKE SURE THE ANSWER IS POSITIVE THE ABSOLUTE VALUE IS TAKEN

        double distance = degree_length*Math.sqrt(lat_change*lat_change
        + lon_change*lon_change); // EVALUATES THE DISTANCE GIVEN THE ABOVE VALUES

        return (int)Math.round(distance); // RETURNS THE DISTANCE AS AN INTEGER. USING (INT) ROUNDS THE DOUBLE DOWN, SO MATH.ROUND() IS USED ROUND THE DISTANCE TO THE NEAREST WHOLE NUMBER BEFORE RETURNING IT AS AN INTEGER.
    }
}
