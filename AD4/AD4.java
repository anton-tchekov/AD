import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Queue;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

class BSTree<K extends Comparable<K>, E>
{
	class Node<K, E>
	{
		private K key;
		private E value;
		private Node<K, E> left, right;

		public Node(K k, E e)
		{
			key = k;
			value = e;
		}

		public void print(int spaces)
		{
			for(int i = 0; i < spaces; ++i)
			{
				System.out.print(" ");
			}

			System.out.println(key + ": " + value);
			spaces += 5;
			if(left != null)
			{
				left.print(spaces);
			}

			if(right != null)
			{
				right.print(spaces);
			}
		}
	}

	private int count;
	private Node<K, E> root;

	public void put(K k, E e)
	{
		int cv;
		Node<K, E> cur;

		assert k != null;

		if(root == null)
		{
			root = new Node<K, E>(k, e);
			++count;
			return;
		}

		cur = root;
		for(;;)
		{
			cv = k.compareTo(cur.key);
			if(cv == 0)
			{
				cur.value = e;
				return;
			}
			else if(cv < 0)
			{
				if(cur.left == null)
				{
					cur.left = new Node<K, E>(k, e);
					++count;
					return;
				}

				cur = cur.left;
			}
			else
			{
				if(cur.right == null)
				{
					cur.right = new Node<K, E>(k, e);
					++count;
					return;
				}

				cur = cur.right;
			}
		}
	}

	private E _get(K k)
	{
		int cv;
		Node<K, E> cur, next;

		assert k != null;

		cur = root;
		if(cur == null)
		{
			return null;
		}

		while((cv = k.compareTo(cur.key)) != 0)
		{
			if((next = (cv < 0) ? cur.left : cur.right) == null)
			{
				return null;
			}

			cur = next;
		}

		return cur.value;
	}

	public E get(K k) throws NoSuchElementException
	{
		E e;
		if((e = _get(k)) == null)
		{
			throw new NoSuchElementException();
		}

		return e;
	}

	public int size()
	{
		return count;
	}

	public boolean containsKey(K k)
	{
		return _get(k) != null;
	}

	public void print()
	{
		if(root != null)
		{
			root.print(0);
		}
	}
}

class BinaryTree<D extends Comparable<D>>
	implements Comparable<BinaryTree<D>>
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

class Main
{
	private static final int BITS_IN_BYTE = 8;
	private static final int BYTE_STATES = (1 << BITS_IN_BYTE);
	private static final int BITS_MIN = 9;
	private static final int BITS_MAX = 14;
	private static boolean DEBUG = false;

	/* ----- HELPERS ----- */
	private static void pln(String s)
	{
		System.out.println(s);
	}

	private static long fsiz(String file)
	{
		return new File(file).length();
	}

	private static String fnam(String s, int bits)
	{
		return s + "." + bits;
	}

	private static void del(String s)
	{
		new File(s).delete();
	}

	private static void ren(String src, String dst)
	{
		new File(src).renameTo(new File(dst));
	}

	private static void pfsiz(long size)
	{
		pln("File size: " + size + " bytes");
	}

	/* ------ READ/WRITE FROM/TO STREAMS ------ */
	static int buf, bbits = 8;
	private static void wrbin(OutputStream out, String val)
		throws IOException
	{
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

	private static long rdlong(InputStream in) throws IOException
	{
		long a, b, c, d, e, f, g, h;
		a = in.read(); b = in.read(); c = in.read(); d = in.read();
		e = in.read(); f = in.read(); g = in.read(); h = in.read();
		return a | (b << 8) | (c << 16) | (d << 24) |
			(e << 32) | (f << 40) | (g << 48) | (h << 56);
	}

	static int in_bits = 0, in_buf = 0;
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
		return (in_buf >> in_bits) & 1;
	}

