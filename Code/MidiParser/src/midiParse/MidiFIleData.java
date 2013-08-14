package midiParse;

import java.util.HashMap;

public class MidiFileData {
	/*averageNote duration expressed in seconds, songLength
	expressed in minutes*/
	private double BPM, averageNoteDuration, songLength;
	private long totalNumOfNotes;
	private Note highestNote, lowestNote;
	private HashMap<Note, Long> noteFrequencies;
	
	public MidiFileData(){
		BPM = 0;
		averageNoteDuration = 0;
		songLength = 0;
		totalNumOfNotes = 0;
		Note floorNote = new Note(NoteName.C, 0);
		highestNote = lowestNote = floorNote;
		noteFrequencies = new HashMap<>();
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

	public HashMap<Note, Long> getNoteFrequencies() {
		return noteFrequencies;
	}

	public void addNote(Note note) {
		if(highestNote.note().compareNote(note.note()) > 0)
			highestNote = note;
		else if(lowestNote.note().compareNote(note.note()) < 0)
			lowestNote = note;
		
		totalNumOfNotes++;
		averageNoteDuration = songLength / totalNumOfNotes;
		
		long currentFrequency = (noteFrequencies.containsKey(note))? noteFrequencies.get(note) : 0;
		
		noteFrequencies.put(note, currentFrequency + 1);
	}
}
