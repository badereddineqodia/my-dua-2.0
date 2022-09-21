package com.districthut.islam.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.districthut.islam.utils.AppManager;
import com.hugomatilla.audioplayerview.AudioPlayerView;
import com.mirfatif.noorulhuda.R;

public class KalimaActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalima);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this, "Use fingers to Zoom In/Zoom Out the text!", Toast.LENGTH_SHORT).show();
        Typeface urduFont = Typeface.createFromAsset(getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        Typeface arabicFont = Typeface.createFromAsset(getAssets(), "fonts/Al_Mushaf.ttf");
        View view1= findViewById(R.id.kalima_layout_1);
        View view2= findViewById(R.id.kalima_layout_2);
        View view3= findViewById(R.id.kalima_layout_3);
        View view4= findViewById(R.id.kalima_layout_4);
        View view5= findViewById(R.id.kalima_layout_5);
        View view6= findViewById(R.id.kalima_layout_6);

        TextView first_heading_name = view1.findViewById(R.id.kalima_name);
        TextView first_heading_name_meaning = view1.findViewById(R.id.kalima_name_meaning);
        TextView first_arabic = view1.findViewById(R.id.arabic);
        TextView first_transliteration = view1.findViewById(R.id.transliteration);
        TextView first_english = view1.findViewById(R.id.english);
        TextView first_urdu = view1.findViewById(R.id.urdu);
        AudioPlayerView first_player = (AudioPlayerView) view1.findViewById(R.id.player);

        first_player.withUrl("https://www.dropbox.com/s/xg29gybieif5ukv/first.MP3?dl=1");
        first_heading_name.setText("First Kalima");
        first_heading_name_meaning.setText("Tayyab");
        first_arabic.setText("ﻵَ اِﻟٰﻪَ اِﻻَّ اﻟﻠّٰﻪُ ﻣُﺤَﻤَّﺪٌ ﺭَّﺳُﻮْﻝُ اﻟﻠّٰﻪِؕ");
        first_transliteration.setText("Laaa Ilaaha Illa-llaahu Muhammadur-Rasoolu-llaah ");
        first_english.setText("There is none worthy of worship except Allah and Muhammad is the Messenger of Allah.");
        first_urdu.setText("اللّٰه کے سِوا کوئی معبود نہیں، محمّد (ﷺ) اللّٰه کے رسول ہیں۔");
        first_arabic.setTypeface(arabicFont);
        first_urdu.setTypeface(urduFont);

        TextView second_heading_name = view2.findViewById(R.id.kalima_name);
        TextView second_heading_name_meaning = view2.findViewById(R.id.kalima_name_meaning);
        TextView second_arabic = view2.findViewById(R.id.arabic);
        TextView second_transliteration = view2.findViewById(R.id.transliteration);
        TextView second_english = view2.findViewById(R.id.english);
        TextView second_urdu = view2.findViewById(R.id.urdu);
        AudioPlayerView second_player = (AudioPlayerView) view2.findViewById(R.id.player);
        second_player.withUrl("https://www.dropbox.com/s/c98nrqepkki7chw/second.MP3?dl=1");
        second_heading_name.setText("Second Kalima");
        second_heading_name_meaning.setText("Shahadat");
        second_arabic.setText("اَﺷْﻬَﺪُ اَﻥْ ﻵَّ اِﻟٰﻪَ اِﻻَّ اﻟﻠّٰﻪُ ﻭَﺣْﺪَﻩٗ ﻻَ ﺷَﺮِﻳْﻚَ ﻟَﻪٗ ﻭَاَﺷْﻬَﺪُ اَﻥَّ ﻣُﺤَﻤَّﺪًا ﻋَﺒْﺪُﻩٗ ﻭَﺭَﺳُﻮْﻟُﻪٗ");
        second_transliteration.setText("Ash-hadu Al-laaa Ilaaha Illa-llaahu Wahdahoo Laa Shareeka Lahoo Wa-Ash-hadu Anna Muhammadan ‘Abduhoo Wa Rasooluhu.");
        second_english.setText("I bear witness that (there is) none worthy of worship except Allah; One is He, no partner hath He, and I bear witness that Muhammad [P.B.U.H.] is His Servant and Messenger.");
        second_urdu.setText("میں گواہی دیتا ہوں کہ اللّٰه کے سِوا کوئی معبود نہیں وہ اکیلا ہے اُس کا کوئی شریک نہیں، اور میں گواہی دیتا ہوں کہ حضرت محمد (ﷺ ) اُس کے بندے اور رسول ہیں۔");
        second_arabic.setTypeface(arabicFont);
        second_urdu.setTypeface(urduFont);

        TextView third_heading_name = view3.findViewById(R.id.kalima_name);
        TextView third_heading_name_meaning = view3.findViewById(R.id.kalima_name_meaning);
        TextView third_arabic = view3.findViewById(R.id.arabic);
        TextView third_transliteration = view3.findViewById(R.id.transliteration);
        TextView third_english = view3.findViewById(R.id.english);
        TextView third_urdu = view3.findViewById(R.id.urdu);
        AudioPlayerView third_player = (AudioPlayerView) view3.findViewById(R.id.player);
        third_player .withUrl("https://www.dropbox.com/s/pnpd8nylv14qq49/third.MP3?dl=1");
        third_heading_name.setText("Third Kalima");
        third_heading_name_meaning.setText("Tamjeed");
        third_arabic.setText("ﺳُﺒْﺤَﺎﻥَ اﻟﻠّٰﻪِ ﻭَاﻟْﺤَﻤْﺪُ ﻟِﻠّﻪِ ﻭَﻻَ ﺇِﻟٰﻪَ ﺇِﻻَّاﻟﻠّٰﻪُ ﻭَاﻟﻠّٰﻪُ ﺃﻛْﺒَﺮُ ﻭَﻻَ ﺣَﻮْﻝَ ﻭَﻻَ ﻗُﻮَّﺓَ ﺇِﻻَّ ﺑِﺎﻟﻠّٰﻪِ اﻟْﻌَﻠِﻲِّ اﻟْﻌَﻆِﻴْﻢ ");
        third_transliteration.setText("Subhaana-llaahi Walhamdu Lillaahi Walaaa Ilaaha Illa-llaahu Wallaahu Akbar. Walaa Hawla Walaa Quwwata Illaa Billaahi-l ‘Aliyyil ‘Azeem.");
        third_english.setText("Flawless is Allah and the Praise is of Allah, and (there is) none worthy of worship except Allah, and Allah is the Greatest. And (there is) no power and no strength except from Allah, the Most High, the Most Great.");
        third_urdu.setText("پاک ہے اللّٰه اور تمام تعریفیں اللّٰه ہی کے لئے ہے اور اللّٰه کے سِوا کوئی معبود نہیں اور اللّٰه بہت بڑا ہے اور گناہوں سے بچنے کی طاقت اور نیک کام کرنے کی قوت اللّٰه ہی کی طرف سے ہے جو عالیشان اور عظمت والا ہے۔");
        third_arabic.setTypeface(arabicFont);
        third_urdu.setTypeface(urduFont);

        TextView fourth_heading_name = view4.findViewById(R.id.kalima_name);
        TextView fourth_heading_name_meaning = view4.findViewById(R.id.kalima_name_meaning);
        TextView fourth_arabic = view4.findViewById(R.id.arabic);
        TextView fourth_transliteration = view4.findViewById(R.id.transliteration);
        TextView fourth_english = view4.findViewById(R.id.english);
        TextView fourth_urdu = view4.findViewById(R.id.urdu);
        AudioPlayerView fourth_player = (AudioPlayerView) view4.findViewById(R.id.player);
        fourth_player.withUrl("https://www.dropbox.com/s/kyv6f22cz2uh3eg/fourth.MP3?dl=1");
        fourth_heading_name.setText("Fourth Kalima");
        fourth_heading_name_meaning.setText("Tauheed");
        fourth_arabic.setText("ﻵَ اِﻟٰﻪَ اِﻻَّ اﻟﻠّٰﻪُ ﻭَﺣْﺪَﻩٗ ﻻَ ﺷَﺮِﻳْﻚَ ﻟَﻪٗ ﻟَﻪُ اﻟْﻤُﻠْﻚُ ﻭَﻟَﻪُ اﻟْﺤَﻤْﺪُ ﻳُﺤْﻰٖ ﻭَﻳُﻤِﻴْﺖُ ﻭَﻫُﻮَ ﺣَﻰُّ ﻻَّ ﻳَﻤُﻮْﺕُ اَﺑَﺪًااَﺑَﺪًاؕ ﺫُﻭاﻟْﺠَﻼَﻝِ ﻭَاﻻِْﻛْﺮَاﻡِؕ ﺑِﻴَﺪِﻩِ اﻟْﺨَﻴْﺮُؕ ﻭَﻫُﻮَ ﻋَﻠٰﻰ ﻛُﻞِّ ﺷَﺊٍ ﻗَﺪِﻳْﺮٌ ");
        fourth_transliteration.setText("Laaa Ilaaha Illa-llaahu Wahdahoo Laa Shareeka-lahoo Lahu-l Mulku Walahu-l Hamdu Yuhyee Wayumeetu Wahuwa Hayyu-l Laa Yamootu Abadan Abada. Dhu-l Jalaali Wal Ikraam. Biyadihil Khair. Wahuwa Alaa Kulli Shai-’in Qadeer.");
        fourth_english.setText("(There is) none worthy of worship except Allah - One is He, no partners hath He. His is the Dominion, and His is the Praise. He gives life and causes death, and He is Living, who will not die, never. He of Majesty and Munificence. Within His Hand is (all) good. And He is, upon everything, Able (to exert His Will).");
        fourth_urdu.setText("اللّٰه کے سِوا کوئی معبود نہیں وہ اکیلا ہے اُس کا کوئی شریک نہیں، اُسی کی بادشاہی ہے اور اُسی کے لئے تمام تعریف ہے، وہ زندہ کرتا ہے اور وہی مارتا ہے اور وہ ہمیشہ کے لئے زندہ ہے جو مرے گا نہیں، عظمت اور بزرگی والا ہے، بہتری اُسی کے ہاتھ میں ہے اور وہ ہر چیز پر قادر ہے۔");
        fourth_arabic.setTypeface(arabicFont);
        fourth_urdu.setTypeface(urduFont);

        TextView fifth_heading_name = view5.findViewById(R.id.kalima_name);
        TextView fifth_heading_name_meaning = view5.findViewById(R.id.kalima_name_meaning);
        TextView fifth_arabic = view5.findViewById(R.id.arabic);
        TextView fifth_transliteration = view5.findViewById(R.id.transliteration);
        TextView fifth_english = view5.findViewById(R.id.english);
        TextView fifth_urdu = view5.findViewById(R.id.urdu);
        AudioPlayerView fifth_player = (AudioPlayerView) view5.findViewById(R.id.player);
        fifth_player.withUrl("https://www.dropbox.com/s/5jf384tk34qttpq/fifth.MP3?dl=1");
        fifth_heading_name.setText("Fifth Kalima");
        fifth_heading_name_meaning.setText("Astagfar");
        fifth_arabic.setText("اَﺳْﺘَﻐْﻔِﺮُ اﻟﻠّٰﻪَ ﺭَﺑِّﻰْ ﻣِﻦْ ﻛُﻞِّ ﺫَﻧْۢﺐٍ اَﺫْﻧَﺒْﺘُﻪٗ ﻋَﻤَﺪًا اَﻭْ ﺧَﻂَﺎًٔ ﺳِﺮًّا اَﻭْﻋَﻼَﻧِﻴَﺔً ﻭَّاَﺗُﻮْﺏُ اِﻟَﻴْﻪِ ﻣِﻦَ اﻟﺬَّﻧْۢﺐِ اﻟَّﺬِی اَﻋْﻠَﻢُ ﻭَﻣِﻦَ اﻟﺬَّﻧْۢﺐِ اﻟَّﺬِﻯْ ﻵَ اَﻋْﻠَﻢُ اِﻧَّﻚَ اَﻧْﺖَ ﻋَﻼَّﻡُ اﻟْﻐُﻴُﻮْﺏِ ﻭَﺳَﺘَّﺎﺭُ اﻟْﻌُﻴُﻮْﺏِ ﻭَﻏَﻔَّﺎﺭُ اﻟﺬُّﻧُﻮْﺏِ ﻭَﻻَ ﺣَﻮْﻝَ ﻭَﻻَ ﻗُﻮَّﺓَ اِﻻَّ ﺑِﺎﻟﻠّٰﻪِ اﻟْﻌَﻠِﻰِّ اﻟْﻌَﻆِﻴْﻢِؕ ");
        fifth_transliteration.setText("Astaghfiru-llaaha Rabbi Min Kulli Dhambin Adhnabtuhoo ‘Amadan Aw Khata-an Sirran Aw ‘Alaaniyata-wn Wa-atoobu Ilaihi Min-adh Dhambi-l Ladhee A’lamu Wamina-dh Dhambi-l Ladhi Laaa A’lamu Innaka Anta ‘Allaamu-l Ghuyoobi Wasattaaru-l ‘Uyoobi Wa Ghaffaaru-dh Dhunubi Walaa Hawla Walaa Quwwata Illaa Billaahi-l ‘Aliyyil ‘Azeem.");
        fifth_english.setText("I seek forgiveness from Allah, my Lord, from every sin I committed knowingly or unknowingly, secretly or openly, and I turn towards Him from the sin that I know and from the sin that I do not know. Certainly You, You (are) the Knower of the hidden things and the Concealer (of) the mistakes and the Forgiver (of) the sins. And (there is) no power and no strength except from Allah, the Most High, the Most Great.");
        fifth_urdu.setText("میں اللّٰه سے معافی مانگتا ہوں جو میرا پروردِگار ہے ہر گناہ سے جو میں نے کیا، جان بوجھ کر یا بھول کر، درپردہ یا کھلم کھلا اور میں توبہ کرتا ہوں اُس کے حضور میں اُس گناہ سے جو مجھے معلوم ہے اور اُس گناہ سے جو مجھے معلوم نہیں۔ بےشک تو عیبوں کو جاننے والا اور عیبوں کو چھپانے والا ہے اور گناہوں کا بخشنے والا ہے اور گناہوں سے بچنے کی طاقت اور نیک کام کرنے کی قوت اللّٰه ہی کی طرف سے ہے جو عالیشان اور عظمت والا ہے۔");
        fifth_arabic.setTypeface(arabicFont);
        fifth_urdu.setTypeface(urduFont);

        TextView sixth_heading_name = view6.findViewById(R.id.kalima_name);
        TextView sixth_heading_name_meaning = view6.findViewById(R.id.kalima_name_meaning);
        TextView sixth_arabic = view6.findViewById(R.id.arabic);
        TextView sixth_transliteration = view6.findViewById(R.id.transliteration);
        TextView sixth_english = view6.findViewById(R.id.english);
        TextView sixth_urdu = view6.findViewById(R.id.urdu);
        AudioPlayerView sixth_player = (AudioPlayerView) view6.findViewById(R.id.player);
        sixth_player.withUrl("https://www.dropbox.com/s/7yrmjvs09dbwjsp/sixth.MP3?dl=1");
        sixth_heading_name.setText("Sixth Kalima");
        sixth_heading_name_meaning.setText("Rad-e-Kufr");
        sixth_arabic.setText("اَﻟﻠّٰﻬُﻢَّ اِﻧِّﻲْ اَﻋُﻮْﺫُﺑِﻚَ ﻣِﻦْ اَﻥْ اُﺷْﺮِﻙَ ﺑِﻚَ ﺷَﻴْﺌًﺎ ﻭَّاَﻧَﺂ اَﻋْﻠَﻢُ ﺑِﻪٖ ﻭَاَﺳْﺘَﻐْﻔِﺮُﻙَ ﻟِﻤَﺎ ﻵَ اَﻋْﻠَﻢُ ﺑِﻬٖﺘُﺒْﺖُ ﻋَﻨْﻪُ ﻭَﺗَﺒَﺮَّﺃْﺕُ ﻣِﻦَ اﻟْﻜُﻔْﺮِ ﻭَاﻟﺸِّﺮْﻙِ ﻭَاﻟْﻜِﺬْﺏِ ﻭَاﻟْﻐِﻴْﺒَﺔِ ﻭَاﻟْﺒِﺪْﻋَﺔِ ﻭَاﻟﻨَّﻤِﻴْﻤَﺔِ ﻭَاﻟْﻔَﻮَاﺣِﺶِ ﻭَاﻟْﺒُﻬْﺘَﺎﻥِ ﻭَاﻟْﻤَﻌَﺎﺻِﻰْ ﻛُﻠِّﻬَﺎ ﻭَاَﺳْﻠَﻤْﺖُ ﻭَاَﻗُﻮْﻝُ ﻵَ اِﻟٰﻪَ اِﻻَّ اﻟﻠّٰﻪُ ﻣُﺤَﻤَّﺪٌ ﺭَّﺳُﻮْﻝُ اﻟﻠّٰﻪِؕ");
        sixth_transliteration.setText("Allaa-humma Inneee A’udhu-bika Min An Ushrika Bika Shay-awn Wa-ana A’lamu Bihee Wa- astaghfiruka Limaa Laaa A’lamu Bihee Tubtu ‘Anhu Wata-barraatu Mina-l Kufri Wash-shirki Wal-kidhbi Wal-gheebati Wal-bid’ati Wan-nameemati Wal-fawahishi Wal-buhtaani Wal-m’aasi Kulli-haa Wa-Aslamtu Wa-aqoolu Laaa Ilaaha Illa-llaahu Muhammadu-r Rasoolu-llah.");
        sixth_english.setText("O Allah! I seek protection in You from that I should not join any partner with You and I have knowledge of that and I seek Your forgiveness from that which I do not know. I repent from it (ignorance) and I reject disbelief (kufr) and joining partners with You (shirk) and of falsehood and slandering (gheebat) and innovation in religion (bid'at) and tell-tales and bad, evil deeds and the blame and the disobedience, all of them. And I submit to Your will and I say: (There is) none worthy of worship except Allah and Muhammad [P.B.U.H.] is the Messenger of Allah.");
        sixth_urdu.setText("الٰہی میں تیری پناہ مانگتا ہوں اِس بات سے کہ میں کسی چیز کو تیرا شریک بناوں اور مجھے اِس کا علم ہو اور میں معافی مانگتا ہوں تجھ سے اس گناہ سے جِس کا علم مجھے نہیں، میں نے اس سے توبہ کی اور بیزار ہوا کفر سے اور شرک سے اور جھوٹ سے اور غیبت سے اور بدعت سے اور چغلی سے اور بے حیائی کے کاموں سے اور تہمت لگانے سے اور (باقی) ہر قسم کی نافرمانیوں سے اور میں ایمان لایا اور کہتا ہوں کہ اللّٰه کے سِوا کوئی معبود نہیں اور حضرت محمد (ﷺ) اللّٰه کے رسول ہیں۔");
        sixth_arabic.setTypeface(arabicFont);
        sixth_urdu.setTypeface(urduFont);





        final ImageView shareView = (ImageView)findViewById(R.id.ad_cat);
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareApp();
            }
        });

    }

    @Override
    public void onClick(View view)
    {

    }


    public void ShareApp()
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "There\\'s an app where you just select your emotions and " +
                "current feelings (e.g ANGRY,CONFIDENT,INSECURE etc. and it gives " +
                "you an Ayat or Surah (in ARABIC, URDU &amp; ENGLISH) that " +
                "correlates with it.\n" + "(Available on Playstore)\n" +
                "https://play.google.com/store/apps/details?id=com.mianasad.myislam\n\n"+
                "Pass it to everyone you know. It\\s Awesome!";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
