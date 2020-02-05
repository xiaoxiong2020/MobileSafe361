package com.xiao.mobilesafe361;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.xiao.mobilesafe361.db.dao.BlackNumberDao;
import com.xiao.mobilesafe361.db.BlackNumberOpenHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.xiao.mobilesafe361", appContext.getPackageName());
    }

    public void testCreateDB(){
        BlackNumberOpenHelper blackNumberOpenHelper = new BlackNumberOpenHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());//不会创建数据库
        blackNumberOpenHelper.getWritableDatabase();//才会去创建数据库
    }

    public void testDBAdd(){
        BlackNumberDao blackNumberDao = new BlackNumberDao(InstrumentationRegistry.getInstrumentation().getTargetContext());
        blackNumberDao.add("138001", 1);
    }


}
