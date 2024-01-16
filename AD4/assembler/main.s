; Shittily coded LZW Compression in x86_64 assembly
; Accepts input from stdin and writes compressed file to stdout
; Uses exactly 3 linux system calls: read, write and exit

BITS 64

STDIN         equ    0
STDOUT        equ    1
STDERR        equ    2

SYS_READ      equ    0
SYS_WRITE     equ    1
SYS_EXIT      equ   60

SUCCESS       equ    0
FAILURE       equ    1

BYTE_STATES   equ  256

DICT_BITS     equ   12
DICT_SIZE     equ     (1 << DICT_BITS)

; ------------------------------------------
; ------------------ DATA ------------------
; ------------------------------------------
section .data

out_bits: dq 8
out_buf: dq 0
wr_idx: dq 0

rd_idx: dq 0
rd_max: dq 0

; ------------------------------------------
; ------------------ BSS -------------------
; ------------------------------------------
section .bss

; Read and write buffer size
BUF_SIZE equ 16 ; 0x100000
RDBUF_SIZE equ BUF_SIZE
WRBUF_SIZE equ BUF_SIZE
TABLE_SIZE equ (64 * 1024)

rdbuf: resb RDBUF_SIZE
wrbuf: resb WRBUF_SIZE
cword: resb DICT_SIZE
xbuf: resb 32
table: resd (16 * TABLE_SIZE)

; ------------------------------------------
; ------------------ TEXT ------------------
; ------------------------------------------
section .text

global _start

_start:
	; === Initialize dictionary ===
	xor   r9d, r9d
	mov   byte [xbuf + 1], 0
fill_dict:
	mov   byte [xbuf], r9b
	mov   eax, xbuf
	mov   ebx, r9d
	call  dict_put
	inc   r9d
	cmp   r9d, BYTE_STATES
	jl    fill_dict

	; === Write dictionary size in 4 bits ===
	mov   eax, 4
	mov   ebx, DICT_BITS
	call  write_bits

read_loop:
	call  in_read ; Read bytes until end of file (-1) reached
	cmp   eax, 0
	jl    read_loop_exit

	push  rax

	mov   [cword + r10], al ; Append char
	inc   r10
	mov   [cword + r10], byte 0

	mov   rax, cword        ; If contained, continue
	call  dict_get
	cmp   rax, 0
	jge   dict_contained

	cmp   r9, DICT_SIZE     ; if(dict_entries < dict_size)
	jge   dict_full
	mov   rax, cword        ; dict.put(next, dict_entries++);
	mov   rbx, r9
	call  dict_put
	inc   r9
dict_full:

	dec   r10               ; Write code for prev word to output
	mov   [cword + r10], byte 0
	mov   rax, cword
	call  dict_get

	; call printn

	; write_bits(out, dict_size_bits, dict.get(word));
	mov   rbx, rax
	mov   eax, DICT_BITS
	call  write_bits

	pop   rax
	mov   [cword], al           ; next = "" + (char)cur_byte;
	mov   [cword + 1], byte 0
	mov   r10, 1

dict_contained:
	jmp   read_loop
read_loop_exit:

	call  out_flush
	call  exit

; ------------------------------------
; --- Immediately exit
exit:
	mov   eax, SYS_EXIT
	mov   edi, SUCCESS
	syscall

; ------------------------------------
; --- Read one byte from input buffer
in_read:
	mov   r15, [rd_max]
	mov   r14, [rd_idx]
	cmp   r14, r15
	jl    rdbuf_full
	mov   eax, SYS_READ
	mov   edi, STDIN
	mov   esi, rdbuf
	mov   edx, RDBUF_SIZE
	syscall
	cmp   eax, 0
	jle   in_read_end
	mov   [rd_max], rax
	xor   r14, r14
rdbuf_full:
	movzx rax, byte [rdbuf + r14]
	inc   r14
	jmp   in_rd_exit
in_read_end:
	mov   rax, -1
in_rd_exit:
	mov   [rd_idx], r14
	ret

