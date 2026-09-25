import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaPrincipal extends JFrame {

    private JTextField txtPlaca = null;
    private JTextField txtModelo = null;
    private JTextField txtFechaAfiliacion = null;
    private JTextField txtCedula = null;

    private JTextField txtNombre = null;
    private JTextField txtCelular = null;
    private JComboBox<String> comboTipoVehiculo = null;
    private JComboBox<String> comboAseguradora = null;
    private JComboBox<String> comboEstado = null;
    private JTextField txtDescripcion = null;
    private JTextField txtCosto = null;
    private JTextField txtBuscarPlaca = null;
    private final JTextArea areaResultados;
    private JButton btnRegistrar = null;
    private JButton btnReparar = null;
    private JButton btnListar = null;
    private JButton btnBuscar = null;

    public VentanaPrincipal() {
        setTitle("Gestión de Taller Mecánico");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(panelVehiculo());
        mainPanel.add(panelPropietario());
        mainPanel.add(panelReparacion());
        mainPanel.add(panelBusqueda());

        areaResultados = new JTextArea(8, 60);
        areaResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultados);
        add(scroll, BorderLayout.SOUTH);

        configurarEventos();
    }

    private JPanel panelVehiculo() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Vehículo"));

        comboTipoVehiculo = new JComboBox<>(new String[]{"Con Convenio", "Sin Convenio"});
        txtPlaca = new JTextField(10);
        txtModelo = new JTextField(6);
        txtFechaAfiliacion = new JTextField(10);
        comboAseguradora = new JComboBox<>(new String[]{"MAPFRE", "SURA", "ALLIANZ", "SOLIDARIA", "LIBERTY"});

        panel.add(new JLabel("Tipo de seguro:"));
        panel.add(comboTipoVehiculo);
        panel.add(new JLabel("Placa:"));
        panel.add(txtPlaca);
        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Fecha Afiliación (DD/MM/YYYY):"));
        panel.add(txtFechaAfiliacion);
        panel.add(new JLabel("Aseguradora:"));
        panel.add(comboAseguradora);

        return panel;
    }

    private JPanel panelPropietario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Propietario"));

        txtCedula = new JTextField(8);
        txtNombre = new JTextField(12);
        txtCelular = new JTextField(10);

        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Celular:"));
        panel.add(txtCelular);

        btnRegistrar = new JButton("Registrar Vehículo");
        panel.add(btnRegistrar);

        return panel;
    }

    private JPanel panelReparacion() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Reparación"));

        txtDescripcion = new JTextField(20);
        comboEstado = new JComboBox<>(new String[]{"DEFINITIVA", "TEMPORAL"});
        txtCosto = new JTextField(6);

        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Estado:"));
        panel.add(comboEstado);
        panel.add(new JLabel("Costo:"));
        panel.add(txtCosto);

        btnReparar = new JButton("Agregar Reparación");
        panel.add(btnReparar);

        return panel;
    }

    private JPanel panelBusqueda() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Consultar"));

        txtBuscarPlaca = new JTextField(10);
        btnBuscar = new JButton("Buscar Vehículo");
        btnListar = new JButton("Listar Todos");

        panel.add(new JLabel("Buscar por placa:"));
        panel.add(txtBuscarPlaca);
        panel.add(btnBuscar);
        panel.add(btnListar);

        return panel;
    }

    private void configurarEventos() {
        comboTipoVehiculo.addActionListener(e -> {
            boolean esConvenio = comboTipoVehiculo.getSelectedItem().equals("Con Convenio");
            txtFechaAfiliacion.setEnabled(esConvenio);
            comboAseguradora.setEnabled(!esConvenio);
        });
        comboTipoVehiculo.setSelectedIndex(0);

        btnRegistrar.addActionListener(e -> {
            try {
                String placa = txtPlaca.getText().trim();
                if (ReporteDAO.existePlacaEnBD(placa)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un vehículo registrado con esa placa.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String modeloStr = txtModelo.getText().trim();
                String fechaStr = txtFechaAfiliacion.getText().trim();
                String aseguradora = comboAseguradora.getSelectedItem().toString();
                String cedulaStr = txtCedula.getText().trim();
                String nombre = txtNombre.getText().trim();
                String celular = txtCelular.getText().trim();

                if (placa.isEmpty() || modeloStr.isEmpty() || cedulaStr.isEmpty() || nombre.isEmpty() || celular.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben estar llenos.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!modeloStr.matches("\\d+") || !cedulaStr.matches("\\d+") || !celular.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Modelo, cédula y celular deben contener solo números.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int modelo = Integer.parseInt(modeloStr);
                int cedula = Integer.parseInt(cedulaStr);
                Propietario p = new Propietario(cedula, nombre, celular);

                Vehiculo v;
                if (comboTipoVehiculo.getSelectedItem().equals("Con Convenio")) {
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                    v = new VehiculoConConvenio(fecha, placa, modelo, p);
                } else {
                    v = new VehiculoSinConvenio(aseguradora, placa, modelo, p);
                }

                UsaVehiculo.insertarVehiculoActionPerformance(v);
                JOptionPane.showMessageDialog(this, "Vehículo registrado exitosamente.");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Fecha inválida. Usa el formato DD/MM/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReparar.addActionListener((ActionEvent e) -> {
            try {
                String placa = txtPlaca.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String estado = comboEstado.getSelectedItem().toString();
                String costoStr = txtCosto.getText().trim();
                if (placa.isEmpty() || descripcion.isEmpty() || costoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Todos los campos de reparación deben estar llenos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!costoStr.matches("\\d+(\\.\\d{1,2})?")) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Costo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double costo = Double.parseDouble(costoStr);
                Vehiculo v = UsaVehiculo.obtenerVehiculoPorPlaca(placa);
                if (v != null) {
                    Reparacion rOriginal = new Reparacion(descripcion, estado, costo);
                    double valorCobrado = v.adicionarReparacion(rOriginal);

                    Reparacion reparacionConDescuento = new Reparacion(descripcion, estado, valorCobrado);

                    ReparacionDAO.insertarReparacionDetallada(v, reparacionConDescuento);

                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                        "Reparación registrada correctamente.\nValor a pagar: $" + valorCobrado,
                        "Confirmación",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                        "No se encontró el vehículo para registrar la reparación.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscar.addActionListener(e -> {
            String placa = txtBuscarPlaca.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una placa para buscar.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            areaResultados.setText(ReporteDAO.buscarVehiculoPorPlaca(placa));
        });

        btnListar.addActionListener(e -> {
            areaResultados.setText(ReporteDAO.listarReparacionesDesdeBD());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
