package co.edu.eci.ieti.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.network.TasksAdapter;
import co.edu.eci.ieti.android.network.viewModel.TasksViewModel;
import co.edu.eci.ieti.android.storage.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity
    extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
{

    private Storage storage;
    private RecyclerView recyclerView;
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        storage = new Storage( this );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG ).setAction( "Action",
                                                                                                       null ).show();
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open,
                                       R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        tasksAdapter = new TasksAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        configureRecyclerView();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if ( drawer.isDrawerOpen( GravityCompat.START ) )
        {
            drawer.closeDrawer( GravityCompat.START );
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings )
        {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    @Override
    public boolean onNavigationItemSelected( MenuItem item )
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if ( id == R.id.nav_logout )
        {
            storage.clear();
            startActivity( new Intent( this, LoginActivity.class ) );
            finish();
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void configureRecyclerView()
    {
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setAdapter( tasksAdapter );
        recyclerView.setLayoutManager(layoutManager);
        TasksViewModel tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        tasksViewModel.getTasks(new Storage(this).getToken()).observe(this, tasks -> {
            runOnUiThread(() -> {
                tasksAdapter.updateTasks(tasks);
            });
        });
    }
}
