package appocorrencias.com.appocorrencias.Adapters;

/**
 * Created by Jeanderson on 20/04/2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import appocorrencias.com.appocorrencias.Fragments.CadastrarOcorrenciasFragment;
import appocorrencias.com.appocorrencias.Fragments.PerfilFragment;


public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] tituloAbas = {"PERFIL", "OCORRENCIAS REGISTRADAS"};
    Fragment fragment = null;
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {



        switch (position){
            case 0:
                fragment = new PerfilFragment();
                break;
            case 1:
                fragment = new CadastrarOcorrenciasFragment();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}

