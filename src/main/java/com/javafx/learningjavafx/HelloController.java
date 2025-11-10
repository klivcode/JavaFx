package com.javafx.learningjavafx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class HelloController {

    // ==== Top bar ====
    @FXML private ImageView logo;
    @FXML private Label appTitle;
    @FXML private Label userLabel;
    @FXML private Button profileBtn;
    @FXML private Button logoutBtn;

    // ==== Sidebar ====
    @FXML private Button dashboardBtn;
    @FXML private Button booksBtn;
    @FXML private Button studentsBtn;
    @FXML private Button borrowBtn;
    @FXML private Button reportsBtn;
    @FXML private Button settingsBtn;

    // ==== Stats cards ====
    @FXML private Label totalBooksLabel;
    @FXML private ProgressBar booksProgress;
    @FXML private Label membersLabel;
    @FXML private ProgressBar membersProgress;
    @FXML private Label borrowedLabel;
    @FXML private ProgressBar borrowedProgress;
    @FXML private Label overdueLabel;
    @FXML private ProgressBar overdueProgress;

    // ==== Search section ====
    @FXML private TextField searchField;
    @FXML private Button searchBtn;
    @FXML private ComboBox<String> filterCombo;
    @FXML private Button addBookBtn;

    // ==== Table ====
    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, String> colISBN;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, String> colStatus;
    @FXML private TableColumn<Book, String> colDueDate;

    @FXML private Button prevPageBtn;
    @FXML private Label pageLabel;
    @FXML private Button nextPageBtn;
    @FXML private Button exportBtn;

    // ==== Right panel ====
    @FXML private Label detailTitle;
    @FXML private Label detailAuthor;
    @FXML private Label detailISBN;
    @FXML private Label detailStatus;
    @FXML private Button editBookBtn;

    @FXML private TextField borrowMemberField;
    @FXML private Spinner<Integer> borrowDaysSpinner;
    @FXML private Button confirmBorrowBtn;

    @FXML private LineChart<Number, Number> trendChart;

    @FXML private ListView<String> activityList;

    // ==== Bottom form ====
    @FXML private TextField newTitleField;
    @FXML private TextField newAuthorField;
    @FXML private TextField newISBNField;
    @FXML private Spinner<Integer> copiesSpinner;
    @FXML private Button addNewBookBtn;

    // ==== Bottom bar ====
    @FXML private Label statusLabel;

    // ==== Internal data ====
    private int currentPage = 1;
    private int totalPages = 1;

    @FXML
    public void initialize() {
        // Initialize combo boxes
        filterCombo.setItems(FXCollections.observableArrayList("All", "Available", "Borrowed", "Overdue"));
        filterCombo.getSelectionModel().selectFirst();

        // Initialize table columns
        colISBN.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("author"));
        colStatus.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));
        colDueDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("dueDate"));

        // Sample data
        booksTable.setItems(FXCollections.observableArrayList(
                new Book("978-0134685991", "Effective Java", "Joshua Bloch", "Available", "-"),
                new Book("978-0596009205", "Head First Java", "Kathy Sierra", "Borrowed", "2025-11-12"),
                new Book("978-1617294945", "Spring in Action", "Craig Walls", "Available", "-")
        ));

        // Initialize chart
        if (trendChart != null) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Borrow Trend");
            for (int i = 1; i <= 7; i++) {
                series.getData().add(new XYChart.Data<>(i, (int)(Math.random() * 10 + 3)));
            }
            trendChart.getData().add(series);
        }

        // Initialize other UI defaults
        booksProgress.setProgress(0.7);
        membersProgress.setProgress(0.5);
        borrowedProgress.setProgress(0.4);
        overdueProgress.setProgress(0.1);

        totalBooksLabel.setText("1234");
        membersLabel.setText("320");
        borrowedLabel.setText("120");
        overdueLabel.setText("15");

        statusLabel.setText("Dashboard loaded successfully");
    }

    // ==== Button handlers ====
    @FXML
    private void onDashboardClicked() { statusLabel.setText("Dashboard view active"); }

    @FXML
    private void onBooksClicked() { statusLabel.setText("Books section opened"); }

    @FXML
    private void onStudentsClicked() { statusLabel.setText("Students section opened"); }

    @FXML
    private void onBorrowClicked() { statusLabel.setText("Borrow / Return section opened"); }

    @FXML
    private void onReportsClicked() { statusLabel.setText("Reports section opened"); }

    @FXML
    private void onSettingsClicked() { statusLabel.setText("Settings opened"); }

    @FXML
    private void onProfileClicked() { statusLabel.setText("Profile clicked"); }

    @FXML
    private void onLogoutClicked() { statusLabel.setText("Logged out successfully"); }

    @FXML
    private void onSearchClicked() {
        String term = searchField.getText().trim();
        String filter = filterCombo.getValue();
        statusLabel.setText("Searching for \"" + term + "\" in " + filter + " books...");
    }

    @FXML
    private void onAddBookClicked() { statusLabel.setText("Add Book button clicked"); }

    @FXML
    private void onPrevPage() {
        if (currentPage > 1) currentPage--;
        updatePageLabel();
    }

    @FXML
    private void onNextPage() {
        if (currentPage < totalPages) currentPage++;
        updatePageLabel();
    }

    @FXML
    private void onExportCsv() { statusLabel.setText("Exporting table data..."); }

    @FXML
    private void onEditBookClicked() { statusLabel.setText("Edit book clicked"); }

    @FXML
    private void onConfirmBorrowClicked() {
        String memberId = borrowMemberField.getText();
        int days = borrowDaysSpinner.getValue();
        statusLabel.setText("Borrow confirmed for member " + memberId + " (" + days + " days)");
    }

    @FXML
    private void onAddNewBookClicked() {
        String title = newTitleField.getText();
        String author = newAuthorField.getText();
        String isbn = newISBNField.getText();
        int copies = copiesSpinner.getValue();
        statusLabel.setText("Added new book: " + title + " by " + author + " (" + copies + " copies)");
    }

    // ==== Helper ====
    private void updatePageLabel() {
        pageLabel.setText("Page " + currentPage + " of " + totalPages);
    }

    // ==== Inner Book model ====
    public static class Book {
        private final String isbn;
        private final String title;
        private final String author;
        private final String status;
        private final String dueDate;

        public Book(String isbn, String title, String author, String status, String dueDate) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.status = status;
            this.dueDate = dueDate;
        }

        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getStatus() { return status; }
        public String getDueDate() { return dueDate; }
    }
}
