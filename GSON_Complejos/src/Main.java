import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Main {
    // Usando VSCode, se puede pulsar F5 y ejecutará el programa con el argumento directamente

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Debes ingresar la URL del JSON como argumento");
            return;
        }

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));

            Type listOfExpense = new TypeToken<List<Person>>() {}.getType();

            List<Person> expenses = gson.fromJson(reader, listOfExpense);


            float max = 0;
            String maxPerson = "";
            float total = 0;
            int totalExpenses = 0;



            System.out.print("Listado de personas: ");
            for (Person person : expenses) {
                System.out.print(person.getWHO());

                if (expenses.indexOf(person) != expenses.size() - 1) {
                    System.out.print(", ");
                }

                for (Week week : person.getWEEK()) {
                    for (Expense expense : week.getEXPENSE()) {
                        if (expense.getAMOUNT() > max) {
                            max = expense.getAMOUNT();
                            maxPerson = person.getWHO();
                        }

                        total += expense.getAMOUNT();
                        totalExpenses++;
                    }
                }
            }
            System.out.println();

            System.out.println("Persona con mayor gasto: " + maxPerson + " - " + max + "€");

            System.out.println("Precio medio de todos los gastos: " + total / totalExpenses + "€");

            // Lo separo en dos bucles para que sea más legible pero se podría hacer en uno solo y sería más eficiente
            float maxExpense = 0;
            String maxExpensePerson = "";
            int maxExpenseWeek = 0;
            for (Person person : expenses) {
                for (Week week : person.getWEEK()) {
                    for (Expense expense : week.getEXPENSE()) {
                        if (expense.getAMOUNT() > maxExpense) {
                            maxExpense = expense.getAMOUNT();
                            maxExpensePerson = person.getWHO();
                            maxExpenseWeek = week.getNUMBER();
                        }
                    }
                }
            }
            System.out.println("Número y factura de la persona con el mayor precio de factura: " + maxExpensePerson + " - Factura" + maxExpenseWeek + ": " + maxExpense + "€");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
