import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.TextArea;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.event.*;

public class CashRegister implements ActionListener {

    JFrame frame;
    JTextArea receipt;
    JButton kaffeButton;
    JButton nalleButton;
    JButton muggButton;
    JButton chipsButton;
    JButton vaniljYoghurtButton;
    JButton daimButton;
    JTextArea inputProductName;
    JTextArea inputCount;
    JButton addToReceiptButton;
    JButton payButton;

    Produkt kaffe = new Produkt("Kaffee Gevalia", 49.99);
    Produkt nalle = new Produkt("Nallebjörn", 109.99);
    Produkt mugg = new Produkt("Mugg", 29.99);
    Produkt chips = new Produkt("Chips", 34.99);
    Produkt yoghurt = new Produkt("Yoghurt", 19.99);
    Produkt daim = new Produkt("Daim", 14.99);

    double totalSum = 0.0;
    boolean clearOnNextAdd = false;

    public CashRegister(){
        frame = new JFrame("IOT24 POS");

        createReceiptArea();
        createQuickButtonsArea();
        createAddArea();

        kaffeButton.addActionListener(this);
        nalleButton.addActionListener(this);
        muggButton.addActionListener(this);
        chipsButton.addActionListener(this);
        vaniljYoghurtButton.addActionListener(this);
        daimButton.addActionListener(this);
        addToReceiptButton.addActionListener(this);
        payButton.addActionListener(this);

        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void createAddArea(){
        inputProductName = new JTextArea();
        inputProductName.setBounds(20, 650, 300, 30);
        inputProductName.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputProductName);

        JLabel label = new JLabel("Antal");
        label.setBounds(340, 625, 300, 30);
        label.setForeground(Color.WHITE);
        frame.add(label);

        inputCount = new JTextArea();
        inputCount.setBounds(330, 650, 50, 30);
        inputCount.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputCount);

        addToReceiptButton = new JButton("Add");
        addToReceiptButton.setBounds(400, 640, 70, 50);
        addToReceiptButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        frame.add(addToReceiptButton);

        payButton = new JButton("Pay");
        payButton.setBounds(480, 640, 70, 50);
        payButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        frame.add(payButton);
    }

    private void createQuickButtonsArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(Color.green);
        panel.setPreferredSize(new Dimension(600, 600));

        kaffeButton = new JButton("Kaffe");
        panel.add(kaffeButton);

        nalleButton = new JButton("Nalle");
        panel.add(nalleButton);

        muggButton = new JButton("Mugg");
        panel.add(muggButton);

        chipsButton = new JButton("Chips");
        panel.add(chipsButton);

        vaniljYoghurtButton = new JButton("Yoghurt");
        panel.add(vaniljYoghurtButton);

        daimButton = new JButton("Daim");
        panel.add(daimButton);

        panel.setBounds(0, 0, 600, 600);
        frame.add(panel);
    }

    private void createReceiptArea() {
        receipt = new JTextArea();
        receipt.setSize(400, 400);
        receipt.setLineWrap(true);
        receipt.setEditable(false);
        receipt.setVisible(true);
        receipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(receipt);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(600, 0, 400, 1000);

        frame.add(scroll);
    }

    public void run(){
        receipt.append("                     STEFANS SUPERSHOP\n");
        receipt.append("----------------------------------------------------\n");
        receipt.append("\n");
        receipt.append("Kvittonummer No: 122      Datum: 2024-09-01 13:00:21\n");
        receipt.append("----------------------------------------------------\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == kaffeButton) {
            inputProductName.setText("Kaffee Gevalia");
        } else if (e.getSource() == nalleButton) {
            inputProductName.setText("Nallebjörn");
        } else if (e.getSource() == muggButton) {
            inputProductName.setText("Mugg");
        } else if (e.getSource() == chipsButton) {
            inputProductName.setText("Chips");
        } else if (e.getSource() == vaniljYoghurtButton) {
            inputProductName.setText("Yoghurt");
        } else if (e.getSource() == daimButton) {
            inputProductName.setText("Daim");
        } else if (e.getSource() == addToReceiptButton) {

            if (clearOnNextAdd) {
                receipt.setText("");
                clearOnNextAdd = false;
            }

            String produktNamn = inputProductName.getText();
            String antalStr = inputCount.getText();
            if (!produktNamn.isEmpty() && !antalStr.isEmpty()) {
                try {
                    int antal = Integer.parseInt(antalStr);
                    if (antal <= 0){
                        receipt.append("Error: Antal måste vara ett nummer\n");
                    }else {
                    double prisPerSt = 0;


                    if (produktNamn.equals(kaffe.getName())) prisPerSt = kaffe.getPrice();
                    else if (produktNamn.equals(nalle.getName())) prisPerSt = nalle.getPrice();
                    else if (produktNamn.equals(mugg.getName())) prisPerSt = mugg.getPrice();
                    else if (produktNamn.equals(chips.getName())) prisPerSt = chips.getPrice();
                    else if (produktNamn.equals(yoghurt.getName())) prisPerSt = yoghurt.getPrice();
                    else if (produktNamn.equals(daim.getName())) prisPerSt = daim.getPrice();

                    double radSumma = antal * prisPerSt;
                    totalSum += radSumma;

                    receipt.append(produktNamn + " \t " + antal + " * \t " + prisPerSt + " = \t " + radSumma + " \n");

                    inputProductName.setText("");
                    inputCount.setText("");
                    }
                } catch (NumberFormatException ex) {
                    receipt.append("Error: Antal måste vara ett nummer\n");
                }

        } else if (e.getSource() == payButton) {
            receipt.append("----------------------------------------------------\n");
            receipt.append("Total:                                       -----\n");
            receipt.append("                                            " + totalSum + "\n");
            receipt.append("Tack för ditt köpet\n\n");

            totalSum = 0.0;
            clearOnNextAdd = true;
        }
    }
    }
}