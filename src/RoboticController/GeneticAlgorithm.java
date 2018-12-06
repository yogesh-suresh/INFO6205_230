package RoboticController;


public class GeneticAlgorithm {

	
	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;

	
	protected int tournamentSize;

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
			int tournamentSize) {

		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}

	
	public Population initPopulation(int chromosomeLength) {
		// Initialize population
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
	}

	
	public double calcFitness(Individual individual, Maze maze) {
		int[] chromosome = individual.getChromosome();
		TestRobot robot = new TestRobot(chromosome, maze, 100);
		robot.run();
		int fitness = maze.scoreRoute(robot.getRoute());
		individual.setFitness(fitness);

		return fitness;
	}


	public void evalPopulation(Population population, Maze maze) {
		double populationFitness = 0;
		for (Individual individual : population.getIndividuals()) {
			populationFitness += this.calcFitness(individual, maze);
		}
//		System.out.println("Fitness calculated" + populationFitness)
		population.setPopulationFitness(populationFitness);
	}


	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations, double maxScore, double currScore) {
		return ((generationsCount > maxGenerations) && (maxScore == currScore));
	}
	
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations) ;
	}

	public Individual selectParent(Population population) {
		// Create tournament
		Population tournament = new Population(this.tournamentSize);

		// Add random individuals to the tournament
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			Individual tournamentIndividual = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndividual);
		}

		// Return the best
		return tournament.getFittest(0);
	}


	public Population mutatePopulation(Population population) {

		Population newPopulation = new Population(this.populationSize);
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);

			
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				
				if (populationIndex >= this.elitismCount) {
					// Does this gene need mutation?
					if (this.mutationRate > Math.random()) {
						// Get new gene
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						// Mutate gene
						individual.setGene(geneIndex, newGene);
					}
				}
			}

			newPopulation.setIndividual(populationIndex, individual);
		}
		return newPopulation;
	}


	public Population crossoverPopulation(Population population){
	
		Population newPopulation = new Population(population.size());
		
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);
			
			if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
				
				Individual offspring = new Individual(parent1.getChromosomeLength());
				Individual parent2 = this.selectParent(population);
				int swapPoint = (int) (Math.random() * (parent1.getChromosomeLength() + 1));
				
				for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
					// Use half of parent1's genes and half of parent2's genes
					if (geneIndex < swapPoint) {
						offspring.setGene(geneIndex, parent1.getGene(geneIndex));
					} else {
						offspring.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}
		return newPopulation;
	}

}
