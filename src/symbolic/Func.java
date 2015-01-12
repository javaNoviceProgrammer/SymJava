package symbolic;

import bytecode.BytecodeFunc;

public class Func extends Expr {
	public Expr expr;
	public Symbol[] args;

	/**
	 * Construct an abstract function
	 * @param name
	 * @param args
	 */
	public Func(String name, Symbol ...args) {
		this.name = name;
		this.args = args;
	}
	
	public Func(String name, Expr expr) {
		//Extract free variables from expr
		this.name = name;
		this.expr = expr;
		args = Utils.extractSymbols(expr);
	}
	
	public BytecodeFunc toBytecodeFunc() {
		try {
			Utils.genClass(this);
			return (BytecodeFunc)Class.forName("bytecode."+this.name).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return name+"("+Utils.joinName(args, ",")+")";
	}

	@Override
	public Expr diff(Expr expr) {
		return this.expr.diff(expr);
	}

	@Override
	public Expr subs(Expr from, Expr to) {
		return new Func(this.name, this.expr.subs(from, to));
	}

}
