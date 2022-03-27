package certapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;


/**
 * @author Onur Can BAŞ
 */
public class FXMLDocumentController implements Initializable {

    public Label sendingFileLabel;
    public Button sendAction;
    public Button sendingFileButton;
    public PasswordField mailPassword;
    public TextField mailAdressText;
    public Button mailNameButton;
    public Button mailListButton;
    int height;
    int width;
    int x = 0;
    Image tempp;
    BufferedImage bufTempp,mailSendingFile;
    File f1, f2,f3,f4,f5;
    List<String> nameList = new ArrayList<>();
    List<String> mailList = new ArrayList<>();
    List<String> mailNameList = new ArrayList<>();
    private Label label;
    @FXML
    private Button tempButton;
    @FXML
    private Button listButton;
    @FXML
    private Button runButton;
    @FXML
    private Button tryButton;
    @FXML
    private ImageView imagePreview;
    @FXML
    private TextField heightText;
    @FXML
    private TextField widthText;
    @FXML
    private Label fontSizeLabel;
    @FXML
    private TextField fontSizeText;
    @FXML
    private ComboBox<String> formatCombo;
    @FXML
    private ComboBox<String> colorCombo;

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    //MAIL TAB FUNCS
    @FXML
    private void mailListButtonAction(ActionEvent actionEvent){
        FileChooser listChooser = new FileChooser();
        listChooser.getExtensionFilters().add(new ExtensionFilter("TXT", "*.txt"));
        f3 = listChooser.showOpenDialog(null);
        try {

            Scanner myReader = new Scanner(f3);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                mailList.add(data);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    @FXML
    private void mailNameListButtonAction(ActionEvent actionEvent){
        FileChooser listChooser = new FileChooser();
        listChooser.getExtensionFilters().add(new ExtensionFilter("TXT", "*.txt"));
        f4 = listChooser.showOpenDialog(null);
        try {

            Scanner myReader = new Scanner(f4);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                mailNameList.add(data);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    @FXML
    private void mailFileAction(ActionEvent actionEvent) throws IOException {
        FileChooser tempChooser = new FileChooser();
        tempChooser.getExtensionFilters().add(new ExtensionFilter("JPG", "*.jpg"));
        f5= tempChooser.showOpenDialog(null);

        if (f5 != null) {

            mailSendingFile = ImageIO.read(f5);
            sendingFileLabel.setText(f5.getName());

        } else {
            tempp = null;
        }
    }

    @FXML
    private void sendMailAction(ActionEvent actionEvent){
        String from = mailAdressText.getText();
        String password = mailPassword.getText();
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host",host);
        properties.setProperty("mail.user",from);
        properties.setProperty("mail.password",password);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,new InternetAddress("onurrcannbass@gmail.com"));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText("This is message body");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();

            DataSource source = new FileDataSource(f5);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(f5.getName());
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    //BASKI TAB FUNCS
    @FXML
    private void tempButtonAction(ActionEvent event) throws IOException {
        FileChooser tempChooser = new FileChooser();
        tempChooser.getExtensionFilters().add(new ExtensionFilter("JPG", "*.jpg"));
        f1 = tempChooser.showOpenDialog(null);

        if (f1 != null) {

            bufTempp = ImageIO.read(f1);
            tempp = new Image(f1.toURI().toString());

        } else {
            tempp = null;
        }

    }

    @FXML
    private void listButtonAction(ActionEvent event) throws IOException {
        {
            FileChooser listChooser = new FileChooser();
            listChooser.getExtensionFilters().add(new ExtensionFilter("TXT", "*.txt"));
            f2 = listChooser.showOpenDialog(null);
            try {

                Scanner myReader = new Scanner(f2);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    nameList.add(data);

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }
        }
    }

    @FXML
    private void tryButtonAction(ActionEvent event) throws IOException {
        height = Integer.parseInt(heightText.getText());
        width = Integer.parseInt(widthText.getText());
        bufTempp = ImageIO.read(f1);
        bufTempp = process(bufTempp, width, height, "@onurrcannbass", fontSizeText.getText());
        imagePreview.setImage(convertToFxImage(bufTempp));
    }

    @FXML
    private void runButtonAction(ActionEvent event) throws IOException, InterruptedException {

        height = Integer.parseInt(heightText.getText());
        width = Integer.parseInt(widthText.getText());

        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(null);

        for (String value : nameList) {
            bufTempp = ImageIO.read(f1);

            bufTempp = process(bufTempp, width, height, value, fontSizeText.getText());

            File outputFile = new File(f.getAbsolutePath() + "\\" + value + ".jpg");
            ImageIO.write(bufTempp, "jpg", outputFile);
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Sonuç");
        String s = "Başarıyla Sonuçlandı";
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();
    }

    private BufferedImage process(BufferedImage old, int x, int y, String s, String fontsize) {

        int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_RGB);
        int[] rgbs = old.getRGB(0, 0, w, h, null, 0, w);

        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, w, h, null);
        if (colorCombo.getSelectionModel().getSelectedItem().equals("Black")) {
            g2d.setPaint(Color.BLACK);
        } else {
            g2d.setPaint(Color.WHITE);
        }
        img.setRGB(0, 0, w, h, rgbs, 0, w);
        if (formatCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase("serif")) {
            g2d.setFont(new Font("Serif", Font.BOLD, Integer.parseInt(fontsize)));
        } else {
            g2d.setFont(new Font("Great Vibes", Font.PLAIN, Integer.parseInt(fontsize)));

        }
        FontMetrics fm = g2d.getFontMetrics();
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width / 2 - stringLen / 2;
        g2d.drawString(s, start + x, y);

        g2d.dispose();
        return img;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colorCombo.getItems().removeAll(colorCombo.getItems());
        colorCombo.getItems().addAll("Black", "White");
        colorCombo.getSelectionModel().select("Black");

        formatCombo.getItems().removeAll(formatCombo.getItems());
        formatCombo.getItems().addAll("Serif", "Great Vibes");
        formatCombo.getSelectionModel().select("Serif");
    }

}