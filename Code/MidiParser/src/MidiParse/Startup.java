package midiParse;

import java.util.Scanner;

public class Startup
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		MidiParser p = new MidiParser();
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
				System.out.println("BPM: " + d.getBPM());
				System.out.println("Song Length: " + d.getSongLength());
				System.out.println("# notes: " + d.getTotalNumOfNotes());
				System.out.println("Avg. NoteLength: " + d.getAverageNoteDuration());
				System.out.println("Highest Note: " + d.getHighestNote());
				System.out.println("Lowest Note: " + d.getLowestNote());
				
				String mostFrequent = "";
				
				for(String s : d.getMostFrequentNotes())
					mostFrequent += s + ",";
				
				System.out.println("Frequency: " + mostFrequent);
				System.out.println("LongestNote: " + d.getLongestNote());
				System.out.println("ShortestNote: " + d.getShortestNote());
			}
			
			System.out.println("\n\n\n");
		}
		scan.close();
	}
}
