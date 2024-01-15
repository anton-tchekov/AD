all:
	nasm -f elf64 main.s
	ld -s -o main main.o
	objdump -d main
