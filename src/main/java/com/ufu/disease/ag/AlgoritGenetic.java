package com.ufu.disease.ag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ufu.disease.dao.DermatologyDAO;
import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.ChromossomoComparator;

public class AlgoritGenetic {

	private static Integer generation = 50;
	private static Integer elementos = 50;
	private static Integer sizeTournament =3;
	public static Integer id = 1000;
	
	private static DermatologyDAO dao = new DermatologyDAO();
	private static List<Chromossomo>  validateDiseae;
	private static List<Chromossomo> trainingDiseae;
	public static List<Chromossomo> classOne;
	public static List<Chromossomo> classTwo;
	public static List<Chromossomo> classThree;
	public static List<Chromossomo> classFour;
	public static List<Chromossomo> classFive;
	public static List<Chromossomo> classSix;
	
	public void randomElements() {
	}
	
	public static List<Chromossomo> createPopulation() {
		
		List<Chromossomo> pop = new ArrayList<Chromossomo>();
		Random random = new Random();
		for (int i = 0; i < elementos; i++) {
			pop.add(Chromossomo.buildChromossome(random));
		}
		
		return pop;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Long init = 0l;
		Long end = 0l;
		initTesteList();
		createValidateList();
		for (int classAg = 1; classAg <= 6; classAg++) {
			
			init = System.currentTimeMillis();
			List<Chromossomo> popupalcao = createPopulation();
			Set<Chromossomo> archive = new HashSet<Chromossomo>();
			
			Fitness f = new Fitness();
			Tournament tournament = new Tournament();
			Spea2Select spea2 = new Spea2Select();
			
			for (int j = 1; j <= generation; j++) {
				List<Chromossomo> nextGeneration = new ArrayList<Chromossomo>();
				
				f.calculateFitnessSpea2(popupalcao, archive,classAg, trainingDiseae);
				Collections.sort(popupalcao, new ChromossomoComparator());
				popupalcao = popupalcao.subList(0, 49);
				
				nextGeneration = new ArrayList<Chromossomo>();
				spea2.speaSelection(popupalcao, archive, nextGeneration);
				
				List<Chromossomo> tourElements = tournament.spea2TournamentTimes(popupalcao,sizeTournament, 25,classAg);
				popupalcao.addAll(tourElements);
				
			}
			//f.calculateFitnessSpea2(popupalcao, archive,classAg, trainingDiseae);
			//Collections.sort(popupalcao, new ChromossomoComparator());
			Float tempo = ((System.currentTimeMillis() - init) / 1000f);
			System.out.println("Tempo Execucao:" + tempo);
			System.out.println("Classe: " + classAg);
			
			filtraParetoDisferentes(archive);
			//Chromossomo.printChromossomo(popupalcao.get(0));
		
		}
	}
	public static void validateClass(Chromossomo winner, Integer classAg) {
		Fitness f = new Fitness();
		f.calculateFitness(winner, classAg, validateDiseae);
		System.out.println("Testando Fit: f1:" + winner.getFunction1() + " f2:" + winner.getFunction2()) ;
	}
	
	public static void createValidateList() {
		validateDiseae = new ArrayList<Chromossomo>();
		validateDiseae.addAll(dao.searchDermtologyNotIn(1,classOne));
		validateDiseae.addAll(dao.searchDermtologyNotIn(2,classTwo));
		validateDiseae.addAll(dao.searchDermtologyNotIn(3,classThree));
		validateDiseae.addAll(dao.searchDermtologyNotIn(4,classFour));
		validateDiseae.addAll(dao.searchDermtologyNotIn(5,classFive));
		validateDiseae.addAll(dao.searchDermtologyNotIn(6,classSix));
	}
	
	public static void initTesteList() {
		classOne = dao.searchDermtology(74, 1);
		classTwo = dao.searchDermtology(40, 2);
		classThree = dao.searchDermtology(49, 3);
		classFour = dao.searchDermtology(32, 4);
		classFive = dao.searchDermtology(35, 5);
		classSix = dao.searchDermtology(14, 6);
		
		trainingDiseae = new ArrayList<Chromossomo>();
		
		trainingDiseae.addAll(classOne);
		trainingDiseae.addAll(classTwo);
		trainingDiseae.addAll(classThree);
		trainingDiseae.addAll(classFour);
		trainingDiseae.addAll(classFive);
		trainingDiseae.addAll(classSix);
	}
	
	public static void filtraParetoDisferentes(Set<Chromossomo> pareto) {
		
		for(Chromossomo c: pareto) {
			Chromossomo.printChromossomo(c);
		}
	}
	
}
