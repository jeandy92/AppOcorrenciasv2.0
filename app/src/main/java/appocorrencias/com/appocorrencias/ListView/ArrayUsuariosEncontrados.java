package appocorrencias.com.appocorrencias.ListView;

import java.util.ArrayList;

/**
 * Created by Jeanderson on 28/05/2017.
 */

public class ArrayUsuariosEncontrados {

    public static ArrayList<DadosUsuarios> getListaUsuariosEncontrados () {
        {
            ArrayList<DadosUsuarios> usu = new ArrayList<>();
            usu.add(new DadosUsuarios("Jeanderson", "5565468846","12564", "22/03/1984", "Amora", "45465464647",
                    "065478778", "Jardim Silveira","Barueri", "SP", "23", "Jo@h.com.br", "cs2"));
            usu.add(new DadosUsuarios("Felipe","5565468846","12564", "22/03/1984", "Amora", "45465464647",
                    "065478778", "Jardim Silveira","Osasco", "SP", "23", "Jo@h.com.br", "cs2"));
            usu.add(new DadosUsuarios("Elenaldo","5565468846","12564", "22/03/1984", "Amora", "45465464647",
                    "065478778", "Jardim Silveira","Carapicuiba", "SP", "23", "Jo@h.com.br", "cs2"));
            usu.add(new DadosUsuarios("Robson","5565468846","12564", "22/03/1984", "Amora", "45465464647",
                    "065478778", "Jardim Silveira","São Jõa", "SP", "23", "Jo@h.com.br", "cs2"));
            usu.add(new DadosUsuarios("Rubem","5565468846","12564", "22/03/1984", "Amora", "45465464647",
                    "065478778", "Jardim Silveira","Santos", "SP", "23", "Jo@h.com.br", "cs2"));

            return usu;

        }

    }
}
