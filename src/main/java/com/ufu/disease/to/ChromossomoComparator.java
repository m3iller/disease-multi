package com.ufu.disease.to;

import java.util.Comparator;

public class ChromossomoComparator implements Comparator<Chromossomo> {

	public int compare(Chromossomo o1, Chromossomo o2) {
		// return o1.getFitness().compareTo(o2.getFitness()) * -1 ;
		if (o1.getFitness() > o2.getFitness()) {
			return -1;
		}
		if (o1.getFitness() < o2.getFitness()) {
			return 1;
		}
		return 0;
	}

}
