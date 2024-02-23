package com.example.myhello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class UserActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView TvSpMess;
    Button BtCont;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.user_activity);
        TvSpMess = findViewById (R.id.TvSpMess);
        BtCont = findViewById (R.id.BtCont);
        BtCont.setOnClickListener (this);
        if (!FileExists ("Students.db"))
        {
            TvSpMess.setText ("Could not find DB. First Run?\nCopy from Assets");
            CopyDB ("Students.db");
        }
        else
        {
            TvSpMess.setText ("DB in position... Let us continue....");
            BtCont.setEnabled (true);
        }
    }

    boolean FileExists (String Fn)
    {
        File file = new File (getApplicationContext (). getFilesDir(), Fn);
        return file.exists ();
    }

    @SuppressLint("SetTextI18n")
    void CopyDB (String Fn)
    {
        AssetManager AssetMan = getAssets();
        InputStream Inp;
        OutputStream Outp;
        byte[] Buffer;
        int BR;
        try
        {
            Inp = AssetMan.open (Fn);
            File OutFile = new File (getApplicationContext (). getFilesDir(), Fn);
            Outp = Files.newOutputStream(OutFile.toPath());
            Buffer = new byte[1024];
            while ((BR = Inp.read (Buffer)) != -1)
                Outp.write (Buffer, 0, BR);
            Inp.close();
            Outp.flush();
            Outp.close();
            BtCont.setEnabled (true);
            TvSpMess.setText ("Database now in: " + OutFile.getAbsolutePath ());

        }
        catch(IOException e)
        {
            System.out.println ("*** IOException: " + e.getMessage ());
            Toast tst = Toast.makeText (getApplicationContext (), "IO Error during DB copy...", Toast.LENGTH_LONG);
            tst.show ();
        }
    }

    @Override
    public void onClick (View v)
    {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity (intent);
    }
}


