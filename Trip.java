import java.util.ArrayList; // IMPORTS ARRAY LIBRARY
import java.util.Collections; // IMPORTS THE COLLECTIONS LIBRARY
import java.util.Comparator; // IMPORTS THE COMPARATOR LIBRARY

import java.awt.BorderLayout; // IMPORTS THE BORDER LAYOUT LIBRARY
import javax.swing.JFrame; // IMPORTS THE JFRAME LIBRARY
import javax.swing.JScrollPane; // IMPORTS THE JSCROLLPANE LIBRARY
import javax.swing.JTable; // IMPORTS THE JTABLE LIBRARY

/**
 * THE TRIP CLASS TAKES GPSPING'S AS AN INPUT AND STORES THEM IN AN ARRAY,
 * RETURNING
 *
 * PING'S IN AN ARRAY
 * STARTING PING
 * LAST PING
 * TRIP DURATION
 * AVERAGE SPEED
 * RESULTS IN A FORMATTED STRING
 * RESULTS IN A FORMATTED TABLE
 * TRIP LENGTH
 * TRIP DISPLACEMENT
 * A TIME ZOOMED ARRAY OF PING'S
 * NORTH EAT BOUND
 * SOUTH WEST BOUND
 * A SPACE ZOOMED ARRAY OF PING'S
 *
 * @author Brandon Barker
 * @version 12/09/2019
 */

public class Trip // THE CLASS FOR TRIP
{
    // DEFINES A PING_LIST ATTRIBUTE USING THE GPSPING CLASS
    private ArrayList<GPSping> Ping_list;

    /**
     * CONSTRUCTOR FOR TRIP CLASS TAKES NO INPUTS AND MAKE A BLANK ARRAY FOR
     * PING_LIST
     */
    public Trip()
    {
        Ping_list = new ArrayList<>(); // INITIALISES
    }

    /**
     * CONSTRUCTOR FOR TRIP CLASS TAKING AN ARRAY OF GPSPING'S AS THE INPUT AND
     * CREATES AN ARRAY PING_LIST OF GPSPING'S
     */
    public Trip(ArrayList<GPSping> triplist)
    {
        Ping_list = new ArrayList<GPSping>(); // INITIALISES THE ARRAY
        for(GPSping ping: triplist){          // ADDS EACH ELEMENT OF TRIPLIST TO PINGLIST
          Ping_list.add(ping);
        }
    }

    /**
     * METHOD THAT RETURNS THE PING_LIST AS AN ARRAY OF GPSPING'S
     */
   public ArrayList<GPSping> getPingList()
    {
        return Ping_list;
    }

    /**
     * BOOLEAN METHOD FOR CHECKING IF A PING IS EXPECTABLE TO BE ADDED
     */
    public boolean addPing(GPSping p)
    {
      // CHECKS IF PING_LIST_SIZE IS EQUAL TO ZERO (CASE OF NO PRIOR PINGS)
      // OR IF THE PING'S TIME IS AFTER THE PRIOR PING
      if (Ping_list.size() == 0 || p.getTime() >=
      Ping_list.get(Ping_list.size()-1).getTime())
      {
        Ping_list.add(p); // ADDS PING TO PING_LIST
        return true;
      } else {
        // DO NOTHING
        return false;
      }
    }

    /**
     * METHOD THAT RETURNS THE FIRST PING IN PING_LIST AS THE CLASS GPSPING
     */
    public GPSping getStart()
    {
    // RETURN THE 1ST ELEMENT IN THE PING_LIST ARRAY
        return Ping_list.get(0);
    }

    /**
     * METHOD THAT RETURNS THE LAST PING IN PING_LIST AS THE CLASS GPSPING
     */
    public GPSping getFinish()
    {
    // RETURNS THE LAST ELEMENT IN THE PING_LIST ARRAY
        return Ping_list.get(Ping_list.size()-1);
    }

    /**
     * METHOD THAT RETURNS THE DURATION OF THE TRIP IN SECONDS AS AN INTEGER
     */
    public int tripDuration()
    {
    // RETURNS THE DURATION BY FINDING THE DIFFERENCE IN THE TIME OF THE
    // FIRST PING AND THE LAST PING
        return getFinish().getTime() - getStart().getTime();
    }

