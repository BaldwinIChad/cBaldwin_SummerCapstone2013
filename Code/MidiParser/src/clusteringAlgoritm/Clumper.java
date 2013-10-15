package clusteringAlgoritm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import midiParse.Note;
import midiParse.NoteName;
import midiParse.Notes;

public class Clumper {
	private final int MIN_BPM = 25;
	static final int NUMBER_OF_ERAS = 3;
	private final int MAX_BPM = 300;
	private final int MAX_OCTAVE = 13;
	private final int MAX_NOTE_DURATION = 15; //seconds
	private final int MAX_SONG_LENGTH = 15; //minutes
	private final int MAX_NUMBER_OF_NOTES = 20000;
	private final int NUM_OF_LOOPS = 30;
	
	private Random gen = new Random();
	
	MidiDataCentroid[] centroids = new MidiDataCentroid[NUMBER_OF_ERAS];
	ArrayList<MidiFileData> allData = new ArrayList<MidiFileData>();
	ArrayList<ClusterResult> results = new ArrayList<ClusterResult>();
	
	public Clumper(){
		for(int i = 0; i < NUMBER_OF_ERAS; i++){
			centroids[i] = new MidiDataCentroid(Integer.toString(i));
		}
		generateRandomCentroids();
	}
	
	public void addDataPoint(MidiFileData d) {
		int index = 0;
		int dataSize = allData.size();
		allData.add(d);
		boolean loopedOnce = false;
		
		while(hasCentroidMoved() || !loopedOnce) {
			if(index > dataSize){
				index = 0;
				loopedOnce = true;
			}
			
			MidiDataCentroid center = findNearestCentroid(d);
			center.addMidiData(allData.get(index++));
		}
		
		for(MidiDataCentroid c : centroids)
			c.resetMoved();
	}
	
	private void resetAllDataVisited(){
		for(MidiFileData d : allData)
			d.setVisited(false);
	}
	
	private void addResult(){
		ClusterResult newResult = new ClusterResult(centroids);
		boolean notSimilar = true;
		
		for(ClusterResult r : results){
			if(r.equals(newResult)){
				r.occurances++;
				notSimilar = false;
			}
		}
		
		if(notSimilar)
			results.add(newResult);
	}
	
	private void generateRandomCentroids() {
		for(MidiDataCentroid c : centroids){
			c.pointsInCluster.clear();
			c.setBPM(MIN_BPM + (Math.random() * ((MAX_BPM - MIN_BPM) + 1)));
			c.setAverageNoteDuration(Math.random() * MAX_NOTE_DURATION);
			c.setSongLength(Math.random() * MAX_SONG_LENGTH);
			c.setTotalNumOfNotes(gen.nextInt(MAX_NUMBER_OF_NOTES));
		
			NoteName note = Notes.getNoteName(gen.nextInt(12));
			
			c.setHighestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLowestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLongestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLongestNote(new Note(note, gen.nextInt(MAX_OCTAVE)));		
			note = Notes.getNoteName(gen.nextInt(12));
			c.setMostFrequentNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
			note = Notes.getNoteName(gen.nextInt(12));
			c.setLeastFrequentNote(new Note(note, gen.nextInt(MAX_OCTAVE)));
		}
	}

	public void addData(MidiFileData[] dataArray) {
		resetAllDataVisited();
		generateRandomCentroids();
		allData.clear();
		for (MidiFileData midiFileData : dataArray) {
			allData.add(midiFileData);
		}
		int index = 0;
		boolean loopedOnce = false;
		int allDataSize = allData.size();
		
		while(hasCentroidMoved() || !loopedOnce){
			if(index >= allDataSize) {
				index = 0;
				loopedOnce = true;
			}
			MidiFileData d = allData.get(index);
			MidiDataCentroid c = findNearestCentroid(d);
			c.addMidiData(d);
			index++;
		}
	}
	
	public void printResult(){
		ClusterResult cr = new ClusterResult(centroids);
		int maxOccurances = 0;
		
		for(ClusterResult r : results)
			if(r.occurances > maxOccurances){
				cr = r;
				maxOccurances = r.occurances;
			}
		MidiDataCentroid[] results = cr.clusters;
		for(int i = 0; i < results.length; i ++){
			System.out.println("-----------------------CLUSTER " + i + "-----------------------------");
			System.out.println("BPM: " + results[i].getBPM());
			System.out.println("Song Length: " + results[i].getSongLength());
			System.out.println("# notes: " + results[i].getTotalNumOfNotes());
			System.out.println("Avg. NoteLength: " + results[i].getAverageNoteDuration());
			System.out.println("Highest Note: " + results[i].getHighestNote());
			System.out.println("Lowest Note: " + results[i].getLowestNote());
			System.out.println("Frequency: " + results[i].getMostFrequentNote());
			System.out.println("LongestNote: " + results[i].getLongestNote());
			System.out.println("ShortestNote: " + results[i].getShortestNote());
			System.out.println("----------Songs-------------");
			for(MidiFileData d : results[i].pointsInCluster){
				System.out.println(d.getFileName());
			}
		}
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
	
//	private int getCentroidIndexContainPoint(MidiFileData d){
//		int index = -1;
//		
//		for(int i = 0; i < centroids.length; i++) {
//			if(centroids[i].containsSongTitle(d.getFileName())){
//				index = i;
//				break;				
//			}
//		}
//		
//		return index;
//	}
	
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
			System.out.println("BPM: " + centroids[i].getBPM());
			System.out.println("Song Length: " + centroids[i].getSongLength());
			System.out.println("# notes: " + centroids[i].getTotalNumOfNotes());
			System.out.println("Avg. NoteLength: " + centroids[i].getAverageNoteDuration());
			System.out.println("Highest Note: " + centroids[i].getHighestNote());
			System.out.println("Lowest Note: " + centroids[i].getLowestNote());
			System.out.println("Frequency: " + centroids[i].getMostFrequentNote());
			System.out.println("LongestNote: " + centroids[i].getLongestNote());
			System.out.println("ShortestNote: " + centroids[i].getShortestNote());
			System.out.println("----------Songs-------------");
			for(MidiFileData d : centroids[i].pointsInCluster){
				System.out.println(d.getFileName());
			}
		}
	}
}
