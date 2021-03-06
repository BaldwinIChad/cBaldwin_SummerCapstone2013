package midiParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;

import clusteringAlgoritm.Clumper;
import clusteringAlgoritm.MidiFileData;

public class Startup
{
	static ArrayList<MidiFileData> data = new ArrayList<>();
	
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
			else if(input.equalsIgnoreCase("cluster")){
				MidiFileData[] dataArray = new MidiFileData[data.size()];
				data.toArray(dataArray);
				readdData(dataArray);
				c.addData(dataArray);
				c.printClusterData();
			}
			else 
			{
				MidiFileData d;
				try {
					d = p.parseFile(input + ".mid");
					data.add(d);
					
					System.out.println("BPM: " + d.getBPM());
					System.out.println("Song Length: " + d.getSongLength());
					System.out.println("# notes: " + d.getTotalNumOfNotes());
					System.out.println("Avg. NoteLength: " + d.getAverageNoteDuration());
					System.out.println("Highest Note: " + d.getHighestNote());
					System.out.println("Lowest Note: " + d.getLowestNote());
					System.out.println("Most Frequent: " + d.getMostFrequentNote());
					System.out.println("Least Frequent: " + d.getLeastFrequentNote());
					System.out.println("LongestNote: " + d.getLongestNote());
					System.out.println("ShortestNote: " + d.getShortestNote());
					
				} catch (InvalidMidiDataException | IOException e) {
					System.out.println("File was not found or corrupt.");
				}
			}
			System.out.println("\n");
		}
		scan.close();
	}

	private static void readdData(MidiFileData[] a) {
		for(MidiFileData d : a)
			data.add(d);
	}
}
