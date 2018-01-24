package com.example.android.encryptiondecryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.android.encryptiondecryption.R.id.Caesar_Cipher_checkbox;
import static com.example.android.encryptiondecryption.R.id.outputPlayfair;

public class MainActivity extends AppCompatActivity {

    //********Casear Cipher ****************************
    static ArrayList<Character> list = new ArrayList<>();
    static ArrayList<Character> encrypted_list = new ArrayList<>();
    static String plainText = null;
    static char cipher = ' ';
    static String output_text = "";
    int quantity = 1;

    public static void Encryption(String text) {
        String plaint = text;
        output_text += "\nCipher Message Is : ";
        for (int i = 0; i < plaint.length(); i++) {
            if ((int) (plaint.charAt(i)) == 32) {
                output_text += " ";
                list.add(' ');
            } else {
                cipher = (char) ((plaint.charAt(i) + 3));
                if (cipher > 90 && cipher < 94) {
                    cipher = (char) ((cipher - 91) + 'A');
                } else if (cipher > 122) {
                    cipher = (char) ((cipher - 123) + 'a');
                }
                list.add(cipher);
                output_text += cipher + "";
            }
        }
        output_text += "\n";
        Decryption(list);
    }

    //********Casear Cipher Decryption *******************
    public static void Decryption(ArrayList<Character> array) {
        encrypted_list = array;
        System.out.println(plainText);
        output_text += "Plain Message Is : ";
        for (int i = 0; i < encrypted_list.size(); i++) {
            if ((int) encrypted_list.get(i) == 32) {
                output_text += " ";
            } else {
                cipher = (char) (encrypted_list.get(i) - 3);
                if (cipher < 65) {
                    cipher = (char) ((cipher + 91) - 'A');
                } else if (cipher < 97 && cipher > 93) {
                    cipher = (char) ((cipher + 123) - 'a');
                }
                output_text += cipher + "";
            }
        }


    }
    //*********************************************************
    //************** Playfair ********************
    char[][] matrix2D = new char[5][5];
    ArrayList<Character> cipherCharacterList = new ArrayList<>();
    ArrayList<Character> plainCharacterList = new ArrayList<>();
    char[] keyCharacterArray;
    int r, c, index = 0;
    int lastRow = 0, lastColumn = 0;
    int rIndex1, cIndex1, rIndex2, cIndex2, rX, cX;
    boolean hasFinished = false, hasFound1 = false, hasFound2 = false, IsLetterIOrJFound = false;
    String plaintextString;
    String output_text_playfair = "";

    public void createMatrix2D() {
        int letter = 97;

        for (r = 0; r < 5; r++) {
            for (c = 0; c < 5; c++) {
                matrix2D[r][c] = keyCharacterArray[index];
                if ((keyCharacterArray[index] == 'i') || (keyCharacterArray[index] == 'j')) {
                    IsLetterIOrJFound = true;
                }
                index++;
                if (index == keyCharacterArray.length) {
                    hasFinished = true;
                    break;
                }
            }
            lastRow++;
            if (hasFinished) {
                lastRow--;
                break;
            }
        }

        for (letter = letter; letter < 123; letter++) {
            if ((letter == 106) || IsLetterIOrJFound) {
                IsLetterIOrJFound = false;
                continue;
            }
            if (!checkCharInMatrix((char) letter)) {
                c++;
                if (c > 4) {
                    if (lastRow >= 4) {
                        break;
                    } else {
                        lastRow++;
                        c = 0;
                        matrix2D[lastRow][c] = (char) letter;
                    }
                } else {
                    matrix2D[lastRow][c] = (char) letter;
                }
            }
        }
    }

    public boolean checkCharInMatrix(char letter) {
        for (int r = 0; r <= lastRow; r++) {
            for (int c = 0; c < 5; c++) {
                if (matrix2D[r][c] == letter) {
                    return true;
                }
            }
        }
        return false;
    }

    public void enciphering() {
        char firstChar, secondChar;
        for (int i = 0; i < plainCharacterList.size() - 1; i += 2) {
            firstChar = plainCharacterList.get(i);
            secondChar = plainCharacterList.get(i + 1);
            findIndex1(firstChar);
            findIndex2(secondChar);
            if (cIndex1 == cIndex2) {
                if (rIndex1 == 4) {
                    rIndex1 = 0;
                } else {
                    rIndex1++;
                }
                if (rIndex2 == 4) {
                    rIndex2 = 0;
                } else {
                    rIndex2++;
                }
            } else if (rIndex1 == rIndex2) {
                if (cIndex1 == 4) {
                    cIndex1 = 0;
                } else {
                    cIndex1++;
                }
                if (cIndex2 == 4) {
                    cIndex2 = 0;
                } else {
                    cIndex2++;
                }
            } else {
                int temp;
                temp = cIndex1;
                cIndex1 = cIndex2;
                cIndex2 = temp;
            }
            cipherCharacterList.add(matrix2D[rIndex1][cIndex1]);
            cipherCharacterList.add(matrix2D[rIndex2][cIndex2]);
        }

    }

