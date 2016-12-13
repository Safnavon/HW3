@echo.
@echo ########################################################################################
@echo #                                                                                      #
@echo #                                                                                      #
@echo # [0] Clean *.class files and machine generated Lexer.java Parser.java TokenNames.java #
@echo #                                                                                      #
@echo #                                                                                      #
@echo ########################################################################################
@echo.
@echo ############################################################
@echo #                                                          #
@echo #                                                          #
@echo # [1] Use JFlex to synthesize Lexer.java from LEX_FILE.lex #
@echo #                                                          #
@echo #                                                          #
@echo ############################################################
call jflex.bat -q -d FOLDER_3_src FOLDER_1_lex/LEX_FILE.lex
@echo on
@echo.
@echo ###############################################################################
@echo #                                                                             #
@echo #                                                                             #
@echo # [2] Use CUP to synthesize Parser.java and TokenNames.java from CUP_FILE.cup #
@echo #                                                                             #
@echo #                                                                             #
@echo ###############################################################################
java -jar ./FOLDER_5_CUP_JARS/java-cup-11b.jar -nowarn -destdir ./FOLDER_3_src -parser Parser -symbols TokenNames ./FOLDER_2_cup/CUP_FILE.cup 2>nul
@echo on
@echo.
@echo ########################################################
@echo #                                                      #
@echo #                                                      #
@echo # [3] Create *.class files from *.java files + CUP JAR #
@echo #                                                      #
@echo #                                                      #
@echo ########################################################
javac -cp ./FOLDER_5_CUP_JARS/java-cup-11b-runtime.jar -d FOLDER_4_bin FOLDER_3_src/*.java FOLDER_3_src/AST/*.java 2>nul
@echo.
@echo ###########################################################
@echo #                                                         #
@echo #                                                         #
@echo # [4] Create a JAR file from from *.class files + CUP JAR #
@echo #                                                         #
@echo #                                                         #
@echo ###########################################################
jar cfm PARSER.jar ./FOLDER_9_MANIFEST/MANIFEST.MF -C ./FOLDER_4_bin/ .
@echo.
@echo #############################
@echo #                           #
@echo #                           #
@echo # [5] Run resulting program #
@echo #                           #
@echo #                           #
@echo #############################
java -jar PARSER.jar ./FOLDER_6_Input/Quicksort.ic ./FOLDER_7_Output/ParseStatus.txt