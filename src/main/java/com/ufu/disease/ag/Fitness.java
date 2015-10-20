package com.ufu.disease.ag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.udojava.evalex.Expression;
import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.ChromossomoComparator;
import com.ufu.disease.to.Gene;

public class Fitness {

	private static Integer TAMANHO_ARCHIVE = 50;
	public static Float threshold = 0.80f;
	private Double distance[][];

	public Fitness() {
		super();
	}
	
	public void calculateFitnessSpea2(List<Chromossomo> population, List<Chromossomo> archive, Integer classAg) {
		//population.addAll(archive);
		for(Chromossomo chromoAleatorio: population) {
			calculateFitness(chromoAleatorio, classAg);
		}
		calculateStrenght(population);
		calculateRawDistanceFit(population, archive);
	}
	
	
	/**
	 * retorno os individuos da proxima geração 
	 * 
	 * @param population
	 * @return
	 */
	public List<Chromossomo> selectSpea2(List<Chromossomo> population, List<Chromossomo> archive) {
		
		List<Chromossomo> nextGen = new ArrayList<Chromossomo>();
		Collections.sort(population, new ChromossomoComparator());
		
		for(Chromossomo c: population) {
			if(c.getFitness() < 1) {
				nextGen.add(c);
			}
		}
		
		for(Chromossomo c: archive) {
			if(c.getFitness() < 1) {
				nextGen.add(c);
			}
		}
		
		//depois de adicionar todos nao dominas  f < 0 teremos 3 condicoes
		//mesmo tamanho
		if(nextGen.size() == archive.size()) {
			//faz nada
		} 
		
		if(nextGen.size() > archive.size()) {
			//pega o ktzimo elemento mais proximo com distancia menor remove ate chegar no numero maximo
			
			distance = calcDistanceEucli(nextGen);
			int x=0;
			while(nextGen.size() < 50) {
				List<Double> list = Arrays.asList(distance[x]);
				Collections.sort(list);
				
				for(int y=0; y< list.size(); y++) {
					if(list.get(y++) < list.get(y)) {
						nextGen.remove(y++);
						break;
					}
				}
				x++;
			}
			
		}
		//se for menor adiciona os melhores elementos para proxima geracao
		if(nextGen.size() < archive.size()) {
			population.addAll(archive);
			Collections.sort(population,new ChromossomoComparator());
			int i=0;
			while(nextGen.size() < 50) {
				nextGen.add(population.get(i++));
			}
		}
		
		return nextGen;
	}
	
	/**
	 * calcula as strenght de cada individuo, +1 para cada indivudo que ele domina
	 * 
	 * @param population
	 */
	public void calculateStrenght(List<Chromossomo> population) {
		for(Chromossomo c1: population) {
			int myStrenght =0;
			
			//quantidade de elementos que sao dominados pelo individuo(streng)
			for(Chromossomo c2: population) {
				if(c1.getFunction1() >= c2.getFunction1() &&
						c1.getFunction2() >= c2.getFunction2()) {
					myStrenght++;
				}
			}
			c1.setRawFitness(Float.valueOf(myStrenght));
		}
	}
	
	/**
	 * calcula o raw fitnes e distancia, soma e gera o fitness
	 * 
	 * @param population
	 */
	public void calculateRawDistanceFit(List<Chromossomo> population,List<Chromossomo> archive) {
		
		// cria um mapa com as distancias
		 distance = calcDistanceEucli(population);
		 int x =-1;
		 for(Chromossomo c1: population) {
			  	x++;
			 	float rawFitness = 0;
				//quantidade que domimam(rawfitness)
				for(Chromossomo c2: population) {
					if(c2.getFunction1() >= c1.getFunction1() && c2.getFunction2() >= c1.getFunction2()) {
						rawFitness += c2.getStrenght(); 
					}
				}
				
				c1.setRawFitness(rawFitness);
				
				Double kth = 0.0d;
				int kthDistance = (int) Math.floor(Math.sqrt(population.size()+archive.size()));
				List<Double> list = Arrays.asList(distance[x]);
				Collections.sort(list);
				
				int counter = 0;
				for (Double dis : list) {
					counter++;
					if (counter == kthDistance) {
						kth = dis;
						break;
				}
				
				c1.setDensity((float) (1 / (kth + 2)));	
				c1.setFitness(c1.getRawFitness() + c1.getDensity());
			}
		 }
	}
	
	/**
	 * calcula a distancia euclidiana entre dois elementos utilizando f1 e f2
	 * 
	 * @param population
	 * @return
	 */
	public Double[][] calcDistanceEucli(List<Chromossomo> population) {
		
			Double[][] distances = new Double[population.size()][population.size()];
			for (int y = 0; y < population.size(); y++) {
				distances[y][y] = 0.0d;
				for (int z = y + 1; z < population.size(); z++) {
					
					Chromossomo cy = population.get(y);
					Chromossomo cz = population.get(z);
					
					distances[z][y] = distances[y][z] = (double) (((cy.getFunction1() - cz.getFunction1()) * (cy.getFunction1() - cz.getFunction1())) +
							((cy.getFunction2() - cz.getFunction2()) + (cy.getFunction2() - cz.getFunction2())));
				}
			}
			return distances;
	}
	
