import com.topologi.diffx.xml.esc.XMLEscapeASCII;
import junit.framework.Assert;
import org.junit.Test;

public class EscapeXmlTest {
    @Test
    public void shouldEscapeAndOperator(){
        Assert.assertEquals("Hello &amp; world",XMLEscapeASCII.ASCII_ESCAPE.toAttributeValue("Hello & world") );
    }
}
