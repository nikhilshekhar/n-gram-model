package hw3.ngramModels

import hw3.NGramReader
import hw3.labels.NGram

import math.{exp,log}

class MLENGramModel( n:Int, trainingCorpus:String ) extends AbstractNGramModel( n ) {

  val fullCounts = NGramReader.readNGrams( trainingCorpus, n )
//  print(fullCounts)
  val conditioningCounts = NGramReader.conditioningCounts( fullCounts )
//  print(conditioningCounts)


  def nGramProb( nGram:NGram ) = {
    fullCounts( nGram ) /
      conditioningCounts( nGram.precedingContext )
  }

}

