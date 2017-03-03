package IR;

/**
 * Created by nitzanh on 14/01/2017.
 */
public class TempFactovider {

    static int temp_no = 1;
    static int label_no = 1;

    public static String newTemp() {
        return "Temp_" + temp_no++;
    }

    public static String newLabel(String suffix) {
        return "Label_" + label_no++ + ((suffix != null && !suffix.isEmpty()) ? ("_" + suffix) : "");
    }

}
