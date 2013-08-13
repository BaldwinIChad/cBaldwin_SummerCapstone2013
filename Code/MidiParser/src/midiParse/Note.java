package midiParse;

public class Note implements Comparable<Note>{
	private final String note;
	private final int octave;
	
	Note(String note) {
		this.note = note;
		this.octave = 0;
	}
	
	Note(String note, int octave) {
		this.note = note;
		this.octave = octave;
	}
	
	public String note() {
		return note;
	}
	
	public int getOctave() {
 		return octave;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		
		if(obj instanceof Note)
		{
			Note o = (Note)obj;
			if(octave == o.getOctave() && note.equals(o.note()))
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
			int noteCompareVal = note.compareToIgnoreCase(o.note);
			
			if(noteCompareVal > 0)
				comparedValue = 1;
			else if(noteCompareVal < 0)
				comparedValue = -1;
			else
				comparedValue = 0;
		}
		
		return comparedValue;
	}
	
	
}
