package com.ufu.disease.to;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import com.ufu.disease.ag.AlgoritGenetic;
import com.ufu.disease.ag.Fitness;

public class Chromossomo {
	
	public Chromossomo() {
		this.erythema = new Gene();
		this.scaling= new Gene();
		this.definiteBorders= new Gene();
		this.itching= new Gene();
		this.koebnerPhenomenon= new Gene();
		this.polygonalPapules= new Gene();
		this.follicularPapules= new Gene();
		this.oralMucosalInvolvement= new Gene();
		this.kneeElbowInvolvement= new Gene();
		this.scalpInvolvement= new Gene();
		this.familyHistory= new Gene();
		this.melaninIncontinence= new Gene();
		this.eosinophils= new Gene();
		this.pnlInfiltrate= new Gene();
		this.fibrosis= new Gene();
		this.exocytosis= new Gene();
		this.acanthosis= new Gene();
		this.hyperkeratosis= new Gene();
		this.parakeratosis= new Gene();
		this.clubbing= new Gene();
		this.elongation= new Gene();
		this.thinning= new Gene();
		this.spongiform= new Gene();
		this.munroIcroabcess= new Gene();
		this.focalHypergranulosis= new Gene();
		this.disappearance= new Gene();
		this.vacuolisation= new Gene();
		this.spongiosis= new Gene();
		this.sawYooth= new Gene();
		this.follicular= new Gene();
		this.perifollicular= new Gene();
		this.inflammatory= new Gene();
		this.bandLike= new Gene();
		this.age= new Gene();
		this.classDisease= new Gene();
	}

	private Integer idDermatology;
	private Gene erythema;
	private Gene scaling;
	private Gene definiteBorders;
	private Gene itching;
	private Gene koebnerPhenomenon;
	private Gene polygonalPapules;
	private Gene follicularPapules;
	private Gene oralMucosalInvolvement;
	private Gene kneeElbowInvolvement;
	private Gene scalpInvolvement;
	private Gene familyHistory;
	private Gene melaninIncontinence;
	private Gene eosinophils;
	private Gene pnlInfiltrate;
	private Gene fibrosis;
	private Gene exocytosis;
	private Gene acanthosis;
	private Gene hyperkeratosis;
	private Gene parakeratosis;
	private Gene clubbing;
	private Gene elongation;
	private Gene thinning;
	private Gene spongiform;
	private Gene munroIcroabcess;
	private Gene focalHypergranulosis;
	private Gene disappearance;
	private Gene vacuolisation;
	private Gene spongiosis;
	private Gene sawYooth;
	private Gene follicular;
	private Gene perifollicular;
	private Gene inflammatory;
	private Gene bandLike;
	private Gene age;
	private Gene classDisease;
	
	private Float fitness;
	
	public final Integer getIdDermatology() {
		return idDermatology;
	}
	public final void setIdDermatology(Integer idDermatology) {
		this.idDermatology = idDermatology;
	}
	public final Gene getErythema() {
		return erythema;
	}
	public final void setErythema(Gene erythema) {
		this.erythema = erythema;
	}
	public final Gene getScaling() {
		return scaling;
	}
	public final void setScaling(Gene scaling) {
		this.scaling = scaling;
	}
	public final Gene getDefiniteBorders() {
		return definiteBorders;
	}
	public final void setDefiniteBorders(Gene definiteBorders) {
		this.definiteBorders = definiteBorders;
	}
	public final Gene getItching() {
		return itching;
	}
	public final void setItching(Gene itching) {
		this.itching = itching;
	}
	public final Gene getKoebnerPhenomenon() {
		return koebnerPhenomenon;
	}
	public final void setKoebnerPhenomenon(Gene koebnerPhenomenon) {
		this.koebnerPhenomenon = koebnerPhenomenon;
	}
	public final Gene getPolygonalPapules() {
		return polygonalPapules;
	}
	public final void setPolygonalPapules(Gene polygonalPapules) {
		this.polygonalPapules = polygonalPapules;
	}
	public final Gene getFollicularPapules() {
		return follicularPapules;
	}
	public final void setFollicularPapules(Gene follicularPapules) {
		this.follicularPapules = follicularPapules;
	}
	public final Gene getOralMucosalInvolvement() {
		return oralMucosalInvolvement;
	}
	public final void setOralMucosalInvolvement(Gene oralMucosalInvolvement) {
		this.oralMucosalInvolvement = oralMucosalInvolvement;
	}
	public final Gene getKneeElbowInvolvement() {
		return kneeElbowInvolvement;
	}
	public final void setKneeElbowInvolvement(Gene kneeElbowInvolvement) {
		this.kneeElbowInvolvement = kneeElbowInvolvement;
	}
	public final Gene getScalpInvolvement() {
		return scalpInvolvement;
	}
	public final void setScalpInvolvement(Gene scalpInvolvement) {
		this.scalpInvolvement = scalpInvolvement;
	}
	public final Gene getFamilyHistory() {
		return familyHistory;
	}
	
