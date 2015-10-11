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
	//private static DermatologyDAO dao = new DermatologyDAO();
	
	public void randomElements() {
	}
	
	public static void initTesteList() {
		DermatologyDAO dao = new DermatologyDAO();
		List<Chromossomo> classOne = dao.searchDermtology(74, 1);
		List<Chromossomo> classTwo = dao.searchDermtology(40, 2);
		List<Chromossomo> classThree = dao.searchDermtology(49, 3);
		List<Chromossomo> classFour = dao.searchDermtology(32, 4);
		List<Chromossomo> classFive = dao.searchDermtology(35, 5);
		List<Chromossomo> classSix = dao.searchDermtology(14, 6);
		
		trainingDiseae = new ArrayList<Chromossomo>();
		
		trainingDiseae.addAll(classOne);
		trainingDiseae.addAll(classTwo);
		trainingDiseae.addAll(classThree);
		trainingDiseae.addAll(classFour);
		trainingDiseae.addAll(classFive);
		trainingDiseae.addAll(classSix);
	}
	
	public static List<Chromossomo> createPopulation() {
		
		List<Chromossomo> pop = new ArrayList<Chromossomo>();
		Random random = new Random();
		for (int i = 0; i < elementos; i++) {
			pop.add(Chromossomo.buildChromossome(random));
		}
		
		return pop;
	}

	public static void main(String[] args) {
		Long init = 0l;
		Long end = 0l;
		initTesteList();
		for (int i = 1; i <= 6; i++) {
			Float tempo = ((end - init) / 1000f);
			init = System.currentTimeMillis();
			System.out.println("Tempo Execucao:" + tempo);
			List<Chromossomo> popupalcao = createPopulation();
			for (Chromossomo c : popupalcao) {
				Fitness f = new Fitness();
				f.calculateFitness(c, i);
				//Chromossomo.printChromossomo(c);
			}
			for (int j = 1; j <= generation; j++) {
				//System.out.println("Classe " + i);
				
				//soma aptididao
				Float fitnessSum = 0.0f;
				for(Chromossomo cr : popupalcao) {
					fitnessSum = fitnessSum + Float.valueOf(
							String.format("%.2f", cr.getFitness()).replace(",", "."));
				}
				
				Collections.sort(popupalcao, new ChromossomoComparator());
				Chromossomo elit = popupalcao.get(0);
				
				TournamentStocastic tournament = new TournamentStocastic();
				List<Chromossomo> tourElements = tournament.tournamentTimes(fitnessSum, popupalcao,sizeTournament, 24,i);
				
				popupalcao.addAll(tourElements);
				for (Chromossomo c : popupalcao) {
					Fitness f = new Fitness();
					f.calculateFitness(c, i);
					//Chromossomo.printChromossomo(c);
				}
				Collections.sort(popupalcao, new ChromossomoComparator());
				
				popupalcao = popupalcao.subList(0, 49);
				if(!popupalcao.contains(elit)) {
					popupalcao.add(elit);
				}
				Collections.sort(popupalcao, new ChromossomoComparator());
			}
			
			System.out.println("Classe: " + i);
			Chromossomo.printChromossomo(popupalcao.get(0));
			end = System.currentTimeMillis();
		}
	}
}