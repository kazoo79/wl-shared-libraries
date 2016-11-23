package pl.sygnity;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by pdebala on 2016-06-29.
 */
public class ApplicationPropertiesTest {

    private static final String EXPECTED_PROPERTY_VALUE = "weblogic.jndi.WLInitialContextFactory";
    private static final String DEFAULT_PROPERTY_VALUE = "default.value";


    @Test
    public void shouldGetsProperty() {
        // when
        String propertyName = "INITIAL_CONTEXT_FACTORY";

        // given
        String propertyValue = ApplicationProperties.getProperty(propertyName, String.class, DEFAULT_PROPERTY_VALUE);

        // then
        assertEquals(propertyValue, EXPECTED_PROPERTY_VALUE);
    }

    public static void main(String[] args)
    {
        while(true)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(ApplicationProperties.getProperty("INITIAL_CONTEXT_FACTORY", String.class, DEFAULT_PROPERTY_VALUE));
        }
    }

}
