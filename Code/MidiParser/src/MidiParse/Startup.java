package midiParse;

import java.util.Scanner;

public class Startup
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		MidiParser p = new MidiParser();
		boolean isRunning = true;
		while(isRunning)
		{
			System.out.print("FileName >> ");
			String input = scan.nextLine();
			if(input.equalsIgnoreCase("exit"))
				isRunning = false;
			else 
			{
				p.parseFile(input + ".mid");
			}
			
			System.out.println("\n\n\n");
		}
		scan.close();
	}
}
