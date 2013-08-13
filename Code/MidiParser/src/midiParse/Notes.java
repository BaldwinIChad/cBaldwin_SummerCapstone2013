package midiParse;

public class Notes {
	private static final int NUMBEROFNOTES = 12;
	private static Note[] notes = {
		new Note(NoteName.C),
		new Note(NoteName.Cs),
		new Note(NoteName.D),
		new Note(NoteName.Ds), 
		new Note(NoteName.E), 
		new Note(NoteName.F), 
		new Note(NoteName.Fs),
		new Note(NoteName.G),
		new Note(NoteName.Gs), 
		new Note(NoteName.A),
		new Note(NoteName.As),
		new Note(NoteName.B)
	};
	
	public static Note getNote(int value) {
		return notes[value];
	}
	
	public static int getNoteIndex(NoteName note) {
		int toReturn = -1;
		
		for(int i = 0; i < NUMBEROFNOTES; i++)
			if(notes[i].note() == note)
				toReturn = i;
		
		return toReturn;
	}
	
	public static NoteName getNoteName(int value) {
		return notes[value].note();
	}
}