    /**
     * METHOD THAT RETURNS THE AVERAGE SPEED OF THE TRIP IN METRES/SECOND AS A DOUBLE
     */
    public double averageSpeed()
    {
    // RETURNS THE TRIP LENGTH DIVIDED BY THE TRIP DURATION, GIVING SPEED
        return (double)tripLength()/(double)tripDuration();
    }


    /**
     * METHOD THAT RETURNS THE OUTPUTS OF OTHER METHODS IN A FORMATTED STRING
     */
    public String toString()
    {
        return
    // INITIAL VALUES OF LATITUDE AND LONGITUDE
        "Initial (Lat,Lon): "+ "("+getStart().getLat()+"\u00b0,"
            + getStart().getLon() + "\u00b0) \r\n" +
    // INITIAL TIME
        "Initial Unix Time: "+ getStart().getTime() + "s \r\n \r\n" +
        // FINAL VALUES OF LATITUDE AND LONGITUDE
    "Final (Lat,Lon): "+ "(" + getFinish().getLat()+"\u00b0,"
            + getFinish().getLon() + "\u00b0) \r\n" + // FINAL VALUES OF LATITUDE AND LONGITUDE
        // FINAL TIME
        "Final Unix Time: "+ getFinish().getTime() + "s \r\n \r\n" +
    // THE DURATION OF THE TRIP IN DAYS, HOURS, MINUTES AND SECONDS
    "The duration of the trip was " + tripDuration()/86400+" Days "
            +(tripDuration()%86400/3600)+" Hours "
            +(tripDuration()%3600/60)+" Minutes "
            +(tripDuration()%60)+" Seconds \r\n" +
    // THE DISTANCE TRAVELLED
        "The distance travelled during the trip was "
            + tripLength() + " m \r\n" +
    // THE DISPLACEMENT
        "The displacement from start to finish of the trip was "
            +tripDisplacement() + " m \r\n"+
    // THE AVERAGE SPEED
        "The average speed of the trip was "
            + String.format("%.3f km/h \r\n",averageSpeed()*3.6);
    }


    /**
     * METHOD THAT CREATES A TABLE TO DISPLAY THE DATA IN A MORE USEFUL AND EASY
     * TO USE FORMAT
     */
    public void makeTable()
    {
         JFrame frame = new JFrame(); // INITIALISES JFRAME

     // PUTS THE DATA INTO AN ARRAY
         Object rowData[][] = {
             {"Results", "", ""},
             {"Trip Duration (s)", tripDuration(),tripDuration()/86400+" Days"
                 +(tripDuration()%86400/3600)+" Hours "
                 +(tripDuration()%3600/60)+" Minutes "
                 +(tripDuration()%60)+" Seconds"},
             {"Trip Length (m)",tripLength(),
                 String.format("%.4f km",(float)tripLength()/1000)},
             {"Trip Displacement (m)", tripDisplacement(),
                 String.format("%.4f km",(float)tripDisplacement()/1000)},
             {"Average Speed (m/s)", String.format("%.4f",averageSpeed())
                 ,String.format("%.4f",averageSpeed()*3.6)+" km/h"},
             {"Max Latitude (\u00b0)", NEbound().getLat(), ""},
             {"Max Longitude (\u00b0)",NEbound().getLon(), ""},
             {"Min Latitude (\u00b0)", SWbound().getLat(), ""},
             {"Min Longitude (\u00b0)",SWbound().getLon(), ""},
             {"", "", ""},
             {"", "", ""},
             {"Inputs", "Initial", "Final"},
             {"Latitude (\u00b0)", getStart().getLat(), getFinish().getLat()},
             {"Longitude (\u00b0)", getStart().getLon(), getFinish().getLat()},
             {"Unix Time (s)", getStart().getTime(), getFinish().getTime()},
         };

     // FORMATS THE FIRST COLUMN TO BE BLANK
         Object columnNames[] = { "", "", "" };
     // CREATES THE TABLE FROM ROWDATA AND COLUMNNAMES
         JTable table = new JTable(rowData, columnNames);
     // INITIALISES SCROLLPANE
         JScrollPane scrollPane = new JScrollPane(table);
     // ADDS TABLE TO SCROLLPANE
         frame.add(scrollPane, BorderLayout.CENTER);
     // SETS SIZE OF FRAME
         frame.setSize(800, 400);
     // MAKES FRAME VISIBLE
         frame.setVisible(true);
    }

