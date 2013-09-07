package midiParse;

import javax.jws.Oneway;

public class Note implements Comparable<Note>{
	private final NoteName noteName;
	private final int octave;
	private double duration;
	
	Note(NoteName noteName) {
		this.noteName = noteName;
		this.octave = 0;
	}
	
	public Note(NoteName noteName, int octave) {
		this.noteName = noteName;
		this.octave = octave;
	}
	
	public NoteName note() {
		return noteName;
	}
	
	public int getOctave() {
 		return octave;
	}
	
	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public int getDistance() {
		return octave * (Notes.getNoteIndex(noteName) + 1);
	}
	
		public Note getMidNote(Note n) {
			int numSemiTones = (getSemitonesBetweenNote(n)/2) + 1;
			Note smallestNote = (this.compareTo(n) < 0) ? this : n;
			int noteIndex = Notes.getNoteIndex(smallestNote.noteName);
			int newOctave = smallestNote.getOctave();
			
			for(int i = 0; i < numSemiTones; i++){
				noteIndex++;
				if(noteIndex >= Notes.NUMBEROFNOTES){
					noteIndex = 0;
					newOctave++;
				}
			}
			
			return new Note(Notes.getNoteName(noteIndex), newOctave);
		}


	
	public int getSemitonesBetweenNote(Note note){
		int comparison = this.compareTo(note);
		Note thisNote = this;
		
		if(comparison < 0){
			return semitonesBetweenNotes(thisNote, note);
		}
		else if(comparison > 0){
			return semitonesBetweenNotes(note, thisNote);
		}
		else return 0;
	}
	
	private int semitonesBetweenNotes(Note lesserNote, Note greaterNote){
		int noteIndex = Notes.getNoteIndex(lesserNote.noteName);
		int newOctave = lesserNote.getOctave();
		int semitones = 0;
		
		while(lesserNote.compareTo(greaterNote) < 0){
			noteIndex++;
			if(noteIndex >= Notes.NUMBEROFNOTES){
				newOctave++;
				noteIndex = 0;
			}
			NoteName name = Notes.getNoteName(noteIndex);
			lesserNote = new Note(name,newOctave);
			semitones++;
		}
		
		return semitones;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		
		if(obj instanceof Note)
		{
			Note o = (Note)obj;
			if(octave == o.getOctave() && noteName.equals(o.note()))
				isEqual = true;
		}
		
		return isEqual;
	}

	@Override
	public int compareTo(Note o) {
		int comparedValue = 0;
		
		if(this.octave > o.octave) {
			comparedValue = 1;
		}
		else if(this.octave < o.octave) {
			comparedValue = -1;
		} 
		else {
			int noteCompareVal = this.noteName.compareNote(o.noteName);
			
			if(noteCompareVal < 0)
				comparedValue = -1;
			else if(noteCompareVal > 0)
				comparedValue = 1;
			else
				comparedValue = 0;
		}
		
		return comparedValue;
	}
	
	@Override
	public String toString() {
		return noteName.name() + "" + octave;
	}
}
