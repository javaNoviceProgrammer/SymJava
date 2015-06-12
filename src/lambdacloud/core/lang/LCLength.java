package lambdacloud.core.lang;

import java.util.Map;

import symjava.symbolic.Expr;
import symjava.symbolic.Symbol;
import symjava.symbolic.Expr.TYPE;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import com.sun.org.apache.bcel.internal.generic.InstructionFactory;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.MethodGen;
import com.sun.org.apache.bcel.internal.generic.PUSH;

public class LCLength extends LCBase {
	protected Symbol array;
	public LCLength(Symbol array) {
		this.array = array;
		this.label = array + ".length";
	}
	@Override
	public InstructionHandle bytecodeGen(String clsName, MethodGen mg,
			ConstantPoolGen cp, InstructionFactory factory,
			InstructionList il, Map<String, Integer> argsMap, int argsStartPos, 
			Map<Expr, Integer> funcRefsMap) {
		
		InstructionHandle startPos = il.append(new ALOAD(argsStartPos));
		il.append(new PUSH(cp, argsMap.get(array.getLabel())));
		il.append(InstructionConstants.AALOAD);
		il.append(InstructionConstants.ARRAYLENGTH);
		
		return startPos;
	}
	
	@Override
	public TYPE getType() {
		return TYPE.INT;
	}	
	
	@Override
	public Expr[] args() {
		return new Expr[]{array};
	}

}