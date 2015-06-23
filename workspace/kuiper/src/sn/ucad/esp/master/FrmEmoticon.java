package sn.ucad.esp.master;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class FrmEmoticon extends JFrame {

	private Socket socket;

	private FrmChat frmMouses;
	
	private JButton buttoRire = new JButton(new ImageIcon("images/smiley.jpg"));
	private JButton btnPleur = new JButton(new ImageIcon("images/pleur.png"));
	private JButton btnLover = new JButton(new ImageIcon("images/lover.png"));
	private JButton btnBizarre = new JButton(new ImageIcon("images/bizarre.png"));
	private JButton buttonOeil = new JButton(new ImageIcon("images/oeil.png"));
	private JButton buttonRire = new JButton(new ImageIcon("images/rire.png"));
	

	public FrmEmoticon(FrmChat mouse, final Socket socket) {
		this.socket = socket;

		this.frmMouses = mouse;
		
	
		
		this.setTitle("Emotocon");
		this.setSize(200, 200);
		this.setResizable(false);
		JPanel jPanel=new JPanel();
		this.setLocationRelativeTo(null);
		jPanel.setLayout(new GridLayout(2, 2));
		this.getContentPane().add(jPanel);
		
		jPanel.add(buttoRire);
		jPanel.add(btnPleur);
		jPanel.add(btnLover);
		jPanel.add(btnBizarre);
		jPanel.add(buttonOeil);
		jPanel.add(buttonRire);
		
		buttoRire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			
				
				Enveloppe enveloppe=new Enveloppe();
				enveloppe.setInstruction("Smiley");
				
				Message message=new Message();
				Utilisateur emetteur=new Utilisateur();
				emetteur.setNom(frmMouses.getCompte());
				Utilisateur destinataire=new Utilisateur();
				destinataire.setNom(frmMouses.getCompte());
				enveloppe.setEmetteur(emetteur);
				destinataire.setNom(frmMouses.getLblSelect());
				enveloppe.setDestinataire(destinataire);
				enveloppe.setMessage(message);
				
				
				
				enveloppe.setMessage(message);
				
				
				

				
				//this.txtChat.append(txtMsg.getText()+"\n");
				
				java.net.URL imageURL = JButton.class.getResource("/lol.wav");
				
				Sound player = new Sound(imageURL.getFile());
				
				enveloppe.setImage("images/smiley.jpg");
				
				InputStream stream = new ByteArrayInputStream(player.getSamples());
				
				player.play(stream);
				
				try {
					
					  Style style = frmMouses.getDoc().addStyle("StyleName", null);
					  StyleConstants.setForeground(style, Color.BLACK);
					  SimpleAttributeSet center = new SimpleAttributeSet();
					  StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
					  frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
					   
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
					 
					   try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "Moi : " , style);
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					
                
					
					StyleContext context = new StyleContext();
				    StyledDocument document = new DefaultStyledDocument(context);

				    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

				    Icon icon = new ImageIcon("images/smiley.jpg");
				    JLabel label = new JLabel(icon);
				    StyleConstants.setComponent(labelStyle, label);
				    
				    try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(), "Ignored", labelStyle);
						  style = frmMouses.getDoc().addStyle("StyleName", null);
							StyleConstants.setForeground(style, Color.BLACK);
						     center = new SimpleAttributeSet();
						    StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
						    frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
						   
						 
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
						   os.writeObject(enveloppe);
						   os.flush();
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	   btnPleur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
				Enveloppe enveloppe=new Enveloppe();
				enveloppe.setInstruction("Smiley");
				
				
				Message message=new Message();
				Utilisateur emetteur=new Utilisateur();
				emetteur.setNom(frmMouses.getCompte());
				Utilisateur destinataire=new Utilisateur();
				destinataire.setNom(frmMouses.getCompte());
				enveloppe.setEmetteur(emetteur);
				destinataire.setNom(frmMouses.getLblSelect());
				enveloppe.setDestinataire(destinataire);
				enveloppe.setMessage(message);
				
				
				
				enveloppe.setMessage(message);
				
				
				

				
				//this.txtChat.append(txtMsg.getText()+"\n");
				
				java.net.URL imageURL = JButton.class.getResource("/lol.wav");
				
				Sound player = new Sound(imageURL.getFile());
				
				enveloppe.setImage("images/pleur.png");
				
				InputStream stream = new ByteArrayInputStream(player.getSamples());
				
				player.play(stream);
				
				try {
					
					  Style style = frmMouses.getDoc().addStyle("StyleName", null);
					  StyleConstants.setForeground(style, Color.BLACK);
					  SimpleAttributeSet center = new SimpleAttributeSet();
					  StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
					  frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
					   
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
					 
					   try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "Moi : " , style);
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					
                
					
					StyleContext context = new StyleContext();
				    StyledDocument document = new DefaultStyledDocument(context);

				    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

				    Icon icon = new ImageIcon("images/pleur.png");
				    JLabel label = new JLabel(icon);
				    StyleConstants.setComponent(labelStyle, label);
				    
				    try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(), "Ignored", labelStyle);
						  style = frmMouses.getDoc().addStyle("StyleName", null);
							StyleConstants.setForeground(style, Color.BLACK);
						     center = new SimpleAttributeSet();
						    StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
						    frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
						   
						 
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
						   os.writeObject(enveloppe);
						   os.flush();
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
	   btnLover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
				Enveloppe enveloppe=new Enveloppe();
				enveloppe.setInstruction("Smiley");
				
				Message message=new Message();
				Utilisateur emetteur=new Utilisateur();
				emetteur.setNom(frmMouses.getCompte());
				Utilisateur destinataire=new Utilisateur();
				destinataire.setNom(frmMouses.getCompte());
				enveloppe.setEmetteur(emetteur);
				destinataire.setNom(frmMouses.getLblSelect());
				enveloppe.setDestinataire(destinataire);
				enveloppe.setMessage(message);
				
				
				
				enveloppe.setMessage(message);
				
				
				

				
				//this.txtChat.append(txtMsg.getText()+"\n");
				
				java.net.URL imageURL = JButton.class.getResource("/lol.wav");
				
				Sound player = new Sound(imageURL.getFile());
				
				enveloppe.setImage("images/lover.png");
				
				InputStream stream = new ByteArrayInputStream(player.getSamples());
				
				player.play(stream);
				
				try {
					
					  Style style = frmMouses.getDoc().addStyle("StyleName", null);
					  StyleConstants.setForeground(style, Color.BLACK);
					  SimpleAttributeSet center = new SimpleAttributeSet();
					  StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
					  frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
					   
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
					 
					   try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "Moi : " , style);
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					
                
					
					StyleContext context = new StyleContext();
				    StyledDocument document = new DefaultStyledDocument(context);

				    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

				    Icon icon = new ImageIcon("images/lover.png");
				    JLabel label = new JLabel(icon);
				    StyleConstants.setComponent(labelStyle, label);
				    
				    try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(), "Ignored", labelStyle);
						  style = frmMouses.getDoc().addStyle("StyleName", null);
							StyleConstants.setForeground(style, Color.BLACK);
						     center = new SimpleAttributeSet();
						    StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
						    frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
						   
						 
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
						   os.writeObject(enveloppe);
						   os.flush();
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
			
	   btnBizarre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
				Enveloppe enveloppe=new Enveloppe();
				enveloppe.setInstruction("Smiley");
				
				Message message=new Message();
				Utilisateur emetteur=new Utilisateur();
				emetteur.setNom(frmMouses.getCompte());
				Utilisateur destinataire=new Utilisateur();
				destinataire.setNom(frmMouses.getCompte());
				enveloppe.setEmetteur(emetteur);
				destinataire.setNom(frmMouses.getLblSelect());
				enveloppe.setDestinataire(destinataire);
				enveloppe.setMessage(message);
				
				
				
				

				
				//this.txtChat.append(txtMsg.getText()+"\n");
				
				java.net.URL imageURL = JButton.class.getResource("/lol.wav");
				
				Sound player = new Sound(imageURL.getFile());
				
				enveloppe.setImage("images/bizarre.png");
				
				InputStream stream = new ByteArrayInputStream(player.getSamples());
				
				player.play(stream);
				
				try {
					
					  Style style = frmMouses.getDoc().addStyle("StyleName", null);
					  StyleConstants.setForeground(style, Color.BLACK);
					  SimpleAttributeSet center = new SimpleAttributeSet();
					  StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
					  frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
					   
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
					 
					   try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "Moi : " , style);
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					
               
					
					StyleContext context = new StyleContext();
				    StyledDocument document = new DefaultStyledDocument(context);

				    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

				    Icon icon = new ImageIcon("images/bizarre.png");
				    JLabel label = new JLabel(icon);
				    StyleConstants.setComponent(labelStyle, label);
				    
				    try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(), "Ignored", labelStyle);
						  style = frmMouses.getDoc().addStyle("StyleName", null);
							StyleConstants.setForeground(style, Color.BLACK);
						     center = new SimpleAttributeSet();
						    StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
						    frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
						   
						 
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
						   os.writeObject(enveloppe);
						   os.flush();
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
	   buttonOeil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
				Enveloppe enveloppe=new Enveloppe();
				enveloppe.setInstruction("Smiley");
				
	
				
				Message message=new Message();
				Utilisateur emetteur=new Utilisateur();
				emetteur.setNom(frmMouses.getCompte());
				Utilisateur destinataire=new Utilisateur();
				destinataire.setNom(frmMouses.getCompte());
				enveloppe.setEmetteur(emetteur);
				destinataire.setNom(frmMouses.getLblSelect());
				enveloppe.setDestinataire(destinataire);
				enveloppe.setMessage(message);
				
				
				
				

				
				//this.txtChat.append(txtMsg.getText()+"\n");
				
				java.net.URL imageURL = JButton.class.getResource("/lol.wav");
				
				Sound player = new Sound(imageURL.getFile());
				
				enveloppe.setImage("images/oeil.png");
				
				InputStream stream = new ByteArrayInputStream(player.getSamples());
				
				player.play(stream);
				
				try {
					
					  Style style = frmMouses.getDoc().addStyle("StyleName", null);
					  StyleConstants.setForeground(style, Color.BLACK);
					  SimpleAttributeSet center = new SimpleAttributeSet();
					  StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
					  frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
					   
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
					 
					   try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "Moi : " , style);
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					
               
					
					StyleContext context = new StyleContext();
				    StyledDocument document = new DefaultStyledDocument(context);

				    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

				    Icon icon = new ImageIcon("images/oeil.png");
				    JLabel label = new JLabel(icon);
				    StyleConstants.setComponent(labelStyle, label);
				    
				    try {
						frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(), "Ignored", labelStyle);
						  style = frmMouses.getDoc().addStyle("StyleName", null);
							StyleConstants.setForeground(style, Color.BLACK);
						     center = new SimpleAttributeSet();
						    StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
						    frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
						   
						 
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
						   ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
						   os.writeObject(enveloppe);
						   os.flush();
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	   buttonRire.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					

					
					Enveloppe enveloppe=new Enveloppe();
					
					enveloppe.setInstruction("Smiley");
					
					Message message=new Message();
					Utilisateur emetteur=new Utilisateur();
					emetteur.setNom(frmMouses.getCompte());
					Utilisateur destinataire=new Utilisateur();
					destinataire.setNom(frmMouses.getCompte());
					enveloppe.setEmetteur(emetteur);
					destinataire.setNom(frmMouses.getLblSelect());
					enveloppe.setDestinataire(destinataire);
					enveloppe.setMessage(message);
					
					
					

					
					//this.txtChat.append(txtMsg.getText()+"\n");
					
					java.net.URL imageURL = JButton.class.getResource("/lol.wav");
					
					Sound player = new Sound(imageURL.getFile());
					
					enveloppe.setImage("images/rire.png");
					
					InputStream stream = new ByteArrayInputStream(player.getSamples());
					
					player.play(stream);
					
					try {
						
						  Style style = frmMouses.getDoc().addStyle("StyleName", null);
						  StyleConstants.setForeground(style, Color.BLACK);
						  SimpleAttributeSet center = new SimpleAttributeSet();
						  StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
						  frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
						   
						   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
						 
						   try {
							frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "Moi : " , style);
						} catch (BadLocationException e2) {
							e2.printStackTrace();
						}
						
	               
						
						StyleContext context = new StyleContext();
					    StyledDocument document = new DefaultStyledDocument(context);

					    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

					    Icon icon = new ImageIcon("images/rire.png");
					    JLabel label = new JLabel(icon);
					    StyleConstants.setComponent(labelStyle, label);
					    
					    try {
							frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(), "Ignored", labelStyle);
							  style = frmMouses.getDoc().addStyle("StyleName", null);
								StyleConstants.setForeground(style, Color.BLACK);
							     center = new SimpleAttributeSet();
							    StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
							    frmMouses.getDoc().setParagraphAttributes(frmMouses.getDoc().getLength(), frmMouses.getDoc().getLength(), center, false);
							   
							 
							   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
							   frmMouses.getDoc().insertString(frmMouses.getDoc().getLength(),  "\n", style);
							   
							   ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
							   os.writeObject(enveloppe);
							   os.flush();
							
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    
					    
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
	}

}
