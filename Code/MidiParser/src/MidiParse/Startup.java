package midiParse;

import java.util.Scanner;

import clusteringAlgoritm.Clumper;
import clusteringAlgoritm.MidiFileData;

public class Startup
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		MidiParser p = new MidiParser();
		Clumper c = new Clumper();
		boolean isRunning = true;
		while(isRunning)
		{
			System.out.print("FileName >> ");
			String input = scan.nextLine();
			if(input.equalsIgnoreCase("exit"))
				isRunning = false;
			else 
			{
				MidiFileData d = p.parseFile(input + ".mid");
				c.addDataPoint(d);
				
				System.out.println("BPM: " + d.getBPM());
				System.out.println("Song Length: " + d.getSongLength());
				System.out.println("# notes: " + d.getTotalNumOfNotes());
				System.out.println("Avg. NoteLength: " + d.getAverageNoteDuration());
				System.out.println("Highest Note: " + d.getHighestNote());
				System.out.println("Lowest Note: " + d.getLowestNote());
				System.out.println("Frequency: " + d.getMostFrequentNote());
				System.out.println("LongestNote: " + d.getLongestNote());
				System.out.println("ShortestNote: " + d.getShortestNote());
			}
			c.printClusterData();
			System.out.println("\n\n\n");
		}
		scan.close();
	}
}
