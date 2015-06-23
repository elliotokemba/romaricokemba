package sn.ucad.esp.master;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.curiosity.kuiper.ihm.Central;



public class FrmChat extends JFrame {
	private static Thread clientTheard;
	private static JLabel lblDestinataire=new JLabel();
	private String lblSelect;
	private static String compte;
	
	private static Socket socket;
	private static FrmChat f;
	private static  FrmConnexionServeur frmConnexionServeur;
	private TextField txtMsg=new TextField(50);
	
	private Central central;
	
	

	/**
	 * Barre d'outils de Gauche Permettant d'appeler et de faire des appels
	 * vidéos
	 */

	private JToolBar barreOutilsGauche = new JToolBar();

	//private JToolBar panelUsr = new JToolBar();

	/** Boutton qui permet d'appeler un ami par IP */
	private static JButton btnTels = new JButton(new ImageIcon("images/phone.png"));

	/** Boutton qui permet de configurer le serveur un ami par IP */
	private JButton btnPam = new JButton(new ImageIcon("images/param.png"));
	private static JButton btnEmoticon= new JButton(new ImageIcon("images/smiley.jpg"));

	/** Boutton qui permet de configurer le serveur un ami par IP */
	private JButton btnChat = new JButton(new ImageIcon("images/chat.png"));
	/* Boutton historique */
	private JButton btnHistorique = new JButton(new ImageIcon("images/historique.png"));

	private JButton btnLoggin = new JButton(new ImageIcon("images/utilisateur.png"));
	private static JButton btnFile = new JButton(new ImageIcon("images/addfile.png"));
	private JPanel panelPrincipal = new JPanel();

	/** Liste des Utilisateurs de l'application */
	private JTable tableUsr;


	/**
	 * Contenu des messages qui s'affichent dans JtextPane
	 */
	private StyleContext sc = new StyleContext();

	private final DefaultStyledDocument doc = new DefaultStyledDocument(sc);

	/** Champ d'affichage des conversations entre les utilisateurs*/
	private JTextPane panelChat = new JTextPane(doc);
	
	public FrmChat(String comptes,Socket socket,Central central) {
		
		this.compte=comptes;
		this.setTitle("Kuiper- "+this.compte);
		
		this.socket=socket;
	
//		try {
//			// Look and feel de l'application
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//			
//		} catch (Exception e) {
//		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnEmoticon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    afficheEmoticon();
				
			}

			
		});
		
		btnTels.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Enveloppe enveloppe=new Enveloppe();
				enveloppe.setInstruction("TEL");
				Message message=new Message();
			
				enveloppe.setMessage(message);
				Utilisateur emetteur=new Utilisateur();
				Utilisateur destinataire=new Utilisateur();
				emetteur.setNom(compte);
				destinataire.setNom(lblDestinataire.getText());
				
				enveloppe.setEmetteur(emetteur);
				enveloppe.setDestinataire(destinataire);
				
				
				try {
					envoyerMsg(enveloppe);
				} catch (IOException e1) {
					
				}
				
			}
		});
		
		/* Taille du formulaire */
		this.setSize(900, 560);
		// On centre le formulaire
		this.setLocationRelativeTo(null);
	    this.panelChat.setEditable(false);

		this.barreOutilsGauche.setBackground(Color.BLACK);
		// On fixe l'rientation de la barre d'outils verticalement
		//this.panelUsr.setOrientation(JToolBar.VERTICAL);
		
		
		this.barreOutilsGauche.setOrientation(JToolBar.VERTICAL);
		// On ajoute le bouton du telephone
		this.barreOutilsGauche.add(btnTels);
		
		this.barreOutilsGauche.add(btnChat);
		
//		btnHistorique.setToolTipText("Historique des Appels");
//		
//		this.barreOutilsGauche.add(btnHistorique);
//		
//		this.barreOutilsGauche.add(btnLoggin);
//		
//		this.barreOutilsGauche.add(btnPam);
//		
//		btnPam.setToolTipText("Configurer votre Compte");
		
		panelPrincipal.setBackground(Color.WHITE);
		
		/* On ajoute la Barre d'outils de Gauche */
		this.getContentPane().add(this.barreOutilsGauche, BorderLayout.WEST);
        //On Ajoute le Panel central
		

		panelPrincipal.setLayout(new BorderLayout());


		

	
		
		
