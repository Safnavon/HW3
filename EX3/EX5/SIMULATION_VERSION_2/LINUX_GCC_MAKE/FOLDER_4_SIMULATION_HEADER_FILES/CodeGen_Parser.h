typedef union
{
	union
	{
		int								ival;
		int								temp_serial_number;
		float							fval;
		string							sval;
		string							register_name;
		PSEUDO_MIPS_ASM_AST_Var			var;
		PSEUDO_MIPS_ASM_AST_Label_Type_	label;
		PSEUDO_MIPS_ASM_AST_exp			exp;
		PSEUDO_MIPS_ASM_AST_expList		expList;
	}
	gval;
} YYSTYPE;
#define	INT	258
#define	FLOAT	259
#define	STRING	260
#define	PRINT_INT	261
#define	PRINT_CHAR	262
#define	STRING_VAR	263
#define	ALLOCATE	264
#define	EXIT	265
#define	ID	266
#define	LABEL	267
#define	COMMA	268
#define	COLON	269
#define	LOAD	270
#define	STORE	271
#define	LOAD_IMMEDIATE	272
#define	LOAD_ADDRESS	273
#define	LOAD_BYTE	274
#define	ADD_IMMEDIATE	275
#define	JUMP	276
#define	JUMP_AND_LINK	277
#define	LPAREN	278
#define	RPAREN	279
#define	PLUS	280
#define	MINUS	281
#define	ADD	282
#define	SUB	283
#define	MUL	284
#define	DIV	285
#define	TEMP	286
#define	BEQ	287
#define	BNE	288
#define	BLT	289
#define	BLE	290
#define	BGT	291
#define	BGE	292
#define	REGISTER	293
#define	AND	294
#define	OR	295
#define	MOVE	296


extern YYSTYPE zzlval;
