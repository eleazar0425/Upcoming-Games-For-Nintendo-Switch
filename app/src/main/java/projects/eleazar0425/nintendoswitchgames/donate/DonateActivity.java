package projects.eleazar0425.nintendoswitchgames.donate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.sufficientlysecure.donations.DonationsFragment;

import projects.eleazar0425.nintendoswitchgames.BuildConfig;
import projects.eleazar0425.nintendoswitchgames.R;

public class DonateActivity extends AppCompatActivity {

    private static final String GOOGLE_PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhhny7LX9kQi1dfTzQMnFj7RRlMtr9bgJGOFerIivQOkrYZnBwPu+a90pRDrjor7fOWXjEUWs/ujS3BKGkfykr7hUXSX6HYn05X+fQ1bdZOzOvdJAyIwcf7tzZ56AuRDFFEBZqN6PCI2Rq/T9nyXXs9WeGifHe7CdV2TCaTfeT2MRN9dm+hj8XVNsv5tWHfDlYqOrrUiqlvTVsBmcjPdH9awn2zwsth7zuQfmUAKmxc7oszE+Nhf73Vfimkd5O0lpfaQfkJZK1KfHFlZcKUtz1i0kETkVyI5q9NHqUFRwLGIavvFgw+f84CSzPKDQMOmSESe7C4IZnGyx7VpWHWA++QIDAQAB";

    private static final String[] GOOGLE_CATALOG = new String[]{"ntpsync.donation.1",
            "ntpsync.donation.3", "ntpsync.donation.5", "ntpsync.donation.8", "ntpsync.donation.10",
            "ntpsync.donation.15"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        DonationsFragment donationsFragment;

        donationsFragment = DonationsFragment.newInstance(BuildConfig.DEBUG, true, GOOGLE_PUBKEY, GOOGLE_CATALOG,
                    getResources().getStringArray(R.array.donation_google_catalog_values), false, null, null,
                    null, false, null, null, false, null);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, donationsFragment, "donationsFragment")
                .commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("donationsFragment");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
