/*
 * GameWindow.java
 *
 * Created on 10.03.2011, 21:06:52
 */
package info.hska.erstiduell.view;

import info.hska.erstiduell.Game;
import info.hska.erstiduell.Team;
import info.hska.erstiduell.questions.Answer;
import info.hska.erstiduell.questions.Question;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

/**
 *
 * @author timroes
 */
public class GameWindow extends javax.swing.JFrame implements Observer {

    private Game game;
    private JLabel[] points;
    private JLabel[] teams;
    private String myAsterisk;

    /**
     * Creates new GameWindow
     *
     * @param game
     */
    public GameWindow(Game game) {
        this.game = game;
        initComponents();
        //this.setSize(1366, 768);
        //this.setSize(1024, 768);
        this.game.getDevice().setFullScreenWindow(this);
        this.points = new JLabel[]{points1, points2, points3, points4};
        this.teams = new JLabel[]{teamName1, teamName2, teamName3, teamName4};
        for (int i = 0; i < teams.length; i++) {
            game.getTeams().get(i).addObserver(new TeamObserver());
        }
    }

    public void update(Observable Game, Object o) {
        this.game = (Game) o;
        redraw();
    }

    private class TeamObserver implements Observer {

        public void update(Observable Team, Object arg) {
            redraw();
        }

    }

    public void setBGColor(Color color) {
        getContentPane().setBackground(color);
        points1.setForeground(color);
        points2.setForeground(color);
        if (points3 != null) {
            points3.setForeground(color);
            teamName3.setForeground(color);
        }
        if (points4 != null) {
            points4.setForeground(color);
            teamName4.setForeground(color);
        }
        content.setBackground(color);
        timer.setForeground(color);
    }

    public void setFGColor(Color color) {
        setForeground(color);
        winnerLabel.setForeground(color);
        content.setForeground(color);
        jPanel1.setBackground(color);
        timerPanel.setBackground(color);
    }

    public void showBuzzer(int team) {
        if (team > -1) {
            timer.setText("<html><center>" + game.getTeams().get(team).getName()
                    + "</center></html>");
        }
        timerPanel.setVisible(true);
    }

    public void showTimer(String text) {
        timer.setText("<html><center>"
                + text
                + "</center></html>");
        timerPanel.setVisible(true);
    }

    public void redraw() {
        timerPanel.setVisible(false);
        timer.setText("");
        drawPlayerNames();

        if (game.isFinished()) {
            drawWinner();
        } else {
            drawContent();
        }
    }

    private void drawPlayerNames() {

        int numAnswers = (game.getCurrentQuestion() == null)
                ? 10 : game.getCurrentQuestion().getAnswers().size();
        String color;
        int triesLeft;
        for (int team = 0; team < game.getNumberOfPlayers(); team++) {
            if (game.getCurrentQuestion() != null) {
                myAsterisk = "<p>";
                triesLeft = game.getCurrentQuestion().getAnswers().size()
                        - game.getTeams().get(team).getPenalty();
                for (int j = 0; j < triesLeft; j++) {
                    myAsterisk = myAsterisk + "*";
                }
                myAsterisk += "</p>";
            } else {
                myAsterisk = "";
                triesLeft = -1;
            }
            switch (triesLeft) {
                case 0:
                    color = "red";
                    break;
                case 1:
                    color = "orange";
                    break;
                default:
                    color = "black";
            }
            if (game.getCurrentTeam() == team) {
                color = "blue";
            }
            if (game.getTeams().get(team).getTemporarilyBlocked()) {
                color = "green";
            }
            myAsterisk = myAsterisk + "</p>";
            teams[team].setText("<html><font color='" + color + "'>"
                    + myAsterisk
                    + game.getTeams().get(team).getName()
                    + myAsterisk
                    + "</font></html>");
            if (points[team] != null) {
                points[team].setText("<html><font color='" + color + "'>"
                        + String.valueOf(game.getTeams().get(team).getPoints())
                        + "</font></html>");
            }
        }
    }

    private void drawContent() {
        winnerLabel.setVisible(false);
        content.setVisible(true);
        StringBuilder stringBuilder = new StringBuilder("<html>");
        Question cur;
        if ((cur = game.getCurrentQuestion()) != null) {
            stringBuilder.append(cur.getQuestionText());
            stringBuilder.append("<br><br><table border=\"0\" cellspacing=\"9\" width=\"");
            stringBuilder.append(content.getWidth());
            stringBuilder.append("\">");

            for (Answer answer : cur.getAnswers()) {
                stringBuilder.append("<tr><td>");
                if (answer.getDone()) {
                    stringBuilder.append(answer.getAnswer());
                    stringBuilder.append("</td><td style=\"text-align: right;\">");
                    stringBuilder.append(answer.getAmount());
                } else {
                    stringBuilder.append("_____________");
                    stringBuilder.append("</td><td style=\"text-align: right;\">");
                    stringBuilder.append("--");
                }
                stringBuilder.append("</td></tr>");
            }
            stringBuilder.append("</table>");
        }
        stringBuilder.append("</html>");

        content.setText(stringBuilder.toString());
    }

