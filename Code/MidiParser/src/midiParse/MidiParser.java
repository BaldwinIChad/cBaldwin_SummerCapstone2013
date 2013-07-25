package midiParse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiParser {
	private final String NOTE_ON = "on";
	private final String NOTE_OFF = "off";
	private final int MAX_NOTE_ARRAY_SIZE = 4;
	ScaleDetector scaleDetector = new ScaleDetector();
	File saveFile = new File("C:\\Users\\cbaldwin\\Desktop\\MidiOutput.txt");
	String[] notesForScaleDetection = new String[MAX_NOTE_ARRAY_SIZE];
	int scaleDetectionIndex = 0;
	
	public File parseFile(String fileName)
	{
		Sequence seq;
		try {
			PrintWriter writer = new PrintWriter(saveFile);
			seq = MidiSystem.getSequence(new File(fileName));
			for(Track track : seq.getTracks()) {
				int currentChannel = -1;
				for(int eventIndex = 0; eventIndex < track.size(); eventIndex++) {
					MidiEvent event = track.get(eventIndex);
					MidiMessage message = event.getMessage();
					String output;
					
					if(message instanceof ShortMessage) {
						ShortMessage a = (ShortMessage)message;
						
						int channel = a.getChannel();
						int velocity = a.getData2();
						int key = a.getData1();
						int octave = (key/MAX_NOTE_ARRAY_SIZE);
						
						String note = Notes.getNoteName(key % MAX_NOTE_ARRAY_SIZE);
						String status = (a.getCommand() == ShortMessage.NOTE_ON) ? NOTE_ON : NOTE_OFF;
						
						if(channel != currentChannel) {
							currentChannel = channel;
							output = "\r\n\r\nChannel:" + channel + "\r\n\tNote:" + note + octave + "\\Status:" + status + "\\Velocity:" + velocity + "\r\n";
							
							if(status.equals(NOTE_ON))detectionSetup(note, octave);
							
							System.out.print(output);
							writer.print(output);
						}
						else {
							output = "\tNote:" + note + octave + " \\Status:" + status + " \\Velocity:" + velocity + "\r\n";
							System.out.print(output);
							writer.print(output);
						}
						
						writer.flush();
					}
					else if(message instanceof MetaMessage) {
						MetaMessage a = (MetaMessage)message;
						output = "METADATA--------- " + new String(a.getMessage()) + "\r\n";
						System.out.print(output);
						writer.print(output);
					}
				}
			}
			writer.close();
		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace();
		}	
		
		return saveFile;
	}
	
	private void detectionSetup(String note, int octave) {
		if(scaleDetectionIndex<MAX_NOTE_ARRAY_SIZE)
		{
			 notesForScaleDetection[scaleDetectionIndex] = note + ";" + octave + ";";
			 scaleDetectionIndex++;
		}else if(scaleDetectionIndex > MAX_NOTE_ARRAY_SIZE) {
			scaleDetector.detectScale(notesForScaleDetection);
			notesForScaleDetection = new String[MAX_NOTE_ARRAY_SIZE];
		}
	}
}
