package com.example.power.fixthefault;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Logic.Session;
import Persistence.Persistencia;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Averias_Principal_Fragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager;
    Persistencia persistencia;
    FloatingActionButton fab;
    int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        persistencia = Persistencia.getInstance();
        persistencia.creaAuthparaUsers(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        state=0;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state==0)
                fragmentManager.beginTransaction().replace(R.id.contenedor,new Averia_Nueva_Fragment()).addToBackStack("blankfragment").commit();
                else
                    fragmentManager.beginTransaction().replace(R.id.contenedor,new Usuario_Registrar_Fragment()).addToBackStack("blankfragment").commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView nombreUsuario = navigationView.getHeaderView(0).findViewById(R.id.topMenu_nombreUsuario);
        Session sesion = Session.getInstance();
        nombreUsuario.setText(sesion.getUser().getNombre());
        fragmentManager.beginTransaction().replace(R.id.contenedor,new Averias_Principal_Fragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
               // menu.findItem(R.id.nav_camera).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_principal) {
            state=0;
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Averias_Principal_Fragment()).commit();
        } else if (id == R.id.nav_usuarios) {
            state=1;
            fragmentManager.beginTransaction().replace(R.id.contenedor,new UsersFragment()).commit();
        } else if (id == R.id.nav_recents) {
            state=0;
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Averias_SinPrioridad_Fragment()).commit();

        } else if (id == R.id.nav_cerrarSesion) {
            new AlertDialog.Builder(this)
                    .setMessage("¿Está seguro de que desea cerrar sesión?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Session sesion = Session.getInstance();
                            sesion.cerrarSesion();
                            Intent intentNueva=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intentNueva);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
public void hideFab(){fab.hide();}
public void showFab(){fab.show();}

}
