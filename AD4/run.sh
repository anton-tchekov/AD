javac -Xlint:unchecked AD4.java

# java Main huffman compress 2023-wise-adp-04.pdf out1.pdf.huf
# java Main huffman decompress out1.pdf.huf out1.pdf
#
# diff 2023-wise-adp-04.pdf out1.pdf
#
# java Main lzw compress 2023-wise-adp-04.pdf out2.pdf.lzw
# java Main lzw decompress out2.pdf.lzw out2.pdf
#
# diff 2023-wise-adp-04.pdf out2.pdf

# java Main huffman compress examples/big.txt out3.txt.huf
# java Main huffman decompress out3.txt.huf out3.txt

# diff examples/big.txt out3.txt

java Main lzw compress examples/big.txt out4.txt.lzw
java Main lzw decompress out4.txt.lzw out4.txt

diff examples/big.txt out4.txt
