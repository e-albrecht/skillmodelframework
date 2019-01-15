package de.unigoe.informatik.swe.kcast;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTProblemStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import de.unigoe.informatik.swe.exception.ParseErrorExeption;
import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.kcast.statement.MyBreak;
import de.unigoe.informatik.swe.kcast.statement.MyCaseLabel;
import de.unigoe.informatik.swe.kcast.statement.MyContinue;
import de.unigoe.informatik.swe.kcast.statement.MyDefaultLabel;
import de.unigoe.informatik.swe.kcast.statement.MyEmptyStatement;
import de.unigoe.informatik.swe.kcast.statement.MyExpressionStatement;
import de.unigoe.informatik.swe.kcast.statement.MyFor;
import de.unigoe.informatik.swe.kcast.statement.MyGoto;
import de.unigoe.informatik.swe.kcast.statement.MyIdLabel;
import de.unigoe.informatik.swe.kcast.statement.MyIf;
import de.unigoe.informatik.swe.kcast.statement.MyReturn;
import de.unigoe.informatik.swe.kcast.statement.MyStatement;
import de.unigoe.informatik.swe.kcast.statement.MySwitch;
import de.unigoe.informatik.swe.kcast.statement.MyWhile;
import de.unigoe.informatik.swe.kcast.statement.MyWhile.WhileType;

public class StatementConstructor {
	

	public static MyStatement construct(IASTStatement stmt) throws ParseErrorExeption {
		if (stmt instanceof IASTBreakStatement) return constructBreak((IASTBreakStatement) stmt);
		if (stmt instanceof IASTCaseStatement) return constructCase((IASTCaseStatement) stmt);
		if (stmt instanceof IASTCompoundStatement) return constructBlock((IASTCompoundStatement) stmt);
		if (stmt instanceof IASTContinueStatement) return constructContinue((IASTContinueStatement) stmt);
		if (stmt instanceof IASTDeclarationStatement) return DeclarationConstructor.construct(((IASTDeclarationStatement) stmt).getDeclaration());
		if (stmt instanceof IASTDefaultStatement) return constructDefault((IASTDefaultStatement) stmt);
		if (stmt instanceof IASTDoStatement) return constructDo((IASTDoStatement) stmt);
		if (stmt instanceof IASTExpressionStatement) return constructExpressionStatement(((IASTExpressionStatement) stmt));
		if (stmt instanceof IASTForStatement) return constructFor((IASTForStatement) stmt);
		if (stmt instanceof IASTGotoStatement) return constructGoto((IASTGotoStatement) stmt);
		if (stmt instanceof IASTIfStatement) return constructIf((IASTIfStatement) stmt);
		if (stmt instanceof IASTLabelStatement) return constructLabel((IASTLabelStatement) stmt);
		if (stmt instanceof IASTNullStatement) return constructEmptyStatement((IASTNullStatement) stmt);
		if (stmt instanceof IASTReturnStatement) return constructReturn((IASTReturnStatement) stmt);
		if (stmt instanceof IASTSwitchStatement) return constructSwitch((IASTSwitchStatement) stmt);
		if (stmt instanceof IASTWhileStatement) return constructWhile((IASTWhileStatement) stmt);	
		if (stmt instanceof IASTProblemStatement) throw new ParseErrorExeption(((IASTProblemStatement)stmt).getRawSignature());
		return null;
	}

	
	
	private static MyWhile constructWhile(IASTWhileStatement stmt) {
		MyExpression cond = ExpressionConstructor.construct(stmt.getCondition());
		MyStatement block;
		try {
			block = construct(stmt.getBody());
		} catch (ParseErrorExeption e) {
			block = new MyEmptyStatement();
		}
		return new MyWhile(WhileType.WHILE, cond, block);
	}

	private static MySwitch constructSwitch(IASTSwitchStatement stmt) {
		MyExpression control = ExpressionConstructor.construct(stmt.getControllerExpression());
		if (!(stmt.getBody() instanceof IASTCompoundStatement))
			try {
				return new MySwitch(control, construct(stmt.getBody()));
			} catch (ParseErrorExeption e1) {
				return new MySwitch(control, new MyEmptyStatement());
			}
		else {
			IASTCompoundStatement b = (IASTCompoundStatement) stmt.getBody();
			MyBlock body = new MyBlock();
			MyBlock lastCaseBody = null;
			for(IASTStatement s : b.getStatements()) {
				MyStatement x;
				try {
					x = construct(s);
				} catch (ParseErrorExeption e) {
					x = new MyEmptyStatement();
				}
				if (x.isCaseLabel()) {
					MyCaseLabel newCase = (MyCaseLabel) x;
					MyBlock newBody = new MyBlock();
					newCase.setStatement(newBody);
					body.addStatement(newCase);
					lastCaseBody = newBody;
				} else if (x.isDefaultLabel()){
					MyDefaultLabel newCase = (MyDefaultLabel) x;
					MyBlock newBody = new MyBlock();
					newCase.setStatement(newBody);
					body.addStatement(newCase);
					lastCaseBody = newBody;		
				} else {
					try {
						if (lastCaseBody == null) lastCaseBody = new MyBlock();
						lastCaseBody.addStatement(construct(s));
						
					} catch (ParseErrorExeption e) {
						lastCaseBody.addStatement(new MyEmptyStatement());
						System.err.println("Parse error");
					}
				}
			}
			return new MySwitch(control, body);
		}
	}

