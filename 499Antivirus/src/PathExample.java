package pathTest;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;


public class PathExample {

    public static void main(String[] args) {
        PathExample objPath = new PathExample();
        List<String> pathToFiles = objPath.folderPath();
       objPath.lookThroughFile(pathToFiles);

    }
    public List<String> folderPath(){
        //First get a scanner and ask the user what folder they want to detect for a virus.
        Scanner in = new Scanner(System.in);
        String fileName;
        System.out.println("type in the path for the folder");
        fileName = in.next();

        //This will search through what file to look through
        Path path = Paths.get(fileName);

        //this will find all the paths for the folder and store the paths in a string
        try(Stream<Path> subPaths = Files.walk(path)){

            List<String> subPathList = subPaths.filter(Files::isRegularFile)
                .map(Objects::toString)
                .collect(Collectors.toList());
            Iterator<String> tempt = subPathList.iterator();

            while(tempt.hasNext()) {
                String str = tempt.next();
                if(!str.endsWith(".txt")) {
                    tempt.remove();
                }
            }
            tempt.forEachRemaining(subPathList::add);
            System.out.println(subPathList);
            return subPathList;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void lookThroughFile(List<String> subPathList) {
      //Next we will put the list of files in a method to read.
        for(int i = 0; i < subPathList.size(); i++){
            try{
                File myObj = new File(subPathList.get(i));
                //make a loop for that file and read through the line to see if there is any viruses detected
                Scanner readFile = new Scanner(myObj);
                while (readFile.hasNextLine()){
                    //Will read through the line and see if it finds any character or any kind of viruses
                    String data = readFile.nextLine();
                    System.out.println(data);
                }
                readFile.close();
            }catch(FileNotFoundException e){
                System.out.println("Can't find File");
                e.printStackTrace();
            }
        }
    }
}
