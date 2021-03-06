package clusteringAlgoritm;

import java.util.ArrayList;

import midiParse.Note;
import midiParse.NoteName;

public class MidiDataCentroid extends MidiFileData{
	String clusterName;
	boolean hasMoved = true;
	
	ArrayList<MidiFileData> pointsInCluster = new ArrayList<>();

	Note leastFrequentNote = new Note(NoteName.C, 1);
	Note mostFrequentNote  = new Note(NoteName.C, 1);
	
	public MidiDataCentroid(String name) {
		this.clusterName = name;
	}
	
	public MidiDataCentroid(String name, MidiFileData...datas ) {
		this.clusterName = name;
	}
	
	public boolean containsSongTitle(String title){
		for(MidiFileData d : pointsInCluster){
			if(d.getFileName().equalsIgnoreCase(title))
				return true;
		}
		
		return false;
	}
	
	public boolean addMidiData(MidiFileData d){
		boolean added = false;
		
		if(!pointsInCluster.contains(d) && d.isVisited() == false){
			pointsInCluster.add(d);
			added = true;
			d.setVisited(true);
		}
		
		repositionCentroid();
		
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
		this.mostFrequentNote = averageMostFrequent();
		this.leastFrequentNote = averageLeastFrequent();
	}
	
	private Note averageMostFrequent() {
		Note currentNote = this.getMostFrequentNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			 newNote = newNote.getMidNote(d.getMostFrequentNote());
		
		if(hasMoved == false)
			hasMoved = !(newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}

	private Note averageLeastFrequent() {
		Note currentNote = this.getLeastFrequentNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			 newNote = newNote.getMidNote(d.getLeastFrequentNote());
		
		if(hasMoved == false)
			hasMoved = !(newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}

	private Note averageShortestNote() {
		Note currentNote = this.getShortestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			 newNote = newNote.getMidNote(d.getShortestNote());
		
		if(hasMoved == false)
			hasMoved = !(newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private Note averageLongestNote() {
		Note currentNote = this.getLongestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			newNote = newNote.getMidNote(d.getLongestNote());

		if(hasMoved == false)
			hasMoved = !(newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	public Note getLeastFrequentNote() {
		return leastFrequentNote;
	}
	
	public void setLeastFrequentNote(Note leastFrequentNote) {
		this.leastFrequentNote = leastFrequentNote;
	}
	
	public Note getMostFrequentNote() {
		return mostFrequentNote;
	}
	
	public void setMostFrequentNote(Note mostFrequentNote) {
		this.mostFrequentNote = mostFrequentNote;
	}
	
	private Note averageLowestNote() {
		Note currentNote = this.getLowestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			newNote = newNote.getMidNote(d.getLowestNote());
		
		if(hasMoved == false)
			hasMoved = !(newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private Note averageHighestNote(){
		Note currentNote = this.getHighestNote();
		Note newNote = currentNote;
		
		for(MidiFileData d : pointsInCluster)
			newNote = newNote.getMidNote(d.getHighestNote());
		
		if(hasMoved == false)
			hasMoved = !(newNote.compareTo(currentNote) == 0);
		
		return newNote;
	}
	
	private long averageTotalNotes(){
		int total = pointsInCluster.size() + 1;
		double currentAverage = this.totalNumOfNotes;
		double averageNotes = currentAverage;
		
		for(MidiFileData d : pointsInCluster)
			averageNotes += d.getTotalNumOfNotes();
		
		long average = (long) (averageNotes / total);
		
		if(hasMoved == false)
			hasMoved = !(currentAverage == average);
		
		return average;
	}
	
	private double averageSongLength(){
		int total = pointsInCluster.size() + 1;
		double currentAverage = this.songLength;
		double averageLength = currentAverage;
		
		for(MidiFileData d : pointsInCluster){
			averageLength += d.getSongLength();
		}
		
		averageLength = roundTo5thDecimal(averageLength) / total;
		
		if(hasMoved == false)
			hasMoved = !(currentAverage == averageLength);
		
		return averageLength;
	}
	
	private double averageBPM(){
		int total = pointsInCluster.size() + 1;
		double currentBPM = this.BPM;
		double bpm = currentBPM;
		
		for(MidiFileData d : pointsInCluster) {
			bpm += d.getBPM();
		}
		
		bpm = roundTo5thDecimal(bpm)/total;
		
		if(hasMoved = false)
			hasMoved = !(currentBPM == bpm);
		
		return bpm;
	}
	
	private double averageNoteDurations(){
		int total = pointsInCluster.size()+1;
		double currentAverage = this.averageNoteDuration;
		double averageNoteDuration = currentAverage;
		
		for(MidiFileData d : pointsInCluster) {
			averageNoteDuration += d.getAverageNoteDuration();
		}
		
		 averageNoteDuration = roundTo5thDecimal(averageNoteDuration) / total;
		
		if(hasMoved == false)
			hasMoved = !(currentAverage == averageNoteDuration);
		
		return averageNoteDuration;
	}
	
	private double roundTo5thDecimal(double number) {
		double factor = 1e5;
		double result = (Math.round(number * factor) / factor);
		return result;
	}
	
	public void resetMoved(){
		hasMoved = true;
	}
	
	public boolean hasMoved() {
		boolean moved = hasMoved;
		hasMoved = false;
		return moved;
	}
}
