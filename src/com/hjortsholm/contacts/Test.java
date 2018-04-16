package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.QueryRow;
import com.hjortsholm.contacts.database.QuerySet;
import com.hjortsholm.contacts.models.Field;
//import javafx.scene.image.Image;
import com.hjortsholm.contacts.util.HexImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
//        Database.configure("contacts.db");
//        Setup setup = new Setup();
//        setup.importSampleData();




        HexImage image = new HexImage(HexImage.getHex());
        ImageView imageView = new ImageView(image.getImage());
    }

    private static void foo(QuerySet queryResults) {
        for (QueryRow row : queryResults) {
            System.out.println(row.toString());
        }
    }

    public static void database(Database db) {
        ArrayList<Boolean> tests = new ArrayList<>();

        tests.add(db.dropTable(Field.class));
//        tests.add(db.createTable(Field.class));
//        tests.add(db.post("INSERT INTO fields (id,contact,type,name,value) VALUES(?,?,?,?,?)", 1,1,1,"foo","bar"));
//        tests.add(db.post("UPDATE fields SET ID = ? WHERE ID = ?", 71,70));
//        tests.add(db.get("SELECT * FROM fields;", System.out::println));

        System.out.println("\n" + Arrays.toString(tests.toArray()));
    }


    @FunctionalInterface
    public interface Me<R, P> {
        R accept(P parameter);
    }

}

