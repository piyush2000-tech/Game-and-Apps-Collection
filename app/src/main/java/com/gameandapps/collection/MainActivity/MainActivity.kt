package com.gameandapps.collection.MainActivity

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast

import com.gameandapps.collection.Activity.GoogleActivity
import com.gameandapps.collection.Fragment.*
import com.gameandapps.collection.MainFragment.MainPageFragment
import com.gameandapps.collection.Splash.TicTacSplash
import com.gameandapps.collection.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.activity_main.*

private const val UPDATE_REQUEST_CODE = 124
class MainActivity : AppCompatActivity() {
    /*private RewardedVideoAd mRewardedVideoAd;
    PublisherInterstitialAd publisherInterstitialAd;
    PublisherAdView mAdView;*/

    private var count = 0

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var bottomNavigationView: BottomNavigationView
    var previousMenuItem: MenuItem? = null
    var bottomNavPreviousMenuItem: MenuItem? = null
    lateinit var fragment: Fragment

    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            /*case R.id.settings:
                settings();
                navigationView.setCheckedItem(R.id.settings);
                return true;

            case R.id.help:
                help();
                navigationView.setCheckedItem(R.id.help);
                return true;

            case R.id.share:
                share();
                navigationView.setCheckedItem(R.id.share);
                return true;

            case R.id.dashboard:
                dashBoard();
                navigationView.setCheckedItem(R.id.dashboard);
                return true;

            case R.id.aboutApp:
                aboutApp();
                navigationView.setCheckedItem(R.id.aboutApp);
                return true;

            case R.id.ticTacGame:
                ticTacActivity();
                return true;

            case R.id.brainGame:
                brainActivity();
                navigationView.setCheckedItem(R.id.brainGame);
                return true;

            case R.id.google:
                google();
                return true;
            case R.id.social:
                socialMedia();
                navigationView.setCheckedItem(R.id.social);
                return true;

            case R.id.online:
                onlineLearning();
                navigationView.setCheckedItem(R.id.online);
                return true;

            case R.id.sites:
                tutorials();
                navigationView.setCheckedItem(R.id.sites);
                return true;*/

            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            else -> return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coordinatorLayout = findViewById(R.id.coordinator)
        drawerLayout = findViewById(R.id.drawerLayoutMain)
        toolbar = findViewById(R.id.toolBar)
        navigationView = findViewById(R.id.navigationView)
        frameLayout = findViewById(R.id.frameLayout)
        bottomNavigationView = findViewById(R.id.bottomView)

        setUpToolbar()
        dashBoard()

        bottomNavigationView.selectedItemId = R.id.navHome

        Firebase.messaging.subscribeToTopic("general")
                .addOnCompleteListener { OnCompleteListener<Void>(){
                    var msg:String = "Successfull..."
                    if(!it.isSuccessful){
                        msg = "Failed"
                    }
                    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
                } }

        AppRate.with(this@MainActivity)
                .setInstallDays(0)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .setShowNeverButton(false)
                .monitor()

        AppRate.showRateDialogIfMeetsConditions(this@MainActivity)

