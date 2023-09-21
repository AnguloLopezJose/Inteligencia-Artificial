package puzzle8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Grafico extends javax.swing.JFrame {

int [][]inicio;    
static int [][]solución={{1,2,3},{4,5,6},{7,8,0}};    
static int cont;   
static int contador;  
static AudioClip sonido;
private Timer t;
private int tiempo=120;
private javax.swing.JButton b1;
private javax.swing.JButton b2;
private javax.swing.JButton b3;
private javax.swing.JButton b4;
private javax.swing.JButton b5;
private javax.swing.JButton b6;
private javax.swing.JButton b7;
private javax.swing.JButton b8;
private javax.swing.JButton b9;
private javax.swing.JButton jButton11;
private javax.swing.JLabel jLabel1;
private javax.swing.JLabel jLabel2;
private javax.swing.JLabel jLabel3;
private javax.swing.JPanel jPanel1;
private javax.swing.JPanel jPanel2;
private javax.swing.JScrollPane jScrollPane1;
public static final javax.swing.JLabel pasos = new javax.swing.JLabel();
private javax.swing.JButton seleccionar;
private javax.swing.JButton seleccionar1;
private javax.swing.JButton seleccionar2;
public static final javax.swing.JTextArea text = new javax.swing.JTextArea();
private javax.swing.JLabel time;
    
    //Se Cargan todos los componentes del Jframe
    public Grafico() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("imagenes/ganar.jpg")).getImage());
        this.setTitle("PUZZLE (IA)");
        inicio=new int [3][3];
        pasos.setVisible(true); 
    }

    //Metodo que resuelve el Puzzle
