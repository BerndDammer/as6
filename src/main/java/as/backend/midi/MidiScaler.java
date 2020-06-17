package as.backend.midi;

import as.globals.Globals;

/**
 * takes the 16 max pot values 
 * converts into 16 scaled float values
 * prepares 16 values for fast Thread reading
 * 
 * all 3 function calls can be from different Threads

 * @author manni1user
 *
 */
public class MidiScaler {
	public void setPotLimits(float[][] limits)
	{
		
	}

	public void newInput(int channel, int value)
	{
		
	}
	/*
	 * gets a mirror of the last pot values
	 * the resulting field must only be changed
	 * at the next call
	 * the last return value is automatically invalid at the next get call
	 * 
	 * @return pot values
	 */
	public float[] getPots()
	{
		return new float[Globals.MAX_POTS];
	}
}
