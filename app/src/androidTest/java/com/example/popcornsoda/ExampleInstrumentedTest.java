package com.example.popcornsoda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.popcornsoda.BdPopcorn.BdPopcornOpenHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    //todo: apagar base de dados antes de fazer os testes

    @Test
    public void criaBdLivros() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        BdPopcornOpenHelper openHelper = new BdPopcornOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }
}