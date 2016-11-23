package pl.sygnity;


import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.EventType;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;

import java.io.File;
import java.util.MissingResourceException;
import java.util.concurrent.TimeUnit;

public class ApplicationProperties {

    public static class ApplicationPropertyNotFoundException extends Exception {

        private static final long serialVersionUID = 4587446296463301542L;

        public ApplicationPropertyNotFoundException() {
            super();
        }

        public ApplicationPropertyNotFoundException(String arg0, Throwable arg1) {
            super(arg0, arg1);
        }

        public ApplicationPropertyNotFoundException(String arg0) {
            super(arg0);
        }

        public ApplicationPropertyNotFoundException(Throwable arg0) {
            super(arg0);
        }
    }

    private static PropertiesConfiguration propertiesConfiguration;
    private static ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    private static PeriodicReloadingTrigger trigger;
    private static final String APPLICATION_PROPERTY_FILE_NAME = "application.properties";


    private final static String getStringProperty(String key)
            throws ApplicationPropertyNotFoundException {
        if (propertiesConfiguration == null) {
            initialize();
        }
        try {
            builder.getReloadingController().addEventListener(EventType., );
            builder.getReloadingController().isInReloadingState();
            return propertiesConfiguration.getString(key);
        } catch (MissingResourceException e) {
            throw new ApplicationPropertyNotFoundException(String.format("Couldn't find value for key '%s'", key));
        }
    }

    public final static <U> U getProperty(String key, Class<U> type,
                                          U defaultValue) {

        String property = null;
        try {
            property = getStringProperty(key);
        } catch (ApplicationPropertyNotFoundException ex) {

            if (type == int.class) {
                return (U) Integer.class.cast(new Integer(0));
            } else if (type == long.class) {
                return (U) Long.class.cast(new Long(0));
            } else if (type == boolean.class) {
                return (U) Boolean.class.cast(new Boolean(false));
            }
            return defaultValue;
        }
        if (type == int.class) {
            return (U) Integer.class.cast(new Integer(property));
        } else if (type == Integer.class) {
            return type.cast(new Integer(property));
        } else if (type == long.class) {
            return (U) Long.class.cast(new Long(property));
        } else if (type == Long.class) {
            return type.cast(new Long(property));
        } else if (type == Boolean.class) {
            return type.cast(new Boolean(property));
        } else if (type == boolean.class) {
            return (U) Boolean.class.cast(new Boolean(property));
        } else if (type == String.class) {
            return type.cast(property);
        } else {
            throw new RuntimeException(String.format("Type '%s' is not supported property type", type.getName()));
        }
    }

    private static void initialize() {
        final File propsFile = new File(Thread.currentThread().getContextClassLoader().getResource(APPLICATION_PROPERTY_FILE_NAME).getPath());
        try {
            propertiesConfiguration = prepareConfiguration(propsFile);
        } catch (ConfigurationException e) {
            new ApplicationPropertyNotFoundException("Could not initialize configuration from file: " + propsFile.getAbsolutePath());
        }
    }

    private static PropertiesConfiguration prepareConfiguration(final File propertiesFile) throws ConfigurationException {
        Parameters params = new Parameters();

        builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.fileBased()
                                .setFile(propertiesFile));

        trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                null, 10, TimeUnit.SECONDS);
        trigger.start();
        return (PropertiesConfiguration)builder.getConfiguration();
    }
}
