package hw3.ngramModels

import hw3.NGramReader
import hw3.labels.NGram

import math.{exp,log}

class BackoffNGramModel( n:Int, trainingCorpus:String, lambda:Double ) extends AbstractNGramModel( n ) {
  val fullCounts = NGramReader.readNGrams( trainingCorpus, n )
  val contextCounts = NGramReader.conditioningCounts( fullCounts )

  val backoffCounts = NGramReader.backoffCounts( fullCounts )
  val backoffContextCounts = NGramReader.conditioningCounts( backoffCounts )

  val vocab = NGramReader.extractVocabulary( fullCounts.keySet )

  def nGramProb( nGram:NGram ) = {
    // Implement me for question 5

    val p_full = (fullCounts(nGram))/(contextCounts(nGram.precedingContext))
//    println(p_full)
    val p_backoff =  (backoffCounts(nGram.backoff))/(backoffContextCounts(nGram.backoff.precedingContext))
//    println(backoffCounts(nGram))

//    val p_full = (fullCounts(nGram) + lambda)/(contextCounts(nGram.precedingContext)+lambda * vocab.size)
//    val p_backoff =  (backoffCounts(nGram) +lambda)/(backoffContextCounts(nGram.backoff)+lambda *vocab.size)

//    val p_backoff = (fullCounts(nGram) + lambda)/(contextCounts(nGram.backoff)+lambda * vocab.size)

    lambda * p_full + (1-lambda) * p_backoff

  }

}