    public void findIndex1(char letter) {
        for (r = 0; r < 5; r++) {
            for (c = 0; c < 5; c++) {
                if (matrix2D[r][c] == letter) {
                    rIndex1 = r;
                    cIndex1 = c;
                    hasFound1 = true;
                    break;
                }
            }
            if (hasFound1) {
                hasFound1 = false;
                break;
            }
        }
    }

    public void findIndex2(char letter) {
        for (r = 0; r < 5; r++) {
            for (c = 0; c < 5; c++) {
                if (matrix2D[r][c] == letter) {
                    rIndex2 = r;
                    cIndex2 = c;
                    hasFound2 = true;
                    break;
                }
            }
            if (hasFound2) {
                hasFound2 = false;
                break;
            }
        }
    }

    public void findX() {
        for (r = 0; r < 5; r++) {
            for (c = 0; c < 5; c++) {
                if (matrix2D[r][c] == 'x') {
                    rX = r;
                    cX = c;
                    hasFound2 = true;
                    break;
                }
            }
            if (hasFound2) {
                hasFound2 = false;
                break;
            }
        }
    }

    public void convertPlaintextToList() {
        for (int i = 0; i < plaintextString.length(); i++) {
            plainCharacterList.add(plaintextString.charAt(i));
        }
    }

    public void checkTwoFollowedSimilarLetter() {
        for (int i = 0; i < plainCharacterList.size() - 1; i += 2) {
            if (plainCharacterList.get(i) == plainCharacterList.get(i + 1)) {
                plainCharacterList.add((i + 1), 'x');
            }
        }
    }

    public void addXOrYForOddPlaintext() {

        if ((plainCharacterList.size() % 2) != 0) {
            if (plainCharacterList.get(plainCharacterList.size() - 1) == 'x') {
                plainCharacterList.add('y');
            } else {
                plainCharacterList.add('x');
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * this method is called when the order button is clicked
     */

    public void Encrypt_Decrtpt(View view) {

        CheckBox CaesarCipherCheckbox = (CheckBox) findViewById(Caesar_Cipher_checkbox);
        boolean CaesarCipher_state = CaesarCipherCheckbox.isChecked();

        CheckBox PlayfairCheckbox = (CheckBox) findViewById(R.id.Playfair_checkbox);
        boolean Playfair_state = PlayfairCheckbox.isChecked();


        TextView plainText_Field = (TextView) findViewById(R.id.plainText_Field);
        plainText = plainText_Field.getText().toString();
        if (plainText.isEmpty()) {
            Toast.makeText(this, "Enter the Plaintext !!!", Toast.LENGTH_SHORT).show();
        }
        else if (CaesarCipher_state==false && Playfair_state==false) {
            Toast.makeText(this, "Please check an algorithm !!!", Toast.LENGTH_SHORT).show();
        }else{
            createOutput(CaesarCipher_state, Playfair_state);
        }
    }

    public void createOutput(boolean CaesarCipherState, boolean PlayfairState) {

        TextView outputTextView = (TextView) findViewById(R.id.outputCaesarCipher);
        if (CaesarCipherState) {
            Encryption(plainText);
            outputTextView.setText(output_text);
            list.clear();
            output_text="";
        }

        if (PlayfairState) {

            String keyString;
            TextView key_Field = (TextView) findViewById(R.id.key_Field);
            keyString = key_Field.getText().toString();
            if (keyString.isEmpty()) {
                Toast.makeText(this, "You must enter a key !!!", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity playFair = new MainActivity();

                playFair.keyCharacterArray = keyString.toCharArray();

                playFair.plaintextString = plainText;

                playFair.convertPlaintextToList();
                playFair.createMatrix2D();
                playFair.checkTwoFollowedSimilarLetter();
                playFair.addXOrYForOddPlaintext();
                playFair.enciphering();

                output_text_playfair = "Ciphertexr Is: ";
                for (int i = 0; i < playFair.cipherCharacterList.size(); i++) {
                    output_text_playfair += playFair.cipherCharacterList.get(i);
                }
                output_text_playfair += "\n";

                TextView outputPlayfair = (TextView) findViewById(R.id.outputPlayfair);
                outputPlayfair.setText(output_text_playfair);

            }
        }
    }

    public String createOrderSummary(int price, boolean state, boolean state2, String name_Field) {
        String message = "Name :" + name_Field + "\nAdd Whipped Cream?" + state + "\nAdd Choclate?" + state2 + "\nQuantity :" + quantity + "\nTotal :$" + price + "\nThank You!";
        return message;
    }

    public void cancel(View view) {
        TextView outputTextView = (TextView) findViewById(R.id.outputCaesarCipher);
        outputTextView.setText("");

        TextView plainText_Field = (TextView) findViewById(R.id.plainText_Field);
        plainText_Field.setText("");

        CheckBox CaesarCipherCheckbox = (CheckBox) findViewById(R.id.Caesar_Cipher_checkbox);
        CaesarCipherCheckbox.setChecked(false);

        TextView fairplayTextView = (TextView) findViewById(outputPlayfair);
        fairplayTextView.setText("");

        TextView keyField = (TextView) findViewById(R.id.key_Field);
        keyField.setText("");

        CheckBox PlayfairCheckbox = (CheckBox) findViewById(R.id.Playfair_checkbox);
        PlayfairCheckbox.setChecked(false);
    }
}
//#FF9800