/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.util.Scanner;

public class Ciphers {

    //Set used for ceaser
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHABETC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //set used for vigenere
    public static final char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K',
		'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; 
    public static final char[][] table = new char[alphabet.length][alphabet.length];
      
    public static void main(String[] args) {
        // TODO code application logic here
                //Preparing 2D matrix for vignere cipher
		int a;
		for(int i = 0; i < alphabet.length; i++){
			for(int j = 0; j < alphabet.length; j++){
				a = i+j;
				if(a >= alphabet.length){
					a = a - alphabet.length;
				}
				table[i][j] = alphabet[a];
			}
		}
       /* for(int i = 0; i < table.length; i++){
			for(int j = 0; j < table.length; j++){
				System.out.print(table[i][j]);
			}
			System.out.println();
		} */
        
        Scanner sc=new Scanner(System.in);  
        //Entering plaintext
        System.out.println("Enter the Text:");  
        String PlainText=sc.nextLine(); 
        
        //Selection of cipher
        System.out.println("Please select appropriate cipher:");
        System.out.println("1. Ceaser Cipher.");
        System.out.println("2. Vigenere Cipher.");
        System.out.println("3. Matrix Transposition Cipher.");
        int choice=sc.nextInt();
        
        //Description of each case and actions
        switch(choice)
        {
           
         //ceaser cipher
        case 1:
        System.out.println("Enter the Key:");  
        int key=sc.nextInt();  
        System.out.println("Do you wish to encrypt(e) or decrypt(d) a message?");
        String input=sc.next();
        if(input.equals("e"))
        {
        String ciphert=encrypt(PlainText,key);
        System.out.println("Cipher Text:"+ciphert);
        }
        else if(input.equals("d"))
        {
         String decipher=decrypt(PlainText,key);
        System.out.println("Plain Text:"+decipher);   
        } 
        break;
        case 2:
        //Vigenere cipher
        System.out.println("Enter the Key:");  
        String keyv=sc.next();  
        System.out.println("Do you wish to encrypt(e) or decrypt(d) a message?");
        String inputv=sc.next();
        if(inputv.equals("e"))
        {
        String ciphert=encryptv(PlainText,keyv);
        System.out.println("Cipher Text:"+ciphert);
        }
        else if(inputv.equals("d"))
        {
         String decipher=decryptv(PlainText,keyv);
        System.out.println("Plain Text:"+decipher);   
        } 
        break;
        
        case 3:
        
       //Matrix transposition
        StringBuilder plaintext=new StringBuilder(PlainText);
        System.out.println("Enter the number of key element:");
        int kmax=sc.nextInt();
        int[] keys=new int[kmax];
        System.out.println("Enter Key elements:");
        for(int i=0;i<kmax;i++)
        {
            keys[i]=sc.nextInt();
        }
        
        System.out.println("Do you wish to encrypt(e) or decrypt(d) a message?");
        String inputm=sc.next();
        if(inputm.equals("e"))
        {
            
        String ciphert=encryptm(plaintext,keys,kmax);
        System.out.println("Cipher Text:"+ciphert);
        }
        else if(inputm.equals("d"))
        {
         String decipher=decryptm(plaintext,keys,kmax);
        System.out.println("Plain Text:"+decipher);   
        }
        break;
        
         default:
         System.out.println("Plase Enter valid choice.");
         break;
    } 
        
       
    }
    
    public static String encrypt(String PlainText, int Key)
    {
        String CipherText = "";
        char replaceVal;
        //looping thorught the length of plaintext
        for (int i = 0; i < PlainText.length(); i++)
        {
            char value=PlainText.charAt(i);
            //If space no need to encrypt
            if(!Character.isWhitespace(value))
            {
                if(Character.isLowerCase(value))
                {
                //Encrypting by performing mode operation
                int position = ALPHABET.indexOf(value);
                int keyVal = (Key + position) % 26;
                 replaceVal = ALPHABET.charAt(keyVal);
                }
                else
                {
                    int position = ALPHABETC.indexOf(value);
                int keyVal = (Key + position) % 26;
                 replaceVal = ALPHABETC.charAt(keyVal);
                }
            }
            else
            {
                replaceVal=' ';
            }
            CipherText += replaceVal;
        }
        return CipherText;
    }
    
    public static String decrypt(String cipherText, int shiftKey)
    {
        String plainText = "";
        char replaceVal=' ';
        //looping thorught the length of plaintext
        for (int i = 0; i < cipherText.length(); i++)
        {
            char value=cipherText.charAt(i);
            if(!Character.isWhitespace(value))
            {
                if(Character.isLowerCase(value))
                {
                //Decrypting by performing mode operation
                int position = ALPHABET.indexOf(value);
                int keyVal = (position - shiftKey) % 26;
                if (keyVal < 0)
                {
                    keyVal = ALPHABET.length() + keyVal;
                }
                 replaceVal = ALPHABET.charAt(keyVal);
                }
                else
                {
                    //Decrypting by performing mode operation
                int position = ALPHABETC.indexOf(value);
                int keyVal = (position - shiftKey) % 26;
                if (keyVal < 0)
                {
                    keyVal = ALPHABETC.length() + keyVal;
                }
                 replaceVal = ALPHABETC.charAt(keyVal);
                }
            }
            else
            {
                replaceVal=' ';
            }
                   
            plainText += replaceVal;
        }
        return plainText;
    }
    
    
    
