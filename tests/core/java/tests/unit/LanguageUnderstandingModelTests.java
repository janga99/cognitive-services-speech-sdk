package tests.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.net.URI;
import java.util.ArrayList;

import com.microsoft.cognitiveservices.speech.AudioInputStream;
import com.microsoft.cognitiveservices.speech.FactoryParameterNames;
import com.microsoft.cognitiveservices.speech.KeywordRecognitionModel;
import com.microsoft.cognitiveservices.speech.ParameterCollection;
import com.microsoft.cognitiveservices.speech.Recognizer;
import com.microsoft.cognitiveservices.speech.SpeechFactory;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.intent.IntentRecognizer;
import com.microsoft.cognitiveservices.speech.intent.LanguageUnderstandingModel;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;

import tests.Settings;

@SuppressWarnings("unused")
public class LanguageUnderstandingModelTests {

    @BeforeClass
    static public void setUpBeforeClass() throws Exception {
        // Override inputs, if necessary
        Settings.LoadSettings();
    }

    @AfterClass
    static public void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testFromEndpoint() {
        LanguageUnderstandingModel s = LanguageUnderstandingModel.fromAppId(Settings.LuisAppId);
        assertNotNull(s);
    }

    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testFromAppId() {
        LanguageUnderstandingModel s = LanguageUnderstandingModel.fromAppId(Settings.LuisAppId);
        assertNotNull(s);
    }

    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testFromSubscription() {
        LanguageUnderstandingModel s = LanguageUnderstandingModel.fromSubscription(Settings.LuisSubscriptionKey, Settings.LuisAppId, Settings.LuisRegion);
        assertNotNull(s);
    }

    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testLanguageUnderstandingModel() {
        fail("Not yet implemented");
    }

    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testGetModelImpl1() {
        LanguageUnderstandingModel s = LanguageUnderstandingModel.fromAppId(Settings.LuisAppId);
        
        assertNotNull(s);
        assertNotNull(s.getModelImpl());
    }

    @Test
    public void testGetModelImpl2() {
        LanguageUnderstandingModel s = LanguageUnderstandingModel.fromEndpoint("https://www.example.com/api/v1");
        
        assertNotNull(s);
        assertNotNull(s.getModelImpl());
    }
    
    @Test
    public void testGetModelImpl3() {
        LanguageUnderstandingModel s = LanguageUnderstandingModel.fromSubscription(Settings.LuisSubscriptionKey, Settings.LuisAppId, Settings.LuisRegion);
        
        assertNotNull(s);
        assertNotNull(s.getModelImpl());
    }

}
