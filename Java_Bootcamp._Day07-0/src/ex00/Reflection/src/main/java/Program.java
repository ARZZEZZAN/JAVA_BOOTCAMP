import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Program {
    private static Class<?> aClass;
    private static Field[] declaredFields;
    private static Method[] declaredMethods;
    private static Object obj;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Set<Class<?>> classes = scanningClasses();
        aClass = userInput(classes);
        scanningFields();
        createObject();
        changeVariable();
        callMethod();
    }

    private static Set<Class<?>> scanningClasses() {
        System.out.println("Classes: ");
        Reflections reflections = new Reflections("org.example.Classes", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for(Class<?> i : classes) {
            System.out.println("- " + i.getSimpleName());
        }
        System.out.println("----------------------------");
        return classes;
    }
    private static Class<?> userInput(Set<Class<?>> classes) {
        System.out.println("Enter class name:");
        System.out.print("-> ");
        String classInput = null;
        if(!scanner.hasNext()) {
            System.err.println("Illegal argument!");
            System.exit(-1);
        }
        classInput = scanner.nextLine();
        for (Class<?> cls : classes) {
            if(cls.getSimpleName().equals(classInput)) {
                System.out.println("----------------------------");
                return cls;
            }

        }
        System.out.println("Doesn't exist");
        return null;
    }
    private static void scanningFields() {
        System.out.println("fields: ");
        declaredFields = aClass.getDeclaredFields();
        declaredMethods = aClass.getDeclaredMethods();
        for (Field field : declaredFields) {
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println("methods: ");
        for (Method method : declaredMethods) {
            String returnType = method.getReturnType().getSimpleName();
            if(returnType.equals("void")) {
                System.out.print("\t");
            } else {
                System.out.print("\t" + returnType + " ");
            }
            System.out.print(getMethodStr(method));
            System.out.println();
        }
        System.out.println("----------------------------");
    }

    private static String getMethodStr(Method method) {
        Class<?>[] returns = method.getParameterTypes();
        StringBuilder methodStr = new StringBuilder(method.getName() + "(");
        if(returns.length > 0) {
            for (int i = 0; i < returns.length; i++) {
                if (i != returns.length - 1) {
                    methodStr.append(returns[i].getSimpleName() + ", ");
                } else {
                    methodStr.append(returns[i].getSimpleName());
                }
            }
        }
        methodStr.append(")");
        return methodStr.toString();
    }

    private static void createObject() throws IllegalAccessException {
        System.out.println("Let’s create an object.");
        try {
            obj = Class.forName(aClass.getName()).newInstance();
        } catch (ClassNotFoundException | InstantiationException | NullPointerException exception) {
            System.out.println("Class doesn't exist");
            System.exit(-1);
        }
        for (Field field : declaredFields) {
            System.out.println(field.getName() + ":");
            System.out.print("\t");
            if(!scanner.hasNext()) {
                System.err.println("Illegal argument!");
                System.exit(-1);
            }
            field.setAccessible(true);
            setVariable(field);
        }
        System.out.println("Object created: " + obj);
        System.out.println("----------------------------");
    }
    private static void setVariable(Field field) throws IllegalAccessException {
        switch (field.getType().getSimpleName()) {
            case "Integer", "int" -> {
                Integer variable = scanner.nextInt();
                scanner.nextLine();
                field.set(obj, variable);
                break;
            }
            case "String" -> {
                String variable = scanner.nextLine();
                field.set(obj, variable);
                break;
            }
            case "Double", "double" -> {
                Double variable = scanner.nextDouble();
                scanner.nextLine();
                field.set(obj, variable);
                break;
            }
            case "Long", "long" -> {
                Long variable = scanner.nextLong();
                scanner.nextLine();
                field.set(obj, variable);
                break;
            }
            case "Float", "float" -> {
                Float variable = scanner.nextFloat();
                scanner.nextLine();
                field.set(obj, variable);
                break;
            }
            case "Boolean", "boolean" -> {
                Boolean variable = scanner.nextBoolean();
                scanner.nextLine();
                field.set(obj, variable);
                break;
            }
        }
    }
    private static void changeVariable() throws IllegalAccessException {
        System.out.println("Enter name of the field for changing:");
        System.out.print("-> ");
        if(!scanner.hasNext()) {
            System.err.println("Illegal argument!");
            System.exit(-1);
        }
        String variable = scanner.nextLine();
        for(Field field : declaredFields) {
            if(field.getName().equals(variable)) {
                System.out.println("Enter String value:");
                System.out.print("-> ");
                field.setAccessible(true);
                setVariable(field);
                System.out.println("Object updated: " + obj);
                System.out.println("----------------------------");
            }
        }
    }
    private static void callMethod() throws IllegalAccessException, InvocationTargetException {
        System.out.println("Enter name of the method for call:");
        System.out.print("-> ");
        if(!scanner.hasNext()) {
            System.err.println("Illegal argument!");
            System.exit(-1);
        }
        String variable = scanner.nextLine();
        for(Method method : declaredMethods) {
            if(getMethodStr(method).equals(variable)) {
                method.setAccessible(true);
                executeMethod(method);
            }
        }
    }
    private static void executeMethod(Method method) throws IllegalAccessException, InvocationTargetException {
        ArrayList<Object> objects = new ArrayList<>();
        for (Class<?> param : method.getParameterTypes()) {
            switch (param.getSimpleName()) {
                case "Integer", "int" -> {
                    printLesson(Integer.class);
                    int variable = scanner.nextInt();
                    objects.add(variable);
                    break;
                }
                case "String" -> {
                    printLesson(String.class);
                    String variable = scanner.nextLine();
                    objects.add(variable);
                    break;
                }
                case "Double", "double" -> {
                    printLesson(Double.class);
                    double variable = scanner.nextDouble();
                    objects.add(variable);
                    break;
                }
                case "Boolean", "boolean" -> {
                    printLesson(Boolean.class);
                    boolean variable = scanner.nextBoolean();
                    objects.add(variable);
                    break;
                }
                case "Float", "float" -> {
                    printLesson(Float.class);
                    float variable = scanner.nextFloat();
                    objects.add(variable);
                    break;
                }
                case "Long", "long" -> {
                    printLesson(Long.class);
                    long variable = scanner.nextLong();
                    objects.add(variable);
                    break;
                }
            }
        }
        try {
            Object[] arguments = objects.toArray(new Object[0]);
            System.out.println("Method returned:");
            if(method.getReturnType().getSimpleName().equals("void")) {
                method.invoke(obj, arguments);
            } else {
                System.out.println(method.invoke(obj, arguments));
            }
        } catch (NullPointerException exception) {
            System.out.println("Class doesn't exist");
            System.exit(-1);
        }
    }
    private static void printLesson(Class<?> variable) {
        System.out.println("Enter " + variable.getSimpleName() + " value:");
        System.out.print("-> ");
    }
}