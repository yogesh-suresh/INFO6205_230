package RoboticController;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeneticAlgorithmTest {

	@Test
	public void Fitnesstest() {
		GeneticAlgorithm test = new GeneticAlgorithm(200, 0.01, 0.9, 2, 10);
		Individual individual = new Individual(128);
		Maze maze = new Maze(new int[][] { { 0, 0, 0, 0, 1, 0, 1, 3, 4 }, { 1, 0, 1, 1, 1, 0, 1, 3, 1 },
				{ 1, 0, 0, 1, 3, 3, 3, 3, 1 }, { 3, 3, 3, 1, 3, 1, 1, 0, 1 }, { 3, 1, 3, 3, 3, 1, 1, 0, 0 },
				{ 3, 3, 1, 1, 1, 1, 0, 1, 1 }, { 1, 3, 0, 1, 3, 3, 3, 3, 3 }, { 0, 3, 1, 1, 3, 1, 0, 1, 3 },
				{ 1, 3, 3, 3, 3, 1, 1, 1, 2 } });
//		System.out.println(individual.getFitness());
		double fitness = test.calcFitness(individual, maze);

		assertNotEquals(-1, fitness);

	}

	@Test
	public void Mutationtest() {
		GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.01, 0.9, 2, 10);
		Population population = ga.initPopulation(128);
		Population mutatedpopulation = ga.mutatePopulation(population);
		assertNotEquals(population, mutatedpopulation);
	}

	@Test
	public void Crossovertest() {
		GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.01, 0.9, 2, 10);
		Population population = ga.initPopulation(128);
		Population crossoverpopulation = ga.crossoverPopulation(population);
		assertNotEquals(population, crossoverpopulation);

	}

	@Test
	public void Fitnesstest2() {
		GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.01, 0.9, 2, 10);
		Maze maze = new Maze(new int[][] { { 0, 0, 0, 0, 1, 0, 1, 3, 4 }, { 1, 0, 1, 1, 1, 0, 1, 3, 1 },
				{ 1, 0, 0, 1, 3, 3, 3, 3, 1 }, { 3, 3, 3, 1, 3, 1, 1, 0, 1 }, { 3, 1, 3, 3, 3, 1, 1, 0, 0 },
				{ 3, 3, 1, 1, 1, 1, 0, 1, 1 }, { 1, 3, 0, 1, 3, 3, 3, 3, 3 }, { 0, 3, 1, 1, 3, 1, 0, 1, 3 },
				{ 1, 3, 3, 3, 3, 1, 1, 1, 2 } });
		Population population = ga.initPopulation(128);
		int generation = 1;
		while (ga.isTerminationConditionMet(generation, 500) == false) {
			population = ga.crossoverPopulation(population);
			population = ga.mutatePopulation(population);
			ga.evalPopulation(population, maze);
			generation++;
		}
		assertEquals(29, population.getFittest(0).getFitness());

	}
	

}
