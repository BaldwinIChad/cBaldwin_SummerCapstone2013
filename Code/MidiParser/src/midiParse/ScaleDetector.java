package midiParse;

import java.util.HashMap;
import java.util.Set;

public class ScaleDetector {
	//Supervised Learning
	
	HashMap<String, float[]> scales = new HashMap<String, float[]>();
	
	public String detectScale(String[] notesString) {
		Note[] notes = convertFormattedString(notesString);
		
		Note currentNote = null;
		Note previousNote = notes[0];
		float[] steps = new float[notes.length];
		
		for(int i = 1; i<notes.length; i++)
		{
			currentNote = notes[i];
			steps[i - 1] = getNoteDistance(currentNote) - getNoteDistance(previousNote);
			previousNote = currentNote;
		}
		
		String toReturn = getClosestScale(steps);
		
		//if(toReturn == null)
			//toReturn = nameScale();
		
		return toReturn;
	}
	
	private Note[] convertFormattedString(String[] notesString) {
		Note[] notes = new Note[notesString.length];
		
		for(int i = 0; i<notesString.length;i++){
			String[] values = notesString[i].split(";");
			notes[i] = new Note(values[0], Integer.parseInt(values[1]));
		}
		
		return notes;
	}
	
	private float getNoteDistance(Note note) {
		return Notes.getNoteIndex(note.note()) * note.getOctave();
	}
	
	private String getClosestScale(float[] newScale) {
		Set<String> keys = scales.keySet();
		String scaleKey = null;
		
		for(String key : keys) {
			if(compareSteps(scales.get(key), newScale))
				scaleKey = key;
		}
		return scaleKey;	
	}
	
	private boolean compareSteps(float[] scale, float[] inputScale) {
		boolean isSimilar = false;
		int count = 0;
		int halfOfScaleSteps = scale.length/2;
		
		for(int i = 0; i < scale.length && i < inputScale.length; i++){
			if(scale[i] == inputScale[i])
				count++;
		}
		if(count >= halfOfScaleSteps)
			isSimilar = true;
		
		return isSimilar;
	}
}
