# Makefile
# by Michiaki Tatsubori
#
#JJ_OPTION = -USER_TOKEN_MANAGER=true -BUILD_TOKEN_MANAGER=false
#SCAN_OPTION = -BUILD_PARSER=false -BUILD_TOKEN_MANAGER=true
JJ_OPTION := -BUILD_TOKEN_MANAGER=true

PARSER_SRC := Parser.java ParserTokenManager.java \
	ParseException.java CustomTokenManager.java \
	TokenIDPublisher.java TokenSourceAdapter.java


Parser: Parser.java


#
# WARNING:
# A modification of patchParserTokenManager.pl may not be automatically
# reflected.  Modification of Parser.jj is also required in this case.
#
Parser.java: Parser.jj
	javacc $(JJ_OPTION) $?
	mv ParserTokenManager.java ParserTokenManager.java.org
	./patchParserTokenManager.pl < ParserTokenManager.java.org \
		> ParserTokenManager.java\

jjdoc: Parser.jj
	jjdoc $?


clean:
	\rm -f *~ Parser.java ParserConstants.java \
		ASCII_UCodeESC_CharStream.java \
		Token.java TokenMgrError.java \
		ParserTokenManager.java ParserTokenManager.java.org
