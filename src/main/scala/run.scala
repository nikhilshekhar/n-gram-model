package hw3

// Import all the ngram models
import ngramModels._

object Run {
  def main( args:Array[String] ) {
    val n = args(0).toInt
    val corpus = args(1)

    val fullCounts = NGramReader.readNGrams( corpus, n )

    println( fullCounts.mkString( "\n" ) )

    val conditioningCounts = NGramReader.readNGrams( corpus, n-1 )

    println( conditioningCounts.mkString( "\n" ) )
  }
}

object MLENGramModel {
  def main( args:Array[String] ) {
    val n = args(0).toInt
    val trainingCorpus = args(1)
    val testingCorpus = args(2)

    val mleModel = new MLENGramModel( n, trainingCorpus )

    println( "Test Corpus Log Probability: " + mleModel.corpusLogProbability( testingCorpus ) )
    println( "Test Corpus Perplexity: " + mleModel.corpusPerplexity( testingCorpus ) )
  }
}

object AddLambdaNGramModel {
  def main( args:Array[String] ) {
    val n = args(0).toInt
    val trainingCorpus = args(1)
    val testingCorpus = args(2)
    val lambda = args(3).toDouble

    val addLambdaModel = new AddLambdaNGramModel( n, trainingCorpus, lambda )

    println( "Test Corpus Log Probability: " + addLambdaModel.corpusLogProbability( testingCorpus ) )
    println( "Test Corpus Perplexity: " + addLambdaModel.corpusPerplexity( testingCorpus ) )
  }
}

object ADNGramModel {
  def main( args:Array[String] ) {
    val n = args(0).toInt
    val trainingCorpus = args(1)
    val testingCorpus = args(2)
    val discount = args(3).toDouble

    val adModel = new ADNGramModel( n, trainingCorpus, discount )

    println( "Test Corpus Log Probability: " + adModel.corpusLogProbability( testingCorpus ) )
    println( "Test Corpus Perplexity: " + adModel.corpusPerplexity( testingCorpus ) )
  }
}


object BackoffNGramModel {
  def main( args:Array[String] ) {
    val n = args(0).toInt
    val trainingCorpus = args(1)
    val testingCorpus = args(2)
    val lambda = args(3).toDouble

    val backoffModel = new BackoffNGramModel( n, trainingCorpus, lambda )

    println( "Test Corpus Log Probability: " + backoffModel.corpusLogProbability( testingCorpus ) )
    println( "Test Corpus Perplexity: " + backoffModel.corpusPerplexity( testingCorpus ) )
  }
}