	public void calculateFitness(Chromossomo chromoAleatorio, Integer classAG) {
		
		int truePositive = 0;
		int falsePositive = 0;
		int falseNegative = 0;
		int trueNegative = 0;

		for (Chromossomo c : AlgoritGenetic.trainingDiseae) {
			// verifica se atributos sao equivalentes
			boolean compareValues = functionCompare(c, chromoAleatorio);
			if (compareValues) {
				if (c.getClassDisease().getValue().intValue() == classAG.intValue()) {
					++truePositive;
				} else {
					++falsePositive;
				}
			} else {
				if (c.getClassDisease().getValue().intValue() == classAG.intValue()) {
					++falseNegative;
				} else {
					++trueNegative;
				}
			}
		}
		
		Float se  = truePositive / 
				Float.valueOf((truePositive + falseNegative));
		Float sp = trueNegative / 
				Float.valueOf((trueNegative + falsePositive));
		
		se = Float.valueOf(String.format("%.2f", se).replace(",", "."));
		sp = Float.valueOf(String.format("%.2f", sp).replace(",", "."));
		Float fitness = se * sp;

		if(fitness == 0) {
			fitness = 0.01f;
		}
		
		chromoAleatorio.setFunction1(fitness);
		chromoAleatorio.setFunction2(calcAcurracy(classAG, truePositive));
	}
	
	public Float calcAcurracy(Integer claz, Integer truePositive) {
		
		Float accuracy = 0.0f;
		switch (claz) {
		case 1:
			accuracy = Float.valueOf(truePositive / AlgoritGenetic.classOne.size());
			break;
		case 2:
			accuracy = Float.valueOf(truePositive / AlgoritGenetic.classTwo.size());
			break;
		case 3:
			accuracy = Float.valueOf(truePositive / AlgoritGenetic.classThree.size());
			break;
		case 4:
			accuracy = Float.valueOf(truePositive / AlgoritGenetic.classFour.size());
			break;
		case 5:
			accuracy = Float.valueOf(truePositive / AlgoritGenetic.classFive.size());
			break;
		case 6:
			accuracy = Float.valueOf(truePositive / AlgoritGenetic.classSix.size());
			break;
		default:
			break;
		}
		
		return accuracy;
	}

	public boolean functionCompare(Chromossomo chromoOriginal,
			Chromossomo chromoAleatory) {

		boolean compareElements = false;
		if (chromoAleatory.getErythema().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getErythema(),
					chromoAleatory.getErythema());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getScaling().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getScaling(),
					chromoAleatory.getScaling());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getDefiniteBorders().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getDefiniteBorders(),
					chromoAleatory.getDefiniteBorders());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getItching().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getItching(),
					chromoAleatory.getItching());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getKoebnerPhenomenon().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getKoebnerPhenomenon(),
					chromoAleatory.getKoebnerPhenomenon());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getPolygonalPapules().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getPolygonalPapules(),
					chromoAleatory.getPolygonalPapules());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getFollicularPapules().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getFollicularPapules(),
					chromoAleatory.getFollicularPapules());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getOralMucosalInvolvement().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getOralMucosalInvolvement(),
					chromoAleatory.getOralMucosalInvolvement());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getKneeElbowInvolvement().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getKneeElbowInvolvement(),
					chromoAleatory.getKneeElbowInvolvement());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getScalpInvolvement().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getScalpInvolvement(),
					chromoAleatory.getScalpInvolvement());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getFamilyHistory().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getFamilyHistory(),
					chromoAleatory.getFamilyHistory());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getMelaninIncontinence().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getMelaninIncontinence(),
					chromoAleatory.getMelaninIncontinence());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getEosinophils().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getEosinophils(),
					chromoAleatory.getEosinophils());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getPnlInfiltrate().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getPnlInfiltrate(),
					chromoAleatory.getPnlInfiltrate());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getFibrosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getFibrosis(),
					chromoAleatory.getFibrosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getExocytosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getExocytosis(),
					chromoAleatory.getExocytosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getAcanthosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getAcanthosis(),
					chromoAleatory.getAcanthosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getHyperkeratosis().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getHyperkeratosis(),
					chromoAleatory.getHyperkeratosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getParakeratosis().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getParakeratosis(),
					chromoAleatory.getParakeratosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getClubbing().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getClubbing(),
					chromoAleatory.getClubbing());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getElongation().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getElongation(),
					chromoAleatory.getElongation());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getThinning().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getThinning(),
					chromoAleatory.getThinning());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getSpongiform().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getSpongiform(),
					chromoAleatory.getSpongiform());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getMunroIcroabcess().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getMunroIcroabcess(),
					chromoAleatory.getMunroIcroabcess());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getFocalHypergranulosis().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getFocalHypergranulosis(),
					chromoAleatory.getFocalHypergranulosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getDisappearance().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getDisappearance(),
					chromoAleatory.getDisappearance());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getVacuolisation().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getVacuolisation(),
					chromoAleatory.getVacuolisation());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getSpongiosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getSpongiosis(),
					chromoAleatory.getSpongiosis());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getSawYooth().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getSawYooth(),
					chromoAleatory.getSawYooth());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getFollicular().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getFollicular(),
					chromoAleatory.getFollicular());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getPerifollicular().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getPerifollicular(),
					chromoAleatory.getPerifollicular());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getInflammatory().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getInflammatory(),
					chromoAleatory.getInflammatory());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getBandLike().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getBandLike(),
					chromoAleatory.getBandLike());
			if (compareElements == false)
				return false;
		}
		if (chromoAleatory.getAge().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getAge(),
					chromoAleatory.getAge());
			if (compareElements == false)
				return false;
		}
	
		return compareElements;
	}

	public boolean compareExpression(Gene original, Gene aleatorio) {
		Expression expression = new Expression(String.format("%s%s%s",
				original.getValue(), aleatorio.getOperator().getNumVal(),
				aleatorio.getValue()));
		Integer result = expression.eval().intValue();
		return result == 1 ? true : false;
	}

}
