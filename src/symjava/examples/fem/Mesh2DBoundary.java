package symjava.examples.fem;

import java.util.ArrayList;
import java.util.List;

import symjava.domains.Domain;
import symjava.domains.Domain1D;
import static symjava.symbolic.Symbol.x;

public class Mesh2DBoundary extends Domain1D {
	public Mesh2D parent = null;
	public List<Domain> eles = new ArrayList<Domain>();
	
	public Mesh2DBoundary(String name) {
		super(name, x);
	}
	
	public List<Domain> getSubDomains() {
		return eles;
	}	
}
