package distcalc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DistanceCalculationTest extends Assert {
    DistanceCalculation dc;
    LineType lt25_1;
    LineType lt25_2;
    LineType lt2x25_1;
    LineType ltconst_1;
    LineType ltconst_2;
    LineType ltauto_1;
    LineType ltauto_2;
    @Before
    public void setUp() throws Exception {
        dc = new DistanceCalculation();
        lt25_1 = new LineType("Провода ДПР, подвешенные с одной стороны путей",
                60, 58, 72, 70, 38, 38, 2.0, 2.0, 1.5, 6, 0, 0.1);
        lt25_2 = new LineType("Однопроводный волновод, подвешенный под проводом ДПР",
                60, 54, 72, 66, 38, 38, 2.0, 12.0, 0.0, 6, 0, 0.1);
        lt2x25_1 = new LineType("Провод ДПР и питающий провод ПП с одной стороны пути",
                60, 58, 72, 70, 37, 37, 2.0, 2.0, 1.5, 6, 0, 4.0);
        ltconst_1 = new LineType("Два провода трехфазной ВЛС (электрическая тяга постоянного тока)",
                58, 52, 70, 64, 40, 40, 2.0, 2.0, 2.0, 6, 6, 0.1);
        ltconst_2 = new LineType("Двухпроводный волновод (электрическая тяга постоянного тока)",
                58, 46, 70, 58, 38, 38, 1.7, 1.7, 1.5, 6, 0, 0.1);
        ltauto_1 = new LineType("Два провода трехфазной ВЛС (автономная тяга)",
                38, 50, 47, 59, 50, 50, 2.0, 2.0, 2.0, 3, 6, 0.1);
        ltauto_2 = new LineType("Двухпроводный волновод, подвешенный на самостоятельных опорах",
                38, 24, 47, 34, 0, 38, 0.0, 12.0, 1.5, 3, 0, 0.1);
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void testCalcAprd(){
        int testLoad = 1;
        int expAprd = dc.calcAprd(testLoad);
        assertEquals(148, expAprd);
        testLoad = 2;
        expAprd = dc.calcAprd(testLoad);
        assertEquals(145, expAprd);
    }

    @Test
    public void testCalcUminMob(){
        int speed = 139;
        double expUminMob = dc.calcUminMob(lt25_1, speed); //60 + 6 + 6
        assertEquals(72, expUminMob, 0.0);
        speed = 150;
        expUminMob = dc.calcUminMob(ltauto_1, speed); //38 + 3.5 + 6 + 3
        assertEquals(50.5, expUminMob, 0.0);
    }

    @Test
    public void testCalcUminStat(){
        int expUminStat = dc.calcUminStat(lt25_2); //54 + 6 + 6
        assertEquals(66, expUminStat);
    }

    @Test
    public void testCalcAper(){
        int dist = 20;
        int lineQuantity = 2;
        boolean tunnel = true;
        double expAper = dc.calcAper(ltauto_1, dist, lineQuantity, tunnel); //47.5
        assertEquals(47.5, expAper, 0.0);
        tunnel = false;
        lineQuantity = 1;
        expAper = dc.calcAper(ltconst_2, dist, lineQuantity, tunnel); //38 - 4
        assertEquals(34, expAper, 0.0);
    }

    @Test (expected = NumberFormatException.class)
    public void testCalcAperWithException(){
        int dist = 5;
        int lineQuantity = 2;
        boolean tunnel = true;
        double expAper = dc.calcAper(ltauto_1, dist, lineQuantity, tunnel);
    }

    @Test
    public void testCalcAst(){
        double feederLength = 10.0;
        boolean anker = false;
        double expAst = dc.calcAst(ltconst_1, feederLength, anker); //0.008*10 + 1.5 + 2.0 + 6 + 3
        assertEquals(12.58, expAst, 0.0);
        feederLength = 17.0;
        anker = true;
        expAst = dc.calcAst(ltauto_2, feederLength, anker); //0.008*17 + 1.5 + 1.5 + 0 + 0
        assertEquals(3.136, expAst, 0.0);
    }

    @Test
    public void testCalcAlin(){
        int n = 3, m = 4;
        String crossType = "air";
        double expAlin = dc.calcAlin(ltauto_2, n, crossType, m); //1 + 1 + 2.5 + 3 * 0.7 + 4 * 0.1
        assertEquals(7, expAlin, 0.0);
        n = 5;
        m = 2;
        crossType = "cable";
        expAlin = dc.calcAlin(lt2x25_1, n, crossType, m); //1 + 1 + 2.5 + 5 * 2.5 + 2 * 4
        assertEquals(25, expAlin, 0.0);
    }

    @Test
    public void testCalcAlok(){
        boolean loco = true;
        double expAlok = dc.calcAlok(loco); //1.5 + 0
        assertEquals(1.5, expAlok, 0.0);
        loco = false;
        expAlok = dc.calcAlok(loco); //1.5 + 12
        assertEquals(13.5, expAlok, 0.0);
    }

    @Test
    public void testCalcAn(){
        boolean tunnel = true;
        double expAn = dc.calcAn(ltconst_2, tunnel);
        assertEquals(1.7, expAn, 0.0);
        tunnel = false;
        expAn = dc.calcAn(ltauto_1, tunnel);
        assertEquals(2.0, expAn, 0.0);
    }
}