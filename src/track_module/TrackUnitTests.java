package src.track_module;

import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import src.track_module.TrackModule.FileFormatException;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;
import src.ctc.CTCModule;
import src.track_controller.TrackControllerModule;
import src.Module;

@RunWith(JUnit4.class)
public class TrackUnitTests {
    TrackModule trackModule;

    @Before
    public void setup() {
        trackModule= new TrackModule();
        TrainControllerModule trainControllerModule= new TrainControllerModule();
        TrackControllerModule trackControllerModule= new TrackControllerModule();
        TrainModule trainModule= new TrainModule();
        CTCModule ctcModule= new CTCModule();

        HashSet<Module> modules= new HashSet<Module>();
        modules.add( trackModule);
        modules.add( trainControllerModule);
        modules.add( trackControllerModule);
        modules.add( trainModule);
        modules.add( ctcModule);

        for( Module module : modules) {
            module.setCTCModule(ctcModule);
            module.setTrackControllerModule(trackControllerModule);
            module.setTrainControllerModule(trainControllerModule);
            module.setTrackModule(trackModule);
            module.setTrainModule(trainModule);
        }
    }

    @Test
    public void successfulNormalTest() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack.csv");
        }
        catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureSection() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_section.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBlockNumber() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_block_number.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadLine() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_line.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadLength() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_length.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadGrade() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_grade.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadUnderground() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_underground.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadCrossing() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_crossing.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadElevation() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_elevation.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadCummulativeElevation() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_cumm_elevation.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadConnection0() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_connection0.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadConnection1() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_connection1.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadSwitch0() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_switch0.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadSwitch1() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_switch1.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadSwitch2() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_switch2.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadSwitch3() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_switch3.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadDirection0() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_direction0.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadDirection1() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_direction1.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadDirection2() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_direction2.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadXCoordinate() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_xCoor.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
    @Test
    public void failureBadYCoordinate() {
        try {
            trackModule.buildTrack("jtests/track_module/testtrack_bad_yCoor.csv");
            fail("Test not successful");
        }
        catch( FileFormatException e) {
            // Success
        } catch( Exception e) {
            fail("Test not successful");
        }
    }
}