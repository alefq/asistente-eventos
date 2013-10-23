package py.gov.senatics.asistente.view;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.bean.ApplicationScoped;

import org.ticpy.tekoporu.annotation.Name;
import org.ticpy.tekoporu.stereotype.ViewController;

@ViewController
@ApplicationScoped
@Name(value = "applicationSettings")
public class ApplicationSettings implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 4066690251294842531L;

        private Locale currentLocale;

        public Locale getCurrentLocale() {
                if (currentLocale == null)
                        currentLocale =  new Locale("es", "PY");
                return currentLocale;
        }

        public void setCurrentLocale(Locale currentLocale) {
                this.currentLocale = currentLocale;

        }
        
        public Date getCurrentTime()
        {
        	return Calendar.getInstance(getCurrentLocale()).getTime();
        }

}