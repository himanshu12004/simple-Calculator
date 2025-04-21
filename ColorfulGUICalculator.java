import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class ColorfulCalculator extends JFrame {
    private JTextField display;
    private StringBuilder input = new StringBuilder();

    public ColorfulCalculator() {
        setTitle("Colorful Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 4, 5, 5));
        String[] buttons = {
            "7", "8", "9", "/", 
            "4", "5", "6", "x", 
            "1", "2", "3", "+", 
            "C", "0", "=", "-", 
            "M+", "M-", "MR", "MC",
            "sqrt", "%", "+/-", "History"
        };

        Color[] colors = {
            new Color(255, 128, 128), new Color(255, 128, 128), new Color(144, 238, 144), new Color(144, 238, 144),
            new Color(255, 128, 128), new Color(255, 128, 128), new Color(144, 238, 144), new Color(144, 238, 144),
            new Color(255, 128, 128), new Color(255, 128, 128), new Color(144, 238, 144), new Color(255, 222, 89),
            new Color(255, 128, 128), new Color(255, 128, 128), new Color(255, 222, 89), new Color(255, 222, 89),
            new Color(173, 255, 255), new Color(173, 255, 255), new Color(173, 255, 255), new Color(255, 165, 0),
            new Color(173, 255, 255), new Color(173, 255, 255), new Color(255, 222, 89), new Color(216, 191, 216)
        };

        for (int i = 0; i < buttons.length; i++) {
            JButton btn = new JButton(buttons[i]);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setBackground(colors[i]);
            btn.addActionListener(e -> handleInput(e.getActionCommand()));
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
    }

    private void handleInput(String command) {
        switch (command) {
            case "C":
                input.setLength(0);
                break;
            case "=":
                try {
                    double result = evaluate(input.toString());
                    input = new StringBuilder(Double.toString(result));
                } catch (Exception e) {
                    input = new StringBuilder("Error");
                }
                break;
            case "sqrt":
                try {
                    double val = Double.parseDouble(input.toString());
                    input = new StringBuilder(Double.toString(Math.sqrt(val)));
                } catch (Exception e) {
                    input = new StringBuilder("Error");
                }
                break;
            case "+/-":
                if (input.length() > 0 && input.charAt(0) == '-') {
                    input.deleteCharAt(0);
                } else {
                    input.insert(0, '-');
                }
                break;
            default:
                input.append(command.equals("x") ? "*" : command);
                break;
        }
        display.setText(input.toString());
    }

    // Simple expression evaluator (supports +, -, *, / only)
    private double evaluate(String expr) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.'))
                    sb.append(expr.charAt(i++));
                numbers.push(Double.parseDouble(sb.toString()));
            } else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(ch))
                    numbers.push(apply(ops.pop(), numbers.pop(), numbers.pop()));
                ops.push(ch);
                i++;
            }
        }
        while (!ops.isEmpty())
            numbers.push(apply(ops.pop(), numbers.pop(), numbers.pop()));
        return numbers.pop();
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : 2;
    }

    private double apply(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return b == 0 ? 0 : a / b;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorfulCalculator().setVisible(true));
    }
}
