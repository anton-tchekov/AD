package huffman;

import java.util.Deque;
import java.util.Queue;
import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

class HuffNode implements Comparable<HuffNode>
{
	public int chr;
	public long freq;

	public HuffNode(int c, long f)
	{
		chr = c;
		freq = f;
	}

	@Override
	public int compareTo(HuffNode other)
	{
		int r = Long.compare(freq, other.freq);
		return (r == 0) ? Integer.compare(chr, other.chr) : r;
	}
}

class BinaryTree<D extends Comparable<D>> implements Comparable<BinaryTree<D>>
{
	public D data;
	public BinaryTree<D> left, right;

	public BinaryTree(D d)
	{
		data = d;
	}

	public BinaryTree(D d, BinaryTree<D> l, BinaryTree<D> r)
	{
		data = d;
		left = l;
		right = r;
	}

	public void print(int nesting)
	{
		for(int i = 0; i < nesting; ++i)
		{
			System.out.print(" ");
		}

		System.out.println("- " + ((HuffNode)data).chr);
		nesting += 2;
		if(left != null) { left.print(nesting); }
		if(right != null) { right.print(nesting); }
	}

	public boolean isLeaf()
	{
		return left == null && right == null;
	}

	@Override
	public int compareTo(BinaryTree<D> other)
	{
		return data.compareTo(other.data);
	}
}

class Main
{
	private static boolean debug = true;
	private static final int BYTE_STATES = 256;

	static BinaryTree<HuffNode> buildHuffmanTree(long[] freq)
	{
		Queue<BinaryTree<HuffNode>> heap = new PriorityQueue<>();
		for(int i = 0; i < BYTE_STATES; ++i)
		{
			if(freq[i] > 0)
			{
				heap.add(new BinaryTree<HuffNode>(new HuffNode(i, freq[i])));
			}
		}

		while(heap.size() > 1)
		{
			BinaryTree<HuffNode> n1, n2;
			n1 = heap.remove();
			n2 = heap.remove();
			heap.add(new BinaryTree<HuffNode>(
				new HuffNode(-1, n1.data.freq + n2.data.freq), n1, n2));
		}

		return heap.remove();
	}

	static void traverseHuffmanTree(BinaryTree<HuffNode> root, String path,
		String[] code)
	{
		if(root.isLeaf())
		{
			int chr = root.data.chr;
			code[chr] = path;
			pln(chr + " : " + path);
			return;
		}

		traverseHuffmanTree(root.left, path + "0", code);
		traverseHuffmanTree(root.right, path + "1", code);
	}

	private static void pln(String s) { System.out.println(s); }

	/* ------ WRITE BITS ------ */
	static int buf, bbits = 8;
	private static void wrbin(OutputStream out, String val)
		throws IOException
	{
		// pln(val);
		for(int i = 0; i < val.length(); ++i)
		{
			--bbits;
			if(val.charAt(i) == '1')
			{
				buf |= (1 << bbits);
			}

			if(bbits == 0)
			{
				out.write(buf);
				buf = 0;
				bbits = 8;
			}
		}
	}

	private static void fini(OutputStream out)
		throws IOException
	{
		if(bbits > 0)
		{
			out.write(buf);
		}
	}

	/* ------ READ BITS ------ */
	static int stor = 0;
	static int sbits = 0;
	private static int rdbit(InputStream in)
		throws IOException
	{
		if(sbits == 0)
		{
			stor = in.read();
			sbits = 8;
		}

		--sbits;
		return stor & (1 << sbits);
	}
	/* ----------------------- */

	private static void pfsiz(long size)
	{
		pln("File size: " + size + " bytes");
	}

	private static void wrlong(OutputStream out, long v) throws IOException
	{
		out.write((int)(v & 0xFF));
		out.write((int)((v >> 8) & 0xFF));
		out.write((int)((v >> 16) & 0xFF));
		out.write((int)((v >> 24) & 0xFF));
		out.write((int)((v >> 32) & 0xFF));
		out.write((int)((v >> 40) & 0xFF));
		out.write((int)((v >> 48) & 0xFF));
		out.write((int)((v >> 56) & 0xFF));
	}

