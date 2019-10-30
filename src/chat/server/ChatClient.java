import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ChatClient extends JFrame implements ActionListener,Runnable {
    JLabel l1,l2;
    JButton b1,b2;
    JTextArea ta1;
    JTextField t1;
    JPanel p1,inputpanel,centerpanel,buttonpanel;
    String name,ip;
    Thread th1;
    //ServerSocket ss;
    Socket s1;
    BufferedReader br;
    BufferedWriter bw;
    
    public ChatClient(){
        setVisible(true);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p1=new JPanel();
        inputpanel=new JPanel();
        centerpanel=new JPanel();
        buttonpanel=new JPanel();
        l1=new JLabel("Name:");
        l2=new JLabel();
        t1=new JTextField(20);
        ta1=new JTextArea(5,20);
        JScrollPane js1=new JScrollPane(ta1);
        b1=new JButton("send");
        b2=new JButton("cancel");
       
       p1.setLayout(new BorderLayout());
        inputpanel.add(l1);
        inputpanel.add(l2);
        p1.add(inputpanel,"North");
        
        centerpanel.setLayout(new BorderLayout());
        centerpanel.add(js1,"Center");
        centerpanel.add(t1,"South");
        p1.add(centerpanel,"Center");
        
        buttonpanel.add(b1);
        buttonpanel.add(b2);
        
        p1.add(buttonpanel,"South");
        
        getContentPane().add(p1);
        
        setTitle("ServerSocket");
        setBounds(750, 150, 401, 401);
        setResizable(false);
        setAlwaysOnTop(true);
        p1.setBorder(BorderFactory.createTitledBorder("CHAT CLIENT"));
        ta1.setEditable(false);
                
        name=JOptionPane.showInputDialog(this, "Enter name","Saurabh Mishra");
        ip=JOptionPane.showInputDialog(this, "Enter IP Address of the server","localhost");
        l2.setText(name);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        try{
            //ss=new ServerSocket(8888);
            //s=ss.accept();
            s1=new Socket(ip,8888);
            br=new BufferedReader(
                    new InputStreamReader(
                            s1.getInputStream()));
            bw=new BufferedWriter(
                    new OutputStreamWriter(
                            s1.getOutputStream()));
            bw.write("Connected to Client");
            bw.newLine();
            bw.flush();
            th1=new Thread(this);
            th1.start();
         }
        catch(Exception ex){
        }
    }
    public void run(){
        for(;;){
            try{
                ta1.append(br.readLine()+"\n");
            }
            catch(Exception ex){
            }
        }
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
          try{
             String msg=name+" says--> "+t1.getText();
             bw.write(msg);
             bw.newLine();
             bw.flush();
             ta1.append(msg+"\n");
             t1.setText("");
          } 
          catch(Exception ex){
          }
        }
        else{
            System.exit(0);
        }
     }
    public static void main(String[] args) {
        new ChatClient();
    }
}

