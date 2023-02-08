package com.example.dadprojecttcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class HelloController {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    String serverName;
    int portName;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private TextArea input;

    @FXML
    private TextArea output;

    @FXML
    private TextArea input1;

    @FXML
    private TextArea output1;

    @FXML
    private TextArea input2;

    @FXML
    private TextArea output2;

    @FXML
    private Button send;


    @FXML
    public void initialize() {
        ip.setText("192.168.0.56");
        port.setText("502");
    }

    private byte[] data;

    private void showMessage1(int count, byte b[]) {
        output.setText(bytesToHex(Arrays.copyOfRange(b, 0, count)));
    }

    private void showMessage2(int count, byte b[]) {
        output1.setText(bytesToHex(Arrays.copyOfRange(b, 0, count)));
    }

    private void showMessage3(int count, byte b[]) {
        output2.setText(bytesToHex(Arrays.copyOfRange(b, 0, count)));
    }

    private byte[] convertToBy(String s) {
        byte b[] = new byte[s.length() / 2];
        for (int i = 0; i < b.length * 2; i += 2) {
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }

    public void click(ActionEvent actionEvent) {
        portName = Integer.parseInt(port.getText());
        serverName = ip.getText();

        try {

            Socket client = new Socket(serverName, portName);

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            InputStream stream = client.getInputStream();


            if (input.getText() != "") {
                out.write(convertToBy(input.getText()));
                data = new byte[256];
                int count = stream.read(data);
                showMessage1(count, data);
            }
            if (input1.getText() != "") {
                out.write(convertToBy(input1.getText()));
                data = new byte[256];
                int count = stream.read(data);
                showMessage2(count, data);
            }
            if (input2.getText()!=""){
                out.write(convertToBy(input2.getText()));
                data = new byte[256];
                int count = stream.read(data);
                showMessage3(count,data);}


            client.close();

        } catch (IOException e) {
            output.setText(e.getMessage());
        } catch (Exception e) {
            output.setText(e.getMessage());
        }
    }
}
