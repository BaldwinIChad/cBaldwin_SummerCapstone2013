package clusteringAlgoritm;

import java.util.ArrayList;

import midiParse.Note;

public class MidiDataCentroid extends MidiFileData{
	String clusterName;
	boolean hasMoved = true;
	
	ArrayList<MidiFileData> pointsInCluster = new ArrayList<>();
	
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
		return true;
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
		
		for(MidiFileData d : pointsInCluster)
			 currentNote = currentNote.getMidNote(d.getShortestNote());
		
		return currentNote;
	}
	
	private Note averageLongestNote() {
		Note currentNote = this.getLongestNote();
		
		for(MidiFileData d : pointsInCluster)
			currentNote = currentNote.getMidNote(d.getLongestNote());
		
		return currentNote;
	}
	
	private Note averageLowestNote() {
		Note currentNote = this.getLowestNote();
		
		for(MidiFileData d : pointsInCluster)
			currentNote = currentNote.getMidNote(d.getLowestNote());
		
		return currentNote;
	}
	
	private Note averageHighestNote(){
		Note currentNote = this.getHighestNote();
		
		for(MidiFileData d : pointsInCluster)
			currentNote = currentNote.getMidNote(d.getHighestNote());
		
		return currentNote;
	}
	
	private long averageTotalNotes(){
		int total = pointsInCluster.size();
		double averageNotes = 0.0;
		
		for(MidiFileData d : pointsInCluster)
			averageNotes += d.getTotalNumOfNotes();
		
		return (long) (averageNotes / total);
	}
	
	private double averageSongLength(){
		int total = pointsInCluster.size();
		double averageLength = 0.0;
		
		for(MidiFileData d : pointsInCluster){
			averageLength += d.getSongLength();
		}
		
		return averageLength / total;
	}
	
	private double averageBPM(){
		int total = pointsInCluster.size();
		double bpm = 0.0;
		
		for(MidiFileData d : pointsInCluster) {
			bpm += d.getBPM();
		}
		
		return bpm / total;
	}
	
	private double averageNoteDurations(){
		int total = pointsInCluster.size();
		double averageNoteDuration = 0.0;
		
		for(MidiFileData d : pointsInCluster) {
			averageNoteDuration += d.getAverageNoteDuration();
		}
		
		return averageNoteDuration / total;
	}
	
	public boolean hasMoved() {
		boolean moved = hasMoved;
		hasMoved = false;
		return moved;
	}
	
	
}
