package fr.efrei.alsi54;

import java.util.List;

public interface ActionsBDD {
    /**
     * Renvoie la liste de tous les programmeurs
     *
     * @return Liste des programmeurs
     */
    List<Programmer> getAllProgrammers();

    /**
     * Renvoie un programmeur en fonction de son ID
     *
     * @param id ID du programmeur
     * @return Le programmeur correspondant
     */
    Programmer getProgrammerById(int id);

    /**
     * Supprime un programmeur en fonction de son ID
     *
     * @param id ID du programmeur à supprimer
     */
    void deleteProgrammer(int id);

    /**
     * Ajoute un nouveau programmeur à la base de données
     *
     * @param programmer Le programmeur à ajouter
     * @return L'ID du programmeur ajouté
     */
    int addProgrammer(Programmer programmer);

    /**
     * Met à jour le salaire d'un programmeur
     *
     * @param programmerId ID du programmeur
     * @param newSalary    Nouveau salaire
     */
    void updateSalary(int programmerId, float newSalary);

    /**
     * Renvoie la liste de tous les projets
     *
     * @return Liste des projets
     */
    List<Project> getAllProjects();

    /**
     * Renvoie la liste des programmeurs travaillant sur un projet donné
     *
     * @param projectId ID du projet
     * @return Liste des programmeurs
     */
    List<Programmer> getProgrammersByProject(int projectId);

    /**
     * Ajoute un nouveau projet à la base de données
     *
     * @param project Le projet à ajouter
     * @return L'ID du projet ajouté
     */
    int addProject(Project project);

    /**
     * Supprime un projet en fonction de son ID
     *
     * @param id ID du projet à supprimer
     */
    void deleteProject(int id);

    /**
     * Ajoute un programmeur à un projet (insère une ligne dans programmer_project).
     *
     * @param programmerId ID du programmeur
     * @param projectId    ID du projet
     */
    void addProgrammerToProject(int programmerId, int projectId);

    /**
     * Retire un programmeur d'un projet (supprime la ligne dans programmer_project).
     *
     * @param programmerId ID du programmeur
     * @param projectId    ID du projet
     */
    void removeProgrammerFromProject(int programmerId, int projectId);
}
