main:

jal Label_0_main

Label_0_main:

	addi $fp,$sp,40

	la $t1,VFtableExample
	li $t2,1
	li $t3,4
	mul $t4,$t2,$t3 
	add $t5,$t1,$t4
	lw $t6,0($t5)
	jr $t6

Label_8_GrandfatherWalk:

	li $t0,700
	addi $sp,$sp,-4
	sw $t0,0($sp)
	jal Label_3_PrintInt

Label_16_FatherWalk:

	li $t0,600
	addi $sp,$sp,-4
	sw $t0,0($sp)
	jal Label_3_PrintInt

Label_24_SonWalk:

	li $t0,500
	addi $sp,$sp,-4
	sw $t0,0($sp)
	jal Label_3_PrintInt

Label_3_PrintInt:

	lw $a0,0($sp)
	li $v0,1
	syscall
	li $a0,32
	li $v0,11
	syscall
	li $v0,10
	syscall

VFtableExample: .word Label_8_GrandfatherWalk,Label_24_SonWalk,Label_16_FatherWalk
