package com.denny.pickerlib;


import android.test.InstrumentationTestCase;


import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Cai on 2016/10/18.
 */
public class PickerTest extends InstrumentationTestCase {

    private Picker testPicker;

    public void setUp() throws Exception {
        super.setUp();
        testPicker = new Picker();
        testPicker.getIntent().putExtra(Picker.Extra.OUTPUT,new String[]{"pick:image1","pick:image2"});
    }

    public void tearDown() throws Exception {

    }

    public void testSingle() throws Exception {
        testPicker.single();

        int choiceNumber = testPicker.getIntent().getIntExtra(Picker.Extra.CHOICE_NUMBER,1);

        assertThat(choiceNumber).isEqualTo(1);
    }

    public void testMulti() throws Exception {
        testPicker.multi(9);

        int choiceNumber = testPicker.getIntent().getIntExtra(Picker.Extra.CHOICE_NUMBER,1);

        assertThat(choiceNumber).isEqualTo(9);
    }


    public void testGetOutput() throws Exception {
        String[] out = Picker.getOutput(testPicker.getIntent());

        assertThat(out).isEqualTo(new String[]{"pick:image1","pick:image2"});
    }

}