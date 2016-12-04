@echo.
@echo ############################################################
@echo #                                                          #
@echo #                                                          #
@echo # [0] Clean *.class files and machine generated Lexer.java #
@echo #                                                          #
@echo #                                                          #
@echo ############################################################
del /q FOLDER_1_src\Lexer.java FOLDER_2_bin\*.class 2>nul
@echo.
@echo ############################################################
@echo #                                                          #
@echo #                                                          #
@echo # [1] Use JFlex to synthesize Lexer.java from LEX_FILE.lex #
@echo #                                                          #
@echo #                                                          #
@echo ############################################################
call jflex.bat -q -d FOLDER_1_src FOLDER_0_lex/LEX_FILE.lex
@echo on
@echo.
@echo ########################################################
@echo #                                                      #
@echo #                                                      #
@echo # [2] Create *.class files from *.java files + CUP JAR #
@echo #                                                      #
@echo #                                                      #
@echo ########################################################
javac -cp ./FOLDER_3_CUP_JARS/java-cup-11b-runtime.jar -d FOLDER_2_bin FOLDER_1_src/*.java
@echo.
@echo ###########################################################
@echo #                                                         #
@echo #                                                         #
@echo # [3] Create a JAR file from from *.class files + CUP JAR #
@echo #                                                         #
@echo #                                                         #
@echo ###########################################################
jar cfm LEXER.jar ./FOLDER_7_MANIFEST/MANIFEST.MF -C ./FOLDER_2_bin/ .
@echo.
@echo #############################
@echo #                           #
@echo #                           #
@echo # [4] Run resulting program #
@echo #                           #
@echo #                           #
@echo #############################
java -jar LEXER.jar ./FOLDER_4_Input/Input.ic ./FOLDER_5_Output/OutputTokens.txt