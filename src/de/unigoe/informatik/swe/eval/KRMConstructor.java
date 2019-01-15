package de.unigoe.informatik.swe.eval;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import de.unigoe.informatik.swe.data.DBManager;
import de.unigoe.informatik.swe.data.Solution;
import de.unigoe.informatik.swe.krm.KRM;
import de.unigoe.informatik.swe.krm.KAM;
import de.unigoe.informatik.swe.kcast.MyKCAST;
import de.unigoe.informatik.swe.kcast.ProgramConstructor;
import de.unigoe.informatik.swe.utils.Utilities;

public class KRMConstructor {

	private DBManager dbm;
	
	public KRMConstructor() {
		dbm = DBManager.instance();
	}
	
	/**
	 * Constructs KAMs for the given solutions
	 * @param level KC level
	 * @param solutions List of solutions
	 * @return Set of KAMs to the given solutions
	 */
	public Set<KAM> buildKAMs(int level, List<Solution> solutions) {

		Set<KAM> kams = new LinkedHashSet<KAM>();
		for (Solution s : solutions) {
			// construct KC-AST
			try {
				KAM kam = buildKAMToSolution(level,s);
				dbm.saveObjectToDB(s);
				kams.add(kam);
			} catch (CoreException e) {
				System.err.println("Could not construct KC-AST for solution " + s.getId());
				e.printStackTrace();
			}
		}
		return kams;
	}
	
	/**
	 * Constructs a KAM to a solution
	 * @param level KC level
	 * @param s Solution for which KAM shall be constructed
	 * @return KAM representing the given solution
	 * @throws CoreException 
	 */
	public KAM buildKAMToSolution(int level, Solution s) throws CoreException {
		KAM kam = new KAM(level,s);
		// construct KC-AST
		char[] code = s.getSourceCode().toCharArray();
		MyKCAST ast = ProgramConstructor.constructFromString(code);
		kam.construct(ast); // construct KAM from KC-AST
		s.setKrm(Utilities.serializeObject(kam),level); // add KAM to solution
		dbm.saveObjectToDB(s);
		return kam;
	}

	/**
	 * Constructs a KRM from a set of KAMs
	 * @param kams Set of KAMs
	 * @param level KC level
	 * @return
	 */
	public KRM buildKRM(Set<KAM> kams, int level) {
		KRM krm = new KRM(level);
		for (KAM kam : kams) {
			KRM krmS = kam.createKRM();
			krm.addKRMSingle(krmS, kam.getSolutionId());
		}
		return krm;
	}
}
