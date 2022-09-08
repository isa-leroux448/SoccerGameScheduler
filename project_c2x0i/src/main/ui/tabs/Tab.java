// Code based on SmartHome
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git

package ui.tabs;

import ui.LeagueManagerUI;

import javax.swing.*;

public abstract class Tab extends JPanel {

    private final LeagueManagerUI controller;

    //REQUIRES: LeagueManagerUI controller that holds this tab
    public Tab(LeagueManagerUI controller) {
        this.controller = controller;
    }

    //EFFECTS: returns the LeagueManagerUI controller for this tab
    public LeagueManagerUI getController() {
        return controller;
    }

}