; ------------------------------------
; --- Write one byte to output buffer
; rax: Input byte
out_write:
	mov   r13, [wr_idx]
	mov   byte [wrbuf + r13], al
	inc   r13
	cmp   r13, WRBUF_SIZE
	jl    wrbuf_empty
	mov   eax, SYS_WRITE
	mov   edi, STDOUT
	mov   esi, wrbuf
	mov   edx, WRBUF_SIZE
	syscall
	xor   r13, r13
wrbuf_empty:
	mov   [wr_idx], r13
	ret

; ------------------------------------
; --- Flush output buffer
out_flush:
	mov   r13, [out_bits]
	mov   r14, [out_buf]
	cmp   r13, 0
	jg    bits_zero
	mov   rax, r14
	call  out_write
bits_zero:

	mov   eax, SYS_WRITE
	mov   edi, STDOUT
	mov   esi, wrbuf
	mov   rdx, [wr_idx]
	syscall
	ret

; ------------------------------------
; --- Write bits to output buffer
; rax: bits
; rbx: value
write_bits:
	xor   r12, r12
	mov   r13, [out_bits]
	mov   r14, [out_buf]
write_bits_loop:
	cmp   r12, rax
	jge   write_bits_exit

	dec   r13
	mov   r15, 1
	mov   rcx, r12
	shl   r15, cl
	test  r15, rbx
	jz    not_one
	mov   r15, 1
	mov   rcx, r13
	shl   r15, cl
	or    r14, r15
not_one:
	test  r13, r13
	jnz   not_full

	push  rax
	push  rbx
	push  r13
	mov   rax, r14
	call  out_write
	; call printn
	pop   r13
	pop   rbx
	pop   rax

	xor   r14, r14
	mov   r13, 8
not_full:
	inc   r12
	jmp   write_bits_loop
write_bits_exit:
	mov   [out_bits], r13
	mov   [out_buf], r14
	ret

; ------------------------------------
; --- Simple hash function
; rax: Pointer to string
hash:
	mov   r15d, 5381
hash_loop:
	movzx rcx, byte [rax]
	inc   rax
	test  rcx, rcx
	je    hash_loop_exit
	mov   rdx, r15
	shl   rdx, 5
	add   r15, rdx
	add   r15, rcx
	jmp   hash_loop
hash_loop_exit:
	mov   rax, r15
	ret

; ------------------------------------
; --- Insert key value pair into hash table
; rax: Pointer to string
; rbx: Value to insert
dict_put:
	call  hash
	mov   r8, rax
	and   r8, (TABLE_SIZE - 1)
dict_put_loop:
	mov   rcx, table
	mov   r15, r8
	shl   r15, 4
	add   rcx, r15
	mov   rdx, [rcx]
	test  rdx, rdx
	jnz   dict_spot_full
	mov   [rcx], rax
	mov   [rcx + 8], rbx
	ret
dict_spot_full:
	inc   r8
	and   r8, (TABLE_SIZE - 1)
	jmp   dict_put_loop

; ------------------------------------
; --- Retrieve value for key from hash table
; rax: Pointer to string
dict_get:
	call  hash
	mov   r8, rax
	and   r8, (TABLE_SIZE - 1)
dict_get_loop:
	mov   rcx, table
	mov   r15, r8
	shl   r15, 4
	add   rcx, r15
	mov   rdx, [rcx]
	test  rdx, rdx
	jnz   dict_not_zero
	mov   rax, -1
	ret
dict_not_zero:
	cmp   rdx, rax
	jne   dict_not_found
	mov   rax, [rcx + 8]
	ret
dict_not_found:
	inc   r8
	and   r8, (TABLE_SIZE - 1)
	jmp   dict_get_loop

; ------------------------------------
; --- Print a number to stderr
; rax: Integer to print
printn:
	xor   r15d, r15d
	mov   esi, xbuf + 30
	inc   r15d
	mov   byte [esi], 10
printn_loop:
	test  rax, rax
	je    printn_loop_exit
	dec   esi
	inc   r15d
	xor   edx, edx
	mov   ebx, 10
	div   rbx
	add   edx, '0'
	mov   byte [esi], dl
	jmp printn_loop
printn_loop_exit:
	mov   eax, SYS_WRITE
	mov   edi, STDERR
	mov   edx, r15d
	syscall
	ret
