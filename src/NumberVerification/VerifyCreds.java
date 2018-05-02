/* 
 * Copyright (C) 2018 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package NumberVerification;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.UIManager.LookAndFeelInfo;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Verify that credentials match up
 *
 * @author N-Tropy
 * @version 1.0
 */
public class VerifyCreds extends JFrame {

    private static final Pattern EMAIL_MATCHER = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static ActionEvent sendOverride;

    private static int option;

    private static JFrame jfrm;

    private static JTextArea jtaDisplay;

    private static JTextField jtfInput;

    private static String input;

    private static String[] pNumParts;

    private JButton jbtnSend;

    private JScrollPane jscrlp;

    /**
     * Constructor calls initializer for JFrame
     */
    private VerifyCreds() {
        init();
    }

    /**
     * Creates JFrame thread
     *
     * @param args unused, would be parameters passed on run
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            jfrm = new VerifyCreds();
            jfrm.setVisible(true);
        });
    }

    /**
     * Initializes values for JFrame
     */
    private void init() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exe) {
            System.err.println("Nimbus unavailable");
        }

        option = 0;
        setTitle("Credential Checker");
        setLayout(new BorderLayout());
        setSize(700, 600);

        jtaDisplay = new JTextArea(20, 40);
        jtaDisplay.setEditable(false);
        jtaDisplay.setLineWrap(true);

        jscrlp = new JScrollPane(jtaDisplay);

        jtfInput = new JTextField(30);

        jbtnSend = new JButton("Send");

        jbtnSend.addActionListener(new handler());

        KeyListener key = new handler();

        jtfInput.addKeyListener(key);

        add(jscrlp, BorderLayout.PAGE_START);

        sendOverride = new ActionEvent(jbtnSend, 1001, "Send");

        JPanel p1 = new JPanel();

        p1.setLayout(new FlowLayout());
        p1.add(jtfInput, BorderLayout.LINE_END);
        p1.add(jbtnSend, BorderLayout.LINE_END);

        add(p1, BorderLayout.PAGE_END);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jtaDisplay.setText("\nWhat is your phone number?: ");
    }

    /**
     * Checks if provided area code and city match
     *
     * @param code provided area code
     * @param city provided city
     */
    private static boolean verify(int code, String city) {
        NumberCred numberVer = new NumberCred();

        int arrSize = numberVer.getArrSize();

        LocationData[] areaCodes = new LocationData[arrSize];

        try {
            for (int j = 0; j < arrSize; j++) {
                areaCodes[j] = numberVer.createLocations(j);
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        for (LocationData curLocation : areaCodes) {
            if (curLocation.getCode() == code) {
                if (curLocation.getCity().equalsIgnoreCase(city)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks provided email against a regular expressions matcher
     *
     * @param email string to be checked
     * @return true/false depending on match
     */
    private static boolean verifyEmail(String email) {
        Matcher emailMatcher = EMAIL_MATCHER.matcher(email);

        return emailMatcher.find();
    }

    /**
     * Sends commands from input to queue handler
     */
    private static class handler implements ActionListener, KeyListener {

        /**
         * If button pressed
         *
         * @param ae ActionEvent invoked
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("Send")) {

                input = jtfInput.getText();

                jtaDisplay.setText(jtaDisplay.getText() + "\nYou: " + input);

                switch (option) {
                    case 0:

                        pNumParts = input.split("[-]");

                        jtaDisplay.setText(jtaDisplay.getText() + "\nWhat city are you from?: ");

                        option = 1;
                        break;

                    case 1:

                        input = input.replaceAll("\\s", "_");

                        if (verify(Integer.valueOf(pNumParts[0]), input)) {
                            jtaDisplay.setText(jtaDisplay.getText() + "\nArea code matches city.");
                        } else {
                            jtaDisplay.setText(jtaDisplay.getText() + "Area code doesn't match any known city...");
                        }

                        jtaDisplay.setText(jtaDisplay.getText() + "\nWhat is your email address?: ");

                        option = 2;
                        break;
                    case 2:

                        if (verifyEmail(input)) {
                            jtaDisplay.setText(jtaDisplay.getText() + "\nSeems legit.");
                        } else {
                            jtaDisplay.setText(jtaDisplay.getText() + "\nI don't think so.");
                        }

                        jtaDisplay.setText(jtaDisplay.getText() + "\nPress any key to start over.");

                        option = 3;
                        break;

                    case 3:

                        jtaDisplay.setText("\nWhat is your phone number?: ");

                        option = 0;
                        break;
                }
                jtfInput.setText("");
            }
        }

        /**
         * Listens for enter key pressed
         *
         * @param ke KeyEvent invoked
         */
        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                actionPerformed(sendOverride);
            }
        }

        /**
         * Necessary override
         *
         * @param ke KeyEvent invoked
         */
        @Override
        public void keyTyped(KeyEvent ke) {
        }

        /**
         * Necessary override
         *
         * @param ke KeyEvent invoked
         */
        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
}
