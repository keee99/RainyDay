//package com.example.loginattempt1;
//
//
//import com.example.loginattempt1.schemas.FirebaseCallback;
//import com.example.loginattempt1.schemas.Schema;
//import com.google.firebase.FirebaseApp;
//
//
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//
//import java.util.Map;
//import android.content.Context;
//
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//public class SchemaTest {
//
//    @Test
//    public void writeReadTest() {
//
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        FirebaseApp.initializeApp(appContext);
//        testSchema testInstance = new testSchema.Builder()
//                .setName("Koh Jia Jun")
//                .setPillar("ISTD")
//                .setYearOfStudy(2024)
//                .setSingle(true)
//                .build();
//
//        testInstance.read(new FirebaseCallback() {
//            @Override
//            public void onResponse(Map<String, Object> documents) {
//
//                assertEquals("Koh Jia Jun", documents.get("name").toString());
//                assertEquals("ISTD", documents.get("pillar").toString());
//                assertEquals(true, documents.get("single"));
//                assertEquals(2024, documents.get("yearOfStudy"));
//
//            }
//        });
//    }
//}
//
//
//
//
//class testSchema extends Schema {
//
//    private String name;
//    private Integer yearOfStudy;
//    private String pillar;
//    private boolean single;
//
//    private testSchema (Builder b) {
//        setCollection("testColle");
//        setUID("testUID1");
//
//        name = b.name;
//        yearOfStudy = b.YearOfStudy;
//        pillar = b.pillar;
//        single = b.single;
//
//    }
//
//    public static class Builder {
//        // Not providing a field will update the document's field to null.
//        private String name;
//        private Integer YearOfStudy;
//        private String pillar;
//        private boolean single;
//
//        public Builder setName(String name) {
//            this.name = name;
//            return this;
//        }
//        public Builder setYearOfStudy(Integer yearOfStudy) {
//            YearOfStudy = yearOfStudy;
//            return this;
//        }
//        public Builder setPillar(String pillar) {
//            this.pillar = pillar;
//            return this;
//        }
//        public Builder setSingle(boolean single) {
//            this.single = single;
//            return this;
//        }
//
//        public testSchema build() {
//            return new testSchema( this );
//        }
//
//
//    }
////    setName("Koh Jia Jun");
////    setYearOfStudy("2024");
////    setPillar("ISTD");
////    setSingle(true);
//
//}