	private static void encode(String infn, String outfn) throws IOException
	{
		/* Calculate character frequencies and count bytes */
		long size = 0;
		long[] freq = new long[BYTE_STATES];
		try(InputStream in = new FileInputStream(infn))
		{
			while(in.available() > 0)
			{
				++freq[in.read()];
				++size;
			}
		}

		int count = 0;
		for(int i = 0; i < BYTE_STATES; ++i)
		{
			if(freq[i] > 0)
			{
				++count;
			}
		}

		if(debug)
		{
			pfsiz(size);
			pln("Frequencies:");
			for(int i = 0; i < BYTE_STATES; ++i)
			{
				pln(i + " : " + freq[i]);
			}
		}

		BinaryTree<HuffNode> root = buildHuffmanTree(freq);
		root.print(0);

		/* Calculate code from huffman tree */
		String[] code = new String[BYTE_STATES];
		traverseHuffmanTree(root, "", code);

		/* Write header, original file size, character frequencies */
		try(OutputStream out = new FileOutputStream(outfn))
		{
			out.write('H');
			out.write('U');
			out.write('F');
			out.write(count);

			/* 64-bit file size support, baby! */
			wrlong(out, size);

			/* We learned good sorting algorithms, but I don't care */
			/* O(n^2) lets go */
			/*for(int i = 0; i < count; ++i)
			{
				long max = -1;
				int mj = -1;
				for(int j = 0; j < BYTE_STATES; ++j)
				{
					long c = freq[j];
					if(c > max)
					{
						max = c;
						mj = j;
					}
				}

				freq[mj] = -1;
				out.write(mj);
			}*/
			for(int i = 0; i < BYTE_STATES; ++i)
			{
				wrlong(out, freq[i]);
			}

			/* Encode data file */
			pln("\n----------\n");
			try(InputStream in = new FileInputStream(infn))
			{
				while(in.available() > 0)
				{
					wrbin(out, code[in.read()]);
				}

				fini(out);
			}
		}
	}

	private static long rdlong(InputStream in) throws IOException
	{
		/* Probably not the way it should be done */
		long a, b, c, d, e, f, g, h;
		a = in.read(); b = in.read(); c = in.read(); d = in.read();
		e = in.read(); f = in.read(); g = in.read(); h = in.read();
		return a | (b << 8) | (c << 16) | (d << 24) |
			(e << 32) | (f << 40) | (g << 48) | (h << 56);
	}

	private static void decode(String infn, String outfn) throws IOException
	{
		try(InputStream in = new FileInputStream(infn))
		{
			/* Check header */
			byte[] hdr = new byte[3];
			in.read(hdr);
			if(hdr[0] != 'H' || hdr[1] != 'U' || hdr[2] != 'F')
			{
				pln("Invalid file header");
				return;
			}

			/* Read character frequencies count */
			int count = in.read();

			/* Read file size */
			long size = rdlong(in);
			pfsiz(size);

			/* Read character frequencies */
			long[] freq = new long[BYTE_STATES];
			int n = BYTE_STATES;
			for(int i = 0; i < BYTE_STATES; ++i)
			{
				freq[i] = rdlong(in);
			}

			/* Decode data file */
			BinaryTree<HuffNode> root = buildHuffmanTree(freq);
			root.print(0);

			try(OutputStream out = new FileOutputStream(outfn))
			{
				BinaryTree<HuffNode> cur = root;
				String s = "";
				for(int i = 0; i < size; )
				{
					if(cur.isLeaf())
					{
						++i;
						out.write(cur.data.chr);
						cur = root;
						//pln(s);
						s = "";
					}
					else
					{
						if(rdbit(in) == 0)
						{
							s += "0";
							cur = cur.left;
						}
						else
						{
							s += "1";
							cur = cur.right;
						}
					}
				}
			}
		}
	}

	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			pln("Usage: ./huffman encode/decode input-file output-file");
			return;
		}

		String option = args[0];
		String input = args[1];
		String output = args[2];
		try
		{
			if(option.equals("decode"))
			{
				System.out.println("Decoding ...");
				decode(input, output);
			}
			else if(option.equals("encode"))
			{
				System.out.println("Encoding ...");
				encode(input, output);
			}
			else
			{
				pln("Invalid option. Must be either encode or decode.");
			}
		}
		catch(IOException e)
		{
			pln("I/O error");
		}
	}
}
