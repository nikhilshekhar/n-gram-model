package hw3.ngramModels

import hw3.NGramReader
import hw3.labels.NGram

import math.{exp,log}

abstract class AbstractNGramModel( n:Int ) {
  def nGramProb( nGram:NGram ):Double

  def corpusLogProbability( testFile:String ) = {
    var logProbability = 0D

//    val fullCounts = NGramReader.readNGrams( testFile, n )
//    val conditioningCounts = NGramReader.conditioningCounts(fullCounts)


    io.Source.fromFile( testFile ).getLines.foreach { line =>
      // vocabulary is fixed at test time, map any new tokens to "UNK"
      val nGrams = NGramReader.extractNGrams(line, n, addToVocab = false)
      nGrams.foreach{ nGram =>
        var currentProbability = log(nGramProb(nGram ))
        logProbability +=  currentProbability

      }

//      var counts = Map[NGram,Double]().withDefaultValue( 0D )
//
//      nGrams.foreach{ nGram =>
//        counts += nGram -> { counts( nGram ) + 1 }
//      }
//
//      val conditioningCounts = NGramReader.conditioningCounts(counts)
//
//            nGrams.foreach { nGram =>
//              val q = counts(nGram)
//              val w = conditioningCounts(nGram.precedingContext)
//              println(w.toString)
//              val t = q/(w)
//              logProbability += log(t)
//              println(logProbability)
//            }

//      logProbability =
//        nGrams.foreach { nGram =>
//          var currentProbability = log(fullCounts(nGram) / conditioningCounts(nGram.precedingContext))
//          logProbability += currentProbability
//          println(logProbability)
//        }
      // MODIFY THIS PART FOR QUESTION 1


    }
    logProbability
  }

  def corpusPerplexity( testFile:String ) = {
    var logProbability = 0D
    var logPerplexity = 0D
    var numTokens = 0D
    io.Source.fromFile( testFile ).getLines.foreach{ line =>
      // vocabulary is fixed at test time, map any new tokens to "UNK"
      val nGrams = NGramReader.extractNGrams( line, n, addToVocab = false )

      // MODIFY THIS PART FOR QUESTION 1
      nGrams.foreach{ nGram =>
        var currentProbability = log(nGramProb(nGram ))
        logProbability +=  currentProbability
        numTokens+=1
      }


    }

    logPerplexity = exp((-1/numTokens)*(logProbability))
    // MODIFY THIS PART FOR QUESTION 1
    logPerplexity
  }

}

