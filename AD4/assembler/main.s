BITS 64

STDIN         equ    0
STDOUT        equ    1

SYS_READ      equ    0
SYS_WRITE     equ    1
SYS_EXIT      equ   60

SUCCESS       equ    0

BYTE_STATES   equ  256

; ------------------------------------------
; ------------------ DATA ------------------
; ------------------------------------------
section .data


; ------------------------------------------
; ------------------ BSS -------------------
; ------------------------------------------
section .bss

; 1 MiB read buffer
RDBUF_SIZE 	equ 0x100000
rdbuf: resb RDBUF_SIZE

; ------------------------------------------
; ------------------ TEXT ------------------
; ------------------------------------------
section .text

global _start

_start:

read_all:
	; mov eax, SYS_READ
	; mov edi, STDIN
	; mov esi, rdbuf
	; mov edx, RDBUF_SIZE
	; syscall



	mov eax, SYS_WRITE
	mov edi, STDOUT
	mov esi, msg
	mov edx, MSG_LEN
	syscall

	mov eax, SYS_EXIT
	mov edi, SUCCESS
	syscall



; ------------------------------------------
; ----------------- RODATA -----------------
; ------------------------------------------
section .rodata

msg db "Hello World", 10
MSG_LEN equ $ - msg

; AD & ISS
; SE1 & BS