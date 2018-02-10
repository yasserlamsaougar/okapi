// Generated from C:/Users/ylamsaou/IdeaProjects/minicluster-service/src/main/java\Filter.g4 by ANTLR 4.7
package yla.hbase.filter;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPEN_PAR=1, CLOSE_PAR=2, LT=3, LT_EQ=4, GT=5, GT_EQ=6, EQ=7, NOT_EQ1=8, 
		NOT_EQ2=9, K_AND=10, K_OR=11, IDENTIFIER=12, BIND_PARAMETER=13, STRING_LITERAL=14, 
		REGEX_LITERAL=15, SPACES=16, UNEXPECTED_CHAR=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"OPEN_PAR", "CLOSE_PAR", "LT", "LT_EQ", "GT", "GT_EQ", "EQ", "NOT_EQ1", 
		"NOT_EQ2", "K_AND", "K_OR", "IDENTIFIER", "BIND_PARAMETER", "STRING_LITERAL", 
		"REGEX_LITERAL", "SPACES", "UNEXPECTED_CHAR", "DIGIT", "A", "B", "C", 
		"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", 
		"R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'<>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "OPEN_PAR", "CLOSE_PAR", "LT", "LT_EQ", "GT", "GT_EQ", "EQ", "NOT_EQ1", 
		"NOT_EQ2", "K_AND", "K_OR", "IDENTIFIER", "BIND_PARAMETER", "STRING_LITERAL", 
		"REGEX_LITERAL", "SPACES", "UNEXPECTED_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FilterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u00fb\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\3\r\7\r~\n\r\f\r\16\r\u0081\13\r\3\r\3\r\3\r\3\r\3\r\7\r\u0088"+
		"\n\r\f\r\16\r\u008b\13\r\3\r\3\r\3\r\7\r\u0090\n\r\f\r\16\r\u0093\13\r"+
		"\3\r\3\r\3\r\7\r\u0098\n\r\f\r\16\r\u009b\13\r\5\r\u009d\n\r\3\16\3\16"+
		"\3\16\3\16\7\16\u00a3\n\16\f\16\16\16\u00a6\13\16\3\16\5\16\u00a9\n\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\7\17\u00b1\n\17\f\17\16\17\u00b4\13\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\6\20\u00bc\n\20\r\20\16\20\u00bd\3\21\3"+
		"\21\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3"+
		"(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\2\2.\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21"+
		"\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\2\'\2)\2+\2-\2/\2"+
		"\61\2\63\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y"+
		"\2\3\2\'\3\2$$\3\2bb\3\2__\5\2C\\aac|\6\2\62;C\\aac|\6\2//C\\aac|\7\2"+
		"//\62;C\\aac|\3\2))\7\2##&&,=Cac\177\5\2\13\r\17\17\"\"\3\2\62;\4\2CC"+
		"cc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2"+
		"LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4"+
		"\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u00ed"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\3[\3\2\2\2\5]\3\2\2\2\7_\3\2\2\2\ta\3\2\2\2\13d\3\2\2\2\rf\3\2\2"+
		"\2\17i\3\2\2\2\21l\3\2\2\2\23o\3\2\2\2\25r\3\2\2\2\27v\3\2\2\2\31\u009c"+
		"\3\2\2\2\33\u00a8\3\2\2\2\35\u00ac\3\2\2\2\37\u00b7\3\2\2\2!\u00bf\3\2"+
		"\2\2#\u00c3\3\2\2\2%\u00c5\3\2\2\2\'\u00c7\3\2\2\2)\u00c9\3\2\2\2+\u00cb"+
		"\3\2\2\2-\u00cd\3\2\2\2/\u00cf\3\2\2\2\61\u00d1\3\2\2\2\63\u00d3\3\2\2"+
		"\2\65\u00d5\3\2\2\2\67\u00d7\3\2\2\29\u00d9\3\2\2\2;\u00db\3\2\2\2=\u00dd"+
		"\3\2\2\2?\u00df\3\2\2\2A\u00e1\3\2\2\2C\u00e3\3\2\2\2E\u00e5\3\2\2\2G"+
		"\u00e7\3\2\2\2I\u00e9\3\2\2\2K\u00eb\3\2\2\2M\u00ed\3\2\2\2O\u00ef\3\2"+
		"\2\2Q\u00f1\3\2\2\2S\u00f3\3\2\2\2U\u00f5\3\2\2\2W\u00f7\3\2\2\2Y\u00f9"+
		"\3\2\2\2[\\\7*\2\2\\\4\3\2\2\2]^\7+\2\2^\6\3\2\2\2_`\7>\2\2`\b\3\2\2\2"+
		"ab\7>\2\2bc\7?\2\2c\n\3\2\2\2de\7@\2\2e\f\3\2\2\2fg\7@\2\2gh\7?\2\2h\16"+
		"\3\2\2\2ij\7?\2\2jk\7?\2\2k\20\3\2\2\2lm\7#\2\2mn\7?\2\2n\22\3\2\2\2o"+
		"p\7>\2\2pq\7@\2\2q\24\3\2\2\2rs\5\'\24\2st\5A!\2tu\5-\27\2u\26\3\2\2\2"+
		"vw\5C\"\2wx\5I%\2x\30\3\2\2\2y\177\7$\2\2z~\n\2\2\2{|\7$\2\2|~\7$\2\2"+
		"}z\3\2\2\2}{\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080"+
		"\u0082\3\2\2\2\u0081\177\3\2\2\2\u0082\u009d\7$\2\2\u0083\u0089\7b\2\2"+
		"\u0084\u0088\n\3\2\2\u0085\u0086\7b\2\2\u0086\u0088\7b\2\2\u0087\u0084"+
		"\3\2\2\2\u0087\u0085\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089"+
		"\u008a\3\2\2\2\u008a\u008c\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u009d\7b"+
		"\2\2\u008d\u0091\7]\2\2\u008e\u0090\n\4\2\2\u008f\u008e\3\2\2\2\u0090"+
		"\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0094\3\2"+
		"\2\2\u0093\u0091\3\2\2\2\u0094\u009d\7_\2\2\u0095\u0099\t\5\2\2\u0096"+
		"\u0098\t\6\2\2\u0097\u0096\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2"+
		"\2\2\u0099\u009a\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009c"+
		"y\3\2\2\2\u009c\u0083\3\2\2\2\u009c\u008d\3\2\2\2\u009c\u0095\3\2\2\2"+
		"\u009d\32\3\2\2\2\u009e\u00a9\7B\2\2\u009f\u00a0\7\'\2\2\u00a0\u00a4\t"+
		"\7\2\2\u00a1\u00a3\t\b\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2"+
		"\2\2\u00a7\u00a9\7<\2\2\u00a8\u009e\3\2\2\2\u00a8\u009f\3\2\2\2\u00a9"+
		"\u00aa\3\2\2\2\u00aa\u00ab\5\31\r\2\u00ab\34\3\2\2\2\u00ac\u00b2\7)\2"+
		"\2\u00ad\u00b1\n\t\2\2\u00ae\u00af\7)\2\2\u00af\u00b1\7)\2\2\u00b0\u00ad"+
		"\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b6\7)"+
		"\2\2\u00b6\36\3\2\2\2\u00b7\u00b8\7\'\2\2\u00b8\u00b9\7\'\2\2\u00b9\u00bb"+
		"\3\2\2\2\u00ba\u00bc\t\n\2\2\u00bb\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be \3\2\2\2\u00bf\u00c0\t\13\2\2"+
		"\u00c0\u00c1\3\2\2\2\u00c1\u00c2\b\21\2\2\u00c2\"\3\2\2\2\u00c3\u00c4"+
		"\13\2\2\2\u00c4$\3\2\2\2\u00c5\u00c6\t\f\2\2\u00c6&\3\2\2\2\u00c7\u00c8"+
		"\t\r\2\2\u00c8(\3\2\2\2\u00c9\u00ca\t\16\2\2\u00ca*\3\2\2\2\u00cb\u00cc"+
		"\t\17\2\2\u00cc,\3\2\2\2\u00cd\u00ce\t\20\2\2\u00ce.\3\2\2\2\u00cf\u00d0"+
		"\t\21\2\2\u00d0\60\3\2\2\2\u00d1\u00d2\t\22\2\2\u00d2\62\3\2\2\2\u00d3"+
		"\u00d4\t\23\2\2\u00d4\64\3\2\2\2\u00d5\u00d6\t\24\2\2\u00d6\66\3\2\2\2"+
		"\u00d7\u00d8\t\25\2\2\u00d88\3\2\2\2\u00d9\u00da\t\26\2\2\u00da:\3\2\2"+
		"\2\u00db\u00dc\t\27\2\2\u00dc<\3\2\2\2\u00dd\u00de\t\30\2\2\u00de>\3\2"+
		"\2\2\u00df\u00e0\t\31\2\2\u00e0@\3\2\2\2\u00e1\u00e2\t\32\2\2\u00e2B\3"+
		"\2\2\2\u00e3\u00e4\t\33\2\2\u00e4D\3\2\2\2\u00e5\u00e6\t\34\2\2\u00e6"+
		"F\3\2\2\2\u00e7\u00e8\t\35\2\2\u00e8H\3\2\2\2\u00e9\u00ea\t\36\2\2\u00ea"+
		"J\3\2\2\2\u00eb\u00ec\t\37\2\2\u00ecL\3\2\2\2\u00ed\u00ee\t \2\2\u00ee"+
		"N\3\2\2\2\u00ef\u00f0\t!\2\2\u00f0P\3\2\2\2\u00f1\u00f2\t\"\2\2\u00f2"+
		"R\3\2\2\2\u00f3\u00f4\t#\2\2\u00f4T\3\2\2\2\u00f5\u00f6\t$\2\2\u00f6V"+
		"\3\2\2\2\u00f7\u00f8\t%\2\2\u00f8X\3\2\2\2\u00f9\u00fa\t&\2\2\u00faZ\3"+
		"\2\2\2\17\2}\177\u0087\u0089\u0091\u0099\u009c\u00a4\u00a8\u00b0\u00b2"+
		"\u00bd\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}