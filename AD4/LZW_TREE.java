package trees;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LZW
{
	final int BITS_IN_BYTE = 8;
	final int BYTE_STATES = (1 << BITS_IN_BYTE);

	static int in_bits = 0, in_buf = 0;

  public LZW()
  {

  }

	private static int read_bit(InputStream in)
		throws IOException
	{
		if(in_bits == 0)
		{
			in_buf = in.read();
			if(in_buf < 0) { return -1; }
			in_bits = 8;
		}

		--in_bits;
		return (in_buf & (1 << in_bits)) != 0 ? 1 : 0;
	}

	private static int read_bits(InputStream in, int bits)
		throws IOException
	{
		int result = 0;
		for(int dict_entries = 0; dict_entries < bits; ++dict_entries)
		{
			int b = read_bit(in);
			if(b < 0) { return -1; }
			result |= (b << dict_entries);
		}

		/* System.out.println("Read " + bits + " bits: " +
			Integer.toBinaryString(result)); */
		return result;
	}

	static int out_bits = 8, out_buf = 0;
	private static void write_bits(OutputStream out, int bits, int value)
		throws IOException
	{
		/* System.out.println("Write " + bits + " bits: " +
			Integer.toBinaryString(value)); */
		for(int dict_entries = 0; dict_entries < bits; ++dict_entries)
		{
			--out_bits;
			if((value & (1 << dict_entries)) != 0)
			{
				out_buf |= (1 << out_bits);
			}

			if(out_bits == 0)
			{
				out.write(out_buf);
				out_buf = 0;
				out_bits = 8;
			}
		}
	}

	private static void output_end(OutputStream out) throws IOException
	{
		if(out_bits > 0)
		{
			out.write(out_buf);
		}
	}

	public void compress(String infn, String outfn, int dict_size_bits)
		throws IOException
	{
		//HashMap<String, Integer> dict = new LinkedHashMap<String, Integer>();
    BSTree<String, Integer> dict = new BSTree<String, Integer>();
		for(int dict_entries = 0; dict_entries < BYTE_STATES; ++dict_entries)
		{
			//dict.put(Character.toString(dict_entries), dict_entries);
      dict.insert(Character.toString(dict_entries), dict_entries);
		}

		int dict_size = (1 << dict_size_bits);
		int dict_entries = BYTE_STATES;
		String word = "";
		try(OutputStream out = new FileOutputStream(outfn))
		{
			write_bits(out, 4, dict_size_bits);
			try(InputStream in = new FileInputStream(infn))
			{
				while(true)
				{
					int curByte = in.read();
					if(curByte < 0)
					{
						break;
					}

					String next = word + (char)curByte;
					if(!dict.contains(next))
					{
						write_bits(out, dict_size_bits, dict.get(word));
						if(dict_entries < dict_size)
						{
							//dict.put(next, dict_entries++);
              dict.insert(next, dict_entries++);
						}
						next = "" + (char)curByte;
					}
					word = next;
				}

				if(word.length() > 0)
				{
					write_bits(out, dict_size_bits, dict.get(word));
				}
			}

			output_end(out);
		}
	}

	public static void write_str(OutputStream out, String s) throws IOException
	{
		char[] ca = s.toCharArray();
		for(int i = 0; i < ca.length; ++i)
		{
			out.write(ca[i]);
		}
	}

	public void decompress(String infn, String outfn) throws IOException
	{
		try(OutputStream out = new FileOutputStream(outfn))
		{
			try(InputStream in = new FileInputStream(infn))
			{
				int dict_size_bits = read_bits(in, 4);
				int dict_size = (1 << dict_size_bits);
				int dict_entries = 0;
				String[] dict = new String[dict_size];
				for(dict_entries = 0; dict_entries < BYTE_STATES; ++dict_entries)
				{
					dict[dict_entries] = "" + (char)dict_entries;
				}

				int codeword = read_bits(in, dict_size_bits);
				if(codeword < 0) return;
				String val = dict[codeword];
				while(true)
				{
					write_str(out, val);
					codeword = read_bits(in, dict_size_bits);
					if(codeword < 0) break;
					String s = dict[codeword];
					if(dict_entries == codeword)
					{
						s = val + val.charAt(0);
					}

					if(dict_entries < dict_size)
					{
						dict[dict_entries++] = val + s.charAt(0);
					}

					val = s;
				}
			}
		}
	}
}
