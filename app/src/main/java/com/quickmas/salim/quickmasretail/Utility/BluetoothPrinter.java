package com.quickmas.salim.quickmasretail.Utility;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Forhad on 09/03/2018.
 */

public class BluetoothPrinter {
    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    Context mContext;
    View ac;
    public BluetoothPrinter(Context mContext,View ac)
    {
        this.mContext = mContext;
        this.ac = ac;
    }

    public void findBT(final String data)
    {
        findBT(data,null);
    }
    public void findBT(String dataP,final Bitmap bitmap) {

        DBInitialization db = new DBInitialization(mContext,null,null,1);
        User cSystemInfo = new User();
        //cSystemInfo.select(db,"1=1");
        //final String data=dataP+"\n\nPrint By: "+cSystemInfo.getUser_full_name()+"\nDate & Time: "+DateTimeCalculation.getCurrentDateTime();
        final String data=dataP;
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                System.out.println("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //mContext.startActivityForResult(enableBluetooth, 0);
                mContext.startActivity(enableBluetooth);
            }

            final List<BluetoothDevice> pairedDevices = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());

            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.pop_up_printer_selection, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView popup_printer_select_txt = (TextView) popupView.findViewById(R.id.popup_printer_select_txt);
            Button popup_printer_select_submit = (Button) popupView.findViewById(R.id.popup_printer_select_submit);
            LinearLayout  radio_button_data_holder= (LinearLayout) popupView.findViewById(R.id.radio_button_data_holder);
            ImageView  cross_img= (ImageView) popupView.findViewById(R.id.cross_img);

            final RadioGroup rg = new RadioGroup(mContext);
            rg.setOrientation(RadioGroup.VERTICAL);

            int btid=0;
            for (BluetoothDevice device : pairedDevices)
            {
                RadioButton rb  = new RadioButton(mContext);
                rb.setText(device.getName());
                rb.setId(btid);
                rg.addView(rb);
                btid++;
            }

            cross_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popup_printer_select_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int radioButtonID = rg.getCheckedRadioButtonId();
                        System.out.println(pairedDevices.get(radioButtonID).getName());
                        mmDevice = pairedDevices.get(radioButtonID);
                        openBT();
                        //printPhoto(bitmap);
                        sendData(data);
                        popupWindow.dismiss();
                    }catch (Exception e){}
                }
            });

            radio_button_data_holder.addView(rg);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(ac, Gravity.CENTER, 0, 0);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void sendData(String msg) {
        try {

            msg += "\n\n\n\n\n";
            mmOutputStream.write(msg.getBytes());
            System.out.println("Data sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // tries to open a connection to the bluetooth printer device
    void openBT(){
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();
            System.out.println("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 * after opening a connection to bluetooth printer device,
 * we have to listen and check if a data were sent to be printed.
 */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "utf-8");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                System.out.println("sent"+data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // close the connection to bluetooth printer.
   public void closeBT(){
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            System.out.println("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    mmOutputStream.write(cc);
                    break;
                case 1:
                    mmOutputStream.write(bb);
                    break;
                case 2:
                    mmOutputStream.write(bb2);
                    break;
                case 3:
                    mmOutputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            mmOutputStream.write(msg.getBytes());
            mmOutputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(Bitmap bmp) {
        try {

           // Bitmap bmp = BitmapFactory.decodeResource(getResources(),img);
            if(bmp!=null){
                byte[] command = PrinterCommands.decodeBitmap(bmp);
                DebugHelper.print(command);
                mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                //printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(PrinterCommands.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            mmOutputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resetPrint() {
        try{
            mmOutputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            mmOutputStream.write(PrinterCommands.FS_FONT_ALIGN);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            mmOutputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            mmOutputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            mmOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            mmOutputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }
}
