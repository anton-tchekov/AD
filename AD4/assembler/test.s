	; mov   eax, 7198

	; mov rax, msg
	; call  hash
	; call  printn

	; mov rax, ta
	; mov rbx, 42
	; call dict_put

	; mov rax, tb
	; mov rbx, 69
	; call dict_put

	; mov rax, tc
	; mov rbx, 777
	; call dict_put

	; mov rax, tb
	; call dict_get
	; call printn

	; mov rax, ta
	; call dict_get
	; call printn

	; mov rax, tc
	; call dict_get
	; call printn

	; mov rax, td
	; call dict_get
	; call printn

	; call  exit

	; mov eax, 8
	; mov ebx, 'B'
	; call write_bits

	; mov eax, 8
	; mov ebx, 'B'
	; call write_bits
	;
	; mov eax, 8
	; mov ebx, 'B'
	; call write_bits

	; mov eax, 65
	; call out_write
	; mov eax, 10
	; call out_write

	; mov eax, 'H'
	; call out_write
	; mov eax, 'e'
	; call out_write
	; mov eax, 'l'
	; call out_write
	; mov eax, 'l'
	; call out_write
	; mov eax, 'o'
	; call out_write
	; mov eax, ' '
	; call out_write
	; mov eax, 'W'
	; call out_write
	; mov eax, 'o'
	; call out_write
	; mov eax, 'r'
	; call out_write
	; mov eax, 'l'
	; call out_write
	; mov eax, 'd'
	; call out_write
	; mov eax, '!'
	; call out_write
	; mov eax, '?'
	; call out_write
	; mov eax, 10
	; call out_write


ta db "Hello", 0
tb db "tst", 0
tc db "bla", 0
td db "irgendwas", 0

; ------------------------------------------
; ----------------- RODATA -----------------
; ------------------------------------------
section .rodata

msg db "Hello World!", 10, 0
MSG_LEN equ $ - msg - 1

msg_io_err db "I/O error", 10
MSG_IO_ERR_LEN equ $ - msg_io_err

; ------------------------------------
; --- Print "Hello World" to stderr
helloworld:
	mov   eax, SYS_WRITE
	mov   edi, STDERR
	mov   esi, msg
	mov   edx, MSG_LEN
	syscall
	ret


	push rax
	push  rbx

	mov rax, rbx
	call printn
	pop  rbx
	pop rax
