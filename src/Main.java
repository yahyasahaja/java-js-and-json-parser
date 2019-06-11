import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

class JSONParser {
    int stack = 0;
    int maxStack = -1;

    public static JSONParser getInstance() {
        return new JSONParser();
    }

    public String parseObject(Object obj, Boolean usingNull)  {
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
                if (maxStack != -1 && stack <= maxStack) {
                    if (value instanceof ArrayList) {
                        result += "\"" + name + "\": " + parseArray((ArrayList) value, usingNull) + ", ";
                    } else {
                        result += "\"" + name + "\": " + parseObject(value, usingNull) + ", ";
                    }
                    stack++;
                }
            }


//            System.out.printf("%s: %s%n", name, value);
        }

        if (result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }

        return result + " }";
    }

    public String parseObject(Object obj) {
        return parseObject(obj, false);
    }

    public String parseArrayResult;
    public String parseArray(ArrayList<Object> objs, Boolean usingNull) {
        parseArrayResult = "[ ";
        objs.forEach((Object obj) -> parseArrayResult += parseObject(obj, usingNull) + ", ");
        if (parseArrayResult.length() > 2) {
            parseArrayResult = parseArrayResult.substring(0, parseArrayResult.length() - 2);
        }
        return parseArrayResult + " ]";
    }

    public String parseArray(ArrayList<Object> objs) {
        return parseArray(objs, false);
    }
}

class JSParser {
    int stack = -1;
    int maxStack = -1;

    public static JSParser getInstance() {
        return new JSParser();
    }

    public String parseObject(Object obj, Boolean usingNull)  {
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
                if (stack != -1 && stack <= maxStack) {
                    if (value instanceof ArrayList) {
                        result += name + ": " + parseArray((ArrayList) value, usingNull) + ", ";
                    } else {
                        result += name + ": " + parseObject(value, usingNull) + ", ";
                    }
                }
            }


//            System.out.printf("%s: %s%n", name, value);
        }

        if (result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }

        return result + " }";
    }

    public String parseObject(Object obj) {
        return parseObject(obj, false);
    }

    public String parseArrayResult;
    public String parseArray(ArrayList<Object> objs, Boolean usingNull) {
        parseArrayResult = "[ ";
        objs.forEach((Object obj) -> parseArrayResult += parseObject(obj, usingNull) + ", ");
        if (parseArrayResult.length() > 2) {
            parseArrayResult = parseArrayResult.substring(0, parseArrayResult.length() - 2);
        }
        return parseArrayResult + " ]";
    }

    public String parseArray(ArrayList<Object> objs) {
        return parseArray(objs, false);
    }
}

class Name {
    private String firstName = "Tzuyu";
    private String lastName = "Yahya";
    private Manusia manusia;
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
        String res = JSParser.getInstance().parseObject(new Manusia());
        System.out.println(res);

        ArrayList<Object> arr = new ArrayList<>();
        arr.add(new Manusia());
        arr.add(new Manusia());
        System.out.println(JSParser.getInstance().parseArray(arr));
        System.out.println(JSParser.getInstance().parseArray(arr, true));


        System.out.println("\nJSON Parser");
        //JSON
        System.out.println(JSONParser.getInstance().parseObject(new Manusia()));

        arr = new ArrayList<>();
        arr.add(new Manusia());
        arr.add(new Manusia());
        System.out.println(JSONParser.getInstance().parseArray(arr));
        System.out.println(JSONParser.getInstance().parseArray(arr, true));

        System.out.println("\nArray");
        //JSON
        System.out.println(JSParser.getInstance().parseObject(new Manusia(true)));
    }
}