	public final void setFamilyHistory(Gene familyHistory) {
		this.familyHistory = familyHistory;
	}
	public final Gene getMelaninIncontinence() {
		return melaninIncontinence;
	}
	public final void setMelaninIncontinence(Gene melaninIncontinence) {
		this.melaninIncontinence = melaninIncontinence;
	}
	public final Gene getEosinophils() {
		return eosinophils;
	}
	public final void setEosinophils(Gene eosinophils) {
		this.eosinophils = eosinophils;
	}
	public final Gene getPnlInfiltrate() {
		return pnlInfiltrate;
	}
	public final void setPnlInfiltrate(Gene pnlInfiltrate) {
		this.pnlInfiltrate = pnlInfiltrate;
	}
	public final Gene getFibrosis() {
		return fibrosis;
	}
	public final void setFibrosis(Gene fibrosis) {
		this.fibrosis = fibrosis;
	}
	public final Gene getExocytosis() {
		return exocytosis;
	}
	public final void setExocytosis(Gene exocytosis) {
		this.exocytosis = exocytosis;
	}
	public final Gene getAcanthosis() {
		return acanthosis;
	}
	public final void setAcanthosis(Gene acanthosis) {
		this.acanthosis = acanthosis;
	}
	public final Gene getHyperkeratosis() {
		return hyperkeratosis;
	}
	public final void setHyperkeratosis(Gene hyperkeratosis) {
		this.hyperkeratosis = hyperkeratosis;
	}
	public final Gene getParakeratosis() {
		return parakeratosis;
	}
	public final void setParakeratosis(Gene parakeratosis) {
		this.parakeratosis = parakeratosis;
	}
	public final Gene getClubbing() {
		return clubbing;
	}
	public final void setClubbing(Gene clubbing) {
		this.clubbing = clubbing;
	}
	public final Gene getElongation() {
		return elongation;
	}
	public final void setElongation(Gene elongation) {
		this.elongation = elongation;
	}
	public final Gene getThinning() {
		return thinning;
	}
	public final void setThinning(Gene thinning) {
		this.thinning = thinning;
	}
	public final Gene getSpongiform() {
		return spongiform;
	}
	public final void setSpongiform(Gene spongiform) {
		this.spongiform = spongiform;
	}
	public final Gene getMunroIcroabcess() {
		return munroIcroabcess;
	}
	public final void setMunroIcroabcess(Gene munroIcroabcess) {
		this.munroIcroabcess = munroIcroabcess;
	}
	public final Gene getFocalHypergranulosis() {
		return focalHypergranulosis;
	}
	public final void setFocalHypergranulosis(Gene focalHypergranulosis) {
		this.focalHypergranulosis = focalHypergranulosis;
	}
	public final Gene getDisappearance() {
		return disappearance;
	}
	public final void setDisappearance(Gene disappearance) {
		this.disappearance = disappearance;
	}
	public final Gene getVacuolisation() {
		return vacuolisation;
	}
	public final void setVacuolisation(Gene vacuolisation) {
		this.vacuolisation = vacuolisation;
	}
	public final Gene getSpongiosis() {
		return spongiosis;
	}
	public final void setSpongiosis(Gene spongiosis) {
		this.spongiosis = spongiosis;
	}
	public final Gene getSawYooth() {
		return sawYooth;
	}
	public final void setSawYooth(Gene sawYooth) {
		this.sawYooth = sawYooth;
	}
	public final Gene getFollicular() {
		return follicular;
	}
	public final void setFollicular(Gene follicular) {
		this.follicular = follicular;
	}
	public final Gene getPerifollicular() {
		return perifollicular;
	}
	public final void setPerifollicular(Gene perifollicular) {
		this.perifollicular = perifollicular;
	}
	public final Gene getInflammatory() {
		return inflammatory;
	}
	public final void setInflammatory(Gene inflammatory) {
		this.inflammatory = inflammatory;
	}
	public final Gene getBandLike() {
		return bandLike;
	}
	public final void setBandLike(Gene bandLike) {
		this.bandLike = bandLike;
	}
	public final Gene getAge() {
		return age;
	}
	public final void setAge(Gene age) {
		this.age = age;
	}
	public final Gene getClassDisease() {
		return classDisease;
	}
	public final void setClassDisease(Gene classDisease) {
		this.classDisease = classDisease;
	}
	public Float getFitness() {
		return fitness;
	}
	public void setFitness(Float fitness) {
		this.fitness = fitness;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDermatology == null) ? 0 : idDermatology.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromossomo other = (Chromossomo) obj;
		if (idDermatology == null) {
			if (other.idDermatology != null)
				return false;
		} else if (!idDermatology.equals(other.idDermatology))
			return false;
		return true;
	}
	public static Chromossomo buildChromossome(Random r) {
		Chromossomo c = new Chromossomo();
		
		c.idDermatology = new Integer(AlgoritGenetic.id++);
		c.erythema = new Gene(r,3,0);
		c.scaling= new Gene(r,3,0);
		c.definiteBorders= new Gene(r,3,0);
		c.itching= new Gene(r,3,0);
		c.koebnerPhenomenon= new Gene(r,3,0);
		c.polygonalPapules= new Gene(r,3,0);
		c.follicularPapules= new Gene(r,3,0);
		c.oralMucosalInvolvement= new Gene(r,3,0);
		c.kneeElbowInvolvement= new Gene(r,3,0);
		c.scalpInvolvement= new Gene(r,3,0);
		c.familyHistory= new Gene(r,1,0);
		c.melaninIncontinence= new Gene(r,3,0);
		c.eosinophils= new Gene(r,3,0);
		c.pnlInfiltrate= new Gene(r,3,0);
		c.fibrosis= new Gene(r,3,0);
		c.exocytosis= new Gene(r,3,0);
		c.acanthosis= new Gene(r,3,0);
		c.hyperkeratosis= new Gene(r,3,0);
		c.parakeratosis= new Gene(r,3,0);
		c.clubbing= new Gene(r,3,0);
		c.elongation= new Gene(r,3,0);
		c.thinning= new Gene(r,3,0);
		c.spongiform= new Gene(r,3,0);
		c.munroIcroabcess= new Gene(r,3,0);
		c.focalHypergranulosis= new Gene(r,3,0);
		c.disappearance= new Gene(r,3,0);
		c.vacuolisation= new Gene(r,3,0);
		c.spongiosis= new Gene(r,3,0);
		c.sawYooth= new Gene(r,3,0);
		c.follicular= new Gene(r,3,0);
		c.perifollicular= new Gene(r,3,0);
		c.inflammatory= new Gene(r,3,0);
		c.bandLike= new Gene(r,3,0);
		c.age= new Gene(r,79,0);
		c.classDisease= new Gene(r,6,1);
		
		return c;
	}
	
	public static void printPopulation(List<Chromossomo> list) {
		for(Chromossomo c: list) {
			System.out.println(c);
		}
	}
	
	@Override
	public String toString() {
		return " ID: " + idDermatology +
				" Fitness:" + this.getFitness();
	}
	
	public static void printChromossomo(Chromossomo c) {
		try {
			System.out.println("id: " + c.getIdDermatology());
			for (Field field : c.getClass().getDeclaredFields()) {
				field.setAccessible(true); // You might want to set modifier to
											// public first.
				Object o = field.get(c);
				if (o instanceof Gene) {
					o = (Gene) o;
					Gene value = (Gene) field.get(c);
					if (value != null && value.getWeigth() != null
							&& value.getWeigth() >= Fitness.threshold) {
						if (field.getName().equals("idDermatology")) {
							System.out.println(field.getName() + " "
									+ value.getOperator() + " "
									+ value.getValue() + "| "
									+ value.getWeigth());
						} else {
							System.out.println(field.getName() + " "
									+ value.getOperator().getNumVal() + " "
									+ value.getValue() + "| "
									+ value.getWeigth());
						}
					}
				}
			}
			System.out.println("Fit:" + c.getFitness());
			System.out.println("\n ");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
