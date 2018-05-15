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
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;

import tests.Settings;

@SuppressWarnings("unused")
public class KeywordRecognitionModelTests {

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
    public void testFromFile() {
        KeywordRecognitionModel s = KeywordRecognitionModel.fromFile(Settings.KeywordModel);
        assertNotNull(s);
    }

    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testClose() {
        KeywordRecognitionModel s = KeywordRecognitionModel.fromFile(Settings.KeywordModel);
        assertNotNull(s);
        
        s.close();
        
        assertNull(s.getModelImpl());
    }


    // -----------------------------------------------------------------------
    // --- 
    // -----------------------------------------------------------------------

    @Test
    public void testGetmodelImpl() {
        KeywordRecognitionModel s = KeywordRecognitionModel.fromFile(Settings.KeywordModel);
        
        assertNotNull(s);
        assertNotNull(s.getModelImpl());
    }

}
