package fr.efrei.alsi54;

import fr.efrei.alsi54.model.Programmer;

import java.util.ArrayList;

public interface ActionsBDD {
    ArrayList<Programmer> getProgrammers();
    Programmer getProgrammer(int id);
    void deleteProgrammer(int id);
    Programmer addProgrammer(Programmer programmer);
    void updateProgrammer(Programmer programmer);
    void getProjects();
    ArrayList<Programmer> getProgrammersByProject(int projectId);
}
