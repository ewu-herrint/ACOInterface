package acg.project.cli.parser;

import java.util.Scanner;

import acg.architecture.datatype.Acceleration;
import acg.architecture.datatype.Altitude;
import acg.architecture.datatype.AngleNavigational;
import acg.architecture.datatype.AttitudePitch;
import acg.architecture.datatype.CoordinateCartesianRelative;
import acg.architecture.datatype.CoordinateWorld;
import acg.architecture.datatype.Distance;
import acg.architecture.datatype.Flow;
import acg.architecture.datatype.Identifier;
import acg.architecture.datatype.Latitude;
import acg.architecture.datatype.Longitude;
import acg.architecture.datatype.Percent;
import acg.architecture.datatype.Rate;
import acg.architecture.datatype.Speed;
import acg.architecture.datatype.Time;
import acg.architecture.datatype.Weight;

/**
 * 
 * All unit parsing methods go in here.  
 * The  methods take a scanner and assume the token 
 * they need is the next token.  They could be overloaded to take a 
 * string if needed. 
 * @author Benjamin Daschel, Tyler Herrin
 *
 */
public class ParseUnits {
	
	public static Acceleration parse_acceleration(Scanner scanner) throws ParseException
	{
		double accelerationFPS;
		
		try { accelerationFPS = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid acceleration value."); }
		
		return new Acceleration(accelerationFPS);
	}
	
	public static Altitude parse_altitude(Scanner scanner) throws ParseException
	{
		double altitiudeFeet;
		
		try { altitiudeFeet = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid altitude value."); }
		
		return new Altitude(altitiudeFeet);
	}
	
	public static CoordinateWorld parse_coordinates(Scanner scanner) throws ParseException
	{	
		Latitude lat = parse_latitude(scanner); // Assuming latitude comes first
		Longitude lon = parse_longitude(scanner);	
		
		return new CoordinateWorld(lat, lon);
	}
	
	public static AngleNavigational parse_course(Scanner scanner) throws ParseException
	{
		double angleDegrees;
		
		try{ angleDegrees = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid heading value."); }
		
		return new AngleNavigational(angleDegrees);
	}
	
	public static Distance parse_distance(Scanner scanner) throws ParseException
	{
		double distanceFeet;
		
		try { distanceFeet = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid distance value."); }
		
		return new Distance(distanceFeet);
	}
	
	public static AttitudePitch parse_elevation(Scanner scanner) throws ParseException
	{
		double attitudeMathDegrees;
		
		try { attitudeMathDegrees = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid attitude value."); }
		
		return new AttitudePitch(attitudeMathDegrees);
	}
	
	public static Flow parse_flow(Scanner scanner) throws ParseException
	{
		double flowPPS;
		
		try { flowPPS = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid flow value."); }
		
		return new Flow(flowPPS);
	}
	
	public static Identifier parse_id(Scanner scanner) throws ParseException 
	{
		String regex = "^[a-zA-Z0-9_]*$";
		String ID = scanner.nextLine();
		
		if(ID.matches(regex))
			return new Identifier(ID);
		else
			throw new ParseException("Invalid ID value.");
	}
	
	public static Latitude parse_latitude(Scanner scanner) throws ParseException
	{
		String[] values = scanner.nextLine().split("[*'\"]");
		int degrees;
		int minutes;
		double seconds;
		
		try
		{
			degrees = Integer.parseInt(values[0]);
			minutes = Integer.parseInt(values[1]);
			seconds = Double.parseDouble(values[2]);
		}
		catch(NumberFormatException e) { throw new ParseException("Invalide latitude value."); }
		
		return new Latitude(degrees, minutes, seconds);
	}

	public static Longitude parse_longitude(Scanner scanner) throws ParseException
	{
		String[] values = scanner.nextLine().split("[*'\"]");
		int degrees;
		int minutes;
		double seconds;
		
		try
		{
			degrees = Integer.parseInt(values[0]);
			minutes = Integer.parseInt(values[1]);
			seconds = Double.parseDouble(values[2]);
		}
		
		catch(NumberFormatException e) { throw new ParseException("Invalid longitude value."); }
		
		return new Longitude(degrees, minutes, seconds);
	}
	
	public static Rate parse_rate(Scanner scanner) throws ParseException
	{
		int rateMS;
		
		try { rateMS = scanner.nextInt(); }
		
		catch(NumberFormatException e) {throw new ParseException("Invalid rate value."); }
		
		return new Rate(rateMS);
	}
	
	public static String parse_string(Scanner scanner)
	{
		return scanner.nextLine();
	}
	
	public static Time parse_time(Scanner scanner) throws ParseException
	{
		double seconds;
		
		try { seconds = scanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid time value."); }
		
		return new Time(seconds);
	}
	
	public static AngleNavigational parse_azimuth(Scanner scanner) throws ParseException 
	{
		double azimuth;
		
		try{ azimuth = Double.parseDouble(scanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid azimuth value."); }
		
		return new AngleNavigational(azimuth);
	}

	public static Percent parse_percent(Scanner scanner) throws ParseException 
	{
		Double percent;
		
		try{ percent = Double.parseDouble(scanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid percent value."); }
		
		return new Percent(percent);
	}

	public static CoordinateCartesianRelative parse_origin(Scanner scanner) throws ParseException 
	{
		String originString = scanner.next();
		String[] xy = originString.split(":");
		
		double x;
		double y;
		
		try
		{
			x = Double.parseDouble(xy[0]);
			y = Double.parseDouble(xy[1]);
		}
		catch(NumberFormatException e) { throw new ParseException("Invalid origin value."); }
		
		return new CoordinateCartesianRelative(x, y);
		
	}

	public static Speed parse_speed(Scanner scanner) throws ParseException 
	{
		double speed;
		
		try{ speed = Double.parseDouble(scanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid speed value."); }
		
		return new Speed(speed);
	}

	public static Weight parse_weight(Scanner scanner) throws ParseException 
	{
		double weight;
		
		try{ weight = Double.parseDouble(scanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid wegiht value"); }
		
		return new Weight(weight);
	}

	public static Distance parse_width(Scanner scanner) throws ParseException 
	{
		double width;
		
		try{ width = Double.parseDouble(scanner.next()); }
		
		catch(NumberFormatException e){ throw new ParseException("Invalid width value.");
		}
		
		return new Distance(width);
	}

	public static Rate parse_rate(String nextToken) throws ParseException 
	{
		return parse_rate(new Scanner(nextToken));
	}
}
