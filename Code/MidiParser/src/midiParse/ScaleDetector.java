package midiParse;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ScaleDetector {
	//Supervised Learning
	
	HashMap<String, float[]> scales = new HashMap<String, float[]>();
	
	public String detectScale(String[] notesString) {
		Note[] notes = convertFormattedString(notesString);
		
		Note currentNote = null;
		Note previousNote = notes[0];
		float[] steps = new float[notes.length - 1];
		
		for(int i = 1; i<notes.length; i++)
		{
			currentNote = notes[i];
			if(currentNote != null && previousNote != null)
			{
				float x = getNoteDistance(currentNote);
				float y = getNoteDistance(previousNote);
				steps[i - 1] = x - y;
			}
			previousNote = currentNote;
		}
		
		String toReturn = getClosestScale(steps);
		
		if(toReturn == null){
			toReturn = nameScale();
			scales.put(toReturn, steps);
		}
		
		return toReturn;
	}
	
	private String nameScale() {
		Scanner s = new Scanner(System.in);
		System.out.println("What scale was this?");
		String name = s.nextLine();
		return name;
	}
	
	private Note[] convertFormattedString(String[] notesString) {
		Note[] notes = new Note[notesString.length];
		
		for(int i = 0; i<notesString.length;i++){
			if(notesString[i] != null){
				String[] values = notesString[i].split(";");
				notes[i] = new Note(values[0], Integer.parseInt(values[1]));			
			}
		}
		
		return notes;
	}
	
	private float getNoteDistance(Note note) {
		return (note.getOctave() * 10) + Notes.getNoteIndex(note.note());
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
