package clusteringAlgoritm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import midiParse.Note;
import midiParse.NoteName;

public class MidiFileData {
	/*averageNote duration expressed in seconds, songLength
	expressed in minutes*/
	private String songTitle;
	protected double BPM;
	protected double averageNoteDuration;
	protected double songLength;
	protected long totalNumOfNotes;
	protected Note highestNote, lowestNote, longestNote, shortestNote;
	private HashMap<String, Long> noteFrequencies;
	
	public MidiFileData(){
		BPM = 0;
		averageNoteDuration = 0;
		songLength = 0;
		totalNumOfNotes = 0;
		Note floorNote = new Note(NoteName.C, 0);
		floorNote.setDuration(Double.MAX_VALUE);
		Note celingNote = new Note(NoteName.C, Integer.MAX_VALUE);
		celingNote.setDuration(Double.MIN_VALUE);
		highestNote = floorNote;
		lowestNote = celingNote;
		longestNote = floorNote;
		shortestNote = celingNote;
		noteFrequencies = new HashMap<String, Long>();
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
	
	public Note getShortestNote() {
		return shortestNote;
	}
	
	public Note getLongestNote() {
		return longestNote;
	}
	
	public String getSongTitle() {
		return songTitle;
	}

	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	//Returns the highest, most frequent note
	public Note getMostFrequentNote() {
		ArrayList<Note> notes = new ArrayList<Note>();
		long highestFrequency = 0;
		
		Iterator<String> iterator = noteFrequencies.keySet().iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			
			long currentNoteFrequency = noteFrequencies.get(key);
			
			if(highestFrequency < currentNoteFrequency) {
				notes.clear();
				NoteName note = NoteName.valueOf((String.valueOf(key.charAt(0))));
				int octave = Integer.parseInt(key.substring(1, key.length()));
				notes.add(new Note(note,octave));
			}
			else if(highestFrequency == currentNoteFrequency){
				NoteName note = NoteName.valueOf((String.valueOf(key.charAt(0))));
				int octave = Integer.parseInt(key.substring(1, key.length()));
				notes.add(new Note(note,octave));
				notes.add(new Note(note, octave));
			}
		}
		
		Note toReturn = new Note(NoteName.C, -1);
		
		for(Note n : notes){
			if(n.compareTo(toReturn) > 0)
				toReturn = n;
		}
		
		return toReturn;
	}

	public HashMap<String, Long> getNoteFrequencies() {
		return noteFrequencies;
	}

	public void addNote(Note note) {
		if(highestNote.compareTo(note) > 0)
			highestNote = note;
		else if(lowestNote.compareTo(note) < 0)
			lowestNote = note;
		
		if(note.getDuration() > longestNote.getDuration())
			longestNote = note;
		else if(note.getDuration() < shortestNote.getDuration())
			shortestNote = note;
		
		totalNumOfNotes++;
		averageNoteDuration = songLength / totalNumOfNotes;
		
		long currentFrequency = (noteFrequencies.containsKey(note.toString()))? noteFrequencies.get(note.toString()) : 0;
		
		noteFrequencies.put(note.toString(), currentFrequency + 1);
	}
	
	public double getDistance(MidiFileData d) {
		double result = 0.0;
		
		result += Math.pow((this.BPM - d.BPM), 2);
		result += Math.pow((this.averageNoteDuration - d.averageNoteDuration), 2);
		result += Math.pow((this.songLength - d.songLength), 2);
		result += Math.pow((this.totalNumOfNotes - d.totalNumOfNotes), 2);
		result += Math.pow((this.longestNote.getDuration() - d.longestNote.getDuration()), 2);
		result += Math.pow((this.shortestNote.getDuration() - d.shortestNote.getDuration()), 2);
		result += Math.pow((this.highestNote.getDuration() - d.highestNote.getDuration()), 2);
		result += Math.pow((this.lowestNote.getDuration() - d.lowestNote.getDuration()), 2);
		
		//Skipping most frequent notes, might be a good idea to get the lowestMostFrequent, etc.
		
		//result += Math.pow((this.getMostFrequentNotes(), b)
		
		
		result += Math.pow((this.highestNote.getDistance() - d.getHighestNote().getDistance()), 2);
		result += Math.pow((this.lowestNote.getDistance() - d.getLowestNote().getDistance()), 2);
		result += Math.pow((this.longestNote.getDistance() - d.longestNote.getDistance()), 2);
		result += Math.pow((this.shortestNote.getDistance() - d.shortestNote.getDistance()), 2);
		
		return Math.abs(Math.sqrt(result));
		
	}
}
