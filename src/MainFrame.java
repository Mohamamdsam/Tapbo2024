import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JTextField nameField;
    private JTextField budgetField;
    private JTextArea resultArea;
    private JRadioButton luxuryHouseRadioButton ;
    private JRadioButton simpleHouseRadioButton;
    private JComboBox<String> locationComboBox;
    private JRadioButton cashRadioButton;
    private JRadioButton creditRadioButton;
    private JComboBox<String> yearsComboBox; // ComboBox untuk durasi kredit
    private JTextField interestRateField;

    public MainFrame() {
        setTitle("Sistem Pembelian Rumah");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label dan Field untuk Nama Pembeli
        JLabel nameLabel = new JLabel("Nama Pembeli:");
        nameLabel.setBounds(10, 10, 150, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 10, 200, 25);
        add(nameField);

        // Label dan Field untuk Anggaran
        JLabel budgetLabel = new JLabel("Anggaran:");
        budgetLabel.setBounds(10, 40, 150, 25);
        add(budgetLabel);

        budgetField = new JTextField();
        budgetField.setBounds(150, 40, 200, 25);
        add(budgetField);

        // Label dan Radio Button untuk Jenis Rumah
        JLabel houseTypeLabel = new JLabel("Jenis Rumah:");
        houseTypeLabel.setBounds(10, 70, 150, 25);
        add(houseTypeLabel);

        luxuryHouseRadioButton = new JRadioButton("Rumah Mewah");
        luxuryHouseRadioButton.setBounds(150, 70, 150, 25);
        add(luxuryHouseRadioButton);

        simpleHouseRadioButton = new JRadioButton("Rumah Sederhana");
        simpleHouseRadioButton.setBounds(150, 100, 150, 25);
        add(simpleHouseRadioButton);

        ButtonGroup houseTypeGroup = new ButtonGroup();
        houseTypeGroup.add(luxuryHouseRadioButton);
        houseTypeGroup.add(simpleHouseRadioButton);

        // Label dan Combo Box untuk Lokasi Rumah
        JLabel locationLabel = new JLabel("Lokasi Rumah:");
        locationLabel.setBounds(10, 130, 150, 25);
        add(locationLabel);

        locationComboBox = new JComboBox<>(new String[]{"Jakarta", "Bandung", "Surabaya","Yogyakarta"});
        locationComboBox.setBounds(150, 130, 200, 25);
        add(locationComboBox);

        // Label untuk Harga Rumah
        JLabel priceLabel = new JLabel("Harga Rumah:");
        priceLabel.setBounds(10, 160, 150, 25);
        add(priceLabel);

        JLabel priceValueLabel = new JLabel("Harga: ");
        priceValueLabel.setBounds(150, 160, 200, 25);
        add(priceValueLabel);

        // Listener untuk mengupdate harga berdasarkan jenis rumah
        ActionListener houseTypeListener = e -> {
            switch (getSelectedHouseType()) {
                case "Mewah":
                    priceValueLabel.setText("Harga: 150.000.000");
                    break;
                case "Sederhana":
                    priceValueLabel.setText("Harga: 100.000.000");
                    break;
            }
        };

        luxuryHouseRadioButton.addActionListener(houseTypeListener);
        simpleHouseRadioButton.addActionListener(houseTypeListener);

        // Label dan Radio Button untuk Metode Pembayaran
        JLabel paymentLabel = new JLabel("Metode Pembayaran:");
        paymentLabel.setBounds(10, 190, 150, 25);
        add(paymentLabel);

        cashRadioButton = new JRadioButton("Cash");
        cashRadioButton.setBounds(150, 190, 100, 25);
        add(cashRadioButton);

        creditRadioButton = new JRadioButton("Kredit");
        creditRadioButton.setBounds(250, 190, 100, 25);
        add(creditRadioButton);

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashRadioButton);
        paymentGroup.add(creditRadioButton);

        // Label dan Combo Box untuk Durasi Kredit
        JLabel yearsLabel = new JLabel("Durasi (tahun):");
        yearsLabel.setBounds(10, 220, 150, 25);
        add(yearsLabel);

        yearsComboBox = new JComboBox<>(new String[]{"1", "2", "3", "5", "10"});
        yearsComboBox.setBounds(150, 220, 200, 25);
        add(yearsComboBox);

        // Label dan Field untuk Suku Bunga
        JLabel interestRateLabel = new JLabel("Suku Bunga (%):");
        interestRateLabel.setBounds(10, 250, 150, 25);
        add(interestRateLabel);

        interestRateField = new JTextField();
        interestRateField.setBounds(150, 250, 200, 25);
        add(interestRateField);

        // Tombol untuk Membeli Rumah
        JButton buyButton = new JButton("Beli Rumah");
        buyButton.setBounds(10, 280, 150, 25);
        add(buyButton);

        // Area untuk Menampilkan Hasil
        resultArea = new JTextArea();
        resultArea.setBounds(10, 320, 360, 200);
        resultArea.setEditable(false);
        add(resultArea);

        // Action Listener untuk Tombol Beli Rumah
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double budget = Double.parseDouble(budgetField.getText());
                String location = (String) locationComboBox.getSelectedItem();
                double price = 0;

                // Menggunakan switch-case untuk menentukan harga rumah
                switch (getSelectedHouseType()) {
                    case "Mewah":
                        price = 1500000; // Harga rumah mewah
                        break;
                    case "Sederhana":
                        price = 500000; // Harga rumah sederhana
                        break;
                }

                Buyer buyer = new Buyer(name, budget);
                House house;

                if (luxuryHouseRadioButton.isSelected()) {
                    house = new LuxuryHouse(location, price, "Fitur Mewah");
                } else {
                    house = new House(location, price);
                }

                if (buyer.canAfford(house)) {
                    String paymentMethod = cashRadioButton.isSelected() ? "Cash" : "Kredit";
                    resultArea.setText("Pembelian berhasil!\n" + house.getDetails() + "\nMetode Pembayaran: " + paymentMethod);

                    if (creditRadioButton.isSelected()) {
                        int years = Integer.parseInt((String) yearsComboBox.getSelectedItem());
                        double interestRate = Double.parseDouble(interestRateField.getText()) / 100;
                        double monthlyPayment = calculateMonthlyPayment(price, years, interestRate);
                        resultArea.append("\nAngsuran Bulanan: " + monthlyPayment);
                    }
                } else {
                    resultArea.setText("Pembelian gagal! Anggaran tidak cukup.");
                }
            }
        });
    }

    private String getSelectedHouseType() {
        if (luxuryHouseRadioButton.isSelected()) {
            return "Mewah";
        } else if (simpleHouseRadioButton.isSelected()) {
            return "Sederhana";
        }
        return "";
    }

    private double calculateMonthlyPayment(double principal, int years, double annualInterestRate) {
        double monthlyInterestRate = annualInterestRate / 12; // Suku bunga bulanan
        int numberOfPayments = years * 12; // Total pembayaran dalam bulan

        // Jika bunga 0%, formula khusus (pembagian jumlah pinjaman dengan jumlah bulan)
        if (monthlyInterestRate == 0) {
            return principal / numberOfPayments;
        }

        // Perhitungan angsuran bulanan
        return (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
