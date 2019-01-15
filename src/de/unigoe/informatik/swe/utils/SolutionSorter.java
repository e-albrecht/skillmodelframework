package de.unigoe.informatik.swe.utils;

import java.util.Comparator;

import de.unigoe.informatik.swe.data.Solution;



public class SolutionSorter implements Comparator<Solution>{

	@Override
	public int compare(Solution sol1, Solution sol2) {
		if (sol1.getStudentId() < sol2.getStudentId()) return -1;
		else if(sol2.getStudentId() < sol1.getStudentId()) return 1;

		return (sol1.getId() - sol2.getId());
	}

}
