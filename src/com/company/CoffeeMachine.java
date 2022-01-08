package com.company;

public class CoffeeMachine {
    private int water = 0;
    private int milk = 0;
    private int coffee = 0;
    private int disposableCups = 0;
    private int money = 0;
    public boolean machineOn = true;
    private MachineStatus currentStatus = MachineStatus.MAIN_MENU;
    private int counterForFilling = 0;
    private String currentMessage;

    enum MachineStatus {
        MAIN_MENU, FILLING_RESOURCES, SELECTING_COFFEE
    }

    public CoffeeMachine(int water, int milk, int coffee, int disposableCups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.disposableCups = disposableCups;
        this.money = money;
        showInstructions();
    }

    public CoffeeMachine(int water, int milk, int coffee, int disposableCups) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.disposableCups = disposableCups;
        showInstructions();
    }

    public CoffeeMachine() {
        showInstructions();
    }
    public void action(String action) {
        if (machineOn) {
            switch (currentStatus) {
                case MAIN_MENU -> mainMenuActions(action);
                case FILLING_RESOURCES -> {
                    fillMachine(action);
                    showInstructions();
                }
                case SELECTING_COFFEE -> {
                    Coffee coffee = selectCoffee(action);
                    if (coffee != null) {
                        prepareCoffee(coffee);
                    }
                    showInstructions();
                }
            }
        } else {
            System.out.println("Coffee Machine is power off.");
        }
    }

    private void showInstructions() {
        switch (currentStatus) {
            case SELECTING_COFFEE -> currentMessage = "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case MAIN_MENU -> currentMessage = "Write action (buy, fill, take, remaining, exit): ";
        }
        System.out.println(currentMessage);
    }

    private void mainMenuActions(String action) {
        System.out.println();
        switch (action) {
            case "buy" -> {
                currentStatus = MachineStatus.SELECTING_COFFEE;
                showInstructions();
            }
            case ("fill") -> {
                currentStatus = MachineStatus.FILLING_RESOURCES;
                fillMachine(null);
                showInstructions();
            }
            case "take" -> {
                takeMoney();
                showInstructions();
            }
            case "remaining" -> {
                machineStatus();
                showInstructions();
            }
            case "exit" -> {
                machineOn = false;
                currentStatus = MachineStatus.MAIN_MENU;
            }
        }
    }

    private void machineStatus() {
        System.out.println("The coffee machine has:\n" + water + " ml of water\n" + milk + " ml of milk\n" + coffee + " g of coffee beans\n" + disposableCups + " disposable cups\n" + "$" + money + " of money \n");
    }

    private void takeMoney() {
        System.out.println("I gave you $" + money + "\n");
        money = 0;
    }

    private Coffee selectCoffee(String option) {
        currentStatus = MachineStatus.MAIN_MENU;
        return switch (option) {
            case "1" -> new Coffee(4, 250, 16);
            case "2" -> new Coffee(7, 350, 20, 75);
            case "3" -> new Coffee(6, 200, 12, 100);
            default -> null;
        };
    }

    private void prepareCoffee(Coffee coffee) {
        boolean noError = true;
        if (water - coffee.water < 0) {
            System.out.println("Sorry, not enough water \n");
            noError = false;
        }
        if (milk - coffee.milk < 0 && noError) {
            System.out.println("Sorry, not enough milk \n");
            noError = false;
        }
        if (this.coffee - coffee.coffee < 0 && noError) {
            System.out.println("Sorry, not enough coffee \n");
            noError = false;
        }
        if (disposableCups - 1 < 0 && noError) {
            System.out.println("Sorry, not enough disposable cups \n");
            noError = false;
        }
        if (noError) {
            water -= coffee.water;
            milk -= coffee.milk;
            this.coffee -= coffee.coffee;
            disposableCups -= 1;
            System.out.println("I have enough resources, making you a coffee! \n");
            money += coffee.cost;
        }
    }

    private void fillMachine(String input) {
        if (input == null && counterForFilling == 0) {
            counterForFilling = 1;
            currentMessage = "Write how many ml of water you want to add: ";
        } else {
            if (counterForFilling == 4 && (input != null && input.matches("\\d+"))) {
                counterForFilling = 0;
                disposableCups += Integer.parseInt(input);
                System.out.println();
                currentStatus = MachineStatus.MAIN_MENU;
            }

            if (counterForFilling == 3 && (input != null && input.matches("\\d+"))) {
                counterForFilling = 4;
                coffee += Integer.parseInt(input);
                currentMessage = "Write how many disposable cups of coffee you want to add: ";
            }

            if (counterForFilling == 2 && (input != null && input.matches("\\d+"))) {
                counterForFilling = 3;
                milk += Integer.parseInt(input);
                currentMessage = "Write how many grams of coffee beans you want to add: ";
            }

            if (counterForFilling == 1 && (input != null && input.matches("\\d+"))) {
                counterForFilling = 2;
                water += Integer.parseInt(input);
                currentMessage = "Write how many ml of milk you want to add: ";
            }
        }
    }
}
