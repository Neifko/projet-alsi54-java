package fr.efrei.alsi54;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionsBDDImpl implements ActionsBDD {

    private final Database db = Database.getInstance();

    @Override
    public List<Programmer> getAllProgrammers() {
        List<Programmer> programmers = new ArrayList<>();
        String query = "SELECT * FROM programmers";

        try (Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                programmers.add(mapResultSetToProgrammer(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (getAllProgrammers) : " + e.getMessage());
        }
        return programmers;
    }

    @Override
    public Programmer getProgrammerById(int id) {
        String query = "SELECT * FROM programmers WHERE id = ?";
        Programmer programmer = null;

        try (Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    programmer = mapResultSetToProgrammer(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (getProgrammerById) : " + e.getMessage());
        }
        return programmer;
    }

    @Override
    public void deleteProgrammer(int id) {
        String query = "DELETE FROM programmers WHERE id = ?";

        try (Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("SUPPRESSION REUSSIE !");
            } else {
                System.out.println("Suppression KO : ID introuvable.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (deleteProgrammer) : " + e.getMessage());
        }
    }

    @Override
    public int addProgrammer(Programmer p) {
        // Attention : birth_year est un INT dans la base
        String query = "INSERT INTO programmers (name, first_name, address, username, manager, hobby, birth_year, salary, bonus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, p.getLastName());
            pstmt.setString(2, p.getFirstName());
            pstmt.setString(3, p.getAddress());
            pstmt.setString(4, p.getUsername());
            pstmt.setString(5, p.getManager());
            pstmt.setString(6, p.getHobby());
            pstmt.setInt(7, p.getBirthYear()); // Correction ici : setInt
            pstmt.setFloat(8, p.getSalary());
            pstmt.setFloat(9, p.getBonus());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
                System.out.println("AJOUT REUSSI !");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (addProgrammer) : " + e.getMessage());
        }
        return generatedId;
    }

    @Override
    public void updateSalary(int programmerId, float newSalary) {
        String query = "UPDATE programmers SET salary = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setFloat(1, newSalary);
            pstmt.setInt(2, programmerId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("MODIFICATION REUSSIE !");
            } else {
                System.out.println("Modification KO : Programmeur introuvable.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (updateSalary) : " + e.getMessage());
        }
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";

        try (Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("state")));
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (getAllProjects) : " + e.getMessage());
        }
        return projects;
    }

    @Override
    public List<Programmer> getProgrammersByProject(int projectId) {
        List<Programmer> programmers = new ArrayList<>();
        String query = "SELECT p.* FROM programmers p " +
                "JOIN programmer_project pp ON p.id = pp.programmer_id " +
                "WHERE pp.project_id = ?";

        try (Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    programmers.add(mapResultSetToProgrammer(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (getProgrammersByProject) : " + e.getMessage());
        }
        return programmers;
    }

    @Override
    public int addProject(Project project) {
        String query = "INSERT INTO projects (name, start_date, end_date, state) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, project.getName());

            java.util.Date utilStart = project.getStartDate();
            if (utilStart == null) {
                throw new IllegalArgumentException("startDate cannot be null");
            }
            pstmt.setDate(2, new java.sql.Date(utilStart.getTime()));

            java.util.Date utilEnd = project.getEndDate();
            if (utilEnd != null) {
                pstmt.setDate(3, new java.sql.Date(utilEnd.getTime()));
            } else {
                pstmt.setNull(3, Types.DATE);
            }

            pstmt.setString(4, project.getState());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) generatedId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (addProject) : " + e.getMessage());
        }

        return generatedId;
    }


    @Override
    public void deleteProject(int id) {
        String deleteLinks = "DELETE FROM programmer_project WHERE project_id = ?";
        String deleteProject = "DELETE FROM projects WHERE id = ?";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(deleteLinks)) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(deleteProject)) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erreur SQL (deleteProject) : " + e.getMessage());
        }
    }

    @Override
    public void addProgrammerToProject(int programmerId, int projectId) {
        String query = "INSERT INTO programmer_project (programmer_id, project_id) VALUES (?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, programmerId);
            pstmt.setInt(2, projectId);
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Link already exists or invalid FK (addProgrammerToProject): " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur SQL (addProgrammerToProject) : " + e.getMessage());
        }
    }

    @Override
    public void removeProgrammerFromProject(int programmerId, int projectId) {
        String query = "DELETE FROM programmer_project WHERE programmer_id = ? AND project_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, programmerId);
            pstmt.setInt(2, projectId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur SQL (removeProgrammerFromProject) : " + e.getMessage());
        }
    }

    /**
     * Helper pour éviter la duplication de code lors de la lecture d'un programmeur
     */
    private Programmer mapResultSetToProgrammer(ResultSet rs) throws SQLException {
        return new Programmer(
                rs.getInt("id"),
                rs.getString("name"), // lastName
                rs.getString("first_name"), // firstName
                rs.getString("address"),
                rs.getString("username"),
                rs.getString("manager"), // String
                rs.getString("hobby"),
                rs.getInt("birth_year"), // INT
                rs.getFloat("salary"),
                rs.getFloat("bonus"));
    }


}