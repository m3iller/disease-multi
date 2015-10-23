package com.ufu.disease.ag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ufu.disease.dao.DermatologyDAO;
import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.ChromossomoComparator;

public class AlgoritGenetic {

	private static Integer generation = 50;
	private static Integer elementos = 50;
	private static Integer sizeTournament =3;
	public static Integer id = 1000;
	
	public static List<Chromossomo> trainingDiseae;
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
		for (int classAg = 1; classAg <= 6; classAg++) {
			
			//initDistance(49);
			Float tempo = ((end - init) / 1000f);
			init = System.currentTimeMillis();
			System.out.println("Tempo Execucao:" + tempo);
			List<Chromossomo> popupalcao = createPopulation();
			List<Chromossomo> archive = new ArrayList<Chromossomo>();
			
			Fitness f = new Fitness();
			TournamentStocastic tournament = new TournamentStocastic();
			Spea2Select spea2 = new Spea2Select();
			
			
			for (int j = 1; j <= generation; j++) {
				
				f.calculateFitnessSpea2(popupalcao, archive,classAg);
				
				Collections.sort(popupalcao, new ChromossomoComparator());
				popupalcao = popupalcao.subList(0, 49);
				
				Collections.sort(popupalcao, new ChromossomoComparator());
				List<Chromossomo> nextGeneration = new ArrayList<Chromossomo>();
				spea2.speaSelection(popupalcao, nextGeneration, archive);
				
				List<Chromossomo> tourElements = tournament.spea2TournamentTimes(nextGeneration,sizeTournament, 25,classAg);
				
				popupalcao.addAll(tourElements);
				Collections.sort(tourElements, new ChromossomoComparator());
				
				archive = new ArrayList<Chromossomo>();
				archive.addAll(nextGeneration);
				Collections.sort(archive, new ChromossomoComparator());
				if(archive.size() > 49) {
					archive.subList(0, 49);
				}
				
			}
			
			System.out.println("Classe: " + classAg);
			Chromossomo.printChromossomo(popupalcao.get(0));
			end = System.currentTimeMillis();
		}
	}
	
	public static void initTesteList() {
		DermatologyDAO dao = new DermatologyDAO();
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
	
}
