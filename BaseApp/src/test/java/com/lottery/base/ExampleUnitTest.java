package com.lottery.base;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        List<ModeBean> list = new ArrayList<>();

        ModeBean modeBean = new ModeBean();
        modeBean.PayName = "A";
        modeBean.SortNum = "2";

        ModeBean modeBean1 = new ModeBean();
        modeBean1.PayName = "A";
        modeBean1.SortNum = "1";

        ModeBean modeBean2 = new ModeBean();
        modeBean2.PayName = "A";
        modeBean2.SortNum = "7";

        ModeBean modeBean3 = new ModeBean();
        modeBean3.PayName = "A";
        modeBean3.SortNum = "4";

        list.add(modeBean);
        list.add(modeBean1);
        list.add(modeBean2);
        list.add(modeBean3);

        Collections.sort(list);

        int i = 0;
    }

    public class ModeBean implements Comparable {
        public String PayName;
        public String SortNum;


        @Override
        public int compareTo(@NonNull Object o) {
            ModeBean mode = (ModeBean) o;
            return SortNum.compareTo(mode.SortNum);
        }
    }
}