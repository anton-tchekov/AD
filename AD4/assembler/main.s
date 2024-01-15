BITS 64

STDIN         equ    0
STDOUT        equ    1

SYS_READ      equ    0
SYS_WRITE     equ    1
SYS_EXIT      equ   60

SUCCESS       equ    0

BYTE_STATES   equ  256

DICT_BITS     equ   12
DICT_SIZE     equ     (1 << DICT_BITS)

; ------------------------------------------
; ------------------ DATA ------------------
; ------------------------------------------
section .data


; ------------------------------------------
; ------------------ BSS -------------------
; ------------------------------------------
section .bss

; 1 MiB read buffer
RDBUF_SIZE equ 0x100000
WRBUF_SIZE equ 0x100000

rdbuf: resb RDBUF_SIZE
wrbuf: resb WRBUF_SIZE
cword: resb DICT_SIZE

; ------------------------------------------
; ------------------ TEXT ------------------
; ------------------------------------------
section .text

global _start

; LZW Compression
; Accepts input from stdin and writes compressed file to stdout
; Uses exactly 3 linux system calls: read, write and exit
_start:
	; Register usages:
	xor r14, r14 ; Read buffer pointer
	xor r15, r15 ; Read buffer stored bytes
	xor r13, r13 ; Current word length

	; Initialize dictionary
	xor ebx, ebx
fill_dict:
	call dict_put
	inc ebx
	cmp ebx, BYTE_STATES
	jl fill_dict

; 	String word = "";
; 	for(;;)
; 	{
; 		String next = word + (char)cur_byte;
; 		if(!dict.containsKey(next))
; 		{
; 			if(dict_entries < dict_size)
; 			{
; 				dict.put(next, dict_entries++);
; 			}
; 			next = "" + (char)cur_byte;
; 		}
; 		word = next;
; 	}
;


	; Write dictionary size in 4 bits
	mov eax, DICT_BITS
	mov ebx, 4
	call write_bits

read_loop:
	; Read bytes until end of file (-1) reached
	call in_read
	cmp eax, 0
	jle read_loop_exit

	call helloworld

	; IF not contained in dict
	mov eax, 42 ; dict.get(word)
	mov ebx, DICT_BITS
	call write_bits

	jmp read_loop
read_loop_exit:

; 	if(word.length() > 0)
; 	{
; 		write_bits(out, dict_size_bits, dict.get(word));
; 	}
;
; 	output_end(out);

	; Exit program
	mov eax, SYS_EXIT
	mov edi, SUCCESS
	syscall

; ------------------------------------
; --- Read one byte from input buffer
in_read:
	cmp r14, r15
	jl rdbuf_full

	; If read buffer empty, read next chunk
	mov eax, SYS_READ
	mov edi, STDIN
	mov esi, rdbuf
	mov edx, RDBUF_SIZE
	syscall

	; If result negative, return it
	cmp eax, 0
	jne no_eof
	ret
no_eof:

	; Save number of bytes read
	mov r15, rax
	xor r14, r14
rdbuf_full:

	movzx rax, byte [rdbuf + r14]
	inc r14
	ret
; ------------------------------------
; ------------------------------------

helloworld:
	mov eax, SYS_WRITE
	mov edi, STDOUT
	mov esi, msg
	mov edx, MSG_LEN
	syscall
	ret

write_bits:
	ret

dict_put:
	call helloworld
	ret

write_buf:
	mov eax, SYS_WRITE
	mov edi, STDOUT
	mov esi, msg
	mov edx, MSG_LEN
	syscall



; ------------------------------------------
; ----------------- RODATA -----------------
; ------------------------------------------
section .rodata

msg db "Hello World", 10
MSG_LEN equ $ - msg

; AD & ISS
; SE1 & BS