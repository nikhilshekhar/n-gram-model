package hw3.ngramModels

import hw3.NGramReader
import hw3.labels.NGram

import math.{exp,log,max}

class ADNGramModel( n:Int, trainingCorpus:String, discount:Double ) extends AbstractNGramModel( n ) {
  val fullCounts = NGramReader.readNGrams( trainingCorpus, n )
  val conditioningCounts = NGramReader.conditioningCounts( fullCounts )

  val byContext = NGramReader.ngramsByContext( fullCounts.keySet )

  val backoffCounts = NGramReader.backoffCounts( fullCounts )
  val backoffContextCounts = NGramReader.conditioningCounts( backoffCounts )

  val vocab = NGramReader.extractVocabulary( fullCounts.keySet )

  def nGramProb( nGram:NGram ) = {
    val precedingContext = nGram.precedingContext
    val contextTypeCount = byContext( precedingContext ).size

    // MODIFY THIS PART FOR QUESTION 7

//    val p_full = (fullCounts(nGram) + lambda)/(backoffContextCounts(nGram.precedingContext)+lambda * vocab.size)
  var lambda = 0D
//    if(backoffContextCounts(nGram.backoff) == 0){
//      lambda = 1- (contextTypeCount *  discount / 1)
//    }else{
//      lambda = 1- (contextTypeCount *  discount / backoffContextCounts(nGram.backoff))
//    }

    lambda = 1- (contextTypeCount *  discount / backoffContextCounts(nGram.backoff.precedingContext))

    val p_full = max(fullCounts(nGram)-discount,0)/conditioningCounts(nGram.precedingContext)
    val p_backoff = (1-lambda) * (backoffCounts(nGram.backoff))/(backoffContextCounts(nGram.backoff.precedingContext))

    p_full + p_backoff
  }

}

