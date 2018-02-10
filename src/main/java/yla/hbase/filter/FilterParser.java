// Generated from C:/Users/ylamsaou/IdeaProjects/minicluster-service/src/main/java\Filter.g4 by ANTLR 4.7
package yla.hbase.filter;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPEN_PAR=1, CLOSE_PAR=2, LT=3, LT_EQ=4, GT=5, GT_EQ=6, EQ=7, NOT_EQ1=8, 
		NOT_EQ2=9, K_AND=10, K_OR=11, IDENTIFIER=12, BIND_PARAMETER=13, STRING_LITERAL=14, 
		REGEX_LITERAL=15, SPACES=16, UNEXPECTED_CHAR=17;
	public static final int
		RULE_expr = 0, RULE_operator = 1, RULE_literal_value = 2;
	public static final String[] ruleNames = {
		"expr", "operator", "literal_value"
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

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FilterParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExprContext extends ParserRuleContext {
		public TerminalNode BIND_PARAMETER() { return getToken(FilterParser.BIND_PARAMETER, 0); }
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public Literal_valueContext literal_value() {
			return getRuleContext(Literal_valueContext.class,0);
		}
		public TerminalNode OPEN_PAR() { return getToken(FilterParser.OPEN_PAR, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CLOSE_PAR() { return getToken(FilterParser.CLOSE_PAR, 0); }
		public TerminalNode K_AND() { return getToken(FilterParser.K_AND, 0); }
		public TerminalNode K_OR() { return getToken(FilterParser.K_OR, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(7);
				match(BIND_PARAMETER);
				setState(8);
				operator();
				setState(9);
				literal_value();
				}
				break;
			case 2:
				{
				setState(11);
				literal_value();
				setState(12);
				operator();
				setState(13);
				match(BIND_PARAMETER);
				}
				break;
			case 3:
				{
				setState(15);
				match(BIND_PARAMETER);
				setState(16);
				operator();
				setState(17);
				literal_value();
				}
				break;
			case 4:
				{
				setState(19);
				literal_value();
				setState(20);
				operator();
				setState(21);
				match(BIND_PARAMETER);
				}
				break;
			case 5:
				{
				setState(23);
				match(OPEN_PAR);
				setState(24);
				expr(0);
				setState(25);
				match(CLOSE_PAR);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(37);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(35);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(29);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(30);
						match(K_AND);
						setState(31);
						expr(4);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(32);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(33);
						match(K_OR);
						setState(34);
						expr(3);
						}
						break;
					}
					} 
				}
				setState(39);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class OperatorContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(FilterParser.LT, 0); }
		public TerminalNode LT_EQ() { return getToken(FilterParser.LT_EQ, 0); }
		public TerminalNode GT() { return getToken(FilterParser.GT, 0); }
		public TerminalNode GT_EQ() { return getToken(FilterParser.GT_EQ, 0); }
		public TerminalNode EQ() { return getToken(FilterParser.EQ, 0); }
		public TerminalNode NOT_EQ1() { return getToken(FilterParser.NOT_EQ1, 0); }
		public TerminalNode NOT_EQ2() { return getToken(FilterParser.NOT_EQ2, 0); }
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitOperator(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_operator);
		int _la;
		try {
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
			case LT_EQ:
			case GT:
			case GT_EQ:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << LT_EQ) | (1L << GT) | (1L << GT_EQ))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case EQ:
			case NOT_EQ1:
			case NOT_EQ2:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NOT_EQ1) | (1L << NOT_EQ2))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_valueContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(FilterParser.STRING_LITERAL, 0); }
		public TerminalNode REGEX_LITERAL() { return getToken(FilterParser.REGEX_LITERAL, 0); }
		public Literal_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterLiteral_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitLiteral_value(this);
		}
	}

	public final Literal_valueContext literal_value() throws RecognitionException {
		Literal_valueContext _localctx = new Literal_valueContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_literal_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			_la = _input.LA(1);
			if ( !(_la==STRING_LITERAL || _la==REGEX_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\61\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\36\n\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2&\n\2"+
		"\f\2\16\2)\13\2\3\3\3\3\5\3-\n\3\3\4\3\4\3\4\2\3\2\5\2\4\6\2\5\3\2\5\b"+
		"\3\2\t\13\3\2\20\21\2\64\2\35\3\2\2\2\4,\3\2\2\2\6.\3\2\2\2\b\t\b\2\1"+
		"\2\t\n\7\17\2\2\n\13\5\4\3\2\13\f\5\6\4\2\f\36\3\2\2\2\r\16\5\6\4\2\16"+
		"\17\5\4\3\2\17\20\7\17\2\2\20\36\3\2\2\2\21\22\7\17\2\2\22\23\5\4\3\2"+
		"\23\24\5\6\4\2\24\36\3\2\2\2\25\26\5\6\4\2\26\27\5\4\3\2\27\30\7\17\2"+
		"\2\30\36\3\2\2\2\31\32\7\3\2\2\32\33\5\2\2\2\33\34\7\4\2\2\34\36\3\2\2"+
		"\2\35\b\3\2\2\2\35\r\3\2\2\2\35\21\3\2\2\2\35\25\3\2\2\2\35\31\3\2\2\2"+
		"\36\'\3\2\2\2\37 \f\5\2\2 !\7\f\2\2!&\5\2\2\6\"#\f\4\2\2#$\7\r\2\2$&\5"+
		"\2\2\5%\37\3\2\2\2%\"\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(\3\3\2\2"+
		"\2)\'\3\2\2\2*-\t\2\2\2+-\t\3\2\2,*\3\2\2\2,+\3\2\2\2-\5\3\2\2\2./\t\4"+
		"\2\2/\7\3\2\2\2\6\35%\',";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}