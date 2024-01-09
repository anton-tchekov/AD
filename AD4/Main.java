package trees;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		/*
		Huffman huffy = new Huffman();
		//huffy.testPriorityQueue();
		huffy.readFile("/home/haron/Desktop/AD2/input.txt");
		huffy.calculateCharacterFrequencies();
		huffy.buildHuffmanTree();
		TreePrinter.printTree(huffy.getRoot());
		huffy.calculateCodeFromHuffmanTree();
		huffy.encodeDataFile("/home/haron/Desktop/AD2/output.txt");
		*/

		LZW lzw = new LZW();
		lzw.compress("../test.pdf", "../output.hzip", 10);
		lzw.decompress("../output.hzip", "../output.pdf");
	}
}