public static Nodo  buscarSolucion(Nodo inicio, int [][] solucion){
                ArrayList<Nodo> expandidos=new ArrayList<>();//Guardara todos los nodos que se generaran
                ArrayList<Nodo> visitados= new ArrayList<>(); //Guarda los nodos que ya han sido visitados
                expandidos.add(inicio);//Pasa a expandidos el primer nodo o la primera matriz
                cont=0;//Leva la cuenta de los intentos 
                boolean llave=true;//Se utiliza para salir del while
                try {
                while(!expandidos.isEmpty() && llave==true){  //Recorrera toda la lista, es un ciclo infinito    
                Nodo revisar=expandidos.remove(0);//Nodo que revisa, nodo que saca
                int [] pcero=ubicarPosicionCero(revisar.getEstado());//guarda el pcero la posicion del 0 para saber a donde se mueve
                
               if(Arrays.deepEquals(revisar.getEstado(), solucion)){//En caso de que algun array del arraylist sea la solucion la retorna
                   System.out.println("**** SE ENCONTRO UNA SOLUCION");
                   return revisar;
               } 
               //Cuando llega a 20000 intentos sale del while      
               if(cont==30000){//Cuando cont==Intentos llegue a los 30000, vuelve la llave falsa y sale del while
                   llave=false;
                   borrartext();//No deja que ponga nada en el TextArea
               }
               
               ArrayList<Nodo> hijos=new ArrayList<>();//Almacena los hijos generados
               visitados.add(revisar);//Guarda en el arraylist de visitados, los nodos revisados
               
               //llevan acabo el calculo para ubicar el 0 en el mejor lugar
                if(pcero[0]!=0){ 
                   Nodo hijo= new Nodo(clonar(revisar.getEstado()));
                   int arriba=hijo.getEstado()[pcero[0]-1][pcero[1]];
                   hijo.getEstado()[pcero[0]][pcero[1]]=arriba;
                   hijo.getEstado()[pcero[0]-1][pcero[1]]=0;
                   if(!estaEnVisitados(visitados,hijo))
                    expandidos.add(hijo);  
                    hijos.add(hijo);
                }
                if(pcero[0]!=2){
                   Nodo hijo= new Nodo(clonar(revisar.getEstado()));
                   int abajo=hijo.getEstado()[pcero[0]+1][pcero[1]];
                   hijo.getEstado()[pcero[0]][pcero[1]]=abajo;
                   hijo.getEstado()[pcero[0]+1][pcero[1]]=0;
                   if(!estaEnVisitados(visitados,hijo))
                   expandidos.add(hijo);  
                   hijos.add(hijo);
                }
                
                if(pcero[1]!=0){
                   Nodo hijo= new Nodo(clonar(revisar.getEstado()));
                   int izquierda=hijo.getEstado()[pcero[0]][pcero[1]-1];
                   hijo.getEstado()[pcero[0]][pcero[1]]=izquierda;
                   hijo.getEstado()[pcero[0]][pcero[1]-1]=0;
                   if(!estaEnVisitados(visitados,hijo))
                   expandidos.add(hijo);  
                   hijos.add(hijo);
                }
                if(pcero[1]!=2){
                   Nodo hijo= new Nodo(clonar(revisar.getEstado()));
                   int derecha=hijo.getEstado()[pcero[0]][pcero[1]+1];
                   hijo.getEstado()[pcero[0]][pcero[1]]=derecha;
                   hijo.getEstado()[pcero[0]][pcero[1]+1]=0;
                   if(!estaEnVisitados(visitados,hijo))
                   expandidos.add(hijo);  
                   hijos.add(hijo);
                }
                revisar.setHijos(hijos);
                }
                
                } catch (Exception e) {
                    System.out.println(e);     
                }
        return null;
        
              } 

             //imprime que Nodo o arreglo se esta revisanso
              public static  void imprimirEstado(int [][]estado){
    for (int[] estado1 : estado) {
        for (int j = 0; j <estado.length; j++) {
            System.out.print("[" + estado1[j] + "]");
            text.append(Integer.toString(estado1[j]));
        }
        text.append("\n");
        System.out.println("");
    }
                  
                  text.append("------------ \n"); 
              }
              
              
              //En caso de que el algoritmo no encuentre solucion, lanza un mensaje y no deja que aparezca nada en Areatext
              public static void borrartext(){  
             text.setText("");
             text.setVisible(true);
             JOptionPane.showMessageDialog(null, " No hay solución, intente mover las piezas");
              }
              
        //Retorna la posicion del 0      
        private static int[] ubicarPosicionCero(int[][] estado) {
        int[] posicion=new int[2]; //Guardar la posicion del 0 o i y j
        
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j <estado.length; j++) {
                if(estado[i][j]==0){
                    
                    posicion[0]=i;
                    posicion[1]=j;
                 
                }        
            }
        }
        return posicion;
    }

    //No deja que se repitan las combinaciones          
    private static int[][] clonar(int[][] estado) { 
        int [][]clon=new int[estado.length][estado.length];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j <estado.length; j++) {
               
              clon[i][j]=estado[i][j];  
            }
            
        }
        return clon;
    } 
    
        //Verifica si hay nodos visitados   
        private static boolean estaEnVisitados(ArrayList<Nodo> visitados, Nodo hijo) {
        for(Nodo v:visitados){
            if(Arrays.deepEquals(v.getEstado(),hijo.getEstado())){
                 System.out.println("true");
                return true;
               
            }               
        }
         return false;
      
    }  
    
    //Verificar que el numero que ingreso, realmente se almaceno en la posicion donde queria
    public void mostrar(){
        
    for (int[] inicio1 : inicio) {
        for (int j = 0; j < inicio.length; j++) {
            System.out.print("[" + inicio1[j] + "]");
        }
        System.out.println("");
    }
         System.out.println("--------------------------");
    }
    
    //Verifica si todos los botones tienen el valor correspondiente
    public  void ganaste(){
       
            if("1".equals(b1.getText()) && "2".equals(b2.getText()) && "3".equals(b3.getText()) && "4".equals(b4.getText()) && "5".equals(b5.getText()) && "6".equals(b6.getText()) && "7".equals(b7.getText()) && "8".equals(b8.getText()) && "0".equals(b9.getText()) ){
               
                if(t!=null){
                t.stop();
                tiempo=160;
                time.setText("");
                }
                JOptionPane.showMessageDialog(null, "Felicidades Ganaste!");
                reiniciar();
        }
        
    }    
    
    //Sonido al poner el cursos en los botones 
    public void sonidos(){    
        try {
        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/audios/clic.wav"));
        sonido.play();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //Reacomoda todo en su lugar
    public void reiniciar(){
    b1.setText("?");
    b2.setText("?");
    b3.setText("?");
    b4.setText("?");
    b5.setText("?");
    b6.setText("?");
    b7.setText("?");
    b8.setText("?");
    b9.setText("?");
   
    tiempo=160;
    time.setText("");
    
    b1.setBackground(Color.getHSBColor(240,240,240));
    b2.setBackground(Color.getHSBColor(240,240,240));
    b3.setBackground(Color.getHSBColor(240,240,240));
    b4.setBackground(Color.getHSBColor(240,240,240));
    b5.setBackground(Color.getHSBColor(240,240,240));
    b6.setBackground(Color.getHSBColor(240,240,240));
    b7.setBackground(Color.getHSBColor(240,240,240));
    b8.setBackground(Color.getHSBColor(240,240,240));
    b9.setBackground(Color.getHSBColor(240,240,240));
    
    text.setText("");
    pasos.setText("");
      
    for (int[] inicio1 : inicio) {
        for (int j = 0; j < inicio.length; j++) {
            inicio1[j] = 0;
        }
    }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        b1 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        b8 = new javax.swing.JButton();
        b9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        seleccionar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        seleccionar1 = new javax.swing.JButton();
        seleccionar2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setBackground(new java.awt.Color(19, 31, 48));

        b1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b1.setText("?");
        b1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b1MouseEntered(evt);
            }
        });
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b3.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b3.setText("?");
        b3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b3MouseClicked(evt);
            }
        });

        b4.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b4.setText("?");
        b4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b4MouseClicked(evt);
            }
        });

        b5.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b5.setText("?");
        b5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b5MouseClicked(evt);
            }
        });

        b2.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b2.setText("?");
        b2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b2MouseClicked(evt);
            }
        });

        b6.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b6.setText("?");
        b6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b6MouseClicked(evt);
            }
        });

        b7.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b7.setText("?");
        b7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b7MouseClicked(evt);
            }
        });

        b8.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b8.setText("?");
        b8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b8MouseClicked(evt);
            }
        });
        b8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b8ActionPerformed(evt);
            }
        });

        b9.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 24)); // NOI18N
        b9.setText("?");
        b9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b9MouseClicked(evt);
            }
        });
        b9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton11.setBackground(new java.awt.Color(0, 102, 153));
        jButton11.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(19, 31, 48));
        jButton11.setText("VS Tiempo");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton11MouseEntered(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        text.setBackground(new java.awt.Color(19, 31, 48));
        text.setColumns(20);
        text.setForeground(new java.awt.Color(255, 255, 255));
        text.setRows(5);
        jScrollPane1.setViewportView(text);

        seleccionar.setBackground(new java.awt.Color(0, 102, 153));
        seleccionar.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 12)); // NOI18N
        seleccionar.setForeground(new java.awt.Color(19, 31, 48));
        seleccionar.setText("Solucionar");
        seleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seleccionarMouseEntered(evt);
            }
        });
        seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        jLabel1.setText("Pasos");

        pasos.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 18)); // NOI18N
        pasos.setForeground(new java.awt.Color(255, 255, 255));

        seleccionar1.setBackground(new java.awt.Color(0, 102, 153));
        seleccionar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 12)); // NOI18N
        seleccionar1.setForeground(new java.awt.Color(19, 31, 48));
        seleccionar1.setText("Aleatorio");
        seleccionar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seleccionar1MouseEntered(evt);
            }
        });
        seleccionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionar1ActionPerformed(evt);
            }
        });

        seleccionar2.setBackground(new java.awt.Color(0, 102, 153));
        seleccionar2.setFont(new java.awt.Font("Humnst777 BlkCn BT", 0, 12)); // NOI18N
        seleccionar2.setForeground(new java.awt.Color(19, 31, 48));
        seleccionar2.setText("Reiniciar");
        seleccionar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seleccionar2MouseEntered(evt);
            }
        });
        seleccionar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionar2ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puzzle8/IMAGENES/f70c8b85f02b4b2fe33f64aa2b4cd75d-bot-n-de-parada-del-icono-cerrar-by-vexels_opt.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Salir");

        time.setFont(new java.awt.Font("Geometr706 BlkCn BT", 0, 24)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(seleccionar1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(seleccionar2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(seleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pasos, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seleccionar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seleccionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seleccionar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pasos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseClicked

    if("?".equals(b1.getText())){
    int unoo;
        String uno=JOptionPane.showInputDialog(null,"Inserte un número");
        unoo=Integer.parseInt(uno);
        if(unoo==0){
            b1.setBackground(Color.red);
        }
        inicio[0][0]=unoo;
        b1.setText(uno);
        mostrar();
    }else{
        
        if("0".equals(b4.getText())){
            int btn4=Integer.parseInt(b4.getText());
            int btn1=Integer.parseInt(b1.getText());
            
            inicio[0][0]=btn4;
            inicio[1][0]=btn1;
            
            b4.setText(b1.getText()); //el Boton hacia donde va
            b1.setText("0");
            b1.setBackground(Color.red);            
            b4.setBackground(Color.getHSBColor(240,240,240));

            for (int[] inicio1 : inicio) {
                for (int j = 0; j < inicio.length; j++) {
                    System.out.print("[" + inicio1[j] + "]");
                }
                System.out.println("");
            }
           ganaste();
        }
        
        if("0".equals(b2.getText())){
             int btn2=Integer.parseInt(b2.getText());
             int btn1=Integer.parseInt(b1.getText());
            
             inicio[0][0]=btn2;
             inicio[0][1]=btn1;
             
            b2.setText(b1.getText()); //el Boton hacia donde va
            b1.setText("0");
            b1.setBackground(Color.red);            
            b2.setBackground(Color.getHSBColor(240,240,240));
            
            for (int[] inicio1 : inicio) {
                for (int j = 0; j < inicio.length; j++) {
                    System.out.print("[" + inicio1[j] + "]");
                }
                System.out.println("");
            }
            
            ganaste();
        } 
    }    
    
    
    }//GEN-LAST:event_b1MouseClicked

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b1ActionPerformed

    private void b2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b2MouseClicked

    if("?".equals(b2.getText())){
    int doss;
        String dos=JOptionPane.showInputDialog(null,"Inserte un número");
        doss=Integer.parseInt(dos);
        if(doss==0){
            b2.setBackground(Color.red);
        }
        inicio[0][1]=doss;
        b2.setText(dos);
        mostrar();
    }else{
          
          if("0".equals(b5.getText())){
               int btn5=Integer.parseInt(b5.getText());
               int btn2=Integer.parseInt(b2.getText());
              
               inicio[0][1]=btn5;
               inicio[1][1]=btn2;
              
               b5.setText(b2.getText()); //el Boton hacia donde va
               b2.setText("0");
               b2.setBackground(Color.red);            
               b5.setBackground(Color.getHSBColor(240,240,240));
               
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              }
                 ganaste();
               
          }
          
          if("0".equals(b3.getText())){
              int btn3=Integer.parseInt(b3.getText());
              int btn2=Integer.parseInt(b2.getText());
              
              inicio[0][1]=btn3;
              inicio[0][2]=btn2;
              
               b3.setText(b2.getText()); //el Boton hacia donde va
               b2.setText("0");
               b2.setBackground(Color.red);            
               b3.setBackground(Color.getHSBColor(240,240,240));
              
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              } 
              
              ganaste();
          }
          
          if("0".equals(b1.getText())){
              int btn1=Integer.parseInt(b1.getText());
              int btn2=Integer.parseInt(b2.getText());
              
              inicio[0][0]=btn2;
              inicio[0][1]=btn1;
              
               b1.setText(b2.getText()); //el Boton hacia donde va
               b2.setText("0");
               b2.setBackground(Color.red);            
               b1.setBackground(Color.getHSBColor(240,240,240));
              
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              } 
              ganaste();   
          }       
      }   
    }//GEN-LAST:event_b2MouseClicked

    private void b3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b3MouseClicked

    if("?".equals(b3.getText())){
    int tress;
        String tres=JOptionPane.showInputDialog(null,"Inserte un número");
        tress=Integer.parseInt(tres);
        if(tress==0){
            b3.setBackground(Color.red);
        }
        inicio[0][2]=tress;
        b3.setText(tres);
        mostrar();
    }else{
        
        if("0".equals(b6.getText())){
             int btn3=Integer.parseInt(b3.getText());
             int btn6=Integer.parseInt(b6.getText());
            
             inicio[0][2]=btn6;
             inicio[1][2]=btn3;
             
               b6.setText(b3.getText()); //el Boton hacia donde va
               b3.setText("0");
               b3.setBackground(Color.red);            
               b6.setBackground(Color.getHSBColor(240,240,240));
               
            for (int[] inicio1 : inicio) {
                for (int j = 0; j < inicio.length; j++) {
                    System.out.print("[" + inicio1[j] + "]");
                }
                System.out.println("");
            } 
             ganaste();
        }
        
        if("0".equals(b2.getText())){
             int btn3=Integer.parseInt(b3.getText());
             int btn2=Integer.parseInt(b2.getText());
             
             inicio[0][1]=btn3;
             inicio[0][2]=btn2;
             
              b2.setText(b3.getText()); //el Boton hacia donde va
              b3.setText("0");
              b3.setBackground(Color.red);            
              b2.setBackground(Color.getHSBColor(240,240,240));
              
            for (int[] inicio1 : inicio) {
                for (int j = 0; j < inicio.length; j++) {
                    System.out.print("[" + inicio1[j] + "]");
                }
                System.out.println("");
            } 
            ganaste();
        }     
      }    
    }//GEN-LAST:event_b3MouseClicked

    private void b4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b4MouseClicked

    if("?".equals(b4.getText())){
    int cuatross;
        String cuatro=JOptionPane.showInputDialog(null,"Inserte un número");
        cuatross=Integer.parseInt(cuatro);
        if(cuatross==0){
            b4.setBackground(Color.red);
        }
        inicio[1][0]=cuatross;
        b4.setText(cuatro);
        mostrar();
    }else{
          
          if("0".equals(b7.getText())){
               int btn4=Integer.parseInt(b4.getText());
               int btn7=Integer.parseInt(b7.getText());
               
               inicio[2][0]=btn4;
               inicio[1][0]=btn7;
              
               b7.setText(b4.getText()); //el Boton hacia donde va
               b4.setText("0");
               b4.setBackground(Color.red);            
               b7.setBackground(Color.getHSBColor(240,240,240));
              
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              } 
              ganaste();
              
          }
          
          if("0".equals(b1.getText())){
               int btn4=Integer.parseInt(b4.getText());
               int btn1=Integer.parseInt(b1.getText());
              
               inicio[1][0]=btn1;
               inicio[0][0]=btn4;
              
               b1.setText(b4.getText()); //el Boton hacia donde va
               b4.setText("0");
               b4.setBackground(Color.red);            
               b1.setBackground(Color.getHSBColor(240,240,240));
              
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              } 
              ganaste();
          }
          
          if("0".equals(b5.getText())){
               int btn4=Integer.parseInt(b4.getText());
               int btn5=Integer.parseInt(b5.getText());
              
               inicio[1][0]=btn5;
               inicio[1][1]=btn4;
              
               b5.setText(b4.getText()); //el Boton hacia donde va
               b4.setText("0");
               b4.setBackground(Color.red);            
               b5.setBackground(Color.getHSBColor(240,240,240));
               
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              } 
                 ganaste();
          }    
      }    
       
    }//GEN-LAST:event_b4MouseClicked

    private void b5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b5MouseClicked

  
    if("?".equals(b5.getText())){
    int cincos;
        String cinco=JOptionPane.showInputDialog(null,"Inserte un número");
        cincos=Integer.parseInt(cinco);
        if(cincos==0){
            b5.setBackground(Color.red);
        }
        inicio[1][1]=cincos;
        b5.setText(cinco);
        mostrar();
    }else{
           
            if("0".equals(b8.getText())){
            int btn5=Integer.parseInt(b5.getText());
            int btn8=Integer.parseInt(b8.getText());
          inicio[2][1]=btn5;
          inicio[1][1]=btn8;
          
          b8.setText(b5.getText()); //el Boton hacia donde va
          b5.setText("0");
          b5.setBackground(Color.red);            
          b8.setBackground(Color.getHSBColor(240,240,240));
              
                for (int[] inicio1 : inicio) {
                    for (int j = 0; j < inicio.length; j++) {
                        System.out.print("[" + inicio1[j] + "]");
                    }
                    System.out.println("");
                } 
           ganaste();
      }         
            
      if("0".equals(b4.getText()))   {
           int btn5=Integer.parseInt(b5.getText());
           int btn4=Integer.parseInt(b4.getText());
          
           inicio[1][0]=btn5;
           inicio[1][1]=btn4;
          
          b4.setText(b5.getText()); //el Boton hacia donde va
          b5.setText("0");
          b5.setBackground(Color.red);            
          b4.setBackground(Color.getHSBColor(240,240,240));
          
                for (int[] inicio1 : inicio) {
                    for (int j = 0; j < inicio.length; j++) {
                        System.out.print("[" + inicio1[j] + "]");
                    }
                    System.out.println("");
                } 
            ganaste();
      }   
      
      if("0".equals(b2.getText())){
             int btn5=Integer.parseInt(b5.getText());
             int btn2=Integer.parseInt(b2.getText());
          
             inicio[0][1]=btn5;
             inicio[1][1]=btn2;
             
          b2.setText(b5.getText()); //el Boton hacia donde va
          b5.setText("0");
          b5.setBackground(Color.red);            
          b2.setBackground(Color.getHSBColor(240,240,240));
          
                for (int[] inicio1 : inicio) {
                    for (int j = 0; j < inicio.length; j++) {
                        System.out.print("[" + inicio1[j] + "]");
                    }
                    System.out.println("");
                } 
             ganaste();
      }
      
      if("0".equals(b6.getText())){
          int btn5=Integer.parseInt(b5.getText());
          int btn6=Integer.parseInt(b6.getText());
          
          inicio[1][1]=btn6;
          inicio[1][2]=btn5;
          
          b6.setText(b5.getText()); //el Boton hacia donde va
          b5.setText("0");
          b5.setBackground(Color.red);            
          b6.setBackground(Color.getHSBColor(240,240,240));
          
                for (int[] inicio1 : inicio) {
                    for (int j = 0; j < inicio.length; j++) {
                        System.out.print("[" + inicio1[j] + "]");
                    }
                    System.out.println("");
                } 
          ganaste();
      }    
      } 
    }//GEN-LAST:event_b5MouseClicked

    private void b6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b6MouseClicked

    if("?".equals(b6.getText())){
    int seiss;
        String seis=JOptionPane.showInputDialog(null,"Inserte un número");
        seiss=Integer.parseInt(seis);
        if(seiss==0){
            b6.setBackground(Color.red);
        }
        inicio[1][2]=seiss;
        b6.setText(seis);
        mostrar();
    }else{
          if("0".equals(b9.getText())){
              int btn6=Integer.parseInt(b6.getText());
              int btn9=Integer.parseInt(b9.getText());
              inicio[2][2]=btn6;
              inicio[1][2]=btn9;
              b9.setText(b6.getText());
              b6.setText("0");
              b6.setBackground(Color.red);
              b9.setBackground(Color.getHSBColor(240,240,240));
              
              
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              }
              ganaste();
          }
          
          if("0".equals(b5.getText())){
              int btn6=Integer.parseInt(b6.getText());
              int btn5=Integer.parseInt(b5.getText());
              
              inicio[1][1]=btn6;
              inicio[1][2]=btn5;
              
              b5.setText(b6.getText());
              b6.setText("0");
              b6.setBackground(Color.red);
              b5.setBackground(Color.getHSBColor(240,240,240));
              
                  
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              }
              ganaste();        
          }
          
          if("0".equals(b3.getText())){
              int btn6=Integer.parseInt(b6.getText());
              int btn3=Integer.parseInt(b3.getText());
              
              inicio[0][2]=btn6;
              inicio[1][2]=btn3;
              
              b3.setText(b6.getText());
              b6.setText("0");
              b6.setBackground(Color.red);
              b3.setBackground(Color.getHSBColor(240,240,240));
              
                      
              for (int[] inicio1 : inicio) {
                  for (int j = 0; j < inicio.length; j++) {
                      System.out.print("[" + inicio1[j] + "]");
                  }
                  System.out.println("");
              }
              ganaste();
          }    
      } 
      
    }//GEN-LAST:event_b6MouseClicked

    private void b7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b7MouseClicked

    if("?".equals(b7.getText())){
    int sietes;
        String siete=JOptionPane.showInputDialog(null,"Inserte un número");
        sietes=Integer.parseInt(siete);
        if(sietes==0){
            b7.setBackground(Color.red);
        }
        inicio[2][0]=sietes;
        b7.setText(siete);
        mostrar();
    }else{
           
               if("0".equals(b8.getText())){
               int btn8=Integer.parseInt(b8.getText());
               int btn7=Integer.parseInt(b7.getText());
               
              inicio[2][0]=btn8;
              inicio[2][1]=btn7;
              b8.setText(b7.getText());
              b7.setText("0");
              b7.setBackground(Color.red);
              b8.setBackground(Color.getHSBColor(240,240,240));
              
                   for (int[] inicio1 : inicio) {
                       for (int j = 0; j < inicio.length; j++) {
                           System.out.print("[" + inicio1[j] + "]");
                       }
                       System.out.println("");
                   }
   ganaste();
           }
          
         
           if("0".equals(b4.getText())){
               int btn4=Integer.parseInt(b4.getText());
               int btn7=Integer.parseInt(b7.getText());
               
               inicio[2][0]=btn4;
               inicio[1][0]=btn7;
               
              b4.setText(b7.getText());
              b7.setText("0");
              b7.setBackground(Color.red);
              b4.setBackground(Color.getHSBColor(240,240,240));
               
                   for (int[] inicio1 : inicio) {
                       for (int j = 0; j < inicio.length; j++) {
                           System.out.print("[" + inicio1[j] + "]");
                       }
                       System.out.println("");
                   }      
                  ganaste();
           }
      }    
    }//GEN-LAST:event_b7MouseClicked

    private void b8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b8MouseClicked

    if("?".equals(b8.getText())){
    int ochos;
        String ocho=JOptionPane.showInputDialog(null,"Inserte un número");
        ochos=Integer.parseInt(ocho);
        if(ochos==0){
            b8.setBackground(Color.red);
        }
        inicio[2][1]=ochos;
        b8.setText(ocho);
        mostrar();
    }else{
             
      if("0".equals(b9.getText())){
            int btn8=Integer.parseInt(b8.getText());
            int btn9=Integer.parseInt(b9.getText());
          inicio[2][1]=btn9;
          inicio[2][2]=btn8;
          
          b9.setText(b8.getText());
          b8.setText("0");
          b8.setBackground(Color.red);            
          b9.setBackground(Color.getHSBColor(240,240,240));
              
          for (int[] inicio1 : inicio) {
              for (int j = 0; j < inicio.length; j++) {
                  System.out.print("[" + inicio1[j] + "]");
              }
              System.out.println("");
          } 
           ganaste();
      }         
      if("0".equals(b5.getText())){
            int btn5=Integer.parseInt(b5.getText());
            int btn8=Integer.parseInt(b8.getText());
          inicio[1][1]=btn8;
          inicio[2][1]=btn5;
          
          b5.setText(b8.getText());
          b8.setText("0");
          b8.setBackground(Color.red);            
          b5.setBackground(Color.getHSBColor(240,240,240));
              
          for (int[] inicio1 : inicio) {
              for (int j = 0; j < inicio.length; j++) {
                  System.out.print("[" + inicio1[j] + "]");
              }
              System.out.println("");
          } 
           ganaste();
      }        
      
      if("0".equals(b7.getText())){
            int btn7=Integer.parseInt(b7.getText());
            int btn8=Integer.parseInt(b8.getText());
          
            inicio[2][0]=btn8;
            inicio[2][1]=btn7;
            
          b7.setText(b8.getText());
          b8.setText("0");
          b8.setBackground(Color.red);            
          b7.setBackground(Color.getHSBColor(240,240,240));
          
          for (int[] inicio1 : inicio) {
              for (int j = 0; j < inicio.length; j++) {
                  System.out.print("[" + inicio1[j] + "]");
              }
              System.out.println("");
          } 
          ganaste();
      }
      }
    
    }//GEN-LAST:event_b8MouseClicked

    private void b9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b9MouseClicked

    if("?".equals(b9.getText())){
    int nueves;
        String nueve=JOptionPane.showInputDialog(null,"Inserte un número");
        nueves=Integer.parseInt(nueve);
        if(nueves==0){
            b9.setBackground(Color.red);
        }
        inicio[2][2]=nueves;
        b9.setText(nueve);
        mostrar();
    }else{
          //Va del boton 9 al 6
               if("0".equals(b6.getText())){
                   
              int btn6=Integer.parseInt(b6.getText());
              int btn9=Integer.parseInt(b9.getText());
                   
                   inicio[2][2]=btn6;
                   inicio[1][2]=btn9;
                   
              b6.setText(b9.getText());
              b9.setText("0");
              b9.setBackground(Color.red);
              b6.setBackground(Color.getHSBColor(240,240,240));
                   
                   for (int[] inicio1 : inicio) {
                       for (int j = 0; j < inicio.length; j++) {
                           System.out.print("[" + inicio1[j] + "]");
                       }
                       System.out.println("");
                   }            
                    ganaste();
               }
               
               //---------------------------------------------------
               // Va del boton 9 al 8
               if("0".equals(b8.getText())){
              int btn8=Integer.parseInt(b8.getText());
              int btn9=Integer.parseInt(b9.getText());
                   
                   inicio[2][2]=btn8;
                   inicio[2][1]=btn9;
                   
                   b8.setText(b9.getText());
                   b9.setText("0");
                   b9.setBackground(Color.red);
                   b8.setBackground(Color.getHSBColor(240,240,240));
                   
                   for (int[] inicio1 : inicio) {
                       for (int j = 0; j < inicio.length; j++) {
                           System.out.print("[" + inicio1[j] + "]");
                       }
                       System.out.println("");
                   }
                   
                  ganaste(); 
               }            
      }
     
    }//GEN-LAST:event_b9MouseClicked

    private void seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionarActionPerformed

    Nodo iniciar=new Nodo(inicio);
    Nodo sol=buscarSolucion(iniciar, solución);
        //Muestra los pasos 
         contador=0;
      
       boolean llave=true;
       
        try {
            while(sol.padre!=null & llave==true){     
               imprimirEstado(sol.getEstado());
                 System.out.println("-----------------" + (++contador));
               sol=sol.padre;
            pasos.setText(Integer.toString(contador)); 
            //Cuando llega a los 20000 sale del while
              if(contador==30000){
                  llave=false;
                  borrartext();
                  
              }
            
              }
           System.out.println(" Inicio ");
           imprimirEstado(inicio);
           
       System.out.println("-----------------");    
    
            
            
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_seleccionarActionPerformed

    private void seleccionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionar1ActionPerformed
  
    text.setText("");
    pasos.setText("");
    b1.setBackground(Color.getHSBColor(240,240,240));
    b2.setBackground(Color.getHSBColor(240,240,240));
    b3.setBackground(Color.getHSBColor(240,240,240));
    b4.setBackground(Color.getHSBColor(240,240,240));
    b5.setBackground(Color.getHSBColor(240,240,240));
    b6.setBackground(Color.getHSBColor(240,240,240));
    b7.setBackground(Color.getHSBColor(240,240,240));
    b8.setBackground(Color.getHSBColor(240,240,240));
    b9.setBackground(Color.getHSBColor(240,240,240));  
        
      int numero = (int) (Math.random() * 6)+1;
      
      switch(numero){
          
          case 1: 
              
              inicio[0][0]=1;
              inicio[0][1]=2;
              inicio[0][2]=3;
              inicio[1][0]=4;
              inicio[1][1]=0;
              inicio[1][2]=6;
              inicio[2][0]=7;
              inicio[2][1]=5;
              inicio[2][2]=8;
              
              b1.setText("1");
              b2.setText("2");
              b3.setText("3");
              b4.setText("4");
              b5.setText("0");
              b5.setBackground(Color.red);
              b6.setText("6");
              b7.setText("7");
              b8.setText("5");
              b9.setText("8");
              
              break;
          
          case 2:
              
              inicio[0][0]=1;
              inicio[0][1]=0;
              inicio[0][2]=3;
              inicio[1][0]=4;
              inicio[1][1]=2;
              inicio[1][2]=6;
              inicio[2][0]=7;
              inicio[2][1]=5;
              inicio[2][2]=8;
              
              b1.setText("1");
              b2.setText("0");
              b2.setBackground(Color.red);
              b3.setText("3");
              b4.setText("4");
              b5.setText("2");      
              b6.setText("6");
              b7.setText("7");
              b8.setText("5");
              b9.setText("8");
              break;
          
          case 3:
                   
              inicio[0][0]=4;
              inicio[0][1]=1;
              inicio[0][2]=3;
              inicio[1][0]=0;
              inicio[1][1]=2;
              inicio[1][2]=6;
              inicio[2][0]=7;
              inicio[2][1]=5;
              inicio[2][2]=8;
              
              b1.setText("4");
              b2.setText("1");
              b3.setText("3");
              b4.setText("0");
              b4.setBackground(Color.red);
              b5.setText("2");      
              b6.setText("6");
              b7.setText("7");
              b8.setText("5");
              b9.setText("8");
              
              break;
          
          case 4:
              inicio[0][0]=4;
              inicio[0][1]=1;
              inicio[0][2]=3;
              inicio[1][0]=2;
              inicio[1][1]=6;
              inicio[1][2]=8;
              inicio[2][0]=7;
              inicio[2][1]=5;
              inicio[2][2]=0;
              
              b1.setText("4");
              b2.setText("1");
              b3.setText("3");
              b4.setText("2");
              b5.setText("6");      
              b6.setText("8");
              b7.setText("7");
              b8.setText("5");
              b9.setText("0");
              b9.setBackground(Color.red);
              break;
          
          case 5:
              
              inicio[0][0]=4;
              inicio[0][1]=1;
              inicio[0][2]=3;
              inicio[1][0]=2;
              inicio[1][1]=6;
              inicio[1][2]=8;
              inicio[2][0]=7;
              inicio[2][1]=5;
              inicio[2][2]=0;
              
              b1.setText("4");
              b2.setText("1");
              b3.setText("3");
              b4.setText("2");
              b5.setText("6");      
              b6.setText("8");
              b7.setText("7");
              b8.setText("5");
              b9.setText("0");
              b9.setBackground(Color.red);
              
              
              break;
          
          
          case 6:
              
              inicio[0][0]=6;
              inicio[0][1]=0;
              inicio[0][2]=1;
              inicio[1][0]=2;
              inicio[1][1]=7;
              inicio[1][2]=3;
              inicio[2][0]=4;
              inicio[2][1]=5;
              inicio[2][2]=8;
              
              b1.setText("6");
              b2.setText("0");
              b2.setBackground(Color.red);
              b3.setText("1");
              b4.setText("2");
              b5.setText("7");      
              b6.setText("3");
              b7.setText("4");
              b8.setText("5");
              b9.setText("8"); 
              break;
          
          
              
      } 
        
    }//GEN-LAST:event_seleccionar1ActionPerformed

    private void seleccionar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionar2ActionPerformed

   reiniciar();
        
    }//GEN-LAST:event_seleccionar2ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
      
    int res=JOptionPane.showConfirmDialog(null, "Seguro que desea salir");
    
    if(res==0){
        
        System.exit(0);
        
    }else{
        
    }
        
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void b9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b9ActionPerformed


    }//GEN-LAST:event_b9ActionPerformed

    private void b8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b8ActionPerformed

    private void jButton11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseEntered
     sonidos();
    }//GEN-LAST:event_jButton11MouseEntered

    private void b1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseEntered

    }//GEN-LAST:event_b1MouseEntered

    private void seleccionar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seleccionar1MouseEntered
    sonidos();
    }//GEN-LAST:event_seleccionar1MouseEntered

    private void seleccionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seleccionarMouseEntered
    sonidos();
    }//GEN-LAST:event_seleccionarMouseEntered

    private void seleccionar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seleccionar2MouseEntered
    sonidos();
    }//GEN-LAST:event_seleccionar2MouseEntered

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
   
   if(!"?".equals(b1.getText())&& !"?".equals(b2.getText())&&!"?".equals(b3.getText())&&!"?".equals(b4.getText())&&!"?".equals(b5.getText())&&!"?".equals(b6.getText())&&!"?".equals(b7.getText())&&!"?".equals(b8.getText())&&!"?".equals(b9.getText()) ){
       
        t= new Timer(1000, (ActionEvent e) -> {
            tiempo--;
            time.setText(tiempo+" ");
            if(tiempo==0){
                AudioClip perdersonido;
                perdersonido= java.applet.Applet.newAudioClip(getClass().getResource("/audios/over.wav"));
                perdersonido.play();
                JOptionPane.showMessageDialog(null, "Has perdido, por el tiempo");
                reiniciar();
                t.stop();
            }
   });
        t.start();
   }else{
       JOptionPane.showMessageDialog(null, "[No puedes juegar si aún no has ingresado todos los valores]");
   }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Grafico().setVisible(true);
        });
    }
}
