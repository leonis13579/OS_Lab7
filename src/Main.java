import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Object mutex = new Object();
    static String file1 = new File("").getAbsolutePath().concat("\\file1.txt");
    static String file2 = new File("").getAbsolutePath().concat("\\file2.txt");

    public static void main(String[] args) {
        System.out.println("[Поток]\t\tДействие\t\t\t\t\t\tfile1.txt\tfile2.txt");
        init();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    int num = new Random().nextInt(100);
                    synchronized (mutex) {
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file1, true));
                            bw.append(String.valueOf(num));
                            bw.append("\n");
                            bw.close();
                            System.out.println("[1]\t\t\tГенерация числа в file1.txt\t\t" + num);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    ArrayList<Integer> nums = new ArrayList<>();
                    synchronized (mutex) {
                        Scanner sc;
                        try {
                            sc = new Scanner(new File(file1));
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                nums.add(Integer.parseInt(line));
                            }
                            sc.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (nums.size() < 2) { continue; }
                        else if (nums.size() > 2) {
                            nums.remove(0);
                            nums.remove(0);
                            String out = nums.stream().map(Objects::toString).collect(Collectors.joining("\n"));

                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
                                bw.write(out);
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
                                bw.write("");
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file2, true));
                            bw.append(String.valueOf(nums.get(0) + nums.get(1)));
                            bw.append("\n");
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("[2]\t\t\tДобавлено число в file2.txt\t\t\t\t\t" + String.valueOf(nums.get(0) + nums.get(1)));
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    ArrayList<Integer> nums = new ArrayList<>();
                    synchronized (mutex) {
                        Scanner sc;
                        try {
                            sc = new Scanner(new File(file2));
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                nums.add(Integer.parseInt(line));
                            }
                            sc.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (nums.size() < 2) { continue; }
                        else if (nums.size() > 2) {
                            nums.remove(0);
                            nums.remove(0);
                            String out = nums.stream().map(Objects::toString).collect(Collectors.joining("\n"));

                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
                                bw.write(out);
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
                                bw.write("");
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file1, true));
                            bw.append(String.valueOf(nums.get(0) * nums.get(1)));
                            bw.append("\n");
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("[3]\t\t\tДобавлено число в file1.txt\t\t" + String.valueOf(nums.get(0) * nums.get(1)));
                }
            }
        }.start();
    }

    private static void init() {
        if(!Files.exists(Paths.get(file1))){
            try {
                new File(file1).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            PrintWriter writer;
            try {
                writer = new PrintWriter(file1);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(Paths.get(file2))) {
            try {
                new File(file2).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            PrintWriter writer;
            try {
                writer = new PrintWriter(file2);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
