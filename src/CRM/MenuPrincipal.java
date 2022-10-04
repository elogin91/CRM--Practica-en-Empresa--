package CRM;

/**
 * @author Ainhoa Lopez Bleda
 */

import CRM.calendario.Calendario;
import CRM.clientes.ClientesPotenciales;
import CRM.clientes.listadoClientes;
import CRM.empresa.Empresa;
import CRM.empresa.listadoEmpresas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MenuPrincipal extends JFrame {
	static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton Potenciales;
	private JButton Empresa;
	private JButton Calendario;
	private JButton BuscarC;
	private JButton BuscarE;

	private listadoClientes lista = null;

	public MenuPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/Imagenes/logo.png")));
		setTitle(" C R M - F I N S E - ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 475);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// BOTON CONTACTO CON CLIENTES
		JLabel lblclientes = new JLabel("CLIENTES");
		lblclientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblclientes.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblclientes.setBounds(50, 151, 90, 20);
		contentPane.add(lblclientes);

		JLabel lblpotenci = new JLabel("POTENCIALES");
		lblpotenci.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblpotenci.setBounds(50, 175, 85, 20);
		contentPane.add(lblpotenci);
		
		Potenciales = new JButton("");
		Potenciales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientesPotenciales clientes = new ClientesPotenciales();
				clientes.setVisible(true);
			}
		});
		Potenciales.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		Potenciales.setBounds(35, 40, 110, 100);
		ImageIcon fotocliente = new ImageIcon("Imagenes/c-potenciales.png");
		Potenciales.setIcon(new ImageIcon(fotocliente.getImage().getScaledInstance(Potenciales.getWidth(),
				Potenciales.getHeight(), Image.SCALE_SMOOTH)));
		Potenciales.setBorder(null);
		Potenciales.setBackground(null);
		contentPane.add(Potenciales);


		// BOTON EMPRESA
		JLabel lblEmpresa = new JLabel("EMPRESA");
		lblEmpresa.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpresa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblEmpresa.setBounds(230, 158, 84, 20);
		contentPane.add(lblEmpresa);
		
		Empresa = new JButton("");
		Empresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CRM.empresa.Empresa empresa = new Empresa();
				empresa.setVisible(true);
			}
		});
		Empresa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		Empresa.setBounds(220, 40, 110, 100);
		ImageIcon fotoempresa = new ImageIcon("Imagenes/empresa.png");
		Empresa.setIcon(new ImageIcon(
				fotoempresa.getImage().getScaledInstance(Empresa.getWidth(), Empresa.getHeight(), Image.SCALE_SMOOTH)));
		Empresa.setBorder(null);
		Empresa.setBackground(null);
		contentPane.add(Empresa);
		
		// BOTON AGENDA
		
		JLabel lblcalendario = new JLabel("CALENDARIO");
		lblcalendario.setHorizontalAlignment(SwingConstants.CENTER);
		lblcalendario.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblcalendario.setBounds(430, 158, 84, 20);
		contentPane.add(lblcalendario);
		
		Calendario = new JButton("");
		Calendario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CRM.calendario.Calendario calendario = new Calendario();
				calendario.setVisible(true);
			}
		});
		Calendario.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		Calendario.setBounds(420, 40, 110, 100);
		ImageIcon fotocalendario = new ImageIcon("Imagenes/calendario.png");
		Calendario.setIcon(new ImageIcon(
				fotocalendario.getImage().getScaledInstance(Calendario.getWidth(), Calendario.getHeight(), Image.SCALE_SMOOTH)));
		Calendario.setBorder(null);
		Calendario.setBackground(null);
		contentPane.add(Calendario);
		
		// BOTON BUSCAR CLIENTES
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblBuscar.setBounds(50, 369, 68, 20);
		contentPane.add(lblBuscar);
		
		JLabel lblclientes_1 = new JLabel("CLIENTES");
		lblclientes_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblclientes_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblclientes_1.setBounds(50, 388, 68, 20);
		contentPane.add(lblclientes_1);
		
		BuscarC = new JButton("");
		BuscarC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (lista==null)
				{
				lista = new listadoClientes();
				lista.setVisible(true);
				}
				else
				{
					lista.setVisible(true);
				}
			}			
		});
		BuscarC.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		BuscarC.setBounds(35, 250, 110, 100);
		ImageIcon fotobuscar = new ImageIcon("Imagenes/buscar_cliente.png");
		BuscarC.setIcon(new ImageIcon(
				fotobuscar.getImage().getScaledInstance(BuscarC.getWidth(), BuscarC.getHeight(), Image.SCALE_SMOOTH)));
		BuscarC.setBorder(null);
		BuscarC.setBackground(null);
		BuscarC.setContentAreaFilled(false);
		contentPane.add(BuscarC);
		
		
		// BOTON BUSCAR EMPRESAS
		
		JLabel lblBuscar_1 = new JLabel("BUSCAR");
		lblBuscar_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblBuscar_1.setBounds(234, 369, 68, 20);
		contentPane.add(lblBuscar_1);
		
		JLabel lblempresas = new JLabel("EMPRESAS");
		lblempresas.setHorizontalAlignment(SwingConstants.CENTER);
		lblempresas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblempresas.setBounds(240, 388, 68, 20);
		contentPane.add(lblempresas);
		
		BuscarE = new JButton("");
		BuscarE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listadoEmpresas lista = new listadoEmpresas();
				lista.setVisible(true);
			}
		});
		BuscarE.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		BuscarE.setBounds(220, 250, 110, 100);
		ImageIcon fotobuscarE = new ImageIcon("Imagenes/buscar_empresa.png");
		BuscarE.setIcon(new ImageIcon(
				fotobuscarE.getImage().getScaledInstance(BuscarE.getWidth(), BuscarE.getHeight(), Image.SCALE_SMOOTH)));
		BuscarE.setBorder(null);
		BuscarE.setBackground((Color) null);
		contentPane.add(BuscarE);
		
		

	}

	// imagen de fondo
	public void paint(Graphics g) {
		super.paint(g);
		Toolkit t = Toolkit.getDefaultToolkit();
		Image imagen = t.getImage("Imagenes/fondo_pantalla.png");
		g.drawImage(imagen, 0, 0, this);
	}
}