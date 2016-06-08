package Statistics

class ScalaLog {
  val start: String = "Starting" 
  val game: String = " game"
  val moveLeft: String = "Moving left" 
  val moveRight: String = "Moving right" 
  val eat: String = "Eating" 
  val moveTop: String = "Moving top" 
  val moveBottom: String = "Moving bottom" 

def makePseudo(act: Int): String = { 
  act match { 
      case 1 => start + game // start
      case 2 => moveLeft  // left
      case 3 => moveRight // right
      case 4 => eat // eating
      case 5 => moveTop // top
      case 6 => moveBottom // bottom 
    } 
  } 
}