	private static MyReturn constructReturn(IASTReturnStatement stmt) {
		return new MyReturn(ExpressionConstructor.construct(stmt.getReturnValue()));
	}

	private static MyEmptyStatement constructEmptyStatement(IASTNullStatement stmt) {
		return new MyEmptyStatement();
	}

	private static MyIdLabel constructLabel(IASTLabelStatement stmt) {
		String id = stmt.getName().toString();
		MyStatement body;
		try {
			body = construct(stmt.getNestedStatement());
		} catch (ParseErrorExeption e) {
			body = new MyEmptyStatement();
			System.err.println("Parse error");
		}
		return new MyIdLabel(id,body);
	}

	private static MyIf constructIf(IASTIfStatement stmt) {
		MyExpression condition = ExpressionConstructor.construct(stmt.getConditionExpression());
		MyStatement thenBranch;
		try {
			thenBranch = construct(stmt.getThenClause());
		} catch (ParseErrorExeption e) {
			thenBranch = new MyEmptyStatement();
			System.err.println("Parse error");
		}
		MyStatement elseBranch;
		try {
			elseBranch = construct(stmt.getElseClause());
		} catch (ParseErrorExeption e) {
			elseBranch = new MyEmptyStatement();
			System.err.println("Parse error");
		}
		return new MyIf(condition, thenBranch, elseBranch);
	}

	private static MyGoto constructGoto(IASTGotoStatement stmt) {
		String label = stmt.getName().toString();
		return new MyGoto(label);
	}

	private static MyFor constructFor(IASTForStatement stmt) {
		MyStatement init;
		try {
			init = construct(stmt.getInitializerStatement());
		} catch (ParseErrorExeption e1) {
			init = new MyEmptyStatement();
			System.err.println("Parse error");
		}
		MyExpression condition = ExpressionConstructor.construct(stmt.getConditionExpression());
		MyExpression iter = ExpressionConstructor.construct(stmt.getIterationExpression());
		MyStatement body;
		try {
			body = construct(stmt.getBody());
		} catch (ParseErrorExeption e) {
			body = new MyEmptyStatement();
			System.err.println("Parse error");
		}
		return new MyFor(init, condition, iter, body);
	}

	private static MyWhile constructDo(IASTDoStatement stmt) {
		MyExpression cond = ExpressionConstructor.construct(stmt.getCondition());
		MyStatement block;
		try {
			block = construct(stmt.getBody());
		} catch (ParseErrorExeption e) {
			block = new MyEmptyStatement();
			System.err.println("Parse error");
		}
		return new MyWhile(WhileType.DO_WHILE, cond, (MyBlock) block);
	}

	private static MyDefaultLabel constructDefault(IASTDefaultStatement stmt) {
		return new MyDefaultLabel(null);
	}

	private static MyContinue constructContinue(IASTContinueStatement stmt) {
		return new MyContinue();
	}

	private static MyBlock constructBlock(IASTCompoundStatement stmt) {
		List<MyStatement> statements = new LinkedList<MyStatement>();
		for (IASTStatement s : stmt.getStatements()) {
			try {
				statements.add(construct(s));
			} catch (ParseErrorExeption e) {
				statements.add(new MyEmptyStatement());
				System.err.println("Parse error");
			}
		}
		return new MyBlock(statements);
	}

	private static MyCaseLabel constructCase(IASTCaseStatement stmt) {
		MyExpression label = ExpressionConstructor.construct(stmt.getExpression());
		return new MyCaseLabel(label, null);
	}
	
	private static MyExpressionStatement constructExpressionStatement(IASTExpressionStatement stmt) {
		MyExpression expr = ExpressionConstructor.construct(stmt.getExpression());
		return new MyExpressionStatement(expr);
	}

	private static MyBreak constructBreak(IASTBreakStatement stmt) {
		return new MyBreak();
	}
}
