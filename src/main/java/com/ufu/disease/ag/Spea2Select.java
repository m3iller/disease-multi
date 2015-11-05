package com.ufu.disease.ag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.ChromossomoComparator;
import com.ufu.disease.to.DistElement;

/**
 * classe responsavel para selecionar elemntos do algoritmo spea2
 * 
 * @author miller
 *
 */
public class Spea2Select {
	
	public static Integer MAX_ARCHIVE  = 20;
	
	void removeAndShift(int val, List<Chromossomo> list) {
		int size = list.size();
		list.set(val, list.get(size - 1));
		list.remove(size - 1);
	}

	public List<DistElement> calculateDistancesFromChromossomo(Chromossomo ind,Set<Chromossomo> inds) {
		List<DistElement> dist = new ArrayList<DistElement>();
		//double[] d = new double[inds.size()+1];
		int i=0;
		
		for(Chromossomo c1: inds) {
			if(c1.getIdDermatology() == ind.getIdDermatology()) {
				continue;
			}
			DistElement dElement = new DistElement();
			Double d1 = (double) ((ind.getFunction1() - c1.getFunction1()) * (ind.getFunction1() - c1.getFunction1()));
			Double d2 = (double) ((ind.getFunction2() - c1.getFunction2()) * (ind.getFunction2() - c1.getFunction2()));
			Double result = (double) Math.sqrt(d1+d2);
			dElement.setDist(result);
			dElement.setPos(i++);
			dElement.setChromossomo(c1);
			dist.add(dElement);
		}
		
		Collections.sort(dist, new Comparator<DistElement>() {
			public int compare(DistElement o1, DistElement o2) {
				return o1.getDist().compareTo(o2.getDist());
			}
		});
		
		return dist;
		
	}
	
	
	public void speaSelection(List<Chromossomo> populacao, Set<Chromossomo> archive, List<Chromossomo> nextGeneration) throws CloneNotSupportedException {
		
		List<Chromossomo> pareto = new ArrayList<Chromossomo>();
		//retorna lista com todos individuos nao dominados
		List<Chromossomo> allChromossomo = new ArrayList<Chromossomo>();
		
		allChromossomo.addAll(populacao);
		allChromossomo.addAll(archive);
		
		definePareto(populacao, archive, pareto);
		
		archive.addAll(pareto);
		
		int archiveSize = archive.size();
		// adiciona elementos ate completar o tamanho maximo
		if(archiveSize < MAX_ARCHIVE ) {
			int j=0;
			Collections.sort(allChromossomo, new ChromossomoComparator());
			for(int i = MAX_ARCHIVE - archiveSize; i <= MAX_ARCHIVE; i++ ) {
				archive.add(allChromossomo.get(j));
			}
			return;
		}
		
		
		if(archiveSize > MAX_ARCHIVE) {
			int aux = archiveSize - MAX_ARCHIVE;
			int i=0;
			
			for (Iterator iterator = allChromossomo.iterator(); iterator
					.hasNext();) {
				Chromossomo c1 = (Chromossomo) iterator.next();
				
				List<DistElement> distancias = calculateDistancesFromChromossomo(c1, archive);
				archive.remove(distancias.get(0).getChromossomo());
				i++;
				if(i == aux) {
					break;
				}
			}
		}
	}
	
	//pega todos indivdiduos nao dominados
	private List<Chromossomo> definePareto(List<Chromossomo> populacao,
			Set<Chromossomo> archive, List<Chromossomo> pareto) {
		
		List<Chromossomo> allChromossomo = new ArrayList<Chromossomo>();
		
		allChromossomo.addAll(populacao);
		allChromossomo.addAll(archive);
		
		for(Chromossomo c1: allChromossomo) {
			boolean dominated = false;
			for(Chromossomo c2: allChromossomo) {
				if(c1.getIdDermatology() == c2.getIdDermatology()) {
					continue;
				}
				if(dominateOther(c2, c1)) {
					dominated = false;
					break;
				} else {
					dominated = true;
				}
			}
			//individuo nao é dominado por ninguem
			if(dominated) {
				pareto.add(c1);
			}
		}
		
		return pareto;
	}

	 /**
		 *  adiciona no front os individuos nao dominados
		 * 
		 */
		public List<Chromossomo> partitionIntoParetoFront(List<Chromossomo> popupation, List<Chromossomo> front, List<Chromossomo> nonFront) {
			if (front == null) {
				front = new ArrayList<Chromossomo>();
			}
			// pega o elite
			front.add(popupation.get(0));
			int aux =0;
			for (Chromossomo ind : popupation) {
				
				if(aux++ == 0) {
					continue;
				}
				boolean noOneWasBetter = true;
				int frontSize = front.size();

				for (int j = 0; j < frontSize; j++) {
					Chromossomo frontmember = (Chromossomo) (front.get(j));

					if (dominateOther(frontmember, ind)) {
						nonFront.add(ind);
						noOneWasBetter = false;
						break; // falha nao esta no front
					}

					if (dominateOther(ind, frontmember)) {
						removeAndShift(j, front);
						frontSize--; 
						j--; 
						nonFront.add(frontmember);
					}
				}
				if (noOneWasBetter)
					front.add(ind);
			}
			return front;
		}
		
		public boolean dominateOther(Chromossomo c1, Chromossomo c2) {
			if(c1.getFunction1() >= c2.getFunction1() &&
					c1.getFunction2() >= c2.getFunction2() ){
					//&& (c1.getFunction3() <= c2.getFunction3()  &&c1.getFunction3()>=0 )) {
				return true;
			}
			return false;
		}
		
		
}


