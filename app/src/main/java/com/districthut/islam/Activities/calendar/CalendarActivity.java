package com.districthut.islam.Activities.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.eltohamy.materialhijricalendarview.CalendarDay;
import com.github.eltohamy.materialhijricalendarview.DayViewDecorator;
import com.github.eltohamy.materialhijricalendarview.DayViewFacade;
import com.github.eltohamy.materialhijricalendarview.format.MonthArrayTitleFormatter;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.snackbar.Snackbar;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.ActivityCalendarBinding;
import com.mirfatif.noorulhuda.databinding.ReminderDialogBinding;

import net.alhazmy13.hijridatepicker.date.hijri.HijriDatePickerDialog;
import net.alhazmy13.hijridatepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;



public class CalendarActivity extends AppCompatActivity {

    ActivityCalendarBinding binding;
    ArrayList<Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isDarkMode =  defaultPreferences.getBoolean("dark_mode",false);
//        if (isDarkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            setTheme(R.style.MaterialTheme_NoActionBarDark);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            setTheme(R.style.MaterialTheme_NoActionBar);
//        }
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.calendarView.setTopbarVisible(true);
        binding.calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));

        binding.calendarView.addDecorator(new DayViewDecorator() {
            private CalendarDay date;

            @Override
            public boolean shouldDecorate(CalendarDay day) {
                date = CalendarDay.today();
                return date != null && day.equals(date);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new StyleSpan(Typeface.BOLD));
                view.addSpan(new RelativeSizeSpan(1.4f));
            }
        });




        events = new ArrayList<>();

        loadDates();
        DatesAdapter adapter = new DatesAdapter(this,events);
        binding.events.setAdapter(adapter);


    }

    void loadDates() {
        UmmalquraCalendar now = new UmmalquraCalendar();
        // ------- Al-Hijira ------- //
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.MONTH, 0);
        addEvent("Al-Hijira", now);
        // ------- Lailat al Miraj ------- //
        now.set(Calendar.DAY_OF_MONTH, 27);
        now.set(Calendar.MONTH, UmmalquraCalendar.RAJAB);
        addEvent("Lailat-al-Miraj", now);
        // ------- Laylat al Baraat ------- //
        now.set(Calendar.DAY_OF_MONTH, 15);
        now.set(Calendar.MONTH, UmmalquraCalendar.SHAABAN);
        addEvent("Laylat-al-Baraat", now);
        // ------- Ramadan ------- //
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.MONTH, UmmalquraCalendar.RAMADHAN);
        addEvent("Ramadan (start)", now);
        // ------- Eid-Ul-Fitr ------- //
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.MONTH, UmmalquraCalendar.SHAWWAL);
        addEvent("Eid-ul-Fitr", now);
        // ------- Waqf Al Arafa ------- //
        now.set(Calendar.DAY_OF_MONTH, 9);
        now.set(Calendar.MONTH, UmmalquraCalendar.THUL_HIJJAH);
        addEvent("Al Arafa - Hajj", now);
        // ------- Eid ul Adha ------- //
        now.set(Calendar.DAY_OF_MONTH, 10);
        now.set(Calendar.MONTH, UmmalquraCalendar.THUL_HIJJAH);
        addEvent("Eid-ul-Adha", now);
    }

    void addEvent(String event, UmmalquraCalendar now) {
        Calendar cal = new UmmalquraCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        events.add(new Event(
                event,
                String.format(
                        "%d %s %d AH",
                        now.get(Calendar.DAY_OF_MONTH),
                        now.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
                        now.get(Calendar.YEAR)
                ),
                dateFormat.format(cal.getTime())));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}