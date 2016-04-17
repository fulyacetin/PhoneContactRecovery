package com.fulya.phonecontactrecovery;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView Contact_listView;

    //first-look
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ContactInformation> contactList=new ArrayList<ContactInformation>();

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);

            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(new ContactInformation(name, phoneNumber));
            }
        }
        Contact_listView = (ListView) findViewById(R.id.listView_Contact);
        Contact_listView.setOnItemClickListener(this);
        MyAdapter adapter = new MyAdapter(this, R.layout.listview_item, contactList);
        Contact_listView.setAdapter(adapter);

    }

    //Clicking listview_item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent callActivity = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + ((TextView) view.findViewById(R.id.contactPhoneNumber))
                .getText().toString()));
        startActivity(callActivity);
    }


    //chosing radio buttons
    public void onRadioButtonClicked(View view) {

        int id = view.getId();

        List<ContactInformation> specialList=new ArrayList<>();

        List<ContactInformation> contactList=new ArrayList<ContactInformation>();
        ContentResolver ContentResolver = getContentResolver();
        Cursor cursor = ContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String idx = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor = ContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{idx}, null);

            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(new ContactInformation(name, phoneNumber));
            }
        }
        switch (id) {

            case R.id.radioButtonAvea:
                for(ContactInformation person:contactList){
                    if(person.getContactPhoneNumber().startsWith("+9055")||person.getContactPhoneNumber().startsWith("055")||
                            person.getContactPhoneNumber().startsWith("+9050")||person.getContactPhoneNumber().startsWith("050"))
                        specialList.add(person);
                }
                Contact_listView = (ListView) findViewById(R.id.listView_Contact);
                Contact_listView.setOnItemClickListener(this);
                MyAdapter adapter1 = new MyAdapter(this, R.layout.listview_item, specialList);
                Contact_listView.setAdapter(adapter1);
                break;


            case R.id.radioButtonTurkcell:
                for (ContactInformation person : contactList) {
                    if (person.getContactPhoneNumber().startsWith("+9053")||person.getContactPhoneNumber().startsWith("053"))
                        specialList.add(person);
                }
                Contact_listView = (ListView) findViewById(R.id.listView_Contact);
                Contact_listView.setOnItemClickListener(this);
                MyAdapter adapter = new MyAdapter(this, R.layout.listview_item, specialList);
                Contact_listView.setAdapter(adapter);
                break;

            case R.id.radioButtonVodafone:
                for(ContactInformation person:contactList){
                    if(person.getContactPhoneNumber().startsWith("+9054")||person.getContactPhoneNumber().startsWith("054"))
                        specialList.add(person);
                }
                Contact_listView = (ListView) findViewById(R.id.listView_Contact);
                Contact_listView.setOnItemClickListener(this);
                MyAdapter adapter2 = new MyAdapter(this, R.layout.listview_item, specialList);
                Contact_listView.setAdapter(adapter2);
                break;

            case R.id.radioButtonAll:
                Contact_listView.setOnItemClickListener(this);
                MyAdapter adapter3 = new MyAdapter(this, R.layout.listview_item, contactList);
                Contact_listView.setAdapter(adapter3);
                break;

            default:
                break;
        }
    }

    //backup
    public void backUp(View view) throws FileNotFoundException {

        List<ContactInformation> aList=new ArrayList<ContactInformation>();

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String idx = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{idx}, null);

            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                aList.add(new ContactInformation(name, phoneNumber));
            }
        }
        Contact_listView = (ListView) findViewById(R.id.listView_Contact);
        Contact_listView.setOnItemClickListener(this);
        MyAdapter adapter3 = new MyAdapter(this, R.layout.listview_item, aList);
        Contact_listView.setAdapter(adapter3);

        PrintStream printStream = new PrintStream(openFileOutput("backUp.txt", MODE_PRIVATE));
        for (ContactInformation person :aList ) {
            printStream.println(person.getContactName()+"/t"+person.getContactPhoneNumber());
        }
        Toast.makeText(this,"Succsesful Back-Up!",Toast.LENGTH_LONG).show();
    }

    //recovery
    public void recover(View view) throws FileNotFoundException {

        File file = new File(getFilesDir()+"/backUp.txt");

        if (file.exists()==false)
        Toast.makeText(this,"Back-up file is not found",Toast.LENGTH_LONG).show();

         else {
            Scanner scanner = new Scanner(openFileInput("backUp.txt"));
            while (scanner.hasNextLine()) {
                String personInformation[] = scanner.nextLine().split("/t");
                String person_name = personInformation[0];
                String person_phone = personInformation[1];

                ArrayList<ContentProviderOperation> list = new ArrayList<ContentProviderOperation>();
                list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

                if (person_name != null) {
                    list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, person_name).build());
                }

              if (person_phone != null) {
                    list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, person_phone)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
                }
                try {
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, list);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(this,"Succsesful! You can go to Contacts",Toast.LENGTH_LONG).show();
        }

    }

}