        appUpdateManager.registerListener {
            if(it.installStatus() == InstallStatus.DOWNLOADED){
                showUpdateDownloadedSnackbar()
            }
        }

        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if(it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && it.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                appUpdateManager.startUpdateFlowForResult(it,AppUpdateType.FLEXIBLE,this, UPDATE_REQUEST_CODE)
            }
        }.addOnFailureListener {
            Snackbar.make(drawerLayoutMain,"Update Failed",Snackbar.LENGTH_SHORT)
        }


        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (previousMenuItem != null) {
                previousMenuItem!!.isChecked = false
            }
            menuItem.isChecked = true
            menuItem.isCheckable = true
            previousMenuItem = menuItem

            val item = menuItem.itemId
            if (item == R.id.dashboard) {
                dashBoard()
            } else if (item == R.id.privacy) {
                menuItem.isCheckable = false
                menuItem.isChecked = false
                privacy()
            } else if (item == R.id.help) {
                help()
            } else if (item == R.id.aboutApp) {
                aboutApp()
            } else if (item == R.id.share) {
                share()
            } else if (item == R.id.ticTacGame) {
                menuItem.isCheckable = false
                menuItem.isChecked = false
                ticTacActivity()
            } else if (item == R.id.brainGame) {
                //menuItem.setCheckable(false);
                //menuItem.setChecked(false);
                brainActivity()
            } else if (item == R.id.social) {
                //menuItem.setCheckable(false);
                //menuItem.setChecked(false);
                socialMedia()
            } else if (item == R.id.online) {
                //menuItem.setCheckable(false);
                //menuItem.setChecked(false);
                onlineLearning()
            } else if (item == R.id.sites) {
                //menuItem.setCheckable(false);
                //menuItem.setChecked(false);
                tutorials()
            } else if (item == R.id.google) {
                menuItem.isCheckable = false
                menuItem.isChecked = false
                google()
            } else if (item == R.id.shopping){
                shopping()
            }else if(item == R.id.music_sites){
                music_sites()
            }
            drawerLayout.closeDrawers()
            false
        }

        // for bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener {menuItem ->
            if (bottomNavPreviousMenuItem != null) {
                bottomNavPreviousMenuItem!!.isChecked = false
            }
            menuItem.isChecked = true
            menuItem.isCheckable = true
            bottomNavPreviousMenuItem = menuItem

            val item = menuItem.itemId

            if (item == R.id.navHome){
                dashBoard()
            }else if(item == R.id.faqs){
                help()
            }else if (item == R.id.aboutApp){
                aboutApp()
            }
            false
        }

        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        PublisherAdView adView = new PublisherAdView(this);
        adView.setAdSizes(AdSize.BANNER);
        adView.setAdUnitId("/6499/example/banner");

        mAdView = findViewById(R.id.adView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        publisherInterstitialAd = new PublisherInterstitialAd(this);
        publisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
        publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        publisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Error ADS",""+errorCode);
                publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }
        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();*/

    }

    /*private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("/6499/example/rewarded-video",
                new PublisherAdRequest.Builder().build());
    }*/
    fun ticTacActivity() {
        dashBoard()
        val intent = Intent(this, TicTacSplash::class.java)
        startActivity(intent)
    }

    fun brainActivity() {
        supportActionBar?.setTitle(R.string.app_name)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        fragment = BrainFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.brainGame)
    }

    fun google() {
        dashBoard()
        val intent = Intent(this, GoogleActivity::class.java)
        startActivity(intent)
    }

    fun socialMedia() {
        supportActionBar?.setTitle(R.string.app_name)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3F51B5")))
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        fragment = SocialMediaFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.social)
    }

    fun onlineLearning() {
        supportActionBar?.setTitle(R.string.app_name)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF5722")))
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        fragment = OnlineFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.online)
    }

    fun tutorials() {
        supportActionBar?.setTitle(R.string.app_name)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#EBE91E63")))
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        fragment = TutorialFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.sites)
    }

    fun shopping(){
        supportActionBar?.setTitle(R.string.app_name)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        fragment = ShoppingFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.shopping)
    }

    fun music_sites(){
        supportActionBar?.setTitle(R.string.app_name)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        fragment = MusicFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.shopping)
    }


    fun dashBoard() {
        supportActionBar?.setTitle(R.string.app_name)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03A9F4")))
        supportActionBar?.setBackgroundDrawable(ColorDrawable(R.drawable.background_color))
        //bottomNavigationView.selectedItemId = R.id.navHome
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3F51B5")))
        fragment = MainPageFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        navigationView.setCheckedItem(R.id.dashboard)
    }

    fun privacy() {
        //getSupportActionBar().show();
        /*supportActionBar?.setTitle("Settings")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3F51B5")))
        fragment = SettingsFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()*/
        val privacyUrl: Uri = Uri.parse("https://piyushguptadevelopers.blogspot.com/2020/11/privacy-policy-piyush-gupta-built-game.html")
        val intent = Intent(Intent.ACTION_VIEW,privacyUrl)
        startActivity(intent)

        /*dashBoard()
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        startActivity(intent)*/
    }

    fun help() {
        //getSupportActionBar().show();
        supportActionBar?.setTitle("FAQ's")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3F51B5")))
        //bottomNavigationView.selectedItemId = R.id.help
        fragment = HelpFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }

    fun aboutApp() {
        //getSupportActionBar().show();
        supportActionBar?.setTitle("About App")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3F51B5")))
        //bottomNavigationView.selectedItemId = R.id.aboutApp
        fragment = AboutAppFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }



    fun share() {
        //val api = applicationContext.applicationInfo
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val shareBody = "Download this Application now:-https://play.google.com/store/apps/details?id=com.gameandapps.collection&hl=en_IN&hl=en"
        val sharesub = "Game and Apps Collection"
        intent.putExtra(Intent.EXTRA_SUBJECT, sharesub)
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(intent, "ShareVia"))
    }


    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    /*public void Brain(View view){
       if(publisherInterstitialAd.isLoaded()){
           publisherInterstitialAd.show();
       }else{
           Toast.makeText(this,"Ad Not Loaded",Toast.LENGTH_SHORT).show();
       }
       Intent intent = new Intent(MainActivity.this,BrainActivity.class);
       startActivity(intent);
    }

    public void ticTac(View view){
        try {
            if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
            }else{
                Toast.makeText(this,"Ad is Not Loaded!",Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(MainActivity.this,TicTacSplash.class);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            Log.i("ERROR is",""+e);
        }
    }
    public void youTube(View view){
        try{
            if(mRewardedVideoAd.isLoaded()){
                mRewardedVideoAd.show();
            }
        }catch(Exception e){
            Toast.makeText(this,"Video Ad is not Loaded",Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(),YouTubeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }*/

    override fun onResume() {
        count=0
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                showUpdateDownloadedSnackbar ()
            }
        }
    }

    private fun showUpdateDownloadedSnackbar(){
        Snackbar.make(drawerLayoutMain,"Update Downloaded!",Snackbar.LENGTH_INDEFINITE)
                .setAction("Install"){
                    appUpdateManager.completeUpdate()
                }
                .show()
    }

    override fun onBackPressed() {
        /*val fragmentManager = supportFragmentManager
        val fr = fragmentManager.findFragmentById(R.id.dashboard)
        val frg = fragmentManager.findFragmentById(R.id.frameLayout)
        if (frg != fr) {
            dashBoard()
        } else {
            super.onBackPressed()
        }*/
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag){
            //!is MainPageFragment -> dashBoard()
            !is MainPageFragment -> {
                dashBoard()
            }


            else -> {
                if (count == 1) {
                    finishAffinity()
                } else {
                    Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT)
                            .show()
                }
                count++
            }
        }
    }
}
