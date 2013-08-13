package midiParse;

import java.util.HashMap;

public class MidiFIleData {
	/*averageNote duration expressed in seconds, songLength
	expressed in minutes*/
	private double BPM, averageNoteDuration, songLength;
	private long totalNumOfNotes;
	private Note highestNote, lowestNote;
	private HashMap<Note, Long> noteFrequencies;
	
	public MidiFIleData(){
		BPM = 0;
		averageNoteDuration = 0;
		songLength = 0;
		totalNumOfNotes = 0;
		Note floorNote = new Note(NoteName.C, 0);
		highestNote = lowestNote = floorNote;
	}
	
	public void setBPM(double bpm) {
		this.BPM = bpm;
	}
	
	public double getBPM() {
		return BPM;
	}
	
	public void setSongLength(double length) {
		this.songLength = length;
	}
	
	public double getSongLength() {
		return songLength;
	}
	
	public double getAverageNoteDuration() {
		averageNoteDuration = songLength / totalNumOfNotes;
		return averageNoteDuration;
	}

	public long getTotalNumOfNotes() {
		return totalNumOfNotes;
	}

	public Note getHighestNote() {
		return highestNote;
	}

	public Note getLowestNote() {
		return lowestNote;
	}

	public Long getNoteFrequencies(Note note) {
		return noteFrequencies.get(note);
	}

	public void addNote(Note note) {
		if(noteFrequencies.containsKey(note)) {
			long currentFrequency = noteFrequencies.get(note) + 1;
			if(false){
				
			}
		} else {
			
		}
	}
}
