package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class App 
{
    public static void main( String[] args )
    {
        try {
            Socket s = new Socket("localhost", 4500);

            BufferedReader inputTastiera = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader inDalServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream outVersoIlServer = new DataOutputStream(s.getOutputStream());

            String stringaRicevuta = "";
            String stringaDaInviare = "";
            boolean esci = false;

            System.out.println("> Connessione effettuata. Digita ESCI per uscire");

            while(!esci){
                System.out.println("> Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate, altrimenti digita ESCI per uscire");
                stringaDaInviare = inputTastiera.readLine();
    
                if(stringaDaInviare.equals("LISTA")){
                    outVersoIlServer.writeBytes("@" + "\n");
                    stringaRicevuta = inDalServer.readLine();
                    stringaRicevuta = stringaRicevuta.replaceAll(";", "\n");
                    System.out.println(stringaRicevuta);
                }
                else if(stringaDaInviare.equals("ESCI")){
                    esci = true;
                    stringaDaInviare = "-1";
                    outVersoIlServer.writeBytes(stringaDaInviare);
                    System.out.println("> Disconnessione effettuata");
                }
                else if(!stringaDaInviare.equals("")){
                    outVersoIlServer.writeBytes(stringaDaInviare + "\n");
                    System.out.println("> Nota salvata");
                }
                else{
                    System.out.println("Attenzione, inserire un'attivita'!");
                }
            }
            s.close();

        } catch (UnknownHostException e) {
            e.getMessage();
            System.out.println("> Errore durante l'istanza del socket");
        } catch (IOException e) {
            e.getMessage();
            System.out.println("> Errore nella creazione degli stream comunicazione");
        }
    }
}
