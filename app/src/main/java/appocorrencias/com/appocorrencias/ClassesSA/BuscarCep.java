package appocorrencias.com.appocorrencias.ClassesSA;


import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

public class BuscarCep {

    public String getEndereco(String pCep) throws IOException{
        String status = null;
        Document doc;





        try {
            doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + pCep).timeout(3000).get();
        }catch (SocketTimeoutException e ){
            status = "erro";
            return status;

        }catch (HttpStatusException w ){
            status = "erro";
            return status;

        }catch (Exception e){
            status = "erro";
            return status;
        }
        Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
        for(Element urlEndereco : urlPesquisa) {
            return urlEndereco.text();
        }
        return pCep;
    }

    public String getBairro(String pCep) throws IOException{

        try{

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/"+pCep).timeout(120000).get();

            Elements urlPesquisa = doc.select("td:gt(1)");
            for(Element urlBairro : urlPesquisa){
                return urlBairro.text();
            }


        }catch (SocketTimeoutException e ){

        }catch (HttpStatusException w ){

        }
        return pCep;
    }


    public String getCidade(String pCep) throws IOException{

        try{

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/"+pCep).timeout(120000).get();

            Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
            for(Element urlCidade : urlPesquisa){
                return urlCidade.text();
            }


        }catch (SocketTimeoutException e ){

        }catch (HttpStatusException w ){

        }
        return pCep;
    }


    public String getUF(String pCep) throws IOException{

        try{

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/"+pCep).timeout(120000).get();

            Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
            for(Element urlUF : urlPesquisa){
                return urlUF.text();
            }


        }catch (SocketTimeoutException e ){

        }catch (HttpStatusException w ){

        }
        return pCep;
    }

    public String getLatLong(String pCep) throws IOException, ParseException{

        try{

            if(pCep.contains("-")) {
                Document doc = Jsoup
                        .connect("http://maps.googleapis.com/maps/api/geocode/xml?address="
                                + pCep + "&language=pt-BR&sensor=true").timeout(120000).get();

                Elements lat = doc.select("geometry").select("location").select("lat");
                Elements lng = doc.select("geometry").select("location").select("lng");
                for (Element Latitude : lat) {
                    for (Element Longitude : lng) {
                        return Latitude.text() + "," + Longitude.text();
                    }
                }

            }else{
                StringBuilder cepHif = new StringBuilder(pCep);
                cepHif.insert(pCep.length() -3, '-');

                Document doc = Jsoup
                        .connect("http://maps.googleapis.com/maps/api/geocode/xml?address="
                                + pCep + "&language=pt-BR&sensor=true").timeout(120000).get();

                Elements lat = doc.select("geometry").select("location").select("lat");
                Elements lng = doc.select("geometry").select("location").select("lng");
                for (Element Latitude : lat) {
                    for (Element Longitude : lng) {
                        return Latitude.text() + "," + Longitude.text();
                    }
                }
            }


        }catch (SocketTimeoutException e ){

        }catch (HttpStatusException w ){

        }
        return pCep;
    }
}