	static int out_bits = 8, out_buf = 0;
	private static void write_bits(OutputStream out, int bits, int value)
		throws IOException
	{
		/* pln("Write " + bits + " bits: " +
			Integer.toBinaryString(value)); */
		for(int i = 0; i < bits; ++i)
		{
			--out_bits;
			if((value & (1 << i)) != 0)
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

	private static void rw_reset()
	{
		in_bits = 0;
		in_buf = 0;
		out_bits = 8;
		out_buf = 0;
	}

	public static void write_str(OutputStream out, String s) throws IOException
	{
		char[] ca = s.toCharArray();
		for(int i = 0; i < ca.length; ++i)
		{
			out.write(ca[i]);
		}
	}

	private static int read_bits(InputStream in, int bits)
		throws IOException
	{
		int result = 0;
		for(int i = 0; i < bits; ++i)
		{
			int b = read_bit(in);
			if(b < 0) { return -1; }
			result |= (b << i);
		}

		/* pln("Read " + bits + " bits: " +
			Integer.toBinaryString(result)); */
		return result;
	}

	private static void output_end(OutputStream out) throws IOException
	{
		if(out_bits > 0)
		{
			out.write(out_buf);
		}
	}

	/* ----- HUFFMAN ----- */
	private static BinaryTree<HuffNode> buildHuffmanTree(long[] freq)
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

	private static void traverseHuffmanTree(BinaryTree<HuffNode> root,
		String path, String[] code)
	{
		if(root.isLeaf())
		{
			int chr = root.data.chr;
			code[chr] = path;
			if(DEBUG)
			{
				pln(chr + " : " + path);
			}
			return;
		}

		traverseHuffmanTree(root.left, path + "0", code);
		traverseHuffmanTree(root.right, path + "1", code);
	}

	private static void huffman_compress(String infn, String outfn)
		throws IOException
	{
		rw_reset();

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

		if(DEBUG)
		{
			pfsiz(size);
			pln("Frequencies:");
			for(int i = 0; i < BYTE_STATES; ++i)
			{
				pln(i + " : " + freq[i]);
			}
		}

		BinaryTree<HuffNode> root = buildHuffmanTree(freq);
		if(DEBUG)
		{
			root.print(0);
		}

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

			/* 64-bit file size support */
			wrlong(out, size);
			for(int i = 0; i < BYTE_STATES; ++i)
			{
				wrlong(out, freq[i]);
			}

			/* Encode data file */
			if(DEBUG)
			{
				pln("\n----------\n");
			}

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

	private static void huffman_decompress(String infn, String outfn)
		throws IOException
	{
		rw_reset();
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
			if(DEBUG)
			{
				root.print(0);
			}

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
						s = "";
					}
					else
					{
						if(read_bit(in) == 0)
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

	/* ----- LZW ----- */
	public static void lzw_compress(String infn, String outfn,
		int dict_size_bits) throws IOException
	{
		rw_reset();
		HashMap<String, Integer> dict = new LinkedHashMap<String, Integer>();
		/* BSTree<String, Integer> dict = new BSTree<String, Integer>(); */
		int dict_entries;
		for(dict_entries = 0; dict_entries < BYTE_STATES; ++dict_entries)
		{
			dict.put(Character.toString(dict_entries), dict_entries);
		}

		int dict_size = (1 << dict_size_bits);
		String word = "";
		try(OutputStream out = new FileOutputStream(outfn))
		{
			pln("dict_size_bits = " + dict_size_bits);
			write_bits(out, 4, dict_size_bits);
			try(InputStream in = new FileInputStream(infn))
			{
				for(;;)
				{
					int cur_byte = in.read();
					if(cur_byte < 0)
					{
						break;
					}

					String next = word + (char)cur_byte;
					if(!dict.containsKey(next))
					{
						write_bits(out, dict_size_bits, dict.get(word));
						if(dict_entries < dict_size)
						{
							dict.put(next, dict_entries++);
						}
						next = "" + (char)cur_byte;
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

	public static void lzw_decompress(String infn, String outfn)
		throws IOException
	{
		rw_reset();
		try(OutputStream out = new FileOutputStream(outfn))
		{
			try(InputStream in = new FileInputStream(infn))
			{
				int dict_size_bits = read_bits(in, 4);
				pln("dict_size_bits = " + dict_size_bits);
				int dict_size = (1 << dict_size_bits);
				int dict_entries;
				String[] dict = new String[dict_size];
				for(dict_entries = 0; dict_entries < BYTE_STATES; ++dict_entries)
				{
					dict[dict_entries] = "" + (char)dict_entries;
				}

				int codeword = read_bits(in, dict_size_bits);
				if(codeword < 0) return;
				String val = dict[codeword];
				for(;;)
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

	public static boolean is_option_compress(String s)
	{
		if(s.equals("compress"))
		{
			return true;
		}
		else if(s.equals("decompress"))
		{
			return false;
		}

		pln("Invalid option. Must be either compress or decompress.");
		System.exit(1);
		return false;
	}

	/* ----- MAIN ----- */
	public static void main(String[] args)
	{
		if(args.length != 4)
		{
			pln("Usage: ./ad4 lzw/huffman compress/decompress input-file output-file");
			return;
		}

		try
		{
			String method = args[0];
			boolean do_compress = is_option_compress(args[1]);
			String input = args[2];
			String output = args[3];
			if(method.equals("lzw"))
			{
				if(do_compress)
				{
					pln("Compressing ...");
					for(int bits = BITS_MIN; bits < BITS_MAX; ++bits)
					{
						pln(bits + " bit");
						lzw_compress(input, fnam(output, bits), bits);
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
						if(bits != optbits)
						{
							del(fnam(output, bits));
						}
					}
				}
				else
				{
					lzw_decompress(input, output);
				}
			}
			else if(method.equals("huffman"))
			{
				if(do_compress)
				{
					pln("Compressing ...");
					huffman_compress(input, output);
				}
				else
				{
					pln("Decompressing ...");
					huffman_decompress(input, output);
				}
			}
			else
			{
				pln("Invalid compression method");
			}
		}
		catch(IOException e)
		{
			pln("I/O error");
		}
	}
}
