package nforla.tap.microservicioconsulta;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeStampProvider {

    public LocalDateTime getTimeStamp(){
        return LocalDateTime.now();
    }
}