    private void drawWinner() {
        content.setVisible(false);

        StringBuilder str = new StringBuilder();

        str.append("<html><center>WINNER:<br><br>");

        for (Team t : game.getWinner()) {
            str.append(t.getName());
            str.append("<br>");
        }

        str.append("</center></html>");

        winnerLabel.setText(str.toString());

        winnerLabel.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        timerPanel = new javax.swing.JPanel();
        timer = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        points1 = new javax.swing.JLabel();
        points2 = new javax.swing.JLabel();
        if (game.getNumberOfPlayers() >= 3) {
            points3 = new javax.swing.JLabel();
        }
        if (game.getNumberOfPlayers() >= 4) {
            points4 = new javax.swing.JLabel();
        }
        teamName1 = new javax.swing.JLabel();
        teamName2 = new javax.swing.JLabel();
        if (game.getNumberOfPlayers() >= 3) {
            teamName3 = new javax.swing.JLabel();
        }
        if (game.getNumberOfPlayers() >= 4) {
            teamName4 = new javax.swing.JLabel();
        }
        winnerLabel = new javax.swing.JLabel();
        content = new javax.swing.JLabel();

        //setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        timerPanel.setLayout(new java.awt.GridBagLayout());

        timer.setFont(new java.awt.Font("Liberation Mono", 1, 80)); // NOI18N
        timer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timer.setText("test");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        timerPanel.add(timer, gridBagConstraints);

        timerPanel.setVisible(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(timerPanel, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        points1.setFont(new java.awt.Font("Liberation Mono", 1, 36));
        points1.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(points1, gridBagConstraints);

        points2.setFont(new java.awt.Font("Liberation Mono", 1, 36));
        points2.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(points2, gridBagConstraints);

        if (game.getNumberOfPlayers() >= 3) {
            points3.setFont(new java.awt.Font("Liberation Mono", 1, 36));
            points3.setText("0");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1.0;
            jPanel1.add(points3, gridBagConstraints);
        }

        if (game.getNumberOfPlayers() >= 4) {
            points4.setFont(new java.awt.Font("Liberation Mono", 1, 36));
            points4.setText("0");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1.0;
            jPanel1.add(points4, gridBagConstraints);
        }

        teamName1.setFont(new java.awt.Font("DejaVu Sans", 1, 20));
        teamName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teamName1.setText("T1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        jPanel1.add(teamName1, gridBagConstraints);

        teamName2.setFont(new java.awt.Font("DejaVu Sans", 1, 20));
        teamName2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teamName2.setText("T2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        jPanel1.add(teamName2, gridBagConstraints);

        if (game.getNumberOfPlayers() >= 3) {
            teamName3.setFont(new java.awt.Font("DejaVu Sans", 1, 20));
            teamName3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            teamName3.setText("T3");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
            jPanel1.add(teamName3, gridBagConstraints);
        }

        if (game.getNumberOfPlayers() >= 4) {
            teamName4.setFont(new java.awt.Font("DejaVu Sans", 1, 20));
            teamName4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            teamName4.setText("T4");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
            jPanel1.add(teamName4, gridBagConstraints);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        getContentPane().add(jPanel1, gridBagConstraints);

        winnerLabel.setFont(new java.awt.Font("Liberation Mono", 1, 70));
        winnerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        winnerLabel.setText("winnerLabel");
        winnerLabel.setVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(winnerLabel, gridBagConstraints);

        content.setFont(new java.awt.Font("Liberation Mono", 1, 40));
        content.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 10, 10);
        getContentPane().add(content, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel content;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel points1;
    private javax.swing.JLabel points2;
    private javax.swing.JLabel points3;
    private javax.swing.JLabel points4;
    private javax.swing.JLabel teamName1;
    private javax.swing.JLabel teamName2;
    private javax.swing.JLabel teamName3;
    private javax.swing.JLabel teamName4;
    private javax.swing.JLabel timer;
    private javax.swing.JPanel timerPanel;
    private javax.swing.JLabel winnerLabel;
    // End of variables declaration//GEN-END:variables

}
