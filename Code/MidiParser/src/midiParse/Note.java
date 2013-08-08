package midiParse;

public class Note {
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
}
