import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

class JSONParser {
    public static String parseObject(Object obj, Boolean usingNull)  {
        HashMap<String, Object> res = new HashMap<>();
        String result = "{ ";
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {

            }

            res.put(name, value);

            if (usingNull) if (value == null) {
                result += "\"" + name + "\": " + "null, ";
            }

            if (value instanceof Integer) {
                result += "\"" + name + "\": " + value + ", ";
            } else if (value instanceof String) {
                result += "\"" + name + "\": \"" + value + "\", ";
            } else if (value != null) {
                if (value instanceof ArrayList) {
                    result += "\"" + name + "\": " + parseArray((ArrayList) value, usingNull) + ", ";
                } else {
                    result += "\"" + name + "\": " + parseObject(value, usingNull) + ", ";
                }
            }


//            System.out.printf("%s: %s%n", name, value);
        }

        if (result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }

        return result + " }";
    }

    public static String parseObject(Object obj) {
        return parseObject(obj, false);
    }

    public static String parseArrayResult;
    public static String parseArray(ArrayList<Object> objs, Boolean usingNull) {
        parseArrayResult = "[ ";
        objs.forEach((Object obj) -> parseArrayResult += parseObject(obj, usingNull) + ", ");
        if (parseArrayResult.length() > 2) {
            parseArrayResult = parseArrayResult.substring(0, parseArrayResult.length() - 2);
        }
        return parseArrayResult + " ]";
    }

    public static String parseArray(ArrayList<Object> objs) {
        return parseArray(objs, false);
    }
}

class JSParser {
    public static String parseObject(Object obj, Boolean usingNull)  {
        HashMap<String, Object> res = new HashMap<>();
        String result = "{ ";
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {

            }

            res.put(name, value);

            if (usingNull) if (value == null) {
                result += name + ": " + "null, ";
            }

            if (value instanceof Integer) {
                result += name + ": " + value + ", ";
            } else if (value instanceof String) {
                result += name + ": \"" + value + "\", ";
            } else if (value != null) {
                if (value instanceof ArrayList) {
                    result += name + ": " + parseArray((ArrayList) value, usingNull) + ", ";
                } else {
                    result += name + ": " + parseObject(value, usingNull) + ", ";
                }
            }


//            System.out.printf("%s: %s%n", name, value);
        }

        if (result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }

        return result + " }";
    }

    public static String parseObject(Object obj) {
        return parseObject(obj, false);
    }

    public static String parseArrayResult;
    public static String parseArray(ArrayList<Object> objs, Boolean usingNull) {
        parseArrayResult = "[ ";
        objs.forEach((Object obj) -> parseArrayResult += parseObject(obj, usingNull) + ", ");
        if (parseArrayResult.length() > 2) {
            parseArrayResult = parseArrayResult.substring(0, parseArrayResult.length() - 2);
        }
        return parseArrayResult + " ]";
    }

    public static String parseArray(ArrayList<Object> objs) {
        return parseArray(objs, false);
    }
}

class Name {
    private String firstName = "Tzuyu";
    private String lastName = "Yahya";
}

class Manusia {
    private String mata = "mata aaa hehe";
    private String telinga;
    private int jumlahMata = 2;
    private Name name = new Name();
    private ArrayList<Manusia> children;

    public Manusia(Boolean hasChildren) {
        children = new ArrayList<>();
        children.add(new Manusia());
        children.add(new Manusia());
    }

    public Manusia() {

    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("JS Parser");
        String res = JSParser.parseObject(new Manusia());
        System.out.println(res);

        ArrayList<Object> arr = new ArrayList<>();
        arr.add(new Manusia());
        arr.add(new Manusia());
        System.out.println(JSParser.parseArray(arr));
        System.out.println(JSParser.parseArray(arr, true));


        System.out.println("\nJSON Parser");
        //JSON
        System.out.println(JSONParser.parseObject(new Manusia()));

        arr = new ArrayList<>();
        arr.add(new Manusia());
        arr.add(new Manusia());
        System.out.println(JSONParser.parseArray(arr));
        System.out.println(JSONParser.parseArray(arr, true));

        System.out.println("\nArray");
        //JSON
        System.out.println(JSParser.parseObject(new Manusia(true)));
    }
}