    /**
     * METHOD THAT RETURNS THE TRIP LENGTH AS IN INTEGER IN METRES
     */
    public int tripLength()
    {
        double total_length = 0; // INITIALISES THE TOTAL_LENGTH
        int pos = 1; // DEFINES THE POSITION IN THE ARRAY. STARTS AT 1

    // THE LOOP CALLS THE DISTTO METHOD OF THE GPS CLASS ON THE PING IN
    // POSITION POS. THIS FINDS THE DISTANCE BETWEEN THIS PING AND ITS
    // PRIOR PING, ADDING IT TOTAL_LENGTH. THE LOOP RUNS UNTIL IT HAS GONE
    // THROUGH ALL ELEMENTS OF THE PING_LIST ARRAY
        do{
            total_length += Ping_list.get(pos).distTo(Ping_list.get(pos-1));
        } while(pos++ < Ping_list.size() -1);
    // (INT)MATH.ROUND() IS USED TO ROUND THE TOTAL_LENGTH TO THE NEAREST
    // METER. THIS VALUE IS RETURNED
        return (int)Math.round(total_length);
    }


    /**
     * METHOD USES THE HAVERSINE EQUATION TO CALCULATE THE DISPLACEMENT OF THE TRIP
     * (THE DISTANCE BETWEEN THE INITIAL AND FINAL POINTS) AND RETURNS IT AS AN
     * INTEGER IN METERS
     */
    public int tripDisplacement()
    {
    // THE RADIUS OF THE EARTH IN METRES
        int earthRadius = 6372797;

    // CALCULATES THE DIFFERENCE IN LATITUDE DIVIDED BY 2 IN DEGREES
        double diffLat=(getFinish().getLat()-getStart().getLat())/2.0;
    // CALCULATES THE DIFFERENCE IN LONGITUDE DIVIDED BY 2 IN DEGREES
        double diffLon=(getFinish().getLon()-getStart().getLon())/2.0;

    // EVALUATES COSINE OF THE INITIAL LATITUDE IN RADIANS
        double cos1 = Math.cos( getStart().getLat()*Math.PI/180);
    // EVALUATES COSINE OF THE FINAL LATITUDE IN RADIANS
        double cos2 = Math.cos( getFinish().getLat()*Math.PI/180);

    // EVALUATES THE SINE OF DIFFLAT IN RADIANS
        double sin1 = Math.sin(diffLat*Math.PI/180);
    // EVALUATES THE SIN OF DIFFLON IN RADIANS
        double sin2 = Math.sin(diffLon*Math.PI/180);

        // EVALUATES THE TOTAL OF THE PRODUCT OF THE SINE'S AND THE PRODUCT OF
    // THE COSINE'S AND SINE'S
    double trigExpression = sin1*sin1 + cos1*cos2 * sin2*sin2;

    // RETURNS THE DISPLACEMENT AS AN INTEGER IN METRES USING MATH.ROUND()
    // TO ROUND THE VALUE TO THE NEAREST METRES
        return (int)Math.round(2.0*earthRadius*Math.asin(Math.sqrt(trigExpression)));
    }

    /**
     * METHOD TAKES THE STARTTIME AND FINISHTIME
     * IT REMOVES ALL PING'S WHICH LIE OUTSIDE THE RANGE FORMED FROM THE INPUTS
     * AND RETURNS AN ARRAY PING_LIST_TIMEZOOM PING'S THE VALUES WITHIN THE
     * RANGE
     */
    public ArrayList<GPSping> timeZoom(int startTime, int finishTime)
    {
    // MAKES THE PING_LIST_TIMEZOOM ARRAY
        ArrayList<GPSping> Ping_list_timezoom;
    // INITIALISES THE PING_LIST_TIMEZOOM ARRAY
        Ping_list_timezoom = new ArrayList<GPSping>();

    // FOR EACH ELEMENT IN PING_LIST IT CHECKS IF IT LIES WITHIN THE RANGE
    // AND IF IT DOES, ADDS IT TO THE ARRAY PING_LIST_TIMEZOOM
        for (GPSping ping: Ping_list)
        {
        // IF STARTTIME<=PING<= FINISHTIME ADD TO PING_LIST_TIMEZOOM
            if (ping.getTime() >= startTime && ping.getTime() <= finishTime)
            {
                Ping_list_timezoom.add(ping);
            }
        // ELSE DOING NOTHING
        }
        return Ping_list_timezoom;
    }

