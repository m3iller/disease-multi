package com.ufu.disease.ag;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.ChromossomoComparator;
import com.ufu.disease.to.Gene;
import com.ufu.disease.to.Operator;


public class Tournament {

	public List<Chromossomo> tournamentBinary(List<Chromossomo> populacao) {
			List<Chromossomo> chromoSelected = new ArrayList<Chromossomo>();
			Random r = new Random();
			
			int r1 = r.nextInt((populacao.size() - 1) + 1) ;
			int r2 = r.nextInt((populacao.size() - 1) + 1) ;
			
			Chromossomo c1 = populacao.get(r1);
			Chromossomo c2 = populacao.get(r2);
			
			chromoSelected.add(c1);
			if(c2.getIdDermatology() == c1.getIdDermatology()) {
				int num = r2==0?1: (r2 >= populacao.size() ? r2-1 : r2-1 ) ;
				chromoSelected.add(populacao.get(num));
			} else {
				chromoSelected.add(c2);
			}
			
			Collections.sort(chromoSelected, new ChromossomoComparator());
			return chromoSelected;
	}
	
	public  List<Chromossomo> spea2TournamentTimes(List<Chromossomo> populacao, int size,int vezes, int geracao) {
		
		List<Chromossomo> torneiosElement = new ArrayList<Chromossomo>();
		for(int k=0;k <=vezes;k++) {
		
			List<Chromossomo> chromoSelected = new ArrayList<Chromossomo>();
			List<Chromossomo> chromoSelected1 = tournamentBinary(populacao);
			List<Chromossomo> chromoSelected2 = tournamentBinary(populacao);
			
			if(chromoSelected1.size() >0 && chromoSelected1.get(0) != null
					&& chromoSelected2.size()>1 && chromoSelected2.get(1) != null) {
				
			chromoSelected.add(chromoSelected1.get(0));
			if(chromoSelected2.get(0).equals(chromoSelected1.get(0))) {
				chromoSelected.add(chromoSelected2.get(1));
			} else {
				chromoSelected.add(chromoSelected2.get(0));
			}
			CrossOver cross =new CrossOver();
			
			try {
				
				Chromossomo c1 =  cross.crossOver(chromoSelected.get(0), chromoSelected.get(1));
				Chromossomo c2 =  cross.crossOver(chromoSelected.get(1), chromoSelected.get(0));
				
				torneiosElement.add(c1);
				torneiosElement.add(c2);
				
				mutation(c1,30);
				mutation(c2,30);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		
		} else {
			System.out.println("tem gente nulo ai max fit:");
		}
		}
		return torneiosElement;
		
	}
	
	
	

	private void mutation(Chromossomo c1, int porcentage) throws IllegalAccessException {
		Field[] fiields = c1.getClass().getDeclaredFields();
		
		Random r = new Random();
		for(Field f:fiields) {
			if(f.getName().equals("id")) {
				continue;
			}
			if(f.getName().equals("fitness")) {
				continue;
			}
			if(f.getName().equals("idDermatology")) {
				continue;
			}
			if(f.getName().equals("function1")) {
				continue;
			}
			if(f.getName().equals("function2")) {
				continue;
			}
			
			int p1 = r.nextInt((100 - 1) + 1) + 1;
			int p2 = r.nextInt((100 - 1) + 1) + 1;
			int p3 = r.nextInt((100 - 1) + 1) + 1;
			
			f.setAccessible(true);
			Object obj = f.get(c1);
			Gene geneChromoOne = null;
			if(obj instanceof Gene) {
				geneChromoOne = (Gene) obj;
			} else {
				continue;
			}
			
			//System.out.println(geneChromoOne);
			
			if(p1 <= porcentage) {
				geneChromoOne.setOperator(Operator.getRandom());
			}
			
			if(p2 <= porcentage) {
				//int plus = r.nextInt((1 - 0) + 1) + 0;
				boolean plus = r.nextBoolean();
//				float randomNum =  Float.valueOf(String.format("%.2f", Math.random() / 10f).replace(",", "."));
//				float sum = (float) (plus == true ? geneChromoOne.getWeigth() +
//						randomNum : geneChromoOne.getWeigth() - randomNum) ;
//				
				int num = r.nextInt((10 - 1) + 1) + 1;
				
				float sum = (float) (plus == true ? geneChromoOne.getWeigth() +
						(num / 100f) : geneChromoOne.getWeigth() - (num/100f)) ;
				
				if(sum > 1.0) {
					sum = 1.0f;
				}
				if(sum <=0) {
					sum = 0.0f;
				}
				geneChromoOne.setWeigth(sum);
			}
			
			if (p3 <= porcentage) {
				if (f.getName().equals("familyHistory")) {
					geneChromoOne.setValue(r.nextInt((1 - 0) + 1) + 0);
				} else if (f.getName().equals("age")) {
					geneChromoOne.setValue(r.nextInt((79 - 0) + 1) + 0);
				} else if (f.getName().equals("classDisease")) {
					//geneChromoOne.setValue(r.nextInt((6 - 0) + 1) + 0);
				} else {
					geneChromoOne.setValue(r.nextInt((3 - 0) + 1) + 0);
				}
			}
			//System.out.println(geneChromoOne);
		}
	}
	
}
