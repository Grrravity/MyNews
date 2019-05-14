package com.error.grrravity.mynews;

import android.widget.CheckBox;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class SearchTest {

    @Test
    public void checkBox () {
        CheckBox Arts = mock(CheckBox.class);
        CheckBox Business = mock(CheckBox.class);
        CheckBox Food = mock(CheckBox.class);
        CheckBox Politics = mock(CheckBox.class);
        CheckBox Science = mock(CheckBox.class);
        CheckBox Sports = mock(CheckBox.class);
        CheckBox Technology = mock(CheckBox.class);

        Mockito.when(Arts.isChecked()).thenReturn(Boolean.valueOf("Arts"));
        Mockito.when(Business.isChecked()).thenReturn(Boolean.valueOf("Business"));
        Mockito.when(Food.isChecked()).thenReturn(Boolean.valueOf("Food"));
        Mockito.when(Politics.isChecked()).thenReturn(Boolean.valueOf("Politics"));
        Mockito.when(Science.isChecked()).thenReturn(Boolean.valueOf("Science"));
        Mockito.when(Sports.isChecked()).thenReturn(Boolean.valueOf("Sports"));
        Mockito.when(Technology.isChecked()).thenReturn(Boolean.valueOf("Technology"));
    }
}
