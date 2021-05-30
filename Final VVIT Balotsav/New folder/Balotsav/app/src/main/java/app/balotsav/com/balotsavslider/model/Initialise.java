
package app.balotsav.com.balotsavslider.model;

import java.util.ArrayList;

public class Initialise {
    ArrayList<Event> event = new ArrayList<>();
    Event e;
    ListEvents listEvents;

    public Initialise() {
        e = new Event("చిత్రలేఖనం", 0, 0, 0, 5, 0, false);
        event.add(0, e);
        e = new Event("వక్తృత్వం", 0, 0, 0, 2, 0, false);
        event.add(1, e);
        e = new Event("ఏకపాత్రాభినయం", 0, -1, 0, 2, 0, false);
        event.add(2, e);
        e = new Event("శాస్త్రీయ నృత్యం", 0, 0, 0, 1, 0, false);
        event.add(3, e);
        e = new Event("సాంప్రదాయ వేషధారణ", -1, 0, -1, 1, 0, false);
        event.add(4, e);
        e = new Event("తెలుగులోనే మాట్లాడడం", 0, 0, 0, 2, 0, false);
        event.add(5, e);
        e = new Event("శాస్త్రీయ సంగీతం (గాత్రం)", 0, -1, 0, 2, 0, false);
        event.add(6, e);
        e = new Event("జనరల్ క్విజ్", 0, 0, 0, 5, 3, false);
        event.add(7, e);
        e = new Event("డిజిటల్ చిత్రలేఖనం", 0, 0, 0, 3, 0, false);
        event.add(8, e);
        e = new Event("తెలుగు పద్యం", 0, 0, 0, 2, 0, false);
        event.add(9, e);
        e = new Event("సినీ,లలిత,జానపద గీతాలు", 0, 0, 0, 2, 0, false);
        event.add(10, e);
        e = new Event("ముఖాభినయం", 0, -1, 0, 2, 0, false);
        event.add(11, e);
        e = new Event("వక్తృత్వం (ఇంగ్లీష్)", 0, 0, 0, 2, 0, false);
        event.add(12, e);
        e = new Event("సంస్కృత శ్లోకం", -1, 0, -1, 2, 0, false);
        event.add(13, e);
        e = new Event("జానపద నృత్యం", 0, 0, 0, 1, 0, false);
        event.add(14, e);
        e = new Event("కవిత రచన (తెలుగు)", 0, -1, 0, 2, 0, false);
        event.add(15, e);
        e = new Event("నాటికలు", 0, 0, 0, 5, 10, false);
        event.add(16, e);
        e = new Event("వాద్య సంగీతం (రాగ ప్రధానం)", 0, 0, 0, 2, 0, false);
        event.add(17, e);
        e = new Event("కథ రచన (తెలుగు)", 0, -1, 0, 2, 0, false);
        event.add(18, e);
        e = new Event("స్పెల్ బీ", -1, -1, 0, 2, 0, false);
        event.add(19, e);
        e = new Event("మట్టితో బొమ్మ చేద్దాం", 0, 0, 0, 5, 2, false);
        event.add(20, e);
        e = new Event("లేఖా రచన", 0, 0, 0, 2, 0, false);
        event.add(21, e);
        e = new Event("కథావిశ్లేషణ", 0, -1, 0, 2, 0, false);
        event.add(22, e);
        e = new Event("వాద్య సంగీతం (తాళ ప్రధానం)", 0, 0, 0, 2, 0, false);
        event.add(23, e);
        e = new Event("లఘు చిత్ర విశ్లేషణ", 0, -1, 0, 2, 0, false);
        event.add(24, e);
        e = new Event("జానపద నృత్యం-బృంద ప్రదర్శన", 0, -1, 0, 2, 10, false);
        event.add(25, e);
        e = new Event("శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన", 0, 0, 0, 5, 10, false);
        event.add(26, e);
        e = new Event("విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)", 0, 0, 0, 5, 1, false);
        event.add(27, e);
        e = new Event("విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)", 0, 0, 0, 5, 1, false);
        event.add(28, e);

        listEvents = new ListEvents(event);
    }

    public ArrayList<Event> getEvent() {
        return event;
    }

    public void setEvent(ArrayList<Event> event) {
        this.event = event;
    }

    public ListEvents getListEvents() {
        return listEvents;
    }

    public void setListEvents(ListEvents listEvents) {
        this.listEvents = listEvents;
    }
}