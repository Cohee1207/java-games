package org.sillylossy.games.common.ui;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

class RulesBrowser extends JFrame {
    RulesBrowser(String content) {
        setLayout(new BorderLayout());
        setTitle("Rules");
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);
        jEditorPane.setDocument(kit.createDefaultDocument());
        jEditorPane.setText(content);
        add(new JScrollPane(jEditorPane), BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
