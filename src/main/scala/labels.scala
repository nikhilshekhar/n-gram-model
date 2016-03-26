package hw3.labels

// Use * to indicate that this case class accepts a variable number of arguments of type String
case class NGram( tokens:String* ) {
  // tokens.init returns all but the last token
  //   -- I.e. this is the w_{i-1}...w_{i-n} of P( w_i | w_{i-1}...w_{i-n} )
  //  We then convert to a variable-length argument list so we can create a new NGram object for it.
  def precedingContext = NGram( tokens.init:_* )

  // tokens.last returns the last token
  //   -- I.e. this is the w_i of P( w_i | w_{i-1}...w_{i-n} )
  def w_i = NGram( tokens.last )

  // tokens.tail returns all but the first token
  //   -- I.e. this is the w_i..w_{i-(n+1)} of P( w_i | w_{i-1}...w_{i-n} )
  //  We then convert to a variable-length argument list so we can create a new NGram object for it.
  def backoff = NGram( tokens.tail:_* )

  // Print out the array of tokens in a readable format
  override def toString = "NGram( " + tokens.mkString(", ") + " )"
}

