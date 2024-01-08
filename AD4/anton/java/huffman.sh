javac -Xlint:unchecked huffman/Main.java
#java huffman/Main encode ../../2023-wise-adp-04.pdf out.huf
java huffman/Main encode big.txt out.huf
java huffman/Main decode out.huf dec.txt
