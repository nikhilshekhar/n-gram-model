package hw3

import hw3.labels.NGram

object NGramReader {

  var vocab = Set[String]()

  def unkifyTokens( tokens:Array[String] ) = {
    tokens.map{ t =>
      if( vocab.contains(t) ) t else "UNK"
    }
  }

  def extractNGrams( line:String, n:Int, addToVocab:Boolean = false ):Array[NGram] = {
    // Skip empty lines
    if( line.length > 0 ) {
      var tokens = line.split( " " )
//      line.split( " " ).foreach(println)

      // If adding to vocab, add any new words to the vocab, otherwise unkify
      if( addToVocab ) {
        vocab ++= tokens
      } else {
        tokens = unkifyTokens( tokens )
      }


      val tokensAndBoundary = Array.fill( n-1 )( "#" ) ++ tokens

      // Array[T] (an array of type T) has a method sliding( n:Int )=>Array[Array[T]] that creates a
      // an iterator of arrays, where each array is of length n, that "slide" over the original
      // array.
      tokensAndBoundary.sliding( n ).map{ nTokens =>


        // :_* converts the array of Strings to a sequence of arguments to the case class
        NGram( nTokens:_* )
      }.toArray
    } else { Array() }
  }

  def readNGrams( corpusPath:String, n:Int ) = {
    var counts = Map[NGram,Double]().withDefaultValue( 0D )

    io.Source.fromFile( corpusPath ).getLines.foreach{ line =>
      val nGrams = extractNGrams( line, n, addToVocab = true )
      nGrams.foreach{ nGram =>
        counts += nGram -> { counts( nGram ) + 1 }
      }
    }

    counts
  }


  def conditioningCounts( fullCounts:Map[NGram,Double] ) = {
    var counts = Map[NGram,Double]().withDefaultValue( 0D )
    fullCounts.foreach{ case ( ngram, count ) =>
      val precedingContext = ngram.precedingContext
      counts += precedingContext -> ( counts( precedingContext ) + count )
    }

    counts
  }

  def backoffCounts( fullCounts:Map[NGram,Double] ) = {
    var counts = Map[NGram,Double]().withDefaultValue( 0D )
    fullCounts.foreach{ case ( nGram, count ) =>
      val backoffNGram = nGram.backoff
      counts += backoffNGram -> ( counts( backoffNGram ) + count )
    }

    counts
  }


  def extractVocabulary( nGrams:Set[NGram] ) = {
    var vocab = Set[String]()

    nGrams.foreach{ nGram =>
      vocab ++= nGram.tokens
    }
    // Beginning-of-string marker is not a word
    vocab -= "#"
    vocab
  }

  def ngramsByContext( nGrams:Set[NGram] ) = {
    var byContext = Map[NGram,Set[NGram]]().withDefaultValue( Set() )
    // This produces a Map from the set of preceding contexts to the set of full n-grams with that
    // preceding context. I.e., if nGrams is:
    //    Set( NGram( "a", "b" ), NGram( "a", "c" ), NGram( "b", "c" ) )
    // then this produces:
    //    Map(
    //      NGram( "a" ) -> Set( NGram( "a", "b" ), NGram( "a", "c" ) ),
    //      NGram( "b" ) -> Set( NGram( "b", "c" ) )
    //    )
    byContext ++= nGrams.groupBy( nGram => nGram.precedingContext )

    byContext
  }
}


