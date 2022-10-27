package jdk.java.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java.util.Date 테스트
 *
 * @author fixalot
 * @since 2017-07-27
 */
public class DateTest {
    //	@SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DateTest.class);

    @Test
    public void shouldBeEquals() {
        long mills = System.currentTimeMillis();
        logger.debug("currentTimeMills: {}", mills);
        Date now = new Date(mills);
        assertEquals(mills, now.getTime());
    }

    @Test
    public void testJavaUtilDate() {
        Date now = new Date();
        logger.debug("{}", now);

        now = new Date(1547168374396L);
        assertEquals("Fri Jan 11 09:59:34 KST 2019", now.toString());
    }

    @Test
    public void changeTimeZone() throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = isoFormat.parse("2010-05-23T09:01:02");
        assertEquals("Sun May 23 18:01:02 KST 2010", date1.toString());

        isoFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date date2 = isoFormat.parse("2010-05-23T09:01:02");
        assertEquals("Sun May 23 09:01:02 KST 2010", date2.toString());
    }
}
