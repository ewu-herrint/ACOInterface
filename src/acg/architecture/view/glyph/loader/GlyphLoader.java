package acg.architecture.view.glyph.loader;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GlyphLoader 
{
	List<EntryCircle> circles;
	List<EntryColor> colors;
	List<List<EntryEdge>> edges;
	List<EntryVertex> vertices;
	LayoutBundle layoutBundle;
	String filename;
	
	public GlyphLoader(String filename)
	{
		this.filename = filename;
	}
	
	// Gets number of lines that start with the specified char.
	private int getAmount(char type) throws FileNotFoundException
	{
		Scanner in = openFile();
		int count = 0;
		do
		{
			String line = in.nextLine();
			if(line.charAt(0) == type)
				count++;
		}while(in.hasNextLine());
		
		return count;
	}
	
	private void makeVertices() throws FileNotFoundException 
	{
		//Vertex indices start at 1.
		int vAmt = getAmount('v') + 1; 
		EntryVertex[] vertices = new EntryVertex[vAmt];
		
		Scanner in = openFile();
		do // Run though file.
		{
			String line = in.nextLine();
			
			if(line.charAt(0) == 'v') // If vertex add to list.
			{
				String[] values = line.split(",");
				
				int index = Integer.parseInt(values[1]);
				double x = Double.parseDouble(values[2]);
				double y = Double.parseDouble(values[3]);
				double z = Double.parseDouble(values[4]);
				
				EntryVertex vertex = new EntryVertex(index, x, y, z);
				vertices[index] = vertex;
			}
		}while(in.hasNextLine());
		this.vertices = Arrays.asList(vertices);
		
		in.close();
	}

	private void makeColors() throws FileNotFoundException 
	{
		//Color indexes start at 1.
		int cAmt = getAmount('c') + 1; 
		EntryColor[] colors = new EntryColor[cAmt];
		
		Scanner in = openFile();
		do // Run though file.
		{
			String line = in.nextLine();
			
			if(line.charAt(0) == 'c') // If color add to list.
			{
				String[] values = line.split(",");
				
				int index = Integer.parseInt(values[1]);
				
				EntryColor color = new EntryColor(index, Color.decode(values[2]));
				colors[index] = color;
			}
		}while(in.hasNextLine());
		
		this.colors = Arrays.asList(colors);
		
		in.close();
	}

	private void makeCircles() throws FileNotFoundException, InvalidLayoutException 
	{
		this.circles = new ArrayList<EntryCircle>(); 
		int index = 1; // Input file doesn't give circle indices so supply them.
		int lineNum = 0;
		
		Scanner in = openFile();
		do // Run though file.
		{
			String line = in.nextLine();
			lineNum++;
			
			if(line.charAt(0) == 'o') // If a circle add to list.
			{
				String[] values = line.split(",");
				
				try
				{
					EntryVertex vertex = this.vertices.get(Integer.parseInt(values[1]));
					double radius = Double.parseDouble(values[2]);
					EntryColor color = this.colors.get(Integer.parseInt(values[3]));
				
					EntryCircle circle = new EntryCircle(index, vertex, radius, color);
					this.circles.add(circle);
				}
				catch(Exception e)
				{
					throw new InvalidLayoutException("Layout error in file at line: " + lineNum, lineNum);
				}
				index++; 
			}
			
		}while(in.hasNextLine());
		
		in.close();
	}

	private void makeEdges() throws FileNotFoundException, InvalidLayoutException 
	{
		Scanner in = openFile();
		this.edges = new ArrayList<List<EntryEdge>>();
		int lineNum = 0;
		
		do // Run through file.
		{
			String line = in.nextLine();
			lineNum++;
			
			ArrayList<EntryEdge> temp = new ArrayList<EntryEdge>();
			int index = 1; // Input file doesn't give edge indices so supply them.
			
			String[] values1 = line.split(",");
			if(line.charAt(0) == 'e') // If line starts with e you've hit a set of edges.
			{
				try
				{
					line = in.nextLine(); // Get what should be the next edge line.
					lineNum++;
				}
				catch(java.util.NoSuchElementException e) // Need another edge line to finish edge.
				{
					throw new InvalidLayoutException("Layout error in file at line: " + lineNum, lineNum);
				}
				
				if(line.charAt(0) != 'e') // Need two edge lines to properly create an edge.
					throw new InvalidLayoutException("Layout error in file at line: " + lineNum, lineNum);

			}
			
			while(line.charAt(0) == 'e') // If the entry was an edge enter loop to connect multiple edges.
			{
				String[] values2 = line.split(",");
				
				try
				{
					EntryVertex start = this.vertices.get(Integer.parseInt(values1[1]));
					EntryVertex end = this.vertices.get(Integer.parseInt(values2[1]));
					EntryColor color = this.colors.get(Integer.parseInt(values2[2]));
					
					EntryEdge edge = new EntryEdge(index, start, end, color);
					temp.add(edge);
				}
				catch(Exception e)
				{
					throw new InvalidLayoutException("Layout error in file at line: " + lineNum,lineNum);
				}
				
				index++;
				values1 = values2; // Set second edge entry as previous edge entry to allow connecting.
				
				if(in.hasNextLine())
				{
					line = in.nextLine();
					lineNum++;
				}
				else
					break;
				
			} // line is no longer an edge entry so the set of edges is finished.
			if(!temp.isEmpty())
				this.edges.add(temp); // Add set of edges to the list of edge lists
			
		}while(in.hasNextLine());
	}

	public LayoutBundle load() throws IOException, InvalidLayoutException
	{
		makeVertices();	
		makeColors();
		
		makeCircles();
		makeEdges();
		
		return new LayoutBundle(this.edges, this.circles);
	}
	
	private Scanner openFile() throws FileNotFoundException
	{
		Scanner in = new Scanner(new File(this.filename));
		return in;
	}
}
