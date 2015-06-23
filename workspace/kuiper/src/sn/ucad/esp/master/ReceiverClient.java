package sn.ucad.esp.master;

import java.awt.Color;
import java.awt.FileDialog;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.curiosity.kuiper.ihm.FrmStart;

public class ReceiverClient implements Runnable {
	private FrmChat chat;
	private ObjectInputStream is;
	private Socket socket;
	private FrmStart frmStart;

	public ReceiverClient(FrmChat chat, Socket socket,FrmStart frmStart) {
		this.chat = chat;
		this.socket = socket;
		this.frmStart=frmStart;
	}

	@Override
	public void run() {

		while (true) {
			try {
				is = new ObjectInputStream(socket.getInputStream());
				Enveloppe enveloppe = (Enveloppe) is.readObject();
				
//				if (!enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getCompte()) && enveloppe.getDestinataire().getNom().equalsIgnoreCase(chat.getCompte())&& enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getLblDestinataire().getText()) && enveloppe.getInstruction().equalsIgnoreCase("FILES")){
//					System.out.println("///////////////");
//					
//					FileDialog fd=new FileDialog(chat,"Telecharger un Fichier",java.awt.FileDialog.SAVE);
//					fd.setFile(enveloppe.getMessage().getMessage());
//					fd.show();
//					OutputStream fos=new FileOutputStream(fd.getDirectory()+fd.getFile());
//					fos.write(enveloppe.getbFile());
//					fos.close();
//
//				}
				
				if (enveloppe.getInstruction().equalsIgnoreCase("FILES") &&  !enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getCompte()) && enveloppe.getDestinataire().getNom().equalsIgnoreCase(chat.getCompte())){
					
					
					FileDialog fd=new FileDialog(chat,"Telecharger un Fichier",java.awt.FileDialog.SAVE);
					fd.setFile(enveloppe.getMessage().getMessage());
					fd.show();
					OutputStream fos=new FileOutputStream(fd.getDirectory()+fd.getFile());
					fos.write(enveloppe.getbFile());
					fos.close();

				}
				
				if (enveloppe.getInstruction().equalsIgnoreCase("OK")) {
					// ON s'est connecté
		           chat.setVisible(true);
		           frmStart.dispose();
					
					Object[][] ROW_DATA = new Object[enveloppe.getLstUtilisateur().size()][enveloppe.getLstUtilisateur().size()];
					String[] tableauUser=new String[enveloppe.getLstUtilisateur().size()];
					enveloppe.getLstUtilisateur().toArray(tableauUser);
					for(int i=0;i<tableauUser.length;i++){
						for(int j=0;j<tableauUser.length;j++){
							ROW_DATA[i][j]=tableauUser[i];
							chat.remplir(ROW_DATA);
						}
					}
				}else if(enveloppe.getInstruction().equalsIgnoreCase("KO")){
					throw  new Exception("Séssion érronée");
				
					//Envoi des Messages
				
				}else if(enveloppe.getInstruction().equalsIgnoreCase("MSG") &&  !enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getCompte()) && enveloppe.getDestinataire().getNom().equalsIgnoreCase(chat.getCompte())){
					
					// Create a style object and then set the style attributes
				    Style style = chat.getDoc().addStyle("StyleName", null);
				    
				    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
				    
		

				    StyleConstants.setForeground(style, Color.BLUE);
				    
				    
				    SimpleAttributeSet center = new SimpleAttributeSet();
				    StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
				    
				    chat.getDoc().setParagraphAttributes(chat.getDoc().getLength(), chat.getDoc().getLength(), center, false);
				    chat.getDoc().insertString(chat.getDoc().getLength(),enveloppe.getEmetteur().getNom()+ " : "+ enveloppe.getMessage().getMessage(), style);
				    chat.getDoc().insertString(chat.getDoc().getLength(),"\n", style);
				    chat.getDoc().insertString(chat.getDoc().getLength(), "Réçu le : "+ dateFormat.format(new Date()), style);
				    chat.getDoc().insertString(chat.getDoc().getLength(), "\n", null);
				    chat.getDoc().insertString(chat.getDoc().getLength(), "\n", null);
				    chat.getDoc().insertString(chat.getDoc().getLength(), "\n", null);
					
					
					java.net.URL imageURL = JButton.class.getResource("/recu.wav");
//
					Sound player = new Sound(imageURL.getFile());

					InputStream stream = new ByteArrayInputStream(player.getSamples());

					player.play(stream);
				    
				    
				}else if(enveloppe.getInstruction().equalsIgnoreCase("ECRIT") &&  !enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getCompte()) && enveloppe.getDestinataire().getNom().equalsIgnoreCase(chat.getCompte())&& enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getLblDestinataire().getText())){
					String temp=chat.getLblDestinataire().getText();
				
					chat.getLblDestinataire().setText(enveloppe.getEmetteur().getNom()+ " est entraint d'écrire..." );

			
					
					Thread.sleep(200);
					chat.getLblDestinataire().setText(temp);
				}else if(enveloppe.getInstruction().equalsIgnoreCase("TEL") &&  !enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getCompte()) && enveloppe.getDestinataire().getNom().equalsIgnoreCase(chat.getCompte())){
					final java.net.URL imageURL = JButton.class.getResource("/ringtone.wav");
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Sound player = new Sound(imageURL.getFile());

							InputStream stream = new ByteArrayInputStream(player.getSamples());

							player.play(stream);
							
						}
					}).start();;
					JOptionPane.showConfirmDialog(null, enveloppe.getEmetteur().getNom()+ "   vous appelle ...","Appelle",JOptionPane.YES_NO_CANCEL_OPTION);
					
					
			
					
					
				}else if (!enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getCompte()) && enveloppe.getDestinataire().getNom().equalsIgnoreCase(chat.getCompte())&& enveloppe.getEmetteur().getNom().equalsIgnoreCase(chat.getLblDestinataire().getText()) && enveloppe.getInstruction().equalsIgnoreCase("Smiley")){
					//Il s'agit d'une émotion
					// Create a style object and then set the style attributes

					
					
					try {
						
						  Style style = chat.getDoc().addStyle("StyleName", null);
						  
						  
						  StyleConstants.setForeground(style, Color.BLACK);
						  
						  SimpleAttributeSet center = new SimpleAttributeSet();
						  
						  StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
						  
						  chat.getDoc().setParagraphAttributes(chat.getDoc().getLength(), chat.getDoc().getLength(), center, false);
						  
						   
						   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
						 
						   try {
							   
							chat.getDoc().insertString(chat.getDoc().getLength(),  enveloppe.getEmetteur().getNom() , style);
						} catch (BadLocationException e2) {
							e2.printStackTrace();
						}
						
	               
						
						StyleContext context = new StyleContext();
					    StyledDocument document = new DefaultStyledDocument(context);

					    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);

					    Icon icon = new ImageIcon(enveloppe.getImage());
					    JLabel label = new JLabel(icon);
					    StyleConstants.setComponent(labelStyle, label);
					    
					    try {
							chat.getDoc().insertString(chat.getDoc().getLength(), "Ignored", labelStyle);
							  style = chat.getDoc().addStyle("StyleName", null);
								StyleConstants.setForeground(style, Color.BLACK);
							     center = new SimpleAttributeSet();
							    StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
							    chat.getDoc().setParagraphAttributes(chat.getDoc().getLength(), chat.getDoc().getLength(), center, false);
							   
							 
							    chat.getDoc().insertString(chat.getDoc().getLength(),  "\n", style);
							    chat.getDoc().insertString(chat.getDoc().getLength(),  "\n", style);
							  
							
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    
					    
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					
		


				}
			} catch (Exception e) {
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(chat, e.getMessage());
			}

		}

	}

}
