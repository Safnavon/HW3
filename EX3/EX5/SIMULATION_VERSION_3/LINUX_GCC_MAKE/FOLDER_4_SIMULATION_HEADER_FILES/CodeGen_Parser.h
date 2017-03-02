typedef union
{
	union
	{
		int								ival;
		int								temp_serial_number;
		float							fval;
		string							sval;
		string							register_name;
		PSEUDO_MIPS_ASM_AST_LabelList	labelList;
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
#define	VFTABLE	261
#define	PRINT_INT	262
#define	PRINT_CHAR	263
#define	STRING_VAR	264
#define	ALLOCATE	265
#define	EXIT	266
#define	ID	267
#define	LABEL	268
#define	COMMA	269
#define	COLON	270
#define	LOAD	271
#define	STORE	272
#define	LOAD_IMMEDIATE	273
#define	LOAD_ADDRESS	274
#define	LOAD_BYTE	275
#define	ADD_IMMEDIATE	276
#define	JUMP	277
#define	JUMP_AND_LINK	278
#define	LPAREN	279
#define	RPAREN	280
#define	PLUS	281
#define	MINUS	282
#define	ADD	283
#define	SUB	284
#define	MUL	285
#define	DIV	286
#define	TEMP	287
#define	BEQ	288
#define	BNE	289
#define	BLT	290
#define	BLE	291
#define	BGT	292
#define	BGE	293
#define	REGISTER	294
#define	AND	295
#define	OR	296
#define	MOVE	297


extern YYSTYPE zzlval;
