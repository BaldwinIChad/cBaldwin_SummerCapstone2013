package clusteringAlgoritm;

import java.util.ArrayList;
import java.util.Random;

import midiParse.Note;
import midiParse.NoteName;
import midiParse.Notes;

public class Clumper {
	private final int NUMBER_OF_ERAS = 3;
	private final int MAX_BPM = 4801;
	private final int MAX_OCTAVE = 15;
	private final int MAX_NOTE_DURATION = 90; //seconds
	private final int MAX_SONG_LENGTH = 30; //minutes
	private final int MAX_NUMBER_OF_NOTES = 3000000;
	
	private Random gen = new Random();
	
	MidiDataCentroid[] centroids = new MidiDataCentroid[NUMBER_OF_ERAS];
	ArrayList<MidiFileData> allData = new ArrayList<MidiFileData>();
	
	public Clumper(){
		for(int i = 0; i < NUMBER_OF_ERAS; i++){
			centroids[i] = new MidiDataCentroid(Integer.toString(i));
		}
		generateRandomCentroids();
	}
	
	public void addDataPoint(MidiFileData d) {
		int index = 0;
		allData.add(d);
		
		while(hasCentroidMoved()) {
			if(index > allData.size() - 1)
				index = 0;
			
			MidiDataCentroid center = findNearestCentroid(d);
			center.addMidiData(allData.get(index++));
		}
		
		for(MidiDataCentroid c : centroids)
			c.resetMoved();
	}
	
	private void generateRandomCentroids() {
		for(MidiDataCentroid c : centroids){
			c.setBPM(gen.nextInt(MAX_BPM));
			c.setAverageNoteDuration(gen.nextInt(MAX_NOTE_DURATION));
			c.setSongLength(gen.nextInt(MAX_SONG_LENGTH));
			c.setTotalNumOfNotes(gen.nextInt(MAX_NUMBER_OF_NOTES));
		
			NoteName note = Notes.getNoteName(gen.nextInt(12));
			
			c.setHighestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLowestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLongestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLongestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));		
		}
	}

	public void addData(MidiFileData[] d) {
		//add a bunch of things!
	}
	
	private boolean hasCentroidMoved() {
		boolean hasMoved = false;
		
		for(MidiDataCentroid c : centroids){
			if(c.hasMoved() == true){
				hasMoved = true;
				break;
			}
		}
		
		return hasMoved;		
	}
	
	private int getCentroidIndexContainPoint(MidiFileData d){
		int index = -1;
		
		for(int i = 0; i < centroids.length; i++) {
			if(centroids[i].containsSongTitle(d.getFileName())){
				index = i;
				break;				
			}
		}
		
		return index;
	}
	
	private MidiDataCentroid findNearestCentroid(MidiFileData data) {
		ArrayList<MidiDataCentroid> shortest = new ArrayList<>();
		double shortestDistance = Double.MAX_VALUE;
		
		for(MidiDataCentroid c : centroids) {
			double distance = c.getDistance(data);
			
			if(distance < shortestDistance){
				shortest.clear();
				shortest.add(c);
				shortestDistance = distance;
			}else if(distance == shortestDistance){
				shortest.add(c);
			}
		}
		
		MidiDataCentroid toReturn;
		if(shortest.size() > 1){
			toReturn = shortest.get(gen.nextInt(shortest.size()));
		} else toReturn = shortest.get(0);
		
		return toReturn;
	}

	public void printClusterData() {
		for(int i = 0; i < centroids.length; i ++){
			System.out.println("-----------------------CLUSTER " + i + "-----------------------------");
			for(MidiFileData d : centroids[i].pointsInCluster){
				System.out.println(d.getFileName());
			}
		}
	}
}
