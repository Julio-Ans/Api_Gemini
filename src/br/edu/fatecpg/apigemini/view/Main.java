package br.edu.fatecpg.apigemini.view;

import br.edu.fatecpg.apigemini.service.ConsomeApi;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        int contador = 1;

        while(contador==1){
            System.out.println("Faça Sua Pergunta para a IA:");
            String pergunta = scan.nextLine();
            System.out.println("\n" + ConsomeApi.fazerPergunta(pergunta));
            System.out.println("Deseja continuar: sim ou não");
            String resp = scan.nextLine();

            if(!Objects.equals(resp, "sim")){
                contador=-1;
            }


        }

    }
}