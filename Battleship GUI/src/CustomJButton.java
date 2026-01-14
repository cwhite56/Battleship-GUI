import javax.swing.JButton;

public class CustomJButton extends JButton {
        boolean selected;

        public boolean getSelected () {
            return selected;
        }

        public void setSelected (boolean value) {
            this.selected = value;
        }
    }