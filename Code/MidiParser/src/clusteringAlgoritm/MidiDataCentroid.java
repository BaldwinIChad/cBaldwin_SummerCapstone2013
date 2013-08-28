package clusteringAlgoritm;

import java.util.ArrayList;

import midiParse.Note;
import midiParse.NoteName;

public class MidiDataCentroid extends MidiFileData{
	String clusterName;
	boolean hasMoved = true;
	
	ArrayList<MidiFileData> pointsInCluster = new ArrayList<>();
	Note leastFrequentNote = new Note(NoteName.C, -1);
	Note mostFrequentNote  = new Note(NoteName.C, -1);
	
	public MidiDataCentroid(String name) {
		this.clusterName = name;
	}
	
	public MidiDataCentroid(String name, MidiFileData...datas ) {
		this.clusterName = name;
	}
	
	public boolean addMidiData(MidiFileData d){
		boolean added = false;
		
		if(!pointsInCluster.contains(d)){
			pointsInCluster.add(d);
			repositionCentroid();
			added = true;
		}
		return added;
	}
	
	public void repositionCentroid(){
		this.BPM = averageBPM();
		this.averageNoteDuration = averageNoteDurations();
		this.songLength = averageSongLength();
		this.totalNumOfNotes = averageTotalNotes();
		this.highestNote = averageHighestNote();
		this.lowestNote = averageLowestNote();
		this.longestNote = averageLongestNote();
		this.shortestNote = averageShortestNote();
	}
	
	
	private Note averageShortestNote() {
		Note currentNote = this.getShortestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			 newNote = newNote.getMidNote(d.getShortestNote());
		
		if(hasMoved == false)
			hasMoved = (newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private Note averageLongestNote() {
		Note currentNote = this.getLongestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			newNote = newNote.getMidNote(d.getLongestNote());

		if(hasMoved == false)
			hasMoved = (newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private Note averageLowestNote() {
		Note currentNote = this.getLowestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			newNote = newNote.getMidNote(d.getLowestNote());
		
		if(hasMoved == false)
			hasMoved = (newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private Note averageHighestNote(){
		Note currentNote = this.getHighestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			newNote = newNote.getMidNote(d.getHighestNote());
		
		if(hasMoved == false)
			hasMoved = (newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private long averageTotalNotes(){
		int total = pointsInCluster.size();
		double averageNotes = 0.0;
		double currentAverage = this.totalNumOfNotes;
		
		for(MidiFileData d : pointsInCluster)
			averageNotes += d.getTotalNumOfNotes();
		
		long average = (long) (averageNotes / total);
		
		if(hasMoved == false)
			hasMoved = (currentAverage == );
		
		return 
	}
	
	private double averageSongLength(){
		int total = pointsInCluster.size();
		double averageLength = 0.0;
		
		for(MidiFileData d : pointsInCluster){
			averageLength += d.getSongLength();
		}
		
		return roundTo5thDecimal(averageLength / total);
	}
	
	private double averageBPM(){
		int total = pointsInCluster.size();
		double bpm = 0.0;
		
		for(MidiFileData d : pointsInCluster) {
			bpm += d.getBPM();
		}
		
		return roundTo5thDecimal(bpm / total);
	}
	
	private double averageNoteDurations(){
		int total = pointsInCluster.size();
		double averageNoteDuration = 0.0;
		
		for(MidiFileData d : pointsInCluster) {
			averageNoteDuration += d.getAverageNoteDuration();
		}
		
		return roundTo5thDecimal(averageNoteDuration / total);
	}
	
	private double roundTo5thDecimal(double number) {
		double factor = 1e5;
		double result = (Math.round(number * factor) / factor);
		return result;
	}
	
	public boolean hasMoved() {
		boolean moved = hasMoved;
		hasMoved = false;
		return moved;
	}
	
	
}