//		panelUsr.setBackground(Color.WHITE);
//		this.panelUsr.add(new TextField("jkwxxxxxxx"));
//		this.panelUsr.add(tableUsr);
//		
		
		this.setResizable(false);
	//	JScrollPane scrollPane=new JScrollPane(tableUsr);
		//scrollPane.setSize(100, 200);
		tableUsr=new JTable();
	

		panelPrincipal.add(tableUsr, BorderLayout.WEST);
		JPanel panelSearch=new JPanel();
		panelSearch.setLayout(new BorderLayout());
		
		JPanel panelCntre=new JPanel();
		panelCntre.setBackground(Color.WHITE);
		panelCntre.setLayout(new BorderLayout());
		JPanel panelBas=new JPanel();
		panelBas.setLayout(new BorderLayout());
		panelBas.setBackground(Color.WHITE);
		
		JPanel panelActions=new JPanel();
		panelActions.setLayout(new GridLayout(1, 2));
		panelActions.add(btnEmoticon);
		panelActions.add(btnFile);
		panelBas.add(panelActions,new BorderLayout().WEST);
		panelBas.add(txtMsg,new BorderLayout().CENTER);
		JPanel panelHaut=new JPanel();
		
//		JLabel labelImage=new JLabel( new ImageIcon("images/utilisateur.png"));
//		panelHaut.add(labelImage);
		panelHaut.add( lblDestinataire);
	
		panelCntre.add( panelHaut, new BorderLayout().NORTH);
		panelCntre.add( panelBas, new BorderLayout().SOUTH);
		panelCntre.add( new JScrollPane(panelChat), new BorderLayout().CENTER);
		panelPrincipal.add(panelCntre, BorderLayout.CENTER);
	
		
		this.getContentPane().add(this.panelPrincipal, BorderLayout.CENTER);
		
		this.txtMsg.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==10){
					try {
						

						Style style = getDoc().addStyle("StyleName", null);
						
						
						StyleConstants.setForeground(style, Color.BLACK);
						
						SimpleAttributeSet center = new SimpleAttributeSet();
						
						StyleConstants.setAlignment(center,	StyleConstants.ALIGN_LEFT);
						
						getDoc().setParagraphAttributes(getDoc().getLength(),getDoc().getLength(), center, false);

						DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
						try {
							
							getDoc().insertString(getDoc().getLength(),"Moi :  " + txtMsg.getText() + "\n", style);
							
							getDoc().insertString(getDoc().getLength(),"Envoyé le: " + dateFormat.format(new Date()),
									style);
							getDoc().insertString(getDoc().getLength(), "\n", style);
							
							getDoc().insertString(getDoc().getLength(), "\n", style);
							
							
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
						try {
							
							getDoc().insertString(getDoc().getLength(), "\n", null);
							
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
						Enveloppe enveloppe=new Enveloppe();
						enveloppe.setInstruction("MSG");
						Message message=new Message();
						message.setMessage(txtMsg.getText());
						enveloppe.setMessage(message);
						Utilisateur emetteur=new Utilisateur();
						Utilisateur destinataire=new Utilisateur();
						emetteur.setNom(compte);
						destinataire.setNom(lblDestinataire.getText());
						
						enveloppe.setEmetteur(emetteur);
						enveloppe.setDestinataire(destinataire);
						
						envoyerMsg(enveloppe);
						txtMsg.setText("");
						
						java.net.URL imageURL = JButton.class.getResource("/beep.wav");
	//
						Sound player = new Sound(imageURL.getFile());

						InputStream stream = new ByteArrayInputStream(player.getSamples());

						player.play(stream);
						
					} catch (Exception e1) {
						e1.getMessage();
					}
					
				}else if (e.getKeyCode() != 8) {
					
					Enveloppe enveloppe=new Enveloppe();
					enveloppe.setInstruction("ECRIT");
					Message message=new Message();
					message.setMessage(txtMsg.getText());
					enveloppe.setMessage(message);
					Utilisateur emetteur=new Utilisateur();
					Utilisateur destinataire=new Utilisateur();
					emetteur.setNom(compte);
					destinataire.setNom(lblDestinataire.getText());
					
					enveloppe.setEmetteur(emetteur);
					enveloppe.setDestinataire(destinataire);
					
					try {
						envoyerMsg(enveloppe);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}

			
		});;
		
		
		
		this.btnLoggin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			

				 frmConnexionServeur=new FrmConnexionServeur();
				frmConnexionServeur.setVisible(true);
			}
		});
		
		
		 btnFile.addActionListener(new ActionListener(
				 ) {
			 
			@Override
			public void actionPerformed(ActionEvent e) {
				//permet de choisir un fichier a envoyer
				FileDialog dialogue=selectionner();
				dialogue.setFile("fichier1");
				dialogue.show();
				
				//EnvFile em =new EnvFile();
				File fl=new File(dialogue.getDirectory()+dialogue.getFile());
				try
				{
				InputStream fis=new FileInputStream(fl);
				byte cont[]=new byte[(int)fl.length()];
				fis.read(cont);

             	Enveloppe enveloppe=new Enveloppe();
			    enveloppe.setInstruction("FILES");
			    enveloppe.setbFile(cont);
			    enveloppe.setTailleFichier((int)fl.length());
			    
			    Message message=new Message();
			    message.setMessage(dialogue.getFile());
			    Utilisateur emetteur=new Utilisateur();
				Utilisateur destinataire=new Utilisateur();
				emetteur.setNom(compte);
				destinataire.setNom(lblDestinataire.getText());
				
				enveloppe.setEmetteur(emetteur);
				enveloppe.setDestinataire(destinataire);
			    enveloppe.setMessage(message);

				
				
				
//				 JFileChooser dialogue = new JFileChooser();
//	             
//		            // affichage
//		            dialogue.showOpenDialog(null);
//		           try {
//		        	   
//		        	   FileInputStream fileInputStream=null;
//		        	   
//		               File file = new File(dialogue.getSelectedFile().getAbsolutePath());
//		        
//		               byte[] bFile = new byte[(int) file.length()];
//		               
//		             	Enveloppe enveloppe=new Enveloppe();
//					    enveloppe.setInstruction("FILES");
//					    enveloppe.setbFile(bFile);
//					
//					Utilisateur emetteur=new Utilisateur();
//					Utilisateur destinataire=new Utilisateur();
//					emetteur.setNom(compte);
//					destinataire.setNom(lblDestinataire.getText());
//					
//					enveloppe.setEmetteur(emetteur);
//					enveloppe.setDestinataire(destinataire);
//					
					envoyerMsg(enveloppe);
		               

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
	}
	
//	public void saveFile(byte cont[],String nomFile,int tf)
//	{
//
//	FileDialog fd=new FileDialog(ci,"Telecharger un Fichier",java.awt.FileDialog.SAVE);
//	fd.setFile(nomFile);
//	fd.show();
//
//	try {
//	OutputStream fos=new FileOutputStream(fd.getDirectory()+fd.getFile());
//	fos.write(cont);
//	fos.close();
//	}
//	catch (Exception ex)
//	{
//	System.out.println("erreur "+ex);
//	}
//	}




//	public static void main(String[] args) {
//		 f=new FrmChat();
//	
//		
//
//		f.addWindowListener(new WindowListener() {
//			
//			@Override
//			public void windowOpened(WindowEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void windowIconified(WindowEvent e) {
//				
//				
//			}
//			
//			@Override
//			public void windowDeiconified(WindowEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void windowClosing(WindowEvent e) {
//				
//				
//			}
//			
//			@Override
//			public void windowClosed(WindowEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void windowActivated(WindowEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		f.setVisible(true);
//		
//	
//		try {
//			 socket=new Socket("127.0.0.1", 86);
//			  clientTheard= new Thread(new ReceiverClient(f, socket));
//			 clientTheard.start();
//			 btnFile.addActionListener(new ActionListener(
//					 ) {
//				 
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					 JFileChooser dialogue = new JFileChooser();
//		             
//			            // affichage
//			            dialogue.showOpenDialog(null);
//			           try {
//			        	   Enveloppe env=new Enveloppe();
//			        	   env.setInstruction("FILE");
//			        	   env.setExt("jpg");
//			        	   Utilisateur utilisateur=new Utilisateur();
//			        	   utilisateur.setNom(compte);
//			        	   Utilisateur desUtilisateur=new Utilisateur();
//			        	   desUtilisateur.setNom(lblDestinataire.getText());
//			        	   env.setDestinataire(desUtilisateur);
//			        	   env.setEmetteur(utilisateur);
//			        	   Message message=new Message();
//			        	   message.setMessage(dialogue.getSelectedFile().getName());
//			        	   env.setMessage(message);
//			        	   envoyerMsg(env);
//						sendFile(dialogue.getSelectedFile().getAbsolutePath(), "");
////						clientTheard.destroy();
////						socket.close();
////						socket=new Socket("127.0.0.1", 86);
////						 clientTheard= new Thread(new ReceiverClient(f, socket));
////						 clientTheard.start();
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
//				}
//			});
//			 
//			 btnEmoticon.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//					  FrmEmoticon frmEmoticon=new FrmEmoticon(f, socket);
//					  frmEmoticon.setVisible(true);
//						
//					}
//				});
//
//				btnTels.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						
//						Enveloppe enveloppe=new Enveloppe();
//						enveloppe.setInstruction("TEL");
//						Message message=new Message();
//					
//						enveloppe.setMessage(message);
//						Utilisateur emetteur=new Utilisateur();
//						Utilisateur destinataire=new Utilisateur();
//						emetteur.setNom(compte);
//						destinataire.setNom(lblDestinataire.getText());
//						
//						enveloppe.setEmetteur(emetteur);
//						enveloppe.setDestinataire(destinataire);
//						
//						
//						try {
//							envoyerMsg(enveloppe);
//						} catch (IOException e1) {
//							
//						}
//					}
//				});
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
	
	public  static class IconCellRenderer extends DefaultTableCellRenderer {
		 
		private final static ImageIcon OUI_IMAGE = new ImageIcon("roy.png");
//		private final static ImageIcon NON_IMAGE = new ImageIcon("no-icon.png");
 
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
 
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
			JLabel label = (JLabel)component;
			label.setIcon(OUI_IMAGE);
			
//			if ( "OUI".equals(value) ) {
//				label.setIcon(OUI_IMAGE);
//			}
//			else if ( "NON".equals(value) ) {
////				label.setIcon(NON_IMAGE);
//			}
//			else {
//				label.setIcon(null);
//			}
 
			return component;
		}
 
 
	}
	public static  void envoyerMsg(Enveloppe enveloppe) throws IOException {
		ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
	
		os.writeObject(enveloppe);
	
		
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		FrmChat.socket = socket;
	}

	public static FrmChat getF() {
		return f;
	}

	public static void setF(FrmChat f) {
		FrmChat.f = f;
	}

	public TextField getTxtMsg() {
		return txtMsg;
	}

	public void setTxtMsg(TextField txtMsg) {
		this.txtMsg = txtMsg;
	}

	public JToolBar getBarreOutilsGauche() {
		return barreOutilsGauche;
	}

	public void setBarreOutilsGauche(JToolBar barreOutilsGauche) {
		this.barreOutilsGauche = barreOutilsGauche;
	}

	public JButton getBtnTels() {
		return btnTels;
	}

	public void setBtnTels(JButton btnTels) {
		this.btnTels = btnTels;
	}

	public JButton getBtnPam() {
		return btnPam;
	}

	public void setBtnPam(JButton btnPam) {
		this.btnPam = btnPam;
	}

	public JButton getBtnEmoticon() {
		return btnEmoticon;
	}

	public void setBtnEmoticon(JButton btnEmoticon) {
		this.btnEmoticon = btnEmoticon;
	}

	public JButton getBtnChat() {
		return btnChat;
	}

	public void setBtnChat(JButton btnChat) {
		this.btnChat = btnChat;
	}

	public JButton getBtnHistorique() {
		return btnHistorique;
	}

	public void setBtnHistorique(JButton btnHistorique) {
		this.btnHistorique = btnHistorique;
	}

	public JButton getBtnLoggin() {
		return btnLoggin;
	}
	
	public FileDialog selectionner(){
		return new FileDialog(this,"Telecharger un Fichier",java.awt.FileDialog.LOAD);
	}

	public void setBtnLoggin(JButton btnLoggin) {
		this.btnLoggin = btnLoggin;
	}

	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}

	public void setPanelPrincipal(JPanel panelPrincipal) {
		this.panelPrincipal = panelPrincipal;
	}

	public JTable getTableUsr() {
		return tableUsr;
	}

	public void setTableUsr(JTable tableUsr) {
		this.tableUsr = tableUsr;
	}

	public StyleContext getSc() {
		return sc;
	}

	public void setSc(StyleContext sc) {
		this.sc = sc;
	}

	public JTextPane getPanelChat() {
		return panelChat;
	}

	public void setPanelChat(JTextPane panelChat) {
		this.panelChat = panelChat;
	}

	public DefaultStyledDocument getDoc() {
		return doc;
	}
	public static FrmConnexionServeur getFrmConnexionServeur() {
		return frmConnexionServeur;
	}

	public static void setFrmConnexionServeur(
			FrmConnexionServeur frmConnexionServeur) {
		FrmChat.frmConnexionServeur = frmConnexionServeur;
	}


	/**
	 * 
	 * @author <a maito="ngromaricokemba@gmail.com"> Romaric Okemba . Architecte Logiciel</a>
	 *  Formulaire de Configuration des Paramètres du Serveur 
	 */
	
    class FrmConnexionServeur extends JDialog {
    	
    	/* Champ de Saisi de l'adress IP du Serveur*/
    	private JTextField fieldCompte=new JTextField(15);
    	
    	/* Champ de Saisi du Mot de Passe*/
    	private JPasswordField fieldPwd =new JPasswordField(10);
    	
    	/*Chemin du Fichier de Configuration des paramètres de Serveur*/
    	private JTextField fieldPathFileCong=new JTextField(15);
    	

    	/* Panel ou Seront placés Les zones de Saisie du Formulaire*/
    	private JPanel panelChampsSaisie=new JPanel();
    	
    	/* Boutton de Selection de Chemin de Configuration*/
    	
    	private JButton btnCreateAccount=new JButton("Nouveau ?");
    	
    	/** Button de validation de l'opération de configuration du Serveur*/
    	
    	private JButton btnValider=new JButton("Valider");
    	
    	/** Panel de validation des  Bouttons*/
    	private JPanel panelButtons=new JPanel();

    	
        public FrmConnexionServeur(){
        	
     
        	
        	
        	
        	/* Le Chemin est non Editable */
        	fieldPathFileCong.setEditable(false);
        	
        	/* Titre du formulaire de connexion à l'application*/
        	setTitle("Connexion");
 
        	
        	/* On affecte le Gestionnaire du panel des Champs de Saisie Comme etant une Grille de 4 lignes et une Colonne*/
        	panelChampsSaisie.setLayout(new GridLayout(5, 1));
        	
        	/* On ajoute le Panel des Champs au Centre du Formulaire*/
        	
        	getContentPane().add(panelChampsSaisie, new BorderLayout().CENTER);
        	
        	/* Info du Serveur IP*/
        	fieldCompte.setToolTipText("votre Compte");
        	
         	/* Info du Serveur IP*/
        	fieldPwd.setToolTipText("Mot de Passe");
        	
        	/* Label du Compte */
        
        	JLabel lblCompte=new JLabel("Votre Compte");
        	
        	lblCompte.setHorizontalAlignment(JLabel.CENTER);
        	
           	/* Ajout du Label de Compte*/
        	panelChampsSaisie.add(lblCompte);
        	
        	/* Ajout de la zone de saisie Compte*/
        	panelChampsSaisie.add(fieldCompte);
        	
          	/* Label du Mot De Passe Serveur */
            
        	JLabel lblMdp=new JLabel("Mot de Passe");
        	/* On centre le label*/
        	lblMdp.setHorizontalAlignment(JLabel.CENTER);
           	/* Ajout de l'adresse IP*/
    
        	panelChampsSaisie.add(lblMdp);
        	
        	/* Ajout de la zone de saisie mot de Passe Utilisateur*/
        	panelChampsSaisie.add(fieldPwd);
        	
        	/* On ajoute le Boutton en Bas*/
        	
        	getContentPane().add(panelButtons, new BorderLayout().SOUTH);
        	
        	/*Ajout du Button de validation dans le Panel */
        	
        	panelButtons.add(btnValider);
        	
        	panelButtons.add(btnCreateAccount);
        	
        	/* On demande de rédimentionner le formulaire*/
        	pack();
        	
        	/* On empeche le redimensionnement du formulaire*/
        	setResizable(false);
        	
        	/* On centre le formualire au centre du Formulaire Pricipale*/
        	setLocationRelativeTo(null);
        	
        	/*Action Connexion à l'application*/
        	
        	btnValider.addActionListener(new  ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				   //Se COnnecter
					compte=fieldCompte.getText();
					Enveloppe enveloppe=new Enveloppe();
					Utilisateur utilisateur=new Utilisateur();
					utilisateur.setNom(compte);
					utilisateur.setCle(fieldPwd.getText());
					enveloppe.setEmetteur(utilisateur);
					enveloppe.setInstruction("LOGGIN");
					try {
						
						envoyerMsg(enveloppe);
//						ObjectInputStream iss=new ObjectInputStream(socket.getInputStream());
//						try {
//							Enveloppe env=(Enveloppe) iss.readObject();
//							if (env.getInstruction().equalsIgnoreCase("Echec")){
//								throw new Exception("Session érroné");
//							}else{
//								btnLoggin.setEnabled(false);
//								//Récupération de la liste des utilisateurs
//								
//								dispose();
//							}
//						} catch (ClassNotFoundException e1) {
//							
//						}
						f.setTitle(compte);
					
						
					} catch (Exception e1) {
						
						 JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					
				}
			});
    
    
        	/* Actions sur le Boutton Nouveau*/
        	
        	btnCreateAccount.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					/**
					 * 
					 * @author <a maito="ngromaricokemba@gmail.com"> Romaric Okemba . Architecte Logiciel</a>
					 * Classe de création de Compte
					 *
					 */
					
					 class FrmCompte extends JDialog {
						
						private JPanel panel=new JPanel();
						
						private JTextField fieldCompte;
						
						private JPasswordField fieldPwd=new JPasswordField(15);
						
						private JPasswordField fieldConfirm=new JPasswordField(15);
						
						private JButton btnCreateAcount=new JButton("Valider");
						
						public FrmCompte(){
							
					
							
							this.setTitle("Compte");
							
							fieldCompte=new JTextField(15);
							
							fieldCompte.setForeground(Color.GRAY);
							
							panel.setLayout(new GridLayout(4,1));
							
							this.setSize(300, 300);
							
							panel.setBackground(Color.WHITE);
							
							/* On fixe le Layout du formulaire*/
							panel.setLayout(new GridLayout(3,2));
							
							this.getContentPane().add(panel,new BorderLayout().CENTER);
							
							/*Label du Compte */
							
							JLabel lblCompte=new JLabel("Compte");
							
							/* On demande que le lable soit centré*/
							lblCompte.setHorizontalAlignment(JLabel.CENTER);
							
							panel.add(lblCompte);
							
							panel.add(fieldCompte);
							
                        	/*Label du Mot de Passe */
							
							JLabel lblPwd=new JLabel("Mot de Passe");
							
							/* On demande que le lable soit centré*/
							lblPwd.setHorizontalAlignment(JLabel.CENTER);
							
							panel.add(lblPwd);
							
							panel.add(fieldPwd);
							
							fieldPwd.setToolTipText("Votre Mot de Passe");
							
							
                         	/*Label du Mot de Passe */
							
							JLabel lblConfPwd=new JLabel("Confirmer Mot de Passe");
							
							/* On demande que le lable soit centré*/
							lblConfPwd.setHorizontalAlignment(JLabel.CENTER);
							
							fieldConfirm.setToolTipText("Confirmez Votre Mot de Passe");
							
							panel.add(lblConfPwd);
				
							panel.add(fieldConfirm);
							
							this.getContentPane().add(btnCreateAcount,new BorderLayout().SOUTH);
							
							pack();
							
							this.setResizable(false);
							
							
							
							this.btnCreateAcount.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									
									
									
						
								}
							});
							
						}
						
					}
					 
					 //Affichage du formulaire de création de Compte
					 
					 FrmCompte frmCompte=new FrmCompte();
					 
					 frmCompte.setModal(true);
					 
					 frmCompte.setLocationRelativeTo(null);
					 
					 frmCompte.setVisible(true);
				}
			});
        	

        }
    	
	}
	
    public void remplir(Object [][] ROW_DATA){
    	/* Information pour l liste des Utilisateurss */
		String[] COLUMN_NAMES = { "Connecté"};
	
		 
    	tableUsr = new JTable(ROW_DATA, COLUMN_NAMES);
    	tableUsr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    	tableUsr.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int colonne, ligne;
				Point p = e.getPoint ();
				colonne = tableUsr.columnAtPoint (p);
				ligne =   tableUsr.rowAtPoint (p);
				lblDestinataire.setText((String) tableUsr.getValueAt(ligne, 0));
				lblSelect=lblDestinataire.getText();
				
			}
		}) ;
    	TableColumn column=tableUsr.getColumnModel().getColumn(0);
		column.setPreferredWidth(200);
		tableUsr.getColumnModel().getColumn(0).setCellRenderer(new IconCellRenderer());
		panelPrincipal.add(tableUsr, BorderLayout.WEST);
		
		
		tableUsr.setRowHeight(75);
		
    }
    
    public class Models extends DefaultTableModel{
    	 @Override
    	    public boolean isCellEditable(int rowIndex, int columnIndex) {
    		 
    	      return false;
    	    }
    	 public Models(Object[][] data, Object[] columnNames) { 
    	       super(data, columnNames); 
    	    } 
    	 
    }
	public JLabel getLblDestinataire() {
		return lblDestinataire;
	}

	public void setLblDestinataire(JLabel lblDestinataire) {
		this.lblDestinataire = lblDestinataire;
	}

	public String getLblSelect() {
		return lblSelect;
	}

	public void setLblSelect(String lblSelect) {
		this.lblSelect = lblSelect;
	}
	
	public static void sendFile(String pathname,String serv) throws Exception {
		File f = new File(pathname); 
		System.out.println("Envoi du fichier "+f.toURI().toURL()); 
	
		OutputStream fluxsortie = socket.getOutputStream(); 
		long taillefichier =f.length(); 

		System.out.println("Taille : "+ taillefichier); 

		long nbpassagesuposé=taillefichier / 4096; 

		System.out.println("Passages supposés : "+nbpassagesuposé); 
		InputStream in = new BufferedInputStream(new FileInputStream(f)); 
		ByteArrayOutputStream tableaubytes = new ByteArrayOutputStream(); 
		BufferedOutputStream tampon = new BufferedOutputStream(tableaubytes); 

		int lu = in.read(); 
		int[] aecrire = new int[4096]; 
		int compteur = 0; 
		long ouonestrendu=0; 
		//Tant qu'on est pas à la fin fu chier 
		while(lu > -1) 
		{ 
		//On lit les données du fichier 
		aecrire[compteur] = lu; 
		lu = in.read(); 
		compteur++; 


		//Quand on a rempli le tableau, on envoie un paquet de 4096 octets 
		if(compteur == 4096) 
		{ 
		compteur=0; 
		ouonestrendu++; 
		//On remplit le tampon 
		for(int x=0;x<4096;x++) 
		tampon.write(aecrire[x]); 

		//Et on l'envoie 
		fluxsortie.write(tableaubytes.toByteArray()); 

		tableaubytes.reset(); 
		System.out.println("Avancement : "+(float) ouonestrendu/nbpassagesuposé * 100+"%"); 
		} 
		} 


		for(int x=0;x<4096;x++) 
		tampon.write(aecrire[x]); 

		//Et on l'envoie 
		tampon.flush(); 
		fluxsortie.write(tableaubytes.toByteArray()); 
		fluxsortie.flush(); 

		System.out.println("Avancement: "+(float) ouonestrendu/nbpassagesuposé * 100+"%"); 

		System.out.println("Youpi finished"); 
		 
		tampon.close(); 
		
		System.out.println("Passages effectués : "+ouonestrendu); 
		
		} 
	
	private void afficheEmoticon() {
		 FrmEmoticon frmEmoticon=new FrmEmoticon(this, socket);
		  frmEmoticon.setVisible(true);
		
	}
}
