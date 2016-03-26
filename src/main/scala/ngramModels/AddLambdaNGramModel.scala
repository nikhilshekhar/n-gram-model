package hw3.ngramModels

import hw3.NGramReader
import hw3.labels.NGram

import math.{exp,log}

class AddLambdaNGramModel( n:Int, trainingCorpus:String, lambda:Double ) extends AbstractNGramModel( n ) {

  val fullCounts = NGramReader.readNGrams( trainingCorpus, n )
  val conditioningCounts = NGramReader.conditioningCounts( fullCounts )

  // This is a Set[String] containing all the words in the training set. N.B.: Sets have a method
  // size that report the number of items they contain, and a Set doesn't contain any duplicate
  // entries.
  val vocab = NGramReader.extractVocabulary( fullCounts.keySet )

  val p = 0D
  def nGramProb( nGram:NGram ) = {
    // Replace me with add lambda computation for question 2
    (fullCounts(nGram) + lambda)/(conditioningCounts(nGram.precedingContext)+lambda * vocab.size)


  }

}

