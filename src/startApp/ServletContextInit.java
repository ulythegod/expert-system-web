package startApp;

import constants.ServletContextKeys;
import core.data.storage.StorageManager;
import core.data.storage.StorageManagerFactory;
import core.data.storage.init.LoadData;
import core.logic.DataManager;
import core.logic.DataManagerMode1;
import core.logic.DataManagerMode2;
import core.logic.DataManagerModeFertilizers;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Author: Natalia Voronova
 * Date: 06.06.14
 * Time: 20:43
 */
public class ServletContextInit implements ServletContextListener {
    public ServletContextInit() {
    }
    private static final Logger logger = Logger.getLogger("out");

    public static String propsRresource = "/resources";
    public static String portalPropertiesFileName = "/WEB-INF/portal.properties";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        try {
            LoadData.jdoProperties.load(servletContext.getResourceAsStream((propsRresource + "/resource.properties")));
            StorageManager sm = StorageManagerFactory.getStorageManager();
            servletContext.setAttribute("storageManager", sm);
            System.out.println("sm != null is " + (sm != null));

            DataManager dm = new DataManager(sm);
            servletContext.setAttribute("dataManager", dm);
            System.out.println("dm != null is " + (dm != null));

            DataManagerMode1 dm1 = new DataManagerMode1(sm);
            servletContext.setAttribute("dataManagerMode1", dm1);
            System.out.println("dm1 != null is " + (dm1 != null));

            DataManagerModeFertilizers dmFert = new DataManagerModeFertilizers(sm);
            servletContext.setAttribute("dataManagerModeFertilizers", dmFert);

            Locale l = new Locale("ru");
            ResourceBundle rb = ResourceBundle.getBundle("Resource", l);
            String status_finished = rb.getString("PL_FORM_TABLE_RES_STATUS_PROC");
            String status_unfinished = rb.getString("PL_FORM_TABLE_RES_STATUS_UNPROC");
            DataManagerMode2 dm2 = new DataManagerMode2(sm, status_finished, status_unfinished);
            servletContext.setAttribute("dataManagerMode2", dm2);
            System.out.println("dm2 != null is " + (dm2 != null));

            //Инициализация файла с настройками

            Properties prop = new Properties();

            try {
                InputStream in = servletContext.getResourceAsStream(portalPropertiesFileName);
                if (in == null) {
                    throw new FileNotFoundException("Property file `" + portalPropertiesFileName +
                            "' not found.");
                }
                try {
                    prop.load(in);
                    servletContext.setAttribute(ServletContextKeys.PORTAL_PROPERTIES, prop);
                    logger.log(Level.INFO, "Portal properties loaded");
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
