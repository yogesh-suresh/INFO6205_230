package RoboticControllerTest;

import RoboticController.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RoboticControllerJTest {

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
	
	@Test
	public void Robot() {
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
		
		Maze newmaze1 = new Maze(new int[][] {   { 1, 0, 1, 0, 2 }, 
												 { 1, 1, 1, 1, 3 },
												 { 1, 1, 3, 3, 3 }, 
												 { 3, 3, 3, 1, 1 }, 
												 { 4, 1, 1, 1, 1 } });
		
		int[] chromosome =  population.getFittest(0).getChromosome();
		TestRobot robot = new TestRobot(chromosome, newmaze1, 100);
		robot.run();
//		System.out.println(robot.printRoute());
//		System.out.println("X" + robot.getCurrentXPos() );
//		System.out.println("Y" + robot.getCurrentYPos() );
		assertNotEquals(1,newmaze1.getPositionValue(robot.getCurrentXPos(), robot.getCurrentYPos()));
	}
	
	@Test
	public void scoreRouteTest() {
		Maze newmaze1 = new Maze(new int[][] {   { 1, 0, 1, 0, 2 }, 
												 { 1, 1, 1, 1, 3 },
												 { 1, 1, 3, 3, 3 }, 
												 { 3, 3, 3, 1, 1 }, 
												 { 4, 1, 1, 1, 1 } });
		 ArrayList<int[]> route = new ArrayList<int[]>();
		 int[] a = {4,0};  
		 route.add(a);
		 int[] a1 = {4,1}; 
		 route.add(a1);
		 int[] a2 = {4,2}; 
		 route.add(a2);
		 int[] a3 = {3,2};
		 route.add(a3);
		 int[] a4 = {2,2};
		 route.add(a4);
		 int[] a5 = {2,3};
		 route.add(a5);
		 int[] a6 = {1,3};
		 route.add(a6);
		 int[] a7 = {0,3};
		 route.add(a7);
		 int[] a8 = {0,4};
		 route.add(a8);
		 int fitness = newmaze1.scoreRoute(route);
//		 System.out.println(fitness);
		assertEquals(7, fitness);

	}
}
