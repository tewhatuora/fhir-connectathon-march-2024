package nz.govt.tewhatuora.Service;

import com.solace.messaging.receiver.InboundMessage;

import nz.govt.tewhatuora.Events.*;
import nz.govt.tewhatuora.Utilities.*;

public class EventLoader {

    // Where users will implement their own code that handles what is done with the
    // Events.
    // below is an example of code that just prints the Event paylod to the comand
    // line
    public static void processEvent(InboundMessage message) {

        String event = "";
        // Get Event from Topic Taxonomy
        try {
            event = EventUtil.GetEvent(message.getDestinationName());
        } catch (Exception e) {
            System.out.println("Dropped Message - " + e.getMessage());
            return;
        }

        switch (event) {
            case "death":
                Death.processDeath(message);
                break;
            // Enter case event here
            default:
                System.out.println("Unknown Event: " + event);
        }

    }
}
