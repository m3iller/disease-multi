package com.ufu.disease.ag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.ChromossomoComparator;

/**
 * classe responsavel para selecionar elemntos do algoritmo spea2
 * 
 * @author miller
 *
 */
public class Spea2Select {
	
	void removeAndShift(int val, List<Chromossomo> list) {
		int size = list.size();
		list.set(val, list.get(size - 1));
		list.remove(size - 1);
	}

	public double[] calculateDistancesFromChromossomo(Chromossomo ind,List<Chromossomo> inds) {
		double[] d = new double[inds.size()+1];
		int i=0;
		for(Chromossomo c1: inds) {
			Double d1 = (double) ((ind.getFunction1() - c1.getFunction1()) * (ind.getFunction1() - c1.getFunction1()));
			Double d2 = (double) ((ind.getFunction2() - c1.getFunction2()) * (ind.getFunction2() - c1.getFunction2()));
			d[i++]= (double) Math.sqrt(d1+d2);
		}
		Arrays.sort(d);
		return d;
	}
	
	
	 public void speaSelection(List<Chromossomo> oldInds, List<Chromossomo> newInds, List<Chromossomo> archive) throws CloneNotSupportedException {
             
	 //int archiveSize= archives.size();
	 int archiveSize= Fitness.TAMANHO_ARCHIVE;
     //List<Chromossomo> archive = new ArrayList<Chromossomo>();
     List<Chromossomo> nonFront = new ArrayList<Chromossomo>();
     
     // pegar melhores elementos 
     partitionIntoParetoFront(oldInds, archive, nonFront);
     int currentArchiveSize = archive.size();
             
     // colocar os melhore chromossos para preencher 
     if (currentArchiveSize < archiveSize)
         {
         Collections.sort(nonFront, new ChromossomoComparator());  
         int len = (archiveSize - currentArchiveSize);
         for(int i = 0; i < len; i++)
             {
             archive.add(nonFront.get(i));
             currentArchiveSize++;
             }
         }
      //  remover kezimos individuos
		while (currentArchiveSize > archiveSize) {
			Chromossomo closest = (Chromossomo) (archive.get(0));
			int closestIndex = 0;
			double[] closestD = calculateDistancesFromChromossomo(closest,
					oldInds);

			int i = 0;
			for (Chromossomo competitor : archive) {
				i++;
				if (i == 0) { //ignora primeiro elemento
					continue;
				}
				double[] competitorD = calculateDistancesFromChromossomo(
						competitor, oldInds);

				for (int k = 0; k < oldInds.size(); k++) {
					//System.out.println( " | " + i);
					if (closestD[i] > competitorD[i]) {
						closest = competitor;
						closestD = competitorD;
						closestIndex = k;
						break;
					} else if (closestD[i] < competitorD[i]) {
						break;
					}
				}
				if(i == 49) {
					break;
				}
			}

			archive.set(closestIndex, archive.get(archive.size() - 1));
			archive.remove(archive.size() - 1);

			currentArchiveSize--;
         }
                                             
     // step 4: put clones of the archive in the new Chromossomos
     for(Chromossomo c: archive) {
    	 newInds.add(c.clone());
     }
     
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
			if(c1.getFunction1() > c2.getFunction1() ||
					c1.getFunction2() > c2.getFunction2()) {
				return true;
			}
			return false;
		}

	
}
