package sort

import application.Constants.NUMBER
import entity.Step
import application.FileStream

import scala.collection.mutable.Buffer
import scala.collection.Map
import scala.collection.immutable.ListMap
import scala.collection.immutable.List
import scala.collection.mutable.LinkedHashMap
import scala.collection.JavaConverters._
import scala.collection.mutable.MutableList
import java.nio.file

class Sorting {
  var path = "kppsort/"
  var listOfSteps = Buffer[entity.Step]();
  var bestGame = "bestGame"
  var worstGame = "worstGame"
  var listOfGames = MutableList[(String, Buffer[entity.Step])]()
  var length = 0
  var numberOfTurns = 0
  var lengthStatistic = 0
  var turnStatistic = 0
  var lengthBest = 0

  def sort(lastNumber: Int, operation: Boolean) = {
    var i = 0
    val map = scala.collection.mutable.Map[String, Int]()

    for (i <- 0 until lastNumber) {
      var filename = path + "game " + "(" + i.toString() + ")" + ".txt";
      var length = 0
      var numberOfTurns = 0
      listOfSteps = FileStream.readFromFile(new java.io.File(filename)).asScala
      listOfSteps.foreach(step => {
        if (operation) {
          length = step.getLength;
          map += filename -> length
        } else {
          numberOfTurns = step.getNumberOfTurns
          map += filename -> numberOfTurns
        }
        length = step.getLength()
        numberOfTurns = step.getNumberOfTurns()
      })
      lengthStatistic += length
      turnStatistic += numberOfTurns
    }

    var resultList = mergeSort(map.toList)
    var stepp = resultList(0).toString()
    worstGame = stepp.substring(1, stepp.indexOf(','))

    print("Worst: " + worstGame + "\n")
    var step = resultList(NUMBER - 1).toString()
    bestGame = step.substring(1, step.indexOf(','))

    print("Best: " + bestGame + "\n")

    i = 0
    for (i <- 0 until lastNumber) {
      print("Game sort" + resultList(i).toString() + "\n")
    }
  }

  val str = change(0)

    def change(action : Int):String = {
    action match {
      case 0 => "Snake length "
      case 1 => "Coordinate SNAKE X "
      case 2 => "Coordinate SNAKE Y "
      case 3 => "Coordinate APPLE X "
      case 4 => "Coordinate APPLE Y "
      case 5 => "****************************************"
    }
    }

  def getstrin(): String = {
     str
   }

  def statistic(lastNumber: Int) = {
    var length = 0
    var numberOfTurns = 0
    listOfSteps.clear();
    listOfSteps = FileStream.readFromFile(new java.io.File(bestGame)).asScala
    listOfSteps.foreach(step => {

      lengthBest = step.getLength;
      })
      lengthBest
      }
  def getBestGame() = {
    bestGame
  }
  def getWorstGame() = {
    worstGame
  }
  def getLength() = {
    lengthStatistic
  }
  def getNumberOfTurns = {
    turnStatistic
  }
  def mergeSort(xs: List[(String, Int)]): List[(String, Int)] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[(String, Int)], ys: List[(String, Int)]): List[(String, Int)] =
        (xs, ys) match {
          case (Nil, ys) => ys
          case (xs, Nil) => xs
          case (x :: xs1, y :: ys1) =>
            if (x._2 < y._2) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }
      val (left, right) = xs splitAt (n)
      merge(mergeSort(left), mergeSort(right))
    }
  }
}


