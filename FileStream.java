package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.Stream;
import java.util.Map;

import javax.swing.JOptionPane;

import entity.Step;
import sort.Sorting;

public class FileStream implements Constants{


public static void writeUsingFiles(ArrayList<Step> listOfSteps, File file, boolean status) {
        if (file.exists()) {
          file.delete();
          try {
            file.createNewFile();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        FileOutputStream fileOutStream;
        try {
          fileOutStream = new FileOutputStream(file, false);
          try (ObjectOutputStream Stream = new ObjectOutputStream(fileOutStream)) {
            Stream.writeInt(listOfSteps.size());
            for (Step step : listOfSteps) {
              Stream.writeObject(step);
            }
            Stream.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: cannot save to file");
            return;
          } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: IO error");
            return;
          }
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
      }


/**
 * @return ArrayList<Step> that was read from file
 */
public static ArrayList<Step> readFromFile(File file) {
  Sorting sort = new Sorting();
  ArrayList<Step> listOfSteps = new ArrayList<>();
  try (ObjectInputStream Stream = new ObjectInputStream(new FileInputStream(file))) {
    Step step = new Step();
    listOfSteps.clear();
    int size = Stream.readInt();
    for (int i = 0; i < size; i++) {
      try {
        step = (Step) Stream.readObject();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      listOfSteps.add(step.clone());
      System.out.println(sort.change(0) + step.getLength());
      System.out.println(sort.change(1) + step.getAppleX());
      System.out.println(sort.change(2) + step.getAppleY());
      System.out.println(sort.change(3) + step.getSnakeXBody());
      System.out.println(sort.change(4) + step.getSnakeYBody());
      System.out.println(sort.change(5));
    }
  } catch (FileNotFoundException e) {
    JOptionPane.showMessageDialog(null, "Error: cannot open file");
  } catch (IOException e) {
    JOptionPane.showMessageDialog(null, "Error: IO error, cannot read");
  }
  return listOfSteps;
}

public static String[] sorting(boolean operation) {

  ArrayList<Step> listOfSteps = new ArrayList<Step>();
  int length;
  int numberOfTurns;
  String path = "kppsort/";
  LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
  LinkedHashMap<String, Integer> resultMap = new LinkedHashMap<String, Integer>();

  System.out.println("Working...\n\n");
  for (int i = 0; i < NUMBER; i++) {
    String string = path + "game " + "(" + i + ")" + ".txt";
    System.out.println(string);
    File gameReplayFile = new File(string);
    listOfSteps.clear();
    listOfSteps = FileStream.readFromFile(gameReplayFile);

    Step step = listOfSteps.get(listOfSteps.size() - 1);
    if (operation) {
      length = step.getLength();
      map.put(string, length);
    } else {
      numberOfTurns = step.getNumberOfTurns();
      map.put(string, numberOfTurns);
    }
  }
  String[] strList = new String[NUMBER];
  resultMap = (LinkedHashMap<String, Integer>) FileStream.sortByValue(map);

  synchronized (resultMap) {
    Iterator<String> iterator = resultMap.keySet().iterator();
    try {
      int tmp = 0;
      while (iterator.hasNext()) {
        strList[tmp] = iterator.next();
        tmp++;
      }
    } catch (Exception e) {
      System.out.println("Iterator problems");
    }
  }

  File fileWorst = new File(strList[0]);

  listOfSteps = FileStream.readFromFile(fileWorst);
  File worst = new File("WorstGameJava"); // save worst replay
  writeUsingFiles(listOfSteps, worst, false);

  File fileBest = new File(strList[NUMBER - 1]);

  listOfSteps = FileStream.readFromFile(fileBest);
  File best = new File("BestGameJava"); // save best replay
  writeUsingFiles(listOfSteps, best, false);

  return strList;
}

public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
  Map<K, V> result = new LinkedHashMap<>();
  Stream<Map.Entry<K, V>> st = map.entrySet().stream();
  st.sorted(Map.Entry.comparingByValue())
      .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
  return result;
  }

}
