package lzw;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

class Main
{
	private static final int BYTE_STATES = 256;
	private static final int BITS_MIN    =   9;
	private static final int BITS_MAX    =  14;

	private static void pln(String s) { System.out.println(s); }

	private static long fsiz(String file) { return new File(file).length(); }

	private static String fnam(String s, int bits) { return s + "." + bits; }

	private static void del(String s) { new File(s).delete(); }

	private static int chkhdr(InputStream in) throws IOException
	{
		byte[] hdr = new byte[4];
		if(in.read(hdr) != 4 || hdr[0] != 'L' ||
			hdr[1] != 'Z' || hdr[2] != 'W')
		{
			return -1;
		}

		int bits = hdr[3];
		if(bits < BITS_MIN || bits > BITS_MAX)
		{
			return -1;
		}

		return bits;
	}

	private static void ren(String s, String d)
	{
		new File(s).renameTo(new File(d));
	}

	private static void wrhdr(OutputStream o, int bits) throws IOException
	{
		o.write(new byte[] { 'L', 'Z', 'W', (byte)(bits) });
	}

	private static void wrbin(OutputStream o, int val, int bits)
		throws IOException
	{

	}

	private static int rdbin(InputStream in, int bits)
		throws IOException
	{
		return 0;
	}

	private static void encode(String infn, String outfn, int bits)
		throws IOException
	{
		int code = BYTE_STATES;
		int nc = 1 << bits;
		int eof = nc - 1;

		try (OutputStream out = new FileOutputStream(fnam(outfn, bits)))
		{
			wrhdr(out, bits);

			try (InputStream in = new FileInputStream(infn))
			{
			}

			wrbin(out, eof, bits);
		}
	}

	private static void decode(String infn, String outfn)
		throws IOException
	{
		try (InputStream in = new FileInputStream(infn))
		{
			int bits = chkhdr(in);
			if(bits < 0)
			{
				pln("Invalid file header");
				return;
			}

			pln("Decoding " + bits + " bit LZW ...");

			try (OutputStream out = new FileOutputStream(fnam(outfn, bits)))
			{
				int nc = 1 << bits;
				int eof = nc - 1;
				String[] st = new String[L];
				int i;
				for (i = 0; i < BYTE_STATES; ++i)
				{
					st[i] = "" + (char)i;
				}
/*
				int codeword = rdbin(in, bits);
				if(codeword == R) return;
				String val = st[codeword];
				while(true)
				{
					wrbin(val);
					codeword = rdbin(in, bits);
					if(codeword == R) break;
					String s = st[codeword];
					if (i == codeword) s = val + val.charAt(0);
					if (i < L) st[i++] = val + s.charAt(0);
					val = s;
				}
*/
			}
		}
	}

	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			pln("Usage: ./lzw encode/decode input-file output-file");
			return;
		}

		try
		{
			String option = args[0];
			String input = args[1];
			String output = args[2];
			if(option.equals("decode"))
			{
				decode(input, output);
			}
			else if(option.equals("encode"))
			{
				pln("Encoding ...");
				for(int bits = BITS_MIN; bits < BITS_MAX; ++bits)
				{
					pln(bits + " bit");
					encode(input, output, bits);
				}

				pln("Analyzing file sizes ...");
				long minsiz = Long.MAX_VALUE;
				int optbits = -1;
				for(int bits = BITS_MIN; bits < BITS_MAX; ++bits)
				{
					long siz = fsiz(fnam(output, bits));
					pln(bits + " bit: " + siz + " bytes");
					if(siz < minsiz)
					{
						optbits = bits;
						minsiz = siz;
					}
				}

				pln("Optimal compressed size: " + minsiz +
					" bytes (" + optbits + " bits LZW)");

				ren(fnam(output, optbits), output);
				for(int bits = BITS_MIN; bits < BITS_MAX; ++bits)
				{
					if(bits != optbits) { del(fnam(output, bits)); }
				}
			}
			else
			{
				pln("Invalid option. Must be either encode or decode.");
				return;
			}
		}
		catch(IOException e)
		{
			pln("I/O error");
		}
	}
}
