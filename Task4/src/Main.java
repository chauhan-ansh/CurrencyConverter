import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    private final JTextField amountTextField;
    private final JTextField resultTextField;
    private final JComboBox<String> fromComboBox;
    private final JComboBox<String> toComboBox;

    private final double[][] exchangeRates = {
            {1.0, 0.014, 0.011, 0.012, 1.38, 0.16, 0.000023},
            {71.57, 1.0, 0.86, 1.27, 113.06, 6.38, 0.000023}
    };

    public Main(){
        setTitle("Currency Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel resultLabel = new JLabel("Result:");

        amountTextField = new JTextField();
        resultTextField = new JTextField();
        resultTextField.setEditable(false);

        fromComboBox = new JComboBox<>(getCurrencies());
        toComboBox = new JComboBox<>(getCurrencies());

        JButton convertButton = new JButton("Convert");

        fromLabel.setBounds(20, 20, 60, 20);
        toLabel.setBounds(20, 50, 60, 20);
        amountLabel.setBounds(20, 80, 60, 20);
        resultLabel.setBounds(20, 110, 60, 20);

        fromComboBox.setBounds(80, 20, 100, 20);
        toComboBox.setBounds(80, 50, 100, 20);
        amountTextField.setBounds(80, 80, 100, 20);
        resultTextField.setBounds(80, 110, 100, 20);

        convertButton.setBounds(200, 80, 100, 50);

        add(fromLabel);
        add(toLabel);
        add(amountLabel);
        add(resultLabel);
        add(fromComboBox);
        add(toComboBox);
        add(amountTextField);
        add(resultTextField);
        add(convertButton);

        convertButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        convertCurrency();
                        showThankYouMessage();
                    }
                });
            }
        });
    }
    private String[] getCurrencies(){
        return new String[]{"INR", "USD", "Euro", "CAD", "JPY", "CNY", "Bitcoin"};
    }
    private double getExchangeRate(String fromCurrency, String toCurrency){
        int fromIndex = getIndex(fromCurrency);
        int toIndex = getIndex(toCurrency);
        return exchangeRates[fromIndex][toIndex];
    }
    private int getIndex(String currency){
        return switch (currency) {
            case "INR" -> 0;
            case "USD" -> 1;
            case "Euro" -> 2;
            case "CAD" -> 3;
            case "JPY" -> 4;
            case "CNY" -> 5;
            case "Bitcoin" -> 6;
            default -> -1;
        };
    }
    private void convertCurrency(){
        String fromCurrency = (String) fromComboBox.getSelectedItem();
        String toCurrency = (String) toComboBox.getSelectedItem();
        double amount = Double.parseDouble(amountTextField.getText());
        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        double result = amount * exchangeRate;
        resultTextField.setText(String.format("%.2f %s", result, toCurrency));
    }
    private void showThankYouMessage(){
        int option = JOptionPane.showOptionDialog(this,
                "Thank you for using the Currency Converter!\nDo you want to try again?",
                "Currency Converter",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Yes", "No"},
                "Yes");
        if(option == JOptionPane.YES_OPTION){
            amountTextField.setText("");
            resultTextField.setText("");
        }
        else{
            System.exit(0);
        }
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                JOptionPane.showMessageDialog(null, "Welcome to the Currency Converter!");
                Main converter = new Main();
                converter.setVisible(true);
            }
        });
    }
}
