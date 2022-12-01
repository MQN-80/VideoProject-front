package Utils;

import android.app.AlertDialog;
import android.content.Context;

public class Dialog {
    Dialog(){}
    public static void showDialog(String title, String text, Context context){

        AlertDialog alertDialog1 = new AlertDialog.Builder(context)
                .setTitle(title)//标题
                .setMessage(text)//内容
                .setPositiveButton("确认",null)
                .create();
        alertDialog1.show();

    }
}
