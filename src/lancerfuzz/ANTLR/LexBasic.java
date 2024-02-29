package lancerfuzz.ANTLR;

// Generated from LexBasic.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class LexBasic extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Ws", "Hws", "Vws", "BlockComment", "DocComment", "LineComment", "EscSeq", 
			"EscAny", "UnicodeEsc", "DecimalNumeral", "HexDigit", "DecDigit", "BoolLiteral", 
			"CharLiteral", "SQuoteLiteral", "DQuoteLiteral", "USQuoteLiteral", "NameChar", 
			"NameStartChar", "Int", "Esc", "Colon", "DColon", "SQuote", "DQuote", 
			"LParen", "RParen", "LBrace", "RBrace", "LBrack", "RBrack", "RArrow", 
			"Lt", "Gt", "Equal", "Question", "Star", "Plus", "PlusAssign", "Underscore", 
			"Pipe", "Dollar", "Comma", "Semi", "Dot", "Range", "At", "Pound", "Tilde"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public LexBasic(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LexBasic.g4"; }

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
		"\u0004\u0000\u0000\u012e\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!"+
		"\u0007!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002"+
		"&\u0007&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002"+
		"+\u0007+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u0002"+
		"0\u00070\u0001\u0000\u0001\u0000\u0003\u0000f\b\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003p\b\u0003\n\u0003\f\u0003s\t\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003x\b\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u007f\b\u0004\n\u0004\f\u0004"+
		"\u0082\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u0087\b"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u008d"+
		"\b\u0005\n\u0005\f\u0005\u0090\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0003\u0006\u0097\b\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00a1\b\b"+
		"\u0003\b\u00a3\b\b\u0003\b\u00a5\b\b\u0003\b\u00a7\b\b\u0001\t\u0001\t"+
		"\u0001\t\u0005\t\u00ac\b\t\n\t\f\t\u00af\t\t\u0003\t\u00b1\b\t\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00c0\b\f\u0001\r\u0001\r\u0001"+
		"\r\u0003\r\u00c5\b\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0005\u000e\u00cc\b\u000e\n\u000e\f\u000e\u00cf\t\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00d6\b\u000f\n"+
		"\u000f\f\u000f\u00d9\t\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0005\u0010\u00e0\b\u0010\n\u0010\f\u0010\u00e3\t\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00e9\b\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c"+
		"\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001"+
		"#\u0001#\u0001$\u0001$\u0001%\u0001%\u0001&\u0001&\u0001&\u0001\'\u0001"+
		"\'\u0001(\u0001(\u0001)\u0001)\u0001*\u0001*\u0001+\u0001+\u0001,\u0001"+
		",\u0001-\u0001-\u0001-\u0001.\u0001.\u0001/\u0001/\u00010\u00010\u0002"+
		"q\u0080\u00001\u0001\u0000\u0003\u0000\u0005\u0000\u0007\u0000\t\u0000"+
		"\u000b\u0000\r\u0000\u000f\u0000\u0011\u0000\u0013\u0000\u0015\u0000\u0017"+
		"\u0000\u0019\u0000\u001b\u0000\u001d\u0000\u001f\u0000!\u0000#\u0000%"+
		"\u0000\'\u0000)\u0000+\u0000-\u0000/\u00001\u00003\u00005\u00007\u0000"+
		"9\u0000;\u0000=\u0000?\u0000A\u0000C\u0000E\u0000G\u0000I\u0000K\u0000"+
		"M\u0000O\u0000Q\u0000S\u0000U\u0000W\u0000Y\u0000[\u0000]\u0000_\u0000"+
		"a\u0000\u0001\u0000\u000b\u0002\u0000\t\t  \u0002\u0000\n\n\f\r\u0002"+
		"\u0000\n\n\r\r\b\u0000\"\"\'\'\\\\bbffnnrrtt\u0001\u000019\u0003\u0000"+
		"09AFaf\u0001\u000009\u0004\u0000\n\n\r\r\'\'\\\\\u0004\u0000\n\n\r\r\""+
		"\"\\\\\u0003\u0000\u00b7\u00b7\u0300\u036f\u203f\u2040\r\u0000AZaz\u00c0"+
		"\u00d6\u00d8\u00f6\u00f8\u02ff\u0370\u037d\u037f\u1fff\u200c\u200d\u2070"+
		"\u218f\u2c00\u2fef\u3001\u8000\ud7ff\u8000\uf900\u8000\ufdcf\u8000\ufdf0"+
		"\u8000\ufffd\u0116\u0001e\u0001\u0000\u0000\u0000\u0003g\u0001\u0000\u0000"+
		"\u0000\u0005i\u0001\u0000\u0000\u0000\u0007k\u0001\u0000\u0000\u0000\t"+
		"y\u0001\u0000\u0000\u0000\u000b\u0088\u0001\u0000\u0000\u0000\r\u0091"+
		"\u0001\u0000\u0000\u0000\u000f\u0098\u0001\u0000\u0000\u0000\u0011\u009b"+
		"\u0001\u0000\u0000\u0000\u0013\u00b0\u0001\u0000\u0000\u0000\u0015\u00b2"+
		"\u0001\u0000\u0000\u0000\u0017\u00b4\u0001\u0000\u0000\u0000\u0019\u00bf"+
		"\u0001\u0000\u0000\u0000\u001b\u00c1\u0001\u0000\u0000\u0000\u001d\u00c8"+
		"\u0001\u0000\u0000\u0000\u001f\u00d2\u0001\u0000\u0000\u0000!\u00dc\u0001"+
		"\u0000\u0000\u0000#\u00e8\u0001\u0000\u0000\u0000%\u00ea\u0001\u0000\u0000"+
		"\u0000\'\u00ec\u0001\u0000\u0000\u0000)\u00f0\u0001\u0000\u0000\u0000"+
		"+\u00f2\u0001\u0000\u0000\u0000-\u00f4\u0001\u0000\u0000\u0000/\u00f7"+
		"\u0001\u0000\u0000\u00001\u00f9\u0001\u0000\u0000\u00003\u00fb\u0001\u0000"+
		"\u0000\u00005\u00fd\u0001\u0000\u0000\u00007\u00ff\u0001\u0000\u0000\u0000"+
		"9\u0101\u0001\u0000\u0000\u0000;\u0103\u0001\u0000\u0000\u0000=\u0105"+
		"\u0001\u0000\u0000\u0000?\u0107\u0001\u0000\u0000\u0000A\u010a\u0001\u0000"+
		"\u0000\u0000C\u010c\u0001\u0000\u0000\u0000E\u010e\u0001\u0000\u0000\u0000"+
		"G\u0110\u0001\u0000\u0000\u0000I\u0112\u0001\u0000\u0000\u0000K\u0114"+
		"\u0001\u0000\u0000\u0000M\u0116\u0001\u0000\u0000\u0000O\u0119\u0001\u0000"+
		"\u0000\u0000Q\u011b\u0001\u0000\u0000\u0000S\u011d\u0001\u0000\u0000\u0000"+
		"U\u011f\u0001\u0000\u0000\u0000W\u0121\u0001\u0000\u0000\u0000Y\u0123"+
		"\u0001\u0000\u0000\u0000[\u0125\u0001\u0000\u0000\u0000]\u0128\u0001\u0000"+
		"\u0000\u0000_\u012a\u0001\u0000\u0000\u0000a\u012c\u0001\u0000\u0000\u0000"+
		"cf\u0003\u0003\u0001\u0000df\u0003\u0005\u0002\u0000ec\u0001\u0000\u0000"+
		"\u0000ed\u0001\u0000\u0000\u0000f\u0002\u0001\u0000\u0000\u0000gh\u0007"+
		"\u0000\u0000\u0000h\u0004\u0001\u0000\u0000\u0000ij\u0007\u0001\u0000"+
		"\u0000j\u0006\u0001\u0000\u0000\u0000kl\u0005/\u0000\u0000lm\u0005*\u0000"+
		"\u0000mq\u0001\u0000\u0000\u0000np\t\u0000\u0000\u0000on\u0001\u0000\u0000"+
		"\u0000ps\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000rw\u0001\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000tu\u0005"+
		"*\u0000\u0000ux\u0005/\u0000\u0000vx\u0005\u0000\u0000\u0001wt\u0001\u0000"+
		"\u0000\u0000wv\u0001\u0000\u0000\u0000x\b\u0001\u0000\u0000\u0000yz\u0005"+
		"/\u0000\u0000z{\u0005*\u0000\u0000{|\u0005*\u0000\u0000|\u0080\u0001\u0000"+
		"\u0000\u0000}\u007f\t\u0000\u0000\u0000~}\u0001\u0000\u0000\u0000\u007f"+
		"\u0082\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0080"+
		"~\u0001\u0000\u0000\u0000\u0081\u0086\u0001\u0000\u0000\u0000\u0082\u0080"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\u0005*\u0000\u0000\u0084\u0087\u0005"+
		"/\u0000\u0000\u0085\u0087\u0005\u0000\u0000\u0001\u0086\u0083\u0001\u0000"+
		"\u0000\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\n\u0001\u0000\u0000"+
		"\u0000\u0088\u0089\u0005/\u0000\u0000\u0089\u008a\u0005/\u0000\u0000\u008a"+
		"\u008e\u0001\u0000\u0000\u0000\u008b\u008d\b\u0002\u0000\u0000\u008c\u008b"+
		"\u0001\u0000\u0000\u0000\u008d\u0090\u0001\u0000\u0000\u0000\u008e\u008c"+
		"\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f\f\u0001"+
		"\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0091\u0096\u0003"+
		")\u0014\u0000\u0092\u0097\u0007\u0003\u0000\u0000\u0093\u0097\u0003\u0011"+
		"\b\u0000\u0094\u0097\t\u0000\u0000\u0000\u0095\u0097\u0005\u0000\u0000"+
		"\u0001\u0096\u0092\u0001\u0000\u0000\u0000\u0096\u0093\u0001\u0000\u0000"+
		"\u0000\u0096\u0094\u0001\u0000\u0000\u0000\u0096\u0095\u0001\u0000\u0000"+
		"\u0000\u0097\u000e\u0001\u0000\u0000\u0000\u0098\u0099\u0003)\u0014\u0000"+
		"\u0099\u009a\t\u0000\u0000\u0000\u009a\u0010\u0001\u0000\u0000\u0000\u009b"+
		"\u00a6\u0005u\u0000\u0000\u009c\u00a4\u0003\u0015\n\u0000\u009d\u00a2"+
		"\u0003\u0015\n\u0000\u009e\u00a0\u0003\u0015\n\u0000\u009f\u00a1\u0003"+
		"\u0015\n\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a1\u00a3\u0001\u0000\u0000\u0000\u00a2\u009e\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a4\u009d\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a7\u0001\u0000\u0000\u0000\u00a6\u009c\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u0012\u0001\u0000"+
		"\u0000\u0000\u00a8\u00b1\u00050\u0000\u0000\u00a9\u00ad\u0007\u0004\u0000"+
		"\u0000\u00aa\u00ac\u0003\u0017\u000b\u0000\u00ab\u00aa\u0001\u0000\u0000"+
		"\u0000\u00ac\u00af\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00b1\u0001\u0000\u0000"+
		"\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0\u00a8\u0001\u0000\u0000"+
		"\u0000\u00b0\u00a9\u0001\u0000\u0000\u0000\u00b1\u0014\u0001\u0000\u0000"+
		"\u0000\u00b2\u00b3\u0007\u0005\u0000\u0000\u00b3\u0016\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b5\u0007\u0006\u0000\u0000\u00b5\u0018\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b7\u0005t\u0000\u0000\u00b7\u00b8\u0005r\u0000\u0000\u00b8"+
		"\u00b9\u0005u\u0000\u0000\u00b9\u00c0\u0005e\u0000\u0000\u00ba\u00bb\u0005"+
		"f\u0000\u0000\u00bb\u00bc\u0005a\u0000\u0000\u00bc\u00bd\u0005l\u0000"+
		"\u0000\u00bd\u00be\u0005s\u0000\u0000\u00be\u00c0\u0005e\u0000\u0000\u00bf"+
		"\u00b6\u0001\u0000\u0000\u0000\u00bf\u00ba\u0001\u0000\u0000\u0000\u00c0"+
		"\u001a\u0001\u0000\u0000\u0000\u00c1\u00c4\u0003/\u0017\u0000\u00c2\u00c5"+
		"\u0003\r\u0006\u0000\u00c3\u00c5\b\u0007\u0000\u0000\u00c4\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c4\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001"+
		"\u0000\u0000\u0000\u00c6\u00c7\u0003/\u0017\u0000\u00c7\u001c\u0001\u0000"+
		"\u0000\u0000\u00c8\u00cd\u0003/\u0017\u0000\u00c9\u00cc\u0003\r\u0006"+
		"\u0000\u00ca\u00cc\b\u0007\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000"+
		"\u00cb\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cf\u0001\u0000\u0000\u0000"+
		"\u00cd\u00cb\u0001\u0000\u0000\u0000\u00cd\u00ce\u0001\u0000\u0000\u0000"+
		"\u00ce\u00d0\u0001\u0000\u0000\u0000\u00cf\u00cd\u0001\u0000\u0000\u0000"+
		"\u00d0\u00d1\u0003/\u0017\u0000\u00d1\u001e\u0001\u0000\u0000\u0000\u00d2"+
		"\u00d7\u00031\u0018\u0000\u00d3\u00d6\u0003\r\u0006\u0000\u00d4\u00d6"+
		"\b\b\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d4\u0001"+
		"\u0000\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001"+
		"\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8\u00da\u0001"+
		"\u0000\u0000\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00da\u00db\u0003"+
		"1\u0018\u0000\u00db \u0001\u0000\u0000\u0000\u00dc\u00e1\u0003/\u0017"+
		"\u0000\u00dd\u00e0\u0003\r\u0006\u0000\u00de\u00e0\b\u0007\u0000\u0000"+
		"\u00df\u00dd\u0001\u0000\u0000\u0000\u00df\u00de\u0001\u0000\u0000\u0000"+
		"\u00e0\u00e3\u0001\u0000\u0000\u0000\u00e1\u00df\u0001\u0000\u0000\u0000"+
		"\u00e1\u00e2\u0001\u0000\u0000\u0000\u00e2\"\u0001\u0000\u0000\u0000\u00e3"+
		"\u00e1\u0001\u0000\u0000\u0000\u00e4\u00e9\u0003%\u0012\u0000\u00e5\u00e9"+
		"\u000209\u0000\u00e6\u00e9\u0003O\'\u0000\u00e7\u00e9\u0007\t\u0000\u0000"+
		"\u00e8\u00e4\u0001\u0000\u0000\u0000\u00e8\u00e5\u0001\u0000\u0000\u0000"+
		"\u00e8\u00e6\u0001\u0000\u0000\u0000\u00e8\u00e7\u0001\u0000\u0000\u0000"+
		"\u00e9$\u0001\u0000\u0000\u0000\u00ea\u00eb\u0007\n\u0000\u0000\u00eb"+
		"&\u0001\u0000\u0000\u0000\u00ec\u00ed\u0005i\u0000\u0000\u00ed\u00ee\u0005"+
		"n\u0000\u0000\u00ee\u00ef\u0005t\u0000\u0000\u00ef(\u0001\u0000\u0000"+
		"\u0000\u00f0\u00f1\u0005\\\u0000\u0000\u00f1*\u0001\u0000\u0000\u0000"+
		"\u00f2\u00f3\u0005:\u0000\u0000\u00f3,\u0001\u0000\u0000\u0000\u00f4\u00f5"+
		"\u0005:\u0000\u0000\u00f5\u00f6\u0005:\u0000\u0000\u00f6.\u0001\u0000"+
		"\u0000\u0000\u00f7\u00f8\u0005\'\u0000\u0000\u00f80\u0001\u0000\u0000"+
		"\u0000\u00f9\u00fa\u0005\"\u0000\u0000\u00fa2\u0001\u0000\u0000\u0000"+
		"\u00fb\u00fc\u0005(\u0000\u0000\u00fc4\u0001\u0000\u0000\u0000\u00fd\u00fe"+
		"\u0005)\u0000\u0000\u00fe6\u0001\u0000\u0000\u0000\u00ff\u0100\u0005{"+
		"\u0000\u0000\u01008\u0001\u0000\u0000\u0000\u0101\u0102\u0005}\u0000\u0000"+
		"\u0102:\u0001\u0000\u0000\u0000\u0103\u0104\u0005[\u0000\u0000\u0104<"+
		"\u0001\u0000\u0000\u0000\u0105\u0106\u0005]\u0000\u0000\u0106>\u0001\u0000"+
		"\u0000\u0000\u0107\u0108\u0005-\u0000\u0000\u0108\u0109\u0005>\u0000\u0000"+
		"\u0109@\u0001\u0000\u0000\u0000\u010a\u010b\u0005<\u0000\u0000\u010bB"+
		"\u0001\u0000\u0000\u0000\u010c\u010d\u0005>\u0000\u0000\u010dD\u0001\u0000"+
		"\u0000\u0000\u010e\u010f\u0005=\u0000\u0000\u010fF\u0001\u0000\u0000\u0000"+
		"\u0110\u0111\u0005?\u0000\u0000\u0111H\u0001\u0000\u0000\u0000\u0112\u0113"+
		"\u0005*\u0000\u0000\u0113J\u0001\u0000\u0000\u0000\u0114\u0115\u0005+"+
		"\u0000\u0000\u0115L\u0001\u0000\u0000\u0000\u0116\u0117\u0005+\u0000\u0000"+
		"\u0117\u0118\u0005=\u0000\u0000\u0118N\u0001\u0000\u0000\u0000\u0119\u011a"+
		"\u0005_\u0000\u0000\u011aP\u0001\u0000\u0000\u0000\u011b\u011c\u0005|"+
		"\u0000\u0000\u011cR\u0001\u0000\u0000\u0000\u011d\u011e\u0005$\u0000\u0000"+
		"\u011eT\u0001\u0000\u0000\u0000\u011f\u0120\u0005,\u0000\u0000\u0120V"+
		"\u0001\u0000\u0000\u0000\u0121\u0122\u0005;\u0000\u0000\u0122X\u0001\u0000"+
		"\u0000\u0000\u0123\u0124\u0005.\u0000\u0000\u0124Z\u0001\u0000\u0000\u0000"+
		"\u0125\u0126\u0005.\u0000\u0000\u0126\u0127\u0005.\u0000\u0000\u0127\\"+
		"\u0001\u0000\u0000\u0000\u0128\u0129\u0005@\u0000\u0000\u0129^\u0001\u0000"+
		"\u0000\u0000\u012a\u012b\u0005#\u0000\u0000\u012b`\u0001\u0000\u0000\u0000"+
		"\u012c\u012d\u0005~\u0000\u0000\u012db\u0001\u0000\u0000\u0000\u0017\u0000"+
		"eqw\u0080\u0086\u008e\u0096\u00a0\u00a2\u00a4\u00a6\u00ad\u00b0\u00bf"+
		"\u00c4\u00cb\u00cd\u00d5\u00d7\u00df\u00e1\u00e8\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}