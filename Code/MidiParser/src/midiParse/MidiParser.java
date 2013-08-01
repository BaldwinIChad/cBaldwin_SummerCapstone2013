package midiParse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiParser {
	private static final int TEMPO_MESSAGE_VALUE = 81;
	private static final int MIRCOSECONDS_IN_MINUTE = 60000000;
	private final String NOTE_ON = "on";
	private final String NOTE_OFF = "off";
	private final int MAX_NOTE_ARRAY_SIZE = 12;
	
	char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	ScaleDetector scaleDetector = new ScaleDetector();
	File saveFile = new File("C:\\Users\\cbaldwin\\Desktop\\MidiOutput.txt");
	ArrayList<String> notesForScaleDetection = new ArrayList<String>();
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
						if(status.equals(NOTE_ON))detectionSetup(note, octave);
						
						if(channel != currentChannel) {
							currentChannel = channel;
							output = "\r\n\r\nChannel:" + channel + "\r\n\tNote:" + note + octave + "\\Status:" + status + "\\Velocity:" + velocity + "\r\n";
							
							
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
						if(a.getType() == TEMPO_MESSAGE_VALUE)
						{
							double bpm = getBPM(a.getData());
							output = "METADATA--------- BPM:" + bpm + "\r\n";
						}
						else
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
		
		System.out.println(scaleDetector.detectScale(getNoteArray()));
		//notesForScaleDetection.clear();
		notesForScaleDetection = new ArrayList<String>();
		scaleDetectionIndex = 0;
		return saveFile;
	}
	
	private double getBPM(byte[] b) {
		char[] hexChars = new char[b.length * 2];
		int v;
		for(int i = 0; i < b.length; i++) {
			v = b[i] & 0xFF;
			hexChars[i * 2] = hexArray[v >>> 4];
			hexChars[i * 2 + 1] = hexArray[v & 0x0F];
		}
		String hexValue = new String(hexChars);
		double microSecsPerQuarterNote = Integer.parseInt(hexValue, 16);
		return (MIRCOSECONDS_IN_MINUTE/microSecsPerQuarterNote);
	}
	
	private String[] getNoteArray() {
		String[] elements = new String[notesForScaleDetection.size()];
		notesForScaleDetection.toArray(elements);
		return elements;
	}
	
	private void detectionSetup(String note, int octave) {
		if(scaleDetectionIndex<MAX_NOTE_ARRAY_SIZE)
		{
			 notesForScaleDetection.add(note + ";" + octave + ";");
			 scaleDetectionIndex++;
		}else if(scaleDetectionIndex > MAX_NOTE_ARRAY_SIZE) {
			System.out.println(scaleDetector.detectScale(getNoteArray()));
			//notesForScaleDetection.clear();
			notesForScaleDetection = new ArrayList<String>();
			scaleDetectionIndex = 0;
		}
	}
}
