package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(400,540,120,9,550);
        while (coffeeMachine.machineOn){
            String action = scanner.nextLine();
            coffeeMachine.action(action);
        }
    }
}

