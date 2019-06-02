//Poner tu directorio examen en -> home/alumno/jade/examen/...(tus java)



package examen;



import jade.core.*;

import jade.core.behaviours.*;

import jade.lang.acl.*;



public class Agente1 extends Agent{



  //ZONA DE VARIABLES GLOBALES

  //el tipico número que hay que ir dividiendo o lo que sea. Un testigo.



  //Metodos auxiliares comunes a todos los Agentes.

  //Por ejemplo dividir(), comprobarCero(), concatenar(), etc.

  //SIEMPRE SON public



  protected void setup(){

    //Inicializas las variables globales, y añades el primer addBehaviour()

  }



  protected void takeDown(){

    //El S.O.P de que el agente ha terminado.

  }



  /*-.-.-.-.-.-.-.-.- >> AQUI EMPIEZAN LOS COMPORTAMIENTOS << -.-.-.-.-.-.-.-.- */



  private class Comportamiento_Enviar extends Behaviour{



    //Este comportamiento ENVIA mensajes, por tanto, lo que necesitamos saber es

    //quien sera el receptor. Por tanto, tendremos un constructor y una variable

    //de clase para saber su nombre. Además del clasico fin.

    private String nombre_receptor;

    private boolean fin = false;



    public Comportamiento_Enviar(String nombre_receptor){

      this.nombre_receptor = nombre_receptor;

    }



    public void action(){

      //Tipico S.O.P de Soy fulanito y voy a enviar un mensaje



      //SIEMPRE necesitamos un AID para el receptor. Como nombre, le ponemos lo

      //que nos han pasado por el constructor.

      AID receptor = new AID();

      receptor.setLocalName(nombre_receptor);



      //Lo unico que nos queda es crear un mensaje, ponerle los valores que

      //necesita y mandarlo.

      ACLMessage msj = new ACLMessage();

      //SIEMPRE NECESITA -> Sender & Receiver & Content

      msj.setSender(getAID());

      msj.addReceiver(receptor);

      msj.setContent("" + /*la variable que quieras mandar*/);



      send(msj); //Mandamos el mensajico

      fin = true; //terminamos

    }



    public boolean done(){

      return fin;

    }



    public int onEnd(){

      //Añadir el behaviour de recibir

      myAgent.addBehaviour(new Recibir("nombre_emisor"));

    }

  }//FIN ENVIAR



  private class Comportamiento_Recibir extends Behaviour{



    //En este caso, mantenemos las variables de clase "fin" y "nombre_emisor"

    //porque estamos recibiendo. No obstante, necesitamos OTRAS DOS variables

    //de clase más. Que son  un AID y un MessageTemplate.



    //Mantenidas

    private String nombre_emisor;

    private boolean fin = false;



    //nuevas

    AID id_emisor;

    MessageTemplate mt;



    //El constructor es igual, solo le pasamos el String

    public Comportamiento_Recibir(String nombre_emisor){

      this.nombre_emisor = nombre_emisor;

      //PERO tendremos que inicializar el resto de variables, no?

      id_emisor = new AID();

      id_emisor.setLocalName(nombre_emisor);

      //El template lleva como argumento el AID que acabamos de crear.

      mt = MessageTemplate.MatchSender(id_emisor);

    }



    public void action(){

      //Tipico print de "Soy fulanito y estoy esperando a recibir mensajes"



      //Por el currele en el constructor, aqui solo toca crear un ACLMessage

      //que coge de argumento el template.

      ACLMessage msj = blockingReceive(mt);

      //Comprueba que hay mensaje

      if(msj!=null){

        //Haces lo que toque hacer.

        //Pasar el numero a string y dividirlo, concatenar shit, lo que sea.

        //Y POR SUPUESTO

        fin = true;

      }

    }



    public boolean done(){

      return fin;

    }



    public int onEnd(){

      //Aqui lo que va a haber normmalmente es una comprobacion de que el programa

      //deba seguir o no. Por ejemplo, que el numero que mandemos sea 0 sabes.

      if(/*la condicion (que es un metodo auxiliar tuyo ;) */){

        myAgent.doDelete();

      }else{

        myAgent.addBehaviour(new Comportamiento_Enviar("Agente al que le toque"));

      }

      return 0;

    }

  }

}