    /**
     * METHOD THAT RETURNS THE NORTH EAST BOUND OF THE PING'S IN PING_LIST WITH
     * A TIME OF 0
     */
    public GPSping NEbound()
    {
    // FINDS THE MOST NORTHERN PING VIA USING USING A COMPARATOR AND
    // COLLECTIONS.MAX() TO FIND THE GREATEST LATITUDE IN PING_LIST
        GPSping Max_North = Collections.max(Ping_list,
        Comparator.comparing((GPSping ping) -> ping.getLat()));

    // FINDS THE MOST EASTERN PING VIA USING USING A COMPARATOR AND
    // COLLECTIONS.MAX() TO FIND THE GREATEST LONGITUDE IN PING_LIST
        GPSping Max_East = Collections.max(Ping_list,
        Comparator.comparing((GPSping ping) -> ping.getLon()));

    // A NEW GPSPING IS CONSTRUCTED WITH THE LATITUDE OF THE MOST NORTHERN
    // PING, THE LONGITUDE OF THE MOST EASTERN PING AND A TIME OF 0 AND IS
    // RETURNED
        return new GPSping(Max_North.getLat(),Max_East.getLon(),0);
    }

    /**
     * METHOD THAT RETURNS THE NORTH EAST BOUND OF THE PING'S IN PING_LIST WITH
     * A TIME OF 0
     */
    public GPSping SWbound()
    {
    // FINDS THE MOST SOUTHERN PING VIA USING USING A COMPARATOR AND
    // COLLECTIONS.MIN() TO FIND THE LOWEST LATITUDE IN PING_LIST
        GPSping Max_South = Collections.min(Ping_list,
        Comparator.comparing((GPSping ping) -> ping.getLat()));

    // FINDS THE MOST WESTERN PING VIA USING USING A COMPARATOR AND
    // COLLECTIONS.MIN() TO FIND THE LOWEST LONGITUDE IN PING_LIST
        GPSping Max_West = Collections.min(Ping_list,
        Comparator.comparing((GPSping ping) -> ping.getLon()));

    // A NEW GPSPING IS CONSTRUCTED WITH THE LATITUDE OF THE MOST SOUTHERN
    // PING, THE LONGITUDE OF THE MOST WESTERN PING AND A TIME OF 0 AND IS
    // RETURNED
        return new GPSping(Max_South.getLat(),Max_West.getLon(),0);
    }

    /**
     * A METHOD THAT REMOVES ALL THE PINGS NOT IN THE SQUARE FORMED BY THE NORTH
     * EASTERN AND SOUTH WESTERN BOUND AND RETURNS THEM IN AN ARRAY
     * PING_LIST_SPACEZOOM
     */
    public ArrayList<GPSping> spaceZoom(GPSping southwest, GPSping northeast)
    {
    // CREATES NEW ARRAY PING_LIST_SPACEZOOM
        ArrayList<GPSping> Ping_list_spacezoom;

    // INITIALISES PING_LIST_SPACEZOOM
        Ping_list_spacezoom = new ArrayList<GPSping>();

    // CHECKS IF EACH ELEMENT IN PING_LIST IS IN THE LATITUDE BOUNDS.
    // IF IT IS, IT GETS ADDED TO PING_LIST_SPACEZOOM
    for(GPSping ping1: Ping_list){
            if (southwest.getLat() < ping1.getLat() &&
            ping1.getLat() < northeast.getLat())
            {
                Ping_list_spacezoom.add(ping1);
            }
        // ELSE DO NOTHING
        }

    // CHECKS IF EACH ELEMENT IN PING_LIST_SPACEZOOM IS WITHIN THE BOUNDS
    // FOR THE LONGITUDE. IF IT ISNT WITHIN THE BOUNDS, IT GETS REMOVED FROM
    // PING_LIST_SPACEZOOM.
        for(GPSping ping2: Ping_list_spacezoom){
            if (southwest.getLon() > ping2.getLon() &&
            ping2.getLon() > northeast.getLon() )
            {
                Ping_list_spacezoom.remove(ping2);
            }
        // ELSE DO NOTHING
        }
        return Ping_list_spacezoom;
    }

}

