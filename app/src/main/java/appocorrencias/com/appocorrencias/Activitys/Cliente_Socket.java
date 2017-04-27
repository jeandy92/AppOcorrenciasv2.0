package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import appocorrencias.com.appocorrencias.R;

public class Cliente_Socket extends AppCompatActivity {

    private Button btnConexao, btnEnviarImagem, btnEnviaImagemServidor;
    private TextView txvRetornoSocket;

    private int CodeFoto = 123;
    private String host = "192.168.0.17";
    private int porta = 2222;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Chama o layout de clientes
        setContentView(R.layout.activity_cliente_socket);

        //Declaração dos botões
        btnConexao = (Button) findViewById(R.id.btnConexao);
        btnEnviarImagem = (Button) findViewById(R.id.btnEnviarImagem);
        btnEnviaImagemServidor = (Button) findViewById(R.id.btnEnviaImagemServidor);


        // Declaração dos TextViews
        txvRetornoSocket = (TextView) findViewById(R.id.txvRetornoSocket);
    }






    //teste para envio de texto  servidor java e erlang =
    public void testar_conexao_servidor(View v) {
        if (v == btnConexao) {
            validar_conexao();
        }
    }


//Metodo para testar a conexao com o servidor erlang
    private void validar_conexao() {
        try {
            Socket socket = null;

            ObjectOutputStream canalSaida = null;
            ObjectInputStream canalEntrada = null;

            socket = new Socket(host,porta);

            canalSaida = new ObjectOutputStream(socket.getOutputStream());
            canalSaida.writeObject("Teste");


            canalEntrada = new ObjectInputStream(socket.getInputStream());
            Object object = canalEntrada.readObject();
            if ((object != null) && (object instanceof String)) {
                txvRetornoSocket.setText(object.toString());
            }


        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
    }


    public void selecionarImagemGaleria(){
        Intent intent =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode ==RESULT_OK &&requestCode ==1){
            Uri selectedImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage,filePath,null,null,null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath =c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(String.valueOf(filePath)));

        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_adm);
        this.startActivity(new Intent(this,Adm.class));
    }
}






//    try{
//        Socket C_SOCKET = new Socket(host,porta);
//
//        File myFile = new File("C://Users//Jeanderson//Desktop//imagem.png");
//
//        byte[] mybytearray = new byte[(int) myFile.length()];
//
//        String teste = "Imagem";
//
//        OutputStream outputStream = C_SOCKET.getOutputStream();
//        InputStream inputStream = C_SOCKET.getInputStream();
//
//        outputStream.write(teste.getBytes());
//        outputStream.flush();
//
//        int Rec = inputStream.read();
//        System.out.println(Rec);
//
//        FileInputStream fis = new FileInputStream(myFile);
//        BufferedInputStream bis = new BufferedInputStream(fis);
//
//        bis.read(mybytearray, 0, mybytearray.length);
//        OutputStream os = C_SOCKET.getOutputStream();
//
//
//
//        System.out.println("Enviando...");
//
//
//        os.write(mybytearray, 0, mybytearray.length);
//        os.flush();
//
//        System.out.println("Enviado");
//
//        C_SOCKET.close();
//
//
//
//        }catch (Exception e){
//
//        }









//
//    public void abrirCamera(){
//
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(i, CodeFoto);
//
//    }
//
//





//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CodeFoto) {
//            if(resultCode == RESULT_OK){
//                Toast.makeText(getApplicationContext(), "Image saved to:\n" + data.getData(), Toast.LENGTH_SHORT).show();
//
//                }
//            else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Toast.makeText(getApplicationContext(), "A caprtura falhou", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//    }
//
//
//    public void enviar_imagem(View v) {
//        if (v == btnEnviarImagem) {
//            abrirCamera();
//        }
//    }


