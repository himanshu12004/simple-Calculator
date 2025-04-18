import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    JTextField display;
    String value = "";
    double result = 0;
    String operator = "";

    Calculator() {
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "x",
            "1", "2", "3", "+",
            "C", "0", "=", "-",
        };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            panel.add(btn);
        }

        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9]")) {
            value += input;
            display.setText(value);
        } else if (input.equals("C")) {
            value = "";
            operator = "";
            result = 0;
            display.setText("0");
        } else if (input.equals("=")) {
            double num = Double.parseDouble(value);
            switch (operator) {
                case "+": result += num; break;
                case "-": result -= num; break;
                case "x": result *= num; break;
                case "/": result /= num; break;
            }
            display.setText(String.valueOf(result));
            value = "";
            operator = "";
        } else {
            if (!value.isEmpty()) {
                result = Double.parseDouble(value);
                value = "";
            }
            operator = input;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}