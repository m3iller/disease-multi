package com.ufu.disease.ag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.udojava.evalex.Expression;
import com.ufu.disease.to.Chromossomo;
import com.ufu.disease.to.Gene;

/**
 * classe que calcula o fitness com base no algoritmo spea2
 * 
 * @author miller
 *
 */
public class Fitness {

	public static Integer TAMANHO_ARCHIVE = 30;
	public static Float threshold = 0.80f;
	private Double distance[][];

	private Spea2Select spea = new Spea2Select();
	public Fitness() {
		super();
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
				if(spea.dominateOther(c1, c2)) {
					myStrenght++;
				}
			}
			c1.setStrenght(Float.valueOf(myStrenght));
		}
	}
	
	
	
	/**
	 * calcula o raw fitnes e distancia, soma e gera o fitness
	 * 
	 * @param population
	 */
	public void calculateRawDistanceFit(List<Chromossomo> population,Set<Chromossomo> archive) {
		
		// cria um mapa com as distancias
		 distance = calcDistanceEucli(population);
		 int x =-1;
		 for(Chromossomo c1: population) {
			  	x++;
			 	float rawFitness = 0;
				//quantidade que domimam(rawfitness)
				for(Chromossomo c2: population) {
					if(spea.dominateOther(c2, c1)) {
						rawFitness += c2.getStrenght(); 
					}
				}
				
				c1.setRawFitness(rawFitness);
				
				Double kth = 0.0d;
				int kthDistance = (int) Math.floor(Math.sqrt(population.size()+archive.size()));
				List<Double> list = Arrays.asList(distance[x]);
				Collections.sort(list, Collections.reverseOrder());
				
				int counter = 0;
				for (Double dis : list) {
					counter++;
					if (counter == kthDistance) {
						kth = dis;
						break;
					}
				}
				
				c1.setDensity((float) (1.0 / (kth + 2)));	
				c1.setFitness(c1.getRawFitness() + c1.getDensity());
		 }
	}
	
	public void calculateFitnessSpea2(List<Chromossomo> population, Set<Chromossomo> archive, Integer classAg,List<Chromossomo> training) {
		
		for(Chromossomo chromoAleatorio: population) {
			calculateFitness(chromoAleatorio, classAg,training);
		}
		calculateStrenght(population);
		calculateRawDistanceFit(population, archive);
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
					
					Double d1 = (double) ((cy.getFunction1() - cz.getFunction1()) * (cy.getFunction1() - cz.getFunction1()));
					Double d2 = (double) ((cy.getFunction2() - cz.getFunction2()) * (cy.getFunction2() - cz.getFunction2()));
					
					distances[z][y] = distances[y][z] = (double) Math.sqrt(d1+d2);
				}
			}
			return distances;
	}
	
	
	
	public void calculateFitness(Chromossomo chromoAleatorio, Integer classAG,
			List<Chromossomo> trainingDisease) {
		
		int truePositive = 0;
		int falsePositive = 0;
		int falseNegative = 0;
		int trueNegative = 0;
		int tamanhoRegra = 0;
			
		for (Chromossomo c : trainingDisease) {
			// verifica se atributos sao equivalentes
			boolean compareValues = functionCompare(c, chromoAleatorio,tamanhoRegra);
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
		
		//chromoAleatorio.setFunction3(tamanhoRegra);
		
		Float se  = truePositive / 
				Float.valueOf((truePositive + falseNegative));
		Float sp = trueNegative / 
				Float.valueOf((trueNegative + falsePositive));
		
		se = Float.valueOf(String.format("%.2f", se).replace(",", "."));
		sp = Float.valueOf(String.format("%.2f", sp).replace(",", "."));
		Float fitness = se * sp;

		if(fitness == 0) {
			fitness = 0.00f;
		}
		
		chromoAleatorio.setFunction1(fitness);
		//tp + tn / (tp +fp+ fn+ true)
		Float acc= (float) ((truePositive + trueNegative) / Float.valueOf((truePositive + falsePositive + falseNegative + trueNegative)));
		chromoAleatorio.setFunction2(acc);
		//chromoAleatorio.setFunction2(calcAcurracy(classAG, truePositive, trueNegative));
	}
	
	public boolean functionCompare(Chromossomo chromoOriginal,
			Chromossomo chromoAleatory, int tamanhoRegra) {

		boolean compareElements = false;
		Boolean existeUmfalse = null;
		
		if (chromoAleatory.getErythema().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getErythema(),
					chromoAleatory.getErythema());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getScaling().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getScaling(),
					chromoAleatory.getScaling());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getDefiniteBorders().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getDefiniteBorders(),
					chromoAleatory.getDefiniteBorders());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getItching().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getItching(),
					chromoAleatory.getItching());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getKoebnerPhenomenon().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getKoebnerPhenomenon(),
					chromoAleatory.getKoebnerPhenomenon());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getPolygonalPapules().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getPolygonalPapules(),
					chromoAleatory.getPolygonalPapules());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getFollicularPapules().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getFollicularPapules(),
					chromoAleatory.getFollicularPapules());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getOralMucosalInvolvement().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getOralMucosalInvolvement(),
					chromoAleatory.getOralMucosalInvolvement());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getKneeElbowInvolvement().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getKneeElbowInvolvement(),
					chromoAleatory.getKneeElbowInvolvement());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getScalpInvolvement().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getScalpInvolvement(),
					chromoAleatory.getScalpInvolvement());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getFamilyHistory().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getFamilyHistory(),
					chromoAleatory.getFamilyHistory());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getMelaninIncontinence().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getMelaninIncontinence(),
					chromoAleatory.getMelaninIncontinence());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getEosinophils().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getEosinophils(),
					chromoAleatory.getEosinophils());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getPnlInfiltrate().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getPnlInfiltrate(),
					chromoAleatory.getPnlInfiltrate());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getFibrosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getFibrosis(),
					chromoAleatory.getFibrosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getExocytosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getExocytosis(),
					chromoAleatory.getExocytosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getAcanthosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getAcanthosis(),
					chromoAleatory.getAcanthosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getHyperkeratosis().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getHyperkeratosis(),
					chromoAleatory.getHyperkeratosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getParakeratosis().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getParakeratosis(),
					chromoAleatory.getParakeratosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getClubbing().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getClubbing(),
					chromoAleatory.getClubbing());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getElongation().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getElongation(),
					chromoAleatory.getElongation());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getThinning().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getThinning(),
					chromoAleatory.getThinning());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getSpongiform().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getSpongiform(),
					chromoAleatory.getSpongiform());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getMunroIcroabcess().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getMunroIcroabcess(),
					chromoAleatory.getMunroIcroabcess());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getFocalHypergranulosis().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getFocalHypergranulosis(),
					chromoAleatory.getFocalHypergranulosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getDisappearance().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getDisappearance(),
					chromoAleatory.getDisappearance());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getVacuolisation().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getVacuolisation(),
					chromoAleatory.getVacuolisation());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getSpongiosis().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getSpongiosis(),
					chromoAleatory.getSpongiosis());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getSawYooth().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getSawYooth(),
					chromoAleatory.getSawYooth());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getFollicular().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getFollicular(),
					chromoAleatory.getFollicular());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getPerifollicular().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getPerifollicular(),
					chromoAleatory.getPerifollicular());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getInflammatory().getWeigth() >= threshold) {
			compareElements = compareExpression(
					chromoOriginal.getInflammatory(),
					chromoAleatory.getInflammatory());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getBandLike().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getBandLike(),
					chromoAleatory.getBandLike());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		if (chromoAleatory.getAge().getWeigth() >= threshold) {
			compareElements = compareExpression(chromoOriginal.getAge(),
					chromoAleatory.getAge());
			if (compareElements == false)
				{ existeUmfalse = true; } tamanhoRegra++;
		}
		
		chromoAleatory.setFunction3(Float.valueOf(tamanhoRegra));
		
		if(existeUmfalse!= null && existeUmfalse == true) {
			return false;
		} else if(existeUmfalse!= null && existeUmfalse == false){
			return true;
		} else {
			return true;
		}
		//return compareElements;
	}

	public boolean compareExpression(Gene original, Gene aleatorio) {
		Expression expression = new Expression(String.format("%s%s%s",
				original.getValue(), aleatorio.getOperator().getNumVal(),
				aleatorio.getValue()));
		Integer result = expression.eval().intValue();
		return result == 1 ? true : false;
	}

}
