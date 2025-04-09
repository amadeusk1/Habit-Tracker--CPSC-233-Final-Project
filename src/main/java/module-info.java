module ca.ucalgary.ahnaf.farhankhan.groupprojectgui1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.ucalgary.ahnaf.farhankhan.groupprojectgui1 to javafx.fxml;
    exports ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;
}