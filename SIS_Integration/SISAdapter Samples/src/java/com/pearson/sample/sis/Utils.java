package com.pearson.sample.sis;

/**
 * @author toddf
 * @since Sep 10, 2012
 */
public final class Utils
{
	public static String join(char sep, String... strings)
	{
		if (strings == null) return "";
		
		StringBuffer b = new StringBuffer();
		
		for (int i = 0; i < strings.length; ++i)
		{
			b.append(strings[i]);
			
			if (i + 1 < strings.length)
			{
				b.append(sep);
			}
		}

		return b.toString();
	}
}
