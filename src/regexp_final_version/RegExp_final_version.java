/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regexp_final_version;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Safat
 */
public class RegExp_final_version {

    /**
     * @param args the command line arguments
     */
    private String fullCode;
    
    public static void main(String[] args) {
        // TODO code application logic here
        RegExp_final_version obj=new RegExp_final_version();
        BufferedReader br = null;

		try {

			String sCurrentLine;
                        
			br = new BufferedReader(new FileReader("E:\\NetBeans Project workspace\\RegExp_final_version\\src\\regexp_final_version\\Inputs"));

                        System.out.println("Reading of Regular Exps: ");
                        
                        String [] exps=new String[Integer.parseInt(br.readLine())];
                        for(int c1=0;c1<exps.length;c1++){
                            System.out.println(exps[c1]=br.readLine());
                        }
                        System.out.println("\n\nReading The test Strings: ");
                        String [] strs=new String[Integer.parseInt(br.readLine())];
                        for(int c1=0;c1<strs.length;c1++){
                            System.out.println(strs[c1]=br.readLine());
                        }
                        for (int i = 0; i < strs.length; i++) {
                            for (int j = 0; j < exps.length; j++) {
                                try{
//                                System.out.println("CHECKER: i: "+i+": "+exps[j]+" , j: "+j+": "+strs[i] );
                                System.out.println("\n-----------------------"
                                        + "\n      RESULTS: "+"i="+i+" , j="+j+"\n       ||"
                                        + obj.supports(exps[j], strs[i])
                                                    +"||\n---------------------");

                                }
                                catch(Exception e){
                                System.out.println("\n_-___----__-\nEXCEPTION: i: "+i+": "+exps[i]+" , j: "+j+": "+strs[j] );
//                                e.printStackTrace();
                                }
                            }
                    }
                        
//			while ((sCurrentLine = br.readLine()) != null) {
//			 obj.fullCode=obj.fullCode+sCurrentLine;
//                            System.out.println(sCurrentLine);
//			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
                
    }
    
    public boolean supports(String regX, String str){
//         String r = "d*ab*[aeiou]{5}xxyf[^io]{3}";//problem when * loop exits before reading *
//        String d = "ddabbbuuouuxxyfpkk";
        boolean status;
        String r=regX,d=str;
        int i = 0;
        int j = 0;
        for (; i < r.length();) {
//            System.out.println(r.length());
            if (i >= r.length() || j >= d.length() || j<0) { // OR added today
                
                if(j >= d.length() ) {
                    while( i+1<r.length() && r.charAt(i + 1) == '*')
                    i += 2;
                }
                System.out.println("breaking in line 28");
                break;
            }
            char a = r.charAt(i);
//            System.out.println("a: " + a);
            char b = d.charAt(j);
//            System.out.println("b: " + b);

            if (r.charAt(i) <= 'z' && r.charAt(i) >= 'a') {
                if ((i + 1) < r.length()) {
                    if (r.charAt(i + 1) == '*') {

                        while (j < d.length() - 1 && r.charAt(i) == d.charAt(j)) {
                            //eating for *
                            j++;
                        }
                        i += 2;

                    } else if (r.charAt(i + 1) == '+') {
//                        System.out.println("HITT");
                        if (d.charAt(j) != r.charAt(i)) {
                            System.out.println("Eror: at least one " + r.charAt(i) + " needed");
                            status=false;
                            break;
                        }
                        while (j < d.length() && r.charAt(i) == d.charAt(j)) {// or length-1 ? check it
                            //eating for *
                            j++;
                        }

                        i += 2;
                    } else if (r.charAt(i + 1) == '?') {
                        if (r.charAt(i) == d.charAt(j)) {
                            j++;
                            if (!(j > d.length() - 1) && r.charAt(i) == d.charAt(j)) {
                                System.out.println("Error ? violated onle ONE, '" + r.charAt(i) + "', expected");
                                status=false;
                                break;
                            }
                        }
                        i += 2;
                    } else {
                        if (r.charAt(i) == d.charAt(j)) {
                            i++;
                            j++;
                        } else {
                            System.out.println("error not match");
                            status=false;
                            break;
                        }
                    }
                } else {// A-Z but no i+1  
                    if (r.charAt(i) == d.charAt(j)) {
                        i++;
                        j++;
                    } else {
                        System.out.println("ERROR: mismatch");
                        status=false;
                        break;
                    }
                }

            } else if (r.charAt(i) == '[') {
//                System.out.println("before: "+r.charAt(i));
                i++;
//                System.out.println("after: "+r.charAt(i));
                String temp = "";
                boolean rangeType;
                int limit;
//                int tCursor = 0;
                while (r.charAt(i) != ']') {

                    temp += r.charAt(i);
                    i++;
                    System.out.println(temp);
                }
                if (temp.charAt(0) == '^') {
                    rangeType = false;
                } else {
                    rangeType = true;
                }
                limit = Integer.parseInt("" + r.charAt(i + 2));
                i=i+4;
//                System.out.println("limit: " + limit);
                
                //matching[...]
                if(j+limit<=d.length()){
                for (int k = 0; k < limit; k++) {
//                    System.out.println("k: " + k);
//                    System.out.println("[...] is: " + d.charAt(j));//ERROR
                    if (rangeType) {
                        if (! temp.contains(""+(char) d.charAt(j) )) {
                            System.out.println("error: [" + temp + "] " + (char) d.charAt(j)+" ffound");
                            j--;
                            status=false;
                            break;
                        } else {
                            j++;
                        }
                    } else {//range false
                        if (temp.contains(""+(char) d.charAt(j) )) {
                            System.out.println("error: [" + temp + "] " + (char) d.charAt(j)+" ffffound");
//                            System.out.println("b: "+i);
                            j--;
//                            System.out.println("a: "+i);
                            status=false;
                            break;
                        } else {
                            j++;
                        }
                    }

                }
            }else{//remaining check's
                    System.out.println("Error: Expecting, ["+temp+"]"+" found "+(char)d.charAt(j));
                    status=false;
                }
            } else {
                i++;
            }

        }

        if (j < d.length() - 1) { //check chnaging d length OK //last CHANGE
            System.out.println("Error: String d Left to parse ");
            status=false;
        } else if (i < r.length()) { // full ok
            System.out.println("i: " + i);
            System.out.println(": " + (r.length()));
            System.out.println("Error: String r Left to parse ");
            status=false;
        } else {
            System.out.println("\n<SUCCESS>: i="+i+" j="+j);
            System.out.println("Full String parsed");
            System.out.println("done");
            status=true;
        }
//        System.out.println("i: " + i + " , " + r.length());
//        System.out.println("j: " + j + " , " + d.length());
    

        return status;
    }
}
