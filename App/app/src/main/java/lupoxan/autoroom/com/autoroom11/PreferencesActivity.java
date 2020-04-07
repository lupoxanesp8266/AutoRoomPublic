package lupoxan.autoroom.com.autoroom11;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import androidx.annotation.Nullable;

/**
 * @author lupo.xan
 * @since 10/11/2019
 * @version 0.0.1
 */
public class PreferencesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
}
