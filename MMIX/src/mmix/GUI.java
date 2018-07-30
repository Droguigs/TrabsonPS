package mmix;

import mmix.Executer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class GUI extends JPanel{

    private static final LayoutManager GridLayout = new GridLayout();

    private String label;

    private Executer exe = new Executer();
    ArrayList<Byte> memArray = new ArrayList<>();

    private JPanel mainPanel        = new JPanel();
    private JPanel buttonsPanel     = new JPanel();
    private JPanel memoryPanel      = new JPanel();
    private JPanel textPanel        = new JPanel();

    private JButton save            = new JButton("Save");
    private JButton compile         = new JButton("Compile");
    private JButton run             = new JButton("Run");

    private JTextArea codeText      = new JTextArea();

    private DefaultListModel<Byte> model = new DefaultListModel<>();

    private JList<Long> registers   = new JList<>();
    private JList<Byte> memory      = new JList<>(new DefaultListModel<Byte>());
    private JScrollPane scrollPane  = new JScrollPane(memory);

    public GUI(String label) {
        initComponents();
        this.label = label;

        testMem();

    }

    private ActionListener saveActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(label+".txt"), "utf-8"))) {
                writer.write(codeText.getText());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    private ActionListener runActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exe.runCode("obj_code.txt");
            ArrayList<Byte> lista = exe.getMem();
            
            for(int i = 0; i < 262144; i = i + 8){
                System.out.println(lista.get(i) + " " + lista.get(i+1) + " " + lista.get(i+2) + " " + lista.get(i+3) + " " + lista.get(i+4) + " " + lista.get(i+5) + " " + lista.get(i+6) + " " + lista.get(i+7));
            }
        }
        
    };

    private ActionListener compileActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Montador montador = new Montador();
            Ligador ligador = new Ligador();
            montador.getInstructions();
            montador.getCode();
            try {
                ligador.link();
            }
            catch (Exception excecao) {

            }
        }
    }; 

    private void initComponents(){

        memory.setLayoutOrientation(JList.VERTICAL);

        buttonsPanel.setLayout((LayoutManager) GridLayout);

        //LISTENERS SETUP:
        save.addActionListener(saveActionListener);
        run.addActionListener(runActionListener);
        compile.addActionListener(compileActionListener);

        //LIST SETUP:
        testMem();

        //SIZES:
        //COMPONENTS
        save.setBounds(10,10,10,10);
        save.setPreferredSize(new Dimension(30,30));
        compile.setBounds(10,10,10,10);
        compile.setPreferredSize(new Dimension(30,30));
        run.setBounds(10,10,10,10);
        run.setPreferredSize(new Dimension(30,30));

        memory.setBounds(400,400,300,300);
        memory.setPreferredSize(new Dimension(600,384));
        registers.setBounds(400,400,300,300);
        registers.setPreferredSize(new Dimension(600,384));

        codeText.setBounds(400,400,400,400);
        codeText.setPreferredSize(new Dimension(1200,600));
        codeText.setAutoscrolls(true);

        //PANELS
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xFFFFFF));
        mainPanel.setBounds(400,400,400,400);
        mainPanel.setPreferredSize(new Dimension(1200,1024));
        buttonsPanel.setBounds(400,400,400,400);
        buttonsPanel.setPreferredSize(new Dimension(1200,save.getHeight()+10));
        buttonsPanel.add(save);
        buttonsPanel.add(compile);
        buttonsPanel.add(run);
//        buttonsPanel.setBackground(new Color(0x8764563));

//        textPanel.setBackground(new Color(123));
        textPanel.setBounds(400,400,400,400);
        textPanel.setPreferredSize(new Dimension(1200,600));
        codeText.setBounds(400,400,400,400);
        codeText.setPreferredSize(new Dimension(1200,600));
        codeText.setAutoscrolls(true);
        textPanel.add(codeText);

//        memoryPanel.setBackground(new Color(0));
        memoryPanel.setBounds(400,400,400,400);
        memoryPanel.setPreferredSize(new Dimension(1200, 384));
        memoryPanel.add(scrollPane);

        //ADD TO MAIN PANEL:
        mainPanel.add(buttonsPanel);
        mainPanel.add(textPanel);
        mainPanel.add(memoryPanel);

        //ADD TO MAIN VIEW:
        this.add(mainPanel);

    }

    public void testMem(){
        memArray = exe.getMem();

        for(Byte b:memArray){
            ((DefaultListModel)memory.getModel()).addElement(b);
        }
    }
}