      static String encryptv(String text, final String key) {
        
        text = text.toUpperCase();
        int[] space=new int[10];
        int count=0;
        //Storing the positions where the space is there
        for(int i=0;i<text.length();i++)
        {
            if(Character.isWhitespace(text.charAt(i)))
            {
                space[count]=i;
                count++;
            }
        }
        String sptext;
        //Removing the space
        text=text.replaceAll(" ", "");
        
        StringBuilder keynew = new StringBuilder();
        //Repeating the key until the length of plaintext
        for (int i = 0, keyIndex = 0; i < text.length(); i++,keyIndex++) {
            if (keyIndex >= key.length()) {
                keyIndex = 0;
            }
            keynew.append(key.charAt(keyIndex));
        }
    
        StringBuilder cipher = new StringBuilder();
        String k=keynew.toString().toUpperCase();
        int iindex=0,jindex=0;
        System.out.println("Key final:"+k);
        
        //Looping throughout the 2D array
        for(int te=0;te<text.length();te++)
        {
            char t=text.charAt(te);
            char ke=k.charAt(te);
        for (int i = 0; i < 26; i++) {
            
            //retrieving the row index
            if(t==table[i][0])
                iindex=i;
        }
            for(int j = 0; j < 26; j++)
            {
                //retrieving the column index
                if(ke==table[j][0])
                {
            jindex=j;
                }
            
            }
            //appending the ciphe character at particular place in 2D array
            cipher.append(table[iindex][jindex]);
            iindex=0;
            jindex=0;
        }
        //Adding back the space
        for(int s=0;s<space.length;s++)
        {
            cipher.insert(space[s], " " );
        }
        return cipher.toString();
    }
 
    static String decryptv(String text, final String key) {
        text = text.toUpperCase();
         int[] space=new int[10];
        int count=0;
        //Storing the positions where the space is there
        for(int i=0;i<text.length();i++)
        {
            if(Character.isWhitespace(text.charAt(i)))
            {
                space[count]=i;
                count++;
            }
        }
        String sptext;
        //removing the space
        text=text.replaceAll(" ", "");
        StringBuilder keynew = new StringBuilder();
        //Repeating the key until the length of plaintext
        for (int i = 0, keyValIn = 0; i < text.length(); i++,keyValIn++) {
            if (keyValIn >= key.length()) {
                keyValIn = 0;
            }
            keynew.append(key.charAt(keyValIn));
        }
        
        StringBuilder cipher = new StringBuilder();
        String k=keynew.toString().toUpperCase();
        int iindex=0,jindex=0;
        //System.out.println("Key final:"+k);
        
        ////Looping throughout the 2D array
        for(int te=0;te<text.length();te++)
        {
            char t=text.charAt(te);
            char ke=k.charAt(te);
        for (int i = 0; i < 26; i++) {
            if(ke==table[i][0])
                iindex=i;
        }
            for(int j = 0; j < 26; j++)
            {
                //retrieving the column index
                if(t==table[iindex][j]) 
                {
                    jindex=j;
                }
            }
            //appending the cipher character at particular place in 2D array
            cipher.append(table[0][jindex]);
            iindex=0;
            jindex=0;
        }
        //Adding back the space
        for(int s=0;s<space.length;s++)
        {
            cipher.insert(space[s], " " );
        }
        return cipher.toString();
    }  
    
    
    public static String encryptm(StringBuilder PlainText, int[] Key,int kmax)
    {
        String ciphertext="";
        
        String[][] mtrans;
        int row=0;
        //Adding the percentage instead of space
        for(int i=0;i<PlainText.length();i++)
        {
            if(Character.isWhitespace(PlainText.charAt(i)))
            {
                PlainText.setCharAt(i, '%');
            }
        }
        //Deciding the row needed for matrix
        if(PlainText.length()%kmax==0)
        {
            row=PlainText.length()/kmax;
        }
        else
        {
            row=(PlainText.length()/kmax)+1;
        }
        
       // System.out.println("Row:"+row);
        //Creating 2d array of size required according to input
        mtrans=new String[row][kmax];
        int size=0;
        //looping throught the 2D array and adding plaintext characters to the matrix
        for(int m=0;m<row;m++)
        {
            for(int n=0;n<kmax;n++)
            {
                //checking if plaintext is finished then can put % at remaining places
                if(size<PlainText.length())
                {
                mtrans[m][n]=Character.toString(PlainText.charAt(size));
                size++;
                }
                else
                {
                mtrans[m][n]="%";
                }
            }
            
        }
        
        //Reading matrix according to the key positions to retrive ciphertext
       for(int i=0;i<kmax;i++)
       {
           for(int j=0;j<row;j++)
           {
               ciphertext=ciphertext+mtrans[j][Key[i]-1];
           }
           
       }
        
        return ciphertext.toString();
    }
    
    public static String decryptm(StringBuilder Ciphertext, int[] Key,int kmax)
    {
        String plaintext="";
        String[][] mtrans;
        int row=0;
        
        //Deciding the row needed for matrix
        if(Ciphertext.length()%kmax==0)
        {
            row=Ciphertext.length()/kmax;
        }
        else
        {
            row=(Ciphertext.length()/kmax)+1;
        }
        
        //System.out.println("Row:"+row);
        //Creating 2d array of size required according to input
        mtrans=new String[row][kmax];
        int size=0;
        //Adding ciphertext characters to the matrix
        for(int m=0;m<kmax;m++)
        {
            for(int n=0;n<row;n++)
            {
                
                mtrans[n][Key[m]-1]=Character.toString(Ciphertext.charAt(size));
               size++;
            }
            
        }
         //Reading matrix according to the key positions to retrive plaintext
       for(int i=0;i<kmax;i++)
       {
           for(int j=0;j<row;j++)
           {
               String val=mtrans[i][j];
               if(val.equals("%"))
               {
               plaintext=plaintext+" ";
               }
               
               else
               {
                   plaintext=plaintext+mtrans[i][j];
               }
           }
       }  
       return plaintext;
    }
}
