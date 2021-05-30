
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/***
 * @author goatdidi
 **/
public class Main {


    // 将二进制前缀与列表中的ID进行比较
    public int seek(String sign, ArrayList<String> list) {
        int count = 0;
        int value = 0;
        String first = "";// 记录识别出第一个标签的id;
        int f=list.size();
        for (int i = 0; i <f; i++) {
            if (list.get(i).substring(0, sign.length()).equals(sign)) {
                count++;
                if (count == 1) {
                    first = list.get(i);
                }
            }
        }
        if (count == 0) {
            System.out.print("发出信号为 " + sign + "  无响应");
        } else if (count == 1) {
            System.out.print("发出信号为 " + sign + "  识别成功");
            list.remove(first);
            value = 1;
        } else {
            value = 2;
            System.out.print("发出信号为 " + sign + "  冲突个数" + count);
        }
        return value;
    }
    public static ArrayList<String> CreatCode(int n ){
        ArrayList<String> list = new ArrayList<String>();
        Random rd=new Random();
        for (int i = 0;i < n;i++){
            String s ="";
            for(int j=0;j<16;j++) {
                s=s+rd.nextInt(2);
            }
            list.add(s);
        }
        for(int i=0;i<list.size()-1;i++) {
            for(int j=i+1;j<list.size();j++) {
                while(list.get(i).equals(list.get(j))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list.size());
        return list;

    }

    public static void main(String[] args) {
        Main m = new Main();
        Picture pic = new Picture();
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<Integer> all = new ArrayList<Integer>();
        System.out.println("仿真次数");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        if (count>0) {
            while (count > 0){
                System.out.println("标签总数为");
                Scanner sc = new Scanner(System.in);
                int n = sc.nextInt();
                all.add(n);
                ArrayList<String> list = CreatCode(n);
                ArrayList<String> signlist = new ArrayList<String>();
                String sign = "";// 二进制前缀
                int value = 0;//  返回值
                int time = 0;// 次数
    //		signlist.add( sign + "00");
    //		signlist.add( sign + "01");
                signlist.add(sign + "0");
                signlist.add(sign + "1");
                int success = 0;// 成功个数
                while (signlist.size() > 0) {
                    sign = signlist.get(0);
                    value = m.seek(sign, list);
                    time++;
                    signlist.remove(sign);
                    switch (value) {
                        case 0:// 无响应
                            break;
                        case 1:// 识别成功
                            success++;
                            break;
                        case 2:
                            signlist.add(0, sign + "0");
                            signlist.add(1, sign + "1");
    //				signlist.add(2, sign + "10");
    //				signlist.add(3, sign + "11");
                            break;
                    }
                    System.out.println("    当前成功识别总数为：" + success);
                }
                System.out.println("识别次数为" + time + "次，标签总数为：" + success + "  识别完成！");
                times.add(time);
                count--;
        }
        }
        /*for (int x:all){
            System.out.println(x);
        }
        for (int y:times){
            System.out.println(y);
        }*/
        CategoryDataset categoryDataset = pic.createDataset(all,times);
        JFreeChart jFreeChart = pic.createChart(categoryDataset);
        pic.saveAsFile(jFreeChart,"/Users/ace/Desktop/射频识别/RandombinaryTree/jpg/line.jpg",600,400);

    }


}

