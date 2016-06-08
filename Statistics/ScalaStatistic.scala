package Statistics

import com.danilchican.pacman

import scala.collection.mutable.ArrayBuffer

class ScalaStatistic {
  
 def getStatistic(array: Array[Int]): Array[Int] = {
   val max = array.max
   var statisticArray = ArrayBuffer[Int]()
  
   for(i <- 1 to max) {
     statisticArray += ((getDigitArray(array,i).length.toFloat/array.size.toFloat) * 100000).toInt
   }
   
   statisticArray.toArray
 }
 
 private def getDigitArray(array: Array[Int], i: Int) : Array[Int] = {
   for(x <- array if (x > i)) 
     yield x
 }
 
}