package com.apress.springrecipes.shop;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.*;

/**
 * Date: 1/24/11
 * Time: 5:28 PM
 */
public class BannerLoader {

    private static Logger logger;
    static {
        logger = Logger.getLogger(BannerLoader.class);
    }

    private Resource banner;

    public void setBanner(Resource banner) {
        this.banner = banner;
    }

    public void showBanner() throws IOException {
        try {
            InputStream is = banner.getInputStream();
            Reader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder buffy = new StringBuilder("\n");
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                buffy.append(line).append("\n");
            }
            reader.close();
            buffy.delete(buffy.length()-1, buffy.length());
            logger.info(buffy);
            buffy.delete(0, buffy.length());
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
            throw new IOException(t);
        }
    }

